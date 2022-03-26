package ft.persistance.repository;

import ft.domain.Rezervare;

import java.util.List;

public interface RezervareReposytory extends IRepository<Integer, Rezervare> {
    List<Rezervare> findByNumeClient(String nume);
}
