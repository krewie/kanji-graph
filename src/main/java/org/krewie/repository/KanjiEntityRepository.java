package org.krewie.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.krewie.model.KanjiEntity;

import java.util.List;

@ApplicationScoped
public class KanjiEntityRepository implements PanacheRepository<KanjiEntity> {

    public List<KanjiEntity> findAllPending() {
        return find("sent", false).list();
    }

    public void markAsSent(List<Integer> ids) {
        update("sent = true where id in ?1", ids);
    }

    public void saveAll(List<KanjiEntity> entities) {
        persist(entities);
    }

    public void save(KanjiEntity entity) {
        persist(entity);
    }
}
