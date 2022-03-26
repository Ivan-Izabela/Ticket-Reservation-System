using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FirmaTransport.repository;
using FirmaTransport.domain;
using System.Runtime;

namespace FirmaTransport.service
{
    class Service
    {
        private IOficiuRemository oficiuRepository;
        private ICursaRepository cursaRepository;
        private IRezervareRepository rezervareRepository;

        public Service(IOficiuRemository oficiuRepository, ICursaRepository cursaRepository, IRezervareRepository rezervareRepository)
        {
            this.oficiuRepository = oficiuRepository;
            this.cursaRepository = cursaRepository;
            this.rezervareRepository = rezervareRepository;
        }

        public bool Login(string nume, string parola)
        {
            Oficiu oficiu = oficiuRepository.findByName(nume);
            return oficiu != null && oficiu.parola == parola;
        }

        public IEnumerable<Cursa> findAllCursa()
        {
            return cursaRepository.findAll();
        }

        public IEnumerable<CursaDTO> findAllCursaDTO()
        {
            List<CursaDTO> curse = new List<CursaDTO>();
            foreach(Cursa c in cursaRepository.findAll())
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
            foreach(Rezervare r in rezervareRepository.findByIdCursa(c.id))
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

        public void saveRezervare(string numeClient, int nrLocuri, int idCursa)
        {
            Cursa cursa = cursaRepository.findOne(idCursa);
            if (cursa.locuriDisponibile == 0 || cursa.locuriDisponibile - nrLocuri < 0)
                throw new Exception("Locuri insuficiente");
            if (numeClient.Length < 2)
                throw new Exception("Nume invalid");
            if (nrLocuri < 0)
                throw new Exception("Numarul de locuri invalid");

            Rezervare rezervare = new Rezervare(rezervareRepository.getNewId(), numeClient,nrLocuri, idCursa);
            cursa.locuriDisponibile = cursa.locuriDisponibile - nrLocuri;
            rezervareRepository.save(rezervare);
            cursaRepository.update(idCursa, cursa);

        }

    }
}
