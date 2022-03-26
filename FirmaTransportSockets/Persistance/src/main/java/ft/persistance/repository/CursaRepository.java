package ft.persistance.repository;

import ft.domain.Cursa;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;



@Repository
public interface CursaRepository extends IRepository<Integer, Cursa> {
    List<Cursa> findByDestinatie(String destinatie);
    List<Cursa> findByPlecare(LocalDateTime plecare);
    Cursa findByID(Integer id);
    void delete(Integer id);
}
