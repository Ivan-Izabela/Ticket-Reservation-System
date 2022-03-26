using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FirmaTransport.domain
{
    class Rezervare : Entity<int>
    {
        public string numeClient { get; set; }
        public int nrLocuri { get; set; }
        public int idCursa { get; set; }

        public Rezervare(int id, string numeClient, int nrLocuri, int idCursa)
        {
            this.id = id;
            this.numeClient = numeClient;
            this.nrLocuri = nrLocuri;
            this.idCursa = idCursa;
        }

        public override string ToString()
        {
            return id + " " + numeClient + " " + nrLocuri+" "+ idCursa;
        }
    }
}
