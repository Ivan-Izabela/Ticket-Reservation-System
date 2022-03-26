using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FirmaTransport.domain
{
    class RezervareDTO
    {
        public string numeClient { get; set; }
        public int nrLo { get; set; }

        public RezervareDTO(string numeClient, int nrLo)
        {
            this.numeClient = numeClient;
            this.nrLo = nrLo;
        }
    }
}
