using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto

{
    [Serializable]
    public class CursaDto
    {
        public int id { get; set; }
        public string destinatie { get; set; }
        public DateTime plecare { get; set; }
        public int locuriDisponibile { get; set; }

        public CursaDto(int id, string destinatie, DateTime plecare, int locuriDisponibile)
        {
            this.id = id;
            this.destinatie = destinatie;
            this.plecare = plecare;
            this.locuriDisponibile = locuriDisponibile;
        }
        /*
        public CursaDto(string destinatie, DateTime plecare, int locuriDisponibile)
        {
            this.destinatie = destinatie;
            this.plecare = plecare;
            this.locuriDisponibile = locuriDisponibile;
        }
        */

    }
}
