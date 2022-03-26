using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;
using Model;
using Persistence;
using Services;


namespace Server
{
    public class Service : IService
    {

        private IOficiuRemository oficiuRepository;
        private ICursaRepository cursaRepository;
        private IRezervareRepository rezervareRepository;
        private ConcurrentDictionary<String, IFTObserver> loggedClients;
        private static ILog logger = LogManager.GetLogger(typeof(Service));

        public Service(IOficiuRemository oficiuRepository, ICursaRepository cursaRepository, IRezervareRepository rezervareRepository)
        {
            this.oficiuRepository = oficiuRepository;
            this.cursaRepository = cursaRepository;
            this.rezervareRepository = rezervareRepository;
            loggedClients = new ConcurrentDictionary<String, IFTObserver>();
            logger.Debug("Created new ConcurrentService instance");
        }

        public Oficiu Login(string nume, string parola, IFTObserver client)
        {
            Oficiu oficiu = oficiuRepository.findByName(nume);
            if (oficiu != null && oficiu.parola == parola)
            {
                if (loggedClients.ContainsKey(nume))                    
                    throw new FTException("User already logged in.");
                loggedClients[nume] = client;
                return oficiu;

            }
            else
            {
                throw new FTException("Authentication failed.");
            }
        }


        
        public IEnumerable<Cursa> findAllCursa()
        {
            return cursaRepository.findAll();
        }

        public IEnumerable<CursaDTO> findAllCursaDTO()
        {
            List<CursaDTO> curse = new List<CursaDTO>();
            foreach (Cursa c in cursaRepository.findAll())
            {
                curse.Add(new CursaDTO(c.destinatie, c.plecare, c.locuriDisponibile));

            }
            return curse;
        }

        public IEnumerable<Cursa> findCursaDTOByDestinatie(string destinatie)
        {
            List<Cursa> curse = new List<Cursa>();
            foreach (Cursa c in cursaRepository.findAll())
            {
                if (c.destinatie.StartsWith(destinatie))
                {
                    curse.Add(c);
                }

            }
            return curse;
            //cursaRepository.findByDestinatie(destinatie);
        }

        public IEnumerable<RezervareDTO> findRezervareDTO(Cursa c)
        {
            List<RezervareDTO> rezervari = new List<RezervareDTO>();
            int nrLoc = 1;
            foreach (Rezervare r in rezervareRepository.findByIdCursa(c.id))
            {
                int l = r.nrLocuri;
                while (l > 0)
                {
                    rezervari.Add(new RezervareDTO(r.numeClient, nrLoc));
                    nrLoc++;
                    l--;
                }

            }
            while (nrLoc <= 18)
            {
                rezervari.Add(new RezervareDTO("-", nrLoc));
                nrLoc++;
            }



            return rezervari;
        }

        public Rezervare saveRezervare(string numeClient, int nrLocuri, int idCursa)
        {
            Cursa cursa = cursaRepository.findOne(idCursa);
            if (cursa.locuriDisponibile == 0 || cursa.locuriDisponibile - nrLocuri < 0)
                throw new FTException("Locuri insuficiente");
            if (numeClient.Length < 2)
                throw new FTException("Nume invalid");
            if (nrLocuri < 0)
                throw new FTException("Numarul de locuri invalid");

            Rezervare rezervare = new Rezervare(rezervareRepository.getNewId(), numeClient, nrLocuri, idCursa);
            cursa.locuriDisponibile = cursa.locuriDisponibile - nrLocuri;
            rezervareRepository.save(rezervare);
            cursaRepository.update(idCursa, cursa);
            notifyUpdateCursa(rezervare);
            return rezervare;

        }

        private void notifyUpdateCursa(Rezervare bilet)
        {
            loggedClients.ToList().ForEach(cl =>
            {
                cl.Value.saveDRezervare(bilet);
            });
        }

        public void Logout(string nume, IFTObserver client)
        {
            IFTObserver returned;
            if(loggedClients.TryRemove(nume, out returned))
            {
                logger.Debug("User " + nume + " successfully logged out");
            }
            else
            {
                logger.Debug("User " + nume + " was already logged out");
            }
            
        }
    }
}
