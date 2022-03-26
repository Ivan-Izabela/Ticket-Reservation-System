using log4net;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;

using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;
using Ft.Protocol;
using proto = Ft.Protocol;
using Model;
using System.Threading;
using Google.Protobuf;
using System.Runtime.Serialization;

namespace Protobuff3
{
    public class ProtoFTWorker : IFTObserver
    {
        private static ILog logger = LogManager.GetLogger(typeof(ProtoFTWorker));
        private IService service;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public ProtoFTWorker(IService service, TcpClient connection)
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
                    Request request = Request.Parser.ParseDelimitedFrom(stream);
                    logger.Debug("Receipt request " + request + " from client" + connection);
                    Response response = handleRequest(request);
                    if (response != null)
                    {
                        sendResponse(response);
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
            Console.WriteLine("sending response " + response);
            lock (stream)
            {
                response.WriteDelimitedTo(stream);
                stream.Flush();
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;

            if (!(request.Type is Request.Types.Type.Login ))
            {
                if (request.Type is Request.Types.Type.Logout)
                {
                    Model.Oficiu oficiu1 =ProtoUtils.getOficiu(request) ;
                    logger.Debug("Processing logout request by user " + oficiu1.nume);
                    try
                    {
                        lock (service)
                        {
                            service.Logout(oficiu1.nume, this);
                        }
                        connected = false;
                        return ProtoUtils.createOKResponse();
                    }
                    catch (FTException e)
                    {
                        return ProtoUtils.createErrorResponse(e.Message);
                    }
                }
                if (request.Type is Request.Types.Type.SaveRezervare)
                {


                    Model.Rezervare rezervare = ProtoUtils.getRezervare(request);
                    logger.Debug("Processing buy ticket request for bilet ID " + rezervare.id);
                    try
                    {
                        Model.Rezervare result;
                        lock (service)
                        {
                            result = service.saveRezervare(rezervare.numeClient, rezervare.nrLocuri, rezervare.idCursa);
                        }
                        if (result != null)
                        {
                            return ProtoUtils.createSavedRezervareResponse(result);
                        }
                        else
                        {
                            return ProtoUtils.createErrorResponse("Bilet " + rezervare.id + " could not be bought");
                        }

                    }
                    catch (FTException e)
                    {
                        return ProtoUtils.createErrorResponse(e.Message);
                    }
                }

                if (request.Type is Request.Types.Type.GetAllCurse)
                {

                    
                    logger.Debug("Processing get zboruri request");
                    try
                    {
                        Model.Cursa[] curse;
                        lock (service)
                        {
                            IList<Model.Cursa> cursas = service.findAllCursa().ToList();
                            curse = cursas.ToArray();
                        }
                        
                        return ProtoUtils.createGetAllCurseResponse(curse);
                    }
                    catch (FTException e)
                    {
                        return ProtoUtils.createErrorResponse(e.Message);

                    }

                }

                if (request.Type is Request.Types.Type.GetFilteredCurse)
                {
                    Console.WriteLine("GetFiltredCure Request ...");
                    
                    string destinatie = ProtoUtils.getCursa(request).destinatie;
                    try
                    {
                        Model.Cursa[] curse;
                        lock (service)
                        {
                            IList<Model.Cursa> cursas = service.findCursaDTOByDestinatie(destinatie).ToList();
                            curse = cursas.ToArray();
                        }
                        
                        return ProtoUtils.createGetFiltredCurseResponse(curse);
                    }
                    catch (FTException e)
                    {
                        return ProtoUtils.createErrorResponse(e.Message);

                    }
                }
                if (request.Type is Request.Types.Type.GetRezervareDto)
                {
                    Console.WriteLine("GetFiltredCure Request ...");
                   
                    int id = ProtoUtils.getRezervare(request).idCursa;

                    try
                    {
                        Model.RezervareDTO[] rezervares;
                        lock (service)
                        {
                            Model.Cursa cursa = new Model.Cursa(id);
                            IList<Model.RezervareDTO> rezervareDTOs = service.findRezervareDTO(cursa).ToList();
                            rezervares = rezervareDTOs.ToArray();
                        }
                        
                        return ProtoUtils.createGetRezervareDTOResponse(rezervares);
                    }
                    catch (FTException e)
                    {
                        return ProtoUtils.createErrorResponse(e.Message);

                    }

                }
                return response;

            }

            Console.WriteLine("login aici");
            Model.Oficiu oficiu =ProtoUtils.getOficiu(request);
            logger.Debug("Processin login request by agent " + oficiu.nume);
            try
            {
                lock (service)
                {
                    service.Login(oficiu.nume, oficiu.parola, this);
                }
                return ProtoUtils.createOKResponse();

            }
            catch (FTException e)
            {
                logger.Debug("User " + oficiu.nume + " already logged in. Disconnecting...");
                connected = false;
                return ProtoUtils.createErrorResponse(e.Message);
            }

        }


        public void saveDRezervare(Model.Rezervare rezervare)
        {
            logger.Debug("Notification - new ticket bought: " + rezervare.id);
            

            try
            {
                sendResponse(ProtoUtils.createSaveRezervareResponse(rezervare));
            }
            catch (Exception e)
            {
                throw new FTException("Saved error:" + e);
            }


        }

        
    }
}
