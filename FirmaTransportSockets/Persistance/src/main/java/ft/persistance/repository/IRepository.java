package ft.persistance.repository;

import ft.domain.Entity;

public interface IRepository<ID, E extends Entity<ID>> {

    Iterable<E> findAll();
    void save(E entity);
    void update(ID id, E entity);

}
