using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Services;
using Model;


namespace Client
{
    public class FirmaTranspotCtrl : IFTObserver
    {
        public event EventHandler<FTUserEventArgs> updateEvent;
        private readonly IService service;
        private string nume;

        public FirmaTranspotCtrl(IService service)
        {
            this.service = service;
            
        }

        public void login(string nume, string parola)
        {
            service.Login(nume, parola, this);
            Console.WriteLine("Login succeeded ....");
            this.nume = nume;
            Console.WriteLine("Current user {0}", nume);
        }

        public void logout()
        {
            Console.WriteLine("Ctrl logout");
            service.Logout(nume, this);
            nume = null;
        }

        protected virtual void OnUserEvent(FTUserEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }



        public IEnumerable<Cursa> findAllCurse()
        {
            IEnumerable<Cursa> curse = service.findAllCursa();
            return curse;
        }

        public IEnumerable<Cursa> findByDestinatie(string destinatie)
        {
            IEnumerable<Cursa> curse = service.findCursaDTOByDestinatie(destinatie);
            return curse;
        }

        public IEnumerable<RezervareDTO> findRezervareDTO(Cursa c)
        {
            IEnumerable<RezervareDTO> rezervareDTOs = service.findRezervareDTO(c);
            return rezervareDTOs;
        }

        public Rezervare saveRezervare(string nume,int nr,int id)
        {

            return service.saveRezervare(nume, nr, id);

        }


        public void saveDRezervare(Rezervare rezervare)
        {
            
            FTUserEventArgs userArgs = new FTUserEventArgs(FTUserEvent.saveDRezervare, rezervare);
            Console.WriteLine("Saved rezervare");
            OnUserEvent(userArgs);
        }
    }
}
