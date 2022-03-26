package persistance.repository;

import domain.Entity;

public interface Repository<ID, E extends Entity<ID>> {

    Iterable<E> findAll();
    void save(E entity);
    void update(ID id, E entity);

}
