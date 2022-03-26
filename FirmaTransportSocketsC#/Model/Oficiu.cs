using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model
{
    public class Oficiu : Entity<int>
    {
        public string nume { get; set; }
        public string parola { get; set; }

        public Oficiu(int id, string nume, string parola)
        {
            this.id = id;
            this.nume = nume;
            this.parola = parola;
        }

        public Oficiu(string nume, string parola)
        {
            this.nume = nume;
            this.parola = parola;
        }

        public override string ToString()
        {
            return id + " " + nume +" "+ parola ;
        }
    }
}
