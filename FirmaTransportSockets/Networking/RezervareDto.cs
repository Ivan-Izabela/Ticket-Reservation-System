using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class RezervareDto
    {

        public int id { get; set; }
        public string numeClient { get; set; }
        public int nrLoc { get; set; }

        public int idCursa { get; set; }

        public RezervareDto(int id ,string numeClient, int nrLoc, int idCursa)
        {
            this.id = id;
            this.numeClient = numeClient;
            this.nrLoc = nrLoc;
            this.idCursa = idCursa;
        }

        public RezervareDto(string numeClient, int nrLoc)
        {
            this.numeClient = numeClient;
            this.nrLoc = nrLoc;
        }
    }
}
