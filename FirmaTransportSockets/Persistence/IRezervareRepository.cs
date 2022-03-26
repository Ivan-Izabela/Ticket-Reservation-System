using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;

namespace Persistence
{
    public interface IRezervareRepository : IRepository<int, Rezervare>
    {
        IEnumerable<Rezervare> findByIdCursa(int id);
        int getNewId();
    }
}
