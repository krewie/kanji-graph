package org.krewie.worker;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.krewie.forwarder.kanji.Forwarder;
import org.krewie.forwarder.kanji.KafkaForwarder;
import org.krewie.forwarder.kanji.RestForwarder;
import org.krewie.model.KanjiEntity;
import org.krewie.repository.KanjiEntityRepository;

import java.util.List;

@ApplicationScoped
public class DispatchWorker {

    private static final Logger logger = Logger.getLogger(DispatchWorker.class);

    @ConfigProperty(name = "kanji.forwarder.type", defaultValue = "rest")
    String forwarderType;

    @ConfigProperty(name = "kanji.forwarder.enabled", defaultValue = "false")
    boolean forwardingEnabled;

    @Inject
    KanjiEntityRepository repository;

    @Inject
    KafkaForwarder kafkaForwarder;

    @Inject
    RestForwarder restForwarder;

    @Scheduled(every = "5m")
    void logMemoryUsage() {
        long freeMem = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        long totalMem = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long maxMem = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        logger.infof("Heap usage — Free: %dMB, Total: %dMB, Max: %dMB", freeMem, totalMem, maxMem);
    }

    @Scheduled(every = "30s")
    public void sendPendingKanji() {
        if (!forwardingEnabled) {
            logger.debug("Fowarder is not enabled...");
            return;
        }

        List<KanjiEntity> pending = repository.findAllPending();
        if (pending.isEmpty()) {
            logger.debug("no pending kanjis to send...");
            return;
        }

        logger.infof("Forwarding %d kanji using forwarder %s", pending.size(), forwarderType);

        getForwarder().forward(pending);
    }

    private Forwarder getForwarder() {
        return switch (forwarderType) {
            case "rest" -> restForwarder;
            case "kafka" -> kafkaForwarder;
            default -> throw new IllegalArgumentException("unknown forwarder: " + forwarderType);
        };
    }

}
