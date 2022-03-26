package persistance.repository;

import domain.Cursa;

import java.time.LocalDateTime;
import java.util.List;

public interface CursaRepository extends Repository<Integer, Cursa> {
    List<Cursa> findByDestinatie(String destinatie);
    List<Cursa> findByPlecare(LocalDateTime plecare);
    Cursa findByID(Integer id);
}
