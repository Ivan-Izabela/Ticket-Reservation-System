using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;
using Ft.Protocol;
using proto = Ft.Protocol;

namespace Protobuff3
{
    public class ProtoUtils
    {
        public static Request createLoginRequest(Model.Oficiu oficiu)
        {
            proto.Oficiu oficiuDTO = new proto.Oficiu { Nume = oficiu.nume, Parola = oficiu.parola };
            Request request = new Request { Type = Request.Types.Type.Login, Oficiu = oficiuDTO };
            return request;

        }

        public static Request createLogoutRequest(Model.Oficiu oficiu)
        {
            proto.Oficiu oficiuDTO = new proto.Oficiu { Nume = oficiu.nume, Parola = oficiu.parola };
            Request request = new Request { Type = Request.Types.Type.Logout, Oficiu = oficiuDTO };
            return request;
        }

        public static Request createGetFiltredCurseRequest(Model.Cursa cursa)
        {
            proto.Cursa cursaDTO = new proto.Cursa { Id = cursa.id.ToString(), Destinatie = cursa.destinatie, Plecare = cursa.plecare.ToString(), LocuriDisponibile = cursa.locuriDisponibile.ToString() };
            Request request = new Request { Type = Request.Types.Type.GetFilteredCurse, Cursa = cursaDTO };
            return request;
        }

        public static Request createSaveRezervareRequest(Model.Rezervare rezervare)
        {
            proto.Rezervare rezervareDTO= new proto.Rezervare { NumeClient = rezervare.numeClient, NrLocuri = rezervare.nrLocuri.ToString(), IdCursa = rezervare.idCursa.ToString() };
            Request request = new Request { Type = Request.Types.Type.SaveRezervare, Rezervare = rezervareDTO };
            return request;
        }

        public static Request createGetAllCurseRequest()
        {
            Request request = new Request { Type = Request.Types.Type.GetAllCurse };
            return request;

        }

        public static Request createGetRezervareDtoRequest(Model.Rezervare rezervare)
        {
            proto.Rezervare rezervareDTO = new proto.Rezervare { NumeClient = rezervare.numeClient, NrLocuri = rezervare.nrLocuri.ToString(), IdCursa = rezervare.idCursa.ToString() };
            Request request = new Request { Type = Request.Types.Type.GetRezervareDto, Rezervare = rezervareDTO };
            return request;
        }

        public static Response createOKResponse()
        {
            Response response = new Response { Type = Response.Types.Type.Ok };
            return response;
        }

        public static Response createErrorResponse(string text)
        {
            Response response = new Response { Type = Response.Types.Type.Error, Error = text };
            return response;
        }

        public static Response createGetFiltredCurseResponse(Model.Cursa[] curse)
        {
            Response response = new Response { Type = Response.Types.Type.GetFilteredCurse };

            foreach(Model.Cursa cursa in curse)
            {
                proto.Cursa cursaDto = new proto.Cursa { Id = cursa.id.ToString(), Destinatie = cursa.destinatie, Plecare = cursa.plecare.ToString(), LocuriDisponibile = cursa.locuriDisponibile.ToString() };
                response.Curse.Add(cursaDto);
            }
            return response;
        }

        public static Response createSaveRezervareResponse(Model.Rezervare rezervare)
        {
            proto.Rezervare rezervareDto = new proto.Rezervare { NumeClient = rezervare.numeClient, NrLocuri = rezervare.nrLocuri.ToString(), IdCursa = rezervare.idCursa.ToString() };
            Response response = new Response { Type = Response.Types.Type.SaveRezervare, Rezervare = rezervareDto };
            return response;
        }

        public static Response createSavedRezervareResponse(Model.Rezervare rezervare)
        {
            proto.Rezervare rezervareDto = new proto.Rezervare { NumeClient = rezervare.numeClient, NrLocuri = rezervare.nrLocuri.ToString(), IdCursa = rezervare.idCursa.ToString() };
            Response response = new Response { Type = Response.Types.Type.SavedRezervare, Rezervare = rezervareDto };
            return response;
        }

        public static Response createGetAllCurseResponse(Model.Cursa[] curse)
        {
            Response response = new Response { Type = Response.Types.Type.GetAllCurse };

            foreach (Model.Cursa cursa in curse)
            {
                proto.Cursa cursaDto = new proto.Cursa { Id = cursa.id.ToString(), Destinatie = cursa.destinatie, Plecare = cursa.plecare.ToString(), LocuriDisponibile = cursa.locuriDisponibile.ToString() };
                response.Curse.Add(cursaDto);
            }
            return response;
        }

        public static Response createGetRezervareDTOResponse(Model.RezervareDTO[] rezervariDTO)
        {
            Response response = new Response { Type = Response.Types.Type.GetRezervareDto };

            foreach(Model.RezervareDTO rezervare in rezervariDTO)
            {
                proto.RezervareDTO rezervareDTO = new proto.RezervareDTO { NumeClient = rezervare.numeClient, NrLoc = rezervare.nrLo.ToString() };
                response.RezervariDTO.Add(rezervareDTO);
            }
            return response;
        }

        public static String getError(Response response)
        {
            String errorMessage = response.Error;
            return errorMessage;
        }

        public static Model.Oficiu getOficiu(Request request)
        {
            Model.Oficiu oficiu = new Model.Oficiu(request.Oficiu.Nume, request.Oficiu.Parola);
            return oficiu;

        }

        public static Model.Oficiu getOficiu(Response request)
        {
            Model.Oficiu oficiu = new Model.Oficiu(request.Oficiu.Nume, request.Oficiu.Parola);
            return oficiu;

        }


        public static Model.Cursa getCursa(Request request)
        {
            Model.Cursa cursa = new Model.Cursa(Int16.Parse(request.Cursa.Id), request.Cursa.Destinatie, DateTime.Parse(request.Cursa.Plecare), Int16.Parse(request.Cursa.LocuriDisponibile));
            return cursa;

        }

        public static Model.Cursa getCursa(Response request)
        {
            Model.Cursa cursa = new Model.Cursa(Int16.Parse(request.Cursa.Id), request.Cursa.Destinatie, DateTime.Parse(request.Cursa.Plecare), Int16.Parse(request.Cursa.LocuriDisponibile));
            return cursa;

        }


        public static Model.Cursa[] getCurse(Response response)
        {
            Model.Cursa[] curse = new Model.Cursa[response.Curse.Count];
            for(int i = 0; i < response.Curse.Count; i++)
            {
                Model.Cursa cursa = new Model.Cursa(Int16.Parse(response.Curse[i].Id), response.Curse[i].Destinatie, DateTime.Parse(response.Curse[i].Plecare), Int16.Parse(response.Curse[i].LocuriDisponibile));
                curse[i] = cursa;
            }

            return curse;

        }


        public static Model.Rezervare getRezervare(Request request)
        {
            Model.Rezervare rezervare = new Model.Rezervare(request.Rezervare.NumeClient, Int16.Parse(request.Rezervare.NrLocuri), Int16.Parse(request.Rezervare.IdCursa));
            return rezervare;
        }

        public static Model.Rezervare getRezervare(Response request)
        {
            Model.Rezervare rezervare = new Model.Rezervare(request.Rezervare.NumeClient, Int16.Parse(request.Rezervare.NrLocuri), Int16.Parse(request.Rezervare.IdCursa));
            return rezervare;
        }

        public static Model.Rezervare[] getRezervari(Response response)
        {
            Model.Rezervare[] rezervari = new Model.Rezervare[response.Rezervari.Count];
            for(int i = 0; i < response.Rezervari.Count; i++)
            {
                Model.Rezervare rezervareDto = new Model.Rezervare(response.Rezervari[i].NumeClient, Int16.Parse(response.Rezervari[i].NrLocuri), Int16.Parse(response.Rezervari[i].IdCursa));
                rezervari[i] = rezervareDto;
            }
            return rezervari;
        }

        public static Model.RezervareDTO getRezervareDTO(Request request)
        {
            Model.RezervareDTO rezervare = new Model.RezervareDTO(request.RezervareDTO.NumeClient, Int16.Parse(request.RezervareDTO.NrLoc));
            return rezervare;
        }

        public static Model.RezervareDTO getRezervareDTO(Response request)
        {
            Model.RezervareDTO rezervare = new Model.RezervareDTO(request.RezervareDTO.NumeClient, Int16.Parse(request.RezervareDTO.NrLoc));
            return rezervare;
        }

        public static Model.RezervareDTO[] getRezervariDTO(Response response)
        {
            Model.RezervareDTO[] rezervari = new Model.RezervareDTO[response.RezervariDTO.Count];
            for (int i = 0; i < response.RezervariDTO.Count; i++)
            {
                Model.RezervareDTO rezervareDto = new Model.RezervareDTO(response.RezervariDTO[i].NumeClient, Int16.Parse(response.RezervariDTO[i].NrLoc));
                rezervari[i] = rezervareDto;
            }
            return rezervari;
        }

    }
}
