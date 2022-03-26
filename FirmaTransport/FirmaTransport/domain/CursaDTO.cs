using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FirmaTransport.domain
{
    class CursaDTO
    {
        public string destinatie { get; set; }
        public DateTime plecare { get; set; }
        public int locuriDisponibile { get; set; }

        public CursaDTO(string destinatie, DateTime plecare, int locuriDisponibile)
        {
            this.destinatie = destinatie;
            this.plecare = plecare;
            this.locuriDisponibile = locuriDisponibile;
        }

        public override string ToString()
        {
            return destinatie+ " " + plecare + " "+ locuriDisponibile;
        }
    }
}
