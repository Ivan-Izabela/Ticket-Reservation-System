using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;


namespace Persistence
{
    public interface IOficiuRemository : IRepository<int, Oficiu>
    {
        Oficiu findByName(string nume);
    }
}
