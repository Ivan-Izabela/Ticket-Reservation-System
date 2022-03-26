using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FirmaTransport.domain;


namespace FirmaTransport.repository
{
    interface IOficiuRemository : IRepository<int, Oficiu>
    {
        Oficiu findByName(string nume);
    }
}
