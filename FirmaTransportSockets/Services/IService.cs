using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;
using Persistence;

namespace Services
{
    public interface IService
    {
        Oficiu Login(string nume, string parola,IFTObserver client);
        void Logout(string nume, IFTObserver client);
        IEnumerable<Cursa> findAllCursa();
        IEnumerable<CursaDTO> findAllCursaDTO();
        IEnumerable<Cursa> findCursaDTOByDestinatie(string destinatie);
        IEnumerable<RezervareDTO> findRezervareDTO(Cursa c);
        Rezervare saveRezervare(string numeClient, int nrLocuri, int idCursa);
    }
}
