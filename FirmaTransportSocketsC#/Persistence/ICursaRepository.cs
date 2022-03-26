using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;


namespace Persistence
{
    public interface ICursaRepository : IRepository<int, Cursa>
    {
        IList<Cursa> findByDestinatie(string destinatie);
        void update(int id, Cursa cursa);
    }
}
