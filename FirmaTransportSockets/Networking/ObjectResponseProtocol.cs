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

	public interface Response
	{
	}

	[Serializable]
	public class OkResponse : Response
	{

	}

	[Serializable]
	public class ErrorResponse : Response
	{
		private string message;

		public ErrorResponse(string message)
		{
			this.message = message;
		}

		public virtual string Message
		{
			get
			{
				return message;
			}
		}
	}

	public interface UpdateResponse : Response
	{
	}


    [Serializable]
    public class GetFiltredCursaResponse : Response
    {
        public CursaDto[] curs { get; set; }

        public GetFiltredCursaResponse(CursaDto[] curs)
        {
            this.curs = curs;
        }
    }


    [Serializable]
    public class SaveRezervareResponse : UpdateResponse
    {
        public RezervareDto rezervare { get; set; }

        public SaveRezervareResponse(RezervareDto rezervare)
        {
            this.rezervare = rezervare;
        }
    }

    [Serializable]
    public class GetAllCursaResponse : Response
    {
        public CursaDto[] curs { get; set; }

        public GetAllCursaResponse(CursaDto[] curs)
        {
            this.curs = curs;
        }
    }


    [Serializable]
    public class GetRezervareDTOResponse : Response
    {
        public RezervareDto[] rezervare { get; set; }

        public GetRezervareDTOResponse(RezervareDto[] rezervare)
        {
            this.rezervare = rezervare;
        }
    }


    [Serializable]
    public class SavedRezervareResponse : Response
    {
        public RezervareDto rezervare { get; set; }

        public SavedRezervareResponse(RezervareDto rezervare)
        {
            this.rezervare = rezervare;
        }
    }




}
