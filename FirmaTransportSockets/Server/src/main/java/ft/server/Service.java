package ft.server;

import ft.domain.Cursa;
import ft.domain.Oficiu;
import ft.domain.Rezervare;
import ft.domain.RezervareDTO;
import ft.persistance.repository.CursaRepository;
import ft.persistance.repository.OficiuRepository;
import ft.persistance.repository.RezervareReposytory;
import ft.services.FTException;
import ft.services.IObserver;
import ft.services.IService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service implements IService {
    private OficiuRepository oficiuRepository;
    private CursaRepository cursaRepository;
    private RezervareReposytory rezervareReposytory;
    private Map<String, IObserver> loggedOffices;


    public Service(OficiuRepository oficiu, CursaRepository cursa, RezervareReposytory rezervare) {
        this.oficiuRepository = oficiu;
        this.cursaRepository = cursa;
        this.rezervareReposytory = rezervare;
        loggedOffices=new ConcurrentHashMap<>();
    }




    @Override
    public Oficiu login(String nume, String parola, IObserver client) throws FTException {

        List<Oficiu> oficii = oficiuRepository.findByname(nume);
        if(!oficii.isEmpty()){
            for(Oficiu o : oficii){
                if (o.getParola().equals(parola)){
                    loggedOffices.put(nume,client);
                    return o;
                }
                else{
                    throw new FTException("Parola incorecta!");
                }
            }
        }
        throw new FTException("Nume invalid");

    }
    private  void notifyUpdateRezervare(Rezervare rezervare){
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        System.out.println("Updating for logged users...");
        for(IObserver agLog:loggedOffices.values())
        {
            executorService.execute(()->{
                try{
                    agLog.saveDRezervare(rezervare);
                }
                catch (FTException ex){
                    System.out.println(ex);
                }
            });
        }
        executorService.shutdown();
    }

    @Override
    public synchronized List<Cursa> findAllCurse()  throws FTException{
        return StreamSupport.stream(cursaRepository.findAll().spliterator(),false).collect(Collectors.toList());

    }

    @Override
    public synchronized List<Rezervare> findAllRezervari() throws FTException{
        return StreamSupport.stream(rezervareReposytory.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public synchronized List<RezervareDTO> findRezervareDTO(String id1) throws FTException{
        Integer id = Integer.parseInt(id1);
        List<RezervareDTO> rezervariDTO = new ArrayList<>();
        Cursa cursa = cursaRepository.findByID(id);
        System.out.println(cursa.getId()+"<!!");
        Integer nrLoc = 1;

        try {
            List<Rezervare> rezervari = findAllRezervari();
            for (Rezervare r : rezervari) {
                if (r.getIdCursa() == id) {
                    int loc = r.getNrLocuri();
                    while (loc != 0) {
                        rezervariDTO.add(new RezervareDTO(r.getNumeClient(), nrLoc));
                        nrLoc++;
                        loc--;
                    }
                }
            }
            while (nrLoc <= 18) {
                rezervariDTO.add(new RezervareDTO("-", nrLoc));
                nrLoc++;
            }
            return rezervariDTO;
        }catch (FTException e){

        }
        return null;
    }

    @Override
    public synchronized List<Cursa> findByDestinatie(String destinatie) throws FTException {
        return StreamSupport.stream(cursaRepository.findByDestinatie(destinatie).spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public synchronized Rezervare saveRezervare(Rezervare rezervare) throws FTException {
        System.out.println(rezervare.getIdCursa()+"??");
        Integer id=rezervare.getIdCursa();
        System.out.println(id+"??");
        Cursa cursa=cursaRepository.findByID(id);

        if(cursa==null){
            throw new FTException("cursa nu exista");
        }
        if(rezervare.getNrLocuri()<0)
            throw new FTException("Nrumarul de locuri trebuie sa fi un nr natural");
        if(cursa.getLocuriDisponibile()-rezervare.getNrLocuri()<0)
            throw new FTException("Locuri insuficiente");
        if(rezervare.getNumeClient().length()<2)
            throw new FTException("Nume client invalid");
        rezervareReposytory.save(rezervare);
        Integer locuri= cursa.getLocuriDisponibile()- rezervare.getNrLocuri();

        cursa.setLocuriDisponibile(locuri);
        System.out.println(cursa);
        cursaRepository.update(rezervare.getIdCursa(), cursa);
        notifyUpdateRezervare(rezervare);
        return rezervare;
        //Cursa cursa1=cursaRepository.findByID(rezervare.getIdCursa());
        //System.out.println(cursa1.getLocuriDisponibile().toString());

    }

    @Override
    public void logout(String nume, IObserver client) throws FTException {
        loggedOffices.remove(nume,client);
    }
}
