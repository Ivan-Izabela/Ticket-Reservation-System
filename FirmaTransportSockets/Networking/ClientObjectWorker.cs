using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using Networking.protocol;
using Networking.dto;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using Services;
using Model;
using log4net;

namespace Networking.client

{
    public class ClientObjectWorker : IFTObserver
    {
        private static ILog logger = LogManager.GetLogger(typeof(ClientObjectWorker));
        private IService service;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public ClientObjectWorker(IService service, TcpClient connection)
        {
            this.service = service;
            this.connection = connection;
            try
            {

                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }


		public virtual void run()
		{
			while (connected)
			{
				try
				{
					object request = formatter.Deserialize(stream);
                    logger.Debug("Receipt request " + request + " from client" + connection);
                    object response = handleRequest((Request)request);
					if (response != null)
					{
						sendResponse((Response)response);
					}
				}
				catch (Exception e)
				{
                    logger.Error(e.Message + '\n' + e.StackTrace);
                    Console.WriteLine(e.StackTrace);
				}

				try
				{
					Thread.Sleep(1000);
				}
				catch (Exception e)
				{
                    logger.Error(e.Message + '\n' + e.StackTrace);
                    Console.WriteLine(e.StackTrace);
				}
			}
			try
			{
				stream.Close();
				connection.Close();
			}
			catch (Exception e)
			{
				Console.WriteLine("Error " + e);
			}
		}

        private void sendResponse(Response response)
        {
            logger.Debug("Sending response " + response);
            formatter.Serialize(stream, response);
            stream.Flush();
        }

        private object handleRequest(Request request)
        {
            Response response = null;
            if (request is LoginRequest)
            {
                LoginRequest logReq = (LoginRequest)request;
                OficiuDto oficiuDto = logReq.oficiu;
                logger.Debug("Processin login request by agent " + oficiuDto.nume);
                Oficiu oficiu = DTOUtils.getFromDTO(oficiuDto);
                try
                {
                    lock (service)
                    {
                        service.Login(oficiu.nume, oficiu.parola, this);
                    }
                    return new OkResponse();

                } catch (FTException e)
                {
                    logger.Debug("User " + oficiuDto.nume + " already logged in. Disconnecting...");
                    connected = false;
                    return new ErrorResponse(e.Message);
                }

            }

            if (request is LogoutRequest)
            { 
                LogoutRequest logReq = (LogoutRequest)request;
                OficiuDto oficiuDto = logReq.oficiu;
                logger.Debug("Processing logout request by user " + oficiuDto.nume);
                Oficiu oficiu = DTOUtils.getFromDTO(oficiuDto);
                try
                {
                    lock (service)
                    {
                        service.Logout(oficiu.nume, this);
                    }
                    connected = false;
                    return new OkResponse();
                } catch (FTException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is SaveRezervareRequest)
            {
                
                SaveRezervareRequest savReq = (SaveRezervareRequest)request;
                RezervareDto rezervareDto = savReq.rezervare;
                logger.Debug("Processing buy ticket request for bilet ID " + rezervareDto.id);
                Rezervare rezervare = DTOUtils.getFromDTO(rezervareDto);
                try
                {
                    Rezervare result;
                    lock (service)
                    {
                        result=service.saveRezervare(rezervare.numeClient, rezervare.nrLocuri, rezervare.idCursa);
                    }
                    if(result != null)
                    {
                        return new SavedRezervareResponse(DTOUtils.getDTO(result));
                    }
                    else
                    {
                        return new ErrorResponse("Bilet " + rezervare.id + " could not be bought");
                    }
                    
                }
                catch (FTException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetAllCursaRequest)
            {
                
                GetAllCursaRequest getReq = (GetAllCursaRequest)request;
                logger.Debug("Processing get zboruri request");
                try
                {
                    Cursa[] curse;
                    lock (service)
                    {
                        IList<Cursa> cursas = service.findAllCursa().ToList();
                        curse = cursas.ToArray();
                    }
                    CursaDto[] cursaDtos = DTOUtils.getDTO(curse);
                    return new GetAllCursaResponse(cursaDtos);
                }
                catch (FTException e)
                {
                    return new ErrorResponse(e.Message);

                }

            }

            if(request is GetFiltredCursaRequest)
            {
                Console.WriteLine("GetFiltredCure Request ...");
                GetFiltredCursaRequest getReq = (GetFiltredCursaRequest)request;
                string destinatie = getReq.destinatie;
                try
                {
                    Cursa[] curse;
                    lock (service)
                    {
                        IList<Cursa> cursas = service.findCursaDTOByDestinatie(destinatie).ToList();
                        curse = cursas.ToArray();
                    }
                    CursaDto[] cursaDtos = DTOUtils.getDTO(curse);
                    return new GetFiltredCursaResponse(cursaDtos);
                }
                catch (FTException e)
                {
                    return new ErrorResponse(e.Message);

                }
            }
            if(request is GetRezervareDTORequest)
            {
                Console.WriteLine("GetFiltredCure Request ...");
                GetRezervareDTORequest getReq = (GetRezervareDTORequest)request;
                int id = getReq.id;
                
                try
                {
                    RezervareDTO[] rezervares;
                    lock (service)
                    {
                        Cursa cursa = new Cursa(id);
                        IList<RezervareDTO> rezervareDTOs = service.findRezervareDTO(cursa).ToList();
                        rezervares = rezervareDTOs.ToArray();
                    }
                    RezervareDto[] rezervareDtos = DTOUtils.getDTOdom(rezervares);
                    return new GetRezervareDTOResponse(rezervareDtos);
                }
                catch (FTException e)
                {
                    return new ErrorResponse(e.Message);

                }

            }
            return response;

        }
        

        public void saveDRezervare(Rezervare rezervare)
        {
            logger.Debug("Notification - new ticket bought: " + rezervare.id);
            RezervareDto rezervareDto = DTOUtils.getDTO(rezervare);
           
            try
            {
                sendResponse(new SaveRezervareResponse(rezervareDto));
            }
            catch (Exception e)
            {
                throw new FTException("Saved error:" + e);
            }

            
        }
    }
}
