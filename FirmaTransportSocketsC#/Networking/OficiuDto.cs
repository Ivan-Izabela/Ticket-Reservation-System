using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class OficiuDto
    {
        public int id { get; set; }
        public string nume { get; set; }
        public string parola { get; set; }

        public OficiuDto(int id,string nume, string parola)
        {
            this.id = id;
            this.nume = nume;
            this.parola = parola;
        }
        public OficiuDto(string nume, string parola)
        {
            this.nume = nume;
            this.parola = parola;
        }
    }
}
