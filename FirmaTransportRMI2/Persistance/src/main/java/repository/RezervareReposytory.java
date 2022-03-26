package repository;

import domain.Rezervare;

import java.util.List;

public interface RezervareReposytory extends Repository<Integer, Rezervare> {
    List<Rezervare> findByNumeClient(String nume);
}
