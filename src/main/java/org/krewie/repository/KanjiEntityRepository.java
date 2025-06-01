package org.krewie.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.krewie.model.KanjiEntity;

import java.util.List;

@ApplicationScoped
public class KanjiEntityRepository implements PanacheRepository<KanjiEntity> {

    public List<KanjiEntity> findAllPending() {
        return find("sent", false).list();
    }

    @Transactional
    public void markAsSent(List<KanjiEntity> pending) {
        try {
            update("sent = true where id in ?1",
                    pending.stream().map(k -> k.id).toList()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(List<KanjiEntity> entities) {
        persist(entities);
    }

    public void save(KanjiEntity entity) {
        persist(entity);
    }
}
