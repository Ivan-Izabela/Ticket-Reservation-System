using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model
{
    public class Cursa : Entity<int>
    {
        public string destinatie { get; set; }
        public DateTime plecare { get; set; }
        public int locuriDisponibile { get; set; }

        public Cursa(int id, string destinatie, DateTime plecare, int locuriDisponibile)
        {
            this.id = id;
            this.destinatie = destinatie;
            this.plecare = plecare;
            this.locuriDisponibile = locuriDisponibile;
        }

        public Cursa(int id)
        {
            this.id = id;

        }

        public Cursa(string destinatie)
        {
            this.destinatie = destinatie;
        }

        public override string ToString()
        {
            return id + " " + destinatie + " " + plecare + " " + locuriDisponibile;
        }
    }
}
