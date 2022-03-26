package ft.persistance.repository;

import ft.domain.Oficiu;

import java.util.List;

public interface OficiuRepository extends IRepository<Integer, Oficiu> {
    List<Oficiu> findByname(String name);
}
