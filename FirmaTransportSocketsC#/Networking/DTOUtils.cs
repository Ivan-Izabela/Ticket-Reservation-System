using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;

namespace Networking.dto
{
    public class DTOUtils
    {
        public static Oficiu getFromDTO(OficiuDto oficiuDto)
        {
            int id = oficiuDto.id;
            string nume = oficiuDto.nume;
            string parola = oficiuDto.parola;

            return new Oficiu(id,nume,parola);
        }

        public static OficiuDto getDTO(Oficiu oficiu)
        {
            int id = oficiu.id;
            string nume = oficiu.nume;
            string parola = oficiu.parola;

            return new OficiuDto(id, nume, parola);
        }


        public static Oficiu[] getFromDTO(OficiuDto[] oficiuDto)
        {
            Oficiu[] oficii = new Oficiu[oficiuDto.Length];
            for(int i = 0; i < oficiuDto.Length; i++)
            {
                oficii[i] = getFromDTO(oficiuDto[i]);
            }

            return oficii;
        }


        public static OficiuDto[] getDTO(Oficiu[] oficiu)
        {
            OficiuDto[] oficii = new OficiuDto[oficiu.Length];
            for (int i = 0; i < oficiu.Length; i++)
            {
                oficii[i] = getDTO(oficiu[i]);
            }

            return oficii;
        }

        //--------------


        public static Cursa getFromDTO(CursaDto cursaDto)
        {
            int id = cursaDto.id;
            string destinatie = cursaDto.destinatie;
            DateTime plecare = cursaDto.plecare;
            int locuri = cursaDto.locuriDisponibile;
            return new Cursa(id, destinatie, plecare, locuri);
        }

        public static CursaDto getDTO(Cursa cursa)
        {
            int id = cursa.id;
            string destinatie = cursa.destinatie;
            DateTime plecare = cursa.plecare;
            int locuri = cursa.locuriDisponibile;
            return new CursaDto(id, destinatie, plecare, locuri);
        }

        public static Cursa[] getFromDTO(CursaDto[] cursaDto)
        {
            Cursa[] curse = new Cursa[cursaDto.Length];
            for (int i = 0; i < cursaDto.Length;i++)
            {
                curse[i] = getFromDTO(cursaDto[i]);

            }

            return curse;
        }

        public static CursaDto[] getDTO(Cursa[] cursa)
        {
            CursaDto[] curse = new CursaDto[cursa.Length];
            for (int i = 0; i < cursa.Length; i++)
            {
                curse[i] = getDTO(cursa[i]);

            }

            return curse;
        }

        //---------------------------


        public static Rezervare getFromDTO(RezervareDto rezervareDto)
        {
            int id = rezervareDto.id;
            string nume = rezervareDto.numeClient;
            int idCursa = rezervareDto.idCursa;
            int nrLoc = rezervareDto.nrLoc;
            return new Rezervare(id, nume, nrLoc, idCursa);

        }

        public static RezervareDto getDTO(Rezervare rezervare)
        {
            int id = rezervare.id;
            string nume = rezervare.numeClient;
            int idCursa = rezervare.idCursa;
            int nrLoc = rezervare.nrLocuri;
            return new RezervareDto(id, nume, nrLoc, idCursa);

        }

        public static Rezervare[] getFromDTO(RezervareDto[] rezervareDto)
        {
            Rezervare[] rezervari = new Rezervare[rezervareDto.Length];
            for(int i = 0; i < rezervareDto.Length; i++)
            {
                rezervari[i] = getFromDTO(rezervareDto[i]);
            }
            return rezervari;

        }

        public static RezervareDto[] getDTO(Rezervare[] rezervare)
        {
            RezervareDto[] rezervari = new RezervareDto[rezervare.Length];
            for (int i = 0; i < rezervare.Length; i++)
            {
                rezervari[i] = getDTO(rezervare[i]);
            }
            return rezervari;

        }

        //--------------------


        public static RezervareDTO getDTOFromDTO(RezervareDto rezervareDto)
        {
            string nume = rezervareDto.numeClient;
            int nrLoc = rezervareDto.nrLoc;
            return new RezervareDTO(nume, nrLoc);
        }

        public static RezervareDto getDTOdom(RezervareDTO rezervareDto)
        {
            string nume = rezervareDto.numeClient;
            int nrLoc = rezervareDto.nrLo;
            return new RezervareDto(nume, nrLoc);

        }


        public static RezervareDTO[] getDTOFromDTO(RezervareDto[] rezervareDto)
        {
            RezervareDTO[] rezervares = new RezervareDTO[rezervareDto.Length];
            for(int i = 0; i < rezervareDto.Length; i++)
            {
                rezervares[i] = getDTOFromDTO(rezervareDto[i]);
            }
            return rezervares;
        }


        public static RezervareDto[] getDTOdom(RezervareDTO[] rezervareDto)
        {
            RezervareDto[] rezervares = new RezervareDto[rezervareDto.Length];
            for (int i = 0; i < rezervareDto.Length; i++)
            {
                rezervares[i] = getDTOdom
                    (rezervareDto[i]);
            }
            return rezervares;
        }




    }
}
