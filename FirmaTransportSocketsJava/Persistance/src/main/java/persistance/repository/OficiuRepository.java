package persistance.repository;

import domain.Oficiu;

import java.util.List;

public interface OficiuRepository extends Repository<Integer, Oficiu> {
    List<Oficiu> findByname(String name);
}
