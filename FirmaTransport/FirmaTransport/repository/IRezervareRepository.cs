using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FirmaTransport.domain;

namespace FirmaTransport.repository
{
    interface IRezervareRepository : IRepository<int, Rezervare>
    {
        IEnumerable<Rezervare> findByIdCursa(int id);
        int getNewId();
    }
}
