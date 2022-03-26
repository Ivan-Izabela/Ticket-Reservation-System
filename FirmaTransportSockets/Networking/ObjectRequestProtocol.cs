using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.protocol
{
    using OficiuDto = Networking.dto.OficiuDto;
    using CursaDto = Networking.dto.CursaDto;
    using RezervareDto = Networking.dto.RezervareDto;

    public interface Request
    {

    }

    [Serializable]
    public class LoginRequest : Request
    {
        public OficiuDto oficiu { get; set; }

        public LoginRequest(OficiuDto oficiu)
        {
            this.oficiu = oficiu;
        }



    }

    [Serializable]
    public class LogoutRequest : Request
    {
        public OficiuDto oficiu { get; set; }

        public LogoutRequest(OficiuDto oficiu)
        {
            this.oficiu = oficiu;
        }



    }


    [Serializable]
    public class GetFiltredCursaRequest : Request
    {
        public string destinatie;


        public GetFiltredCursaRequest(string destinatie)
        {
            this.destinatie = destinatie;
        }
    }


    [Serializable]
    public class SaveRezervareRequest : Request
    {
        public RezervareDto rezervare { get; set; }

        public SaveRezervareRequest(RezervareDto rezervare)
        {
            this.rezervare = rezervare;
        }
    }

    [Serializable]
    public class GetAllCursaRequest : Request
    {
        //public CursaDto curs { get; set; }

        public GetAllCursaRequest()
        {
            
        }
    }


    [Serializable]
    public class GetRezervareDTORequest : Request
    {
        public int id;

        public GetRezervareDTORequest(int id)
        {
            this.id = id;
        }
    }





}
