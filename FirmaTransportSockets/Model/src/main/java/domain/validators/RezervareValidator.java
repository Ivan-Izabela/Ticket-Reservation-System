package domain.validators;

import domain.Rezervare;

public class RezervareValidator implements Validator<Rezervare> {



    @Override
    public void validate(Rezervare entity) throws ValidationException {
        if(entity.getNrLocuri()<0) throw  new ValidationException("Numarul de locuri trebuie sa fie numar natural");
        if(entity.getNumeClient().length()<1) throw new ValidationException("Nume invalid");

    }
}
