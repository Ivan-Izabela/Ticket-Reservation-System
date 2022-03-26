using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Services;
using Model;
using Networking.dto;
using Networking.protocol;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Threading;
using System.Runtime.Serialization.Formatters.Binary;
using log4net;
using Networking.client;

namespace Networking.server

{
    public class ServerObjectProxy : IService
    {
		private static ILog logger = LogManager.GetLogger(typeof(ClientObjectWorker));

		private string host;
        private int port;
        private IFTObserver client;

        private NetworkStream stream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public ServerObjectProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
			logger.Debug("Created new ServerProxy instance");
		}

        public IEnumerable<Cursa> findAllCursa()
        {
			Console.WriteLine("sending request findallcurse");
			sendRequest(new GetAllCursaRequest());
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new FTException(err.Message);
			}
			GetAllCursaResponse resp = (GetAllCursaResponse)response;
			CursaDto[] cursaDtos = resp.curs;
			Cursa[] cursas = DTOUtils.getFromDTO(cursaDtos);
			return cursas;

			
        }

        public IEnumerable<CursaDTO> findAllCursaDTO()
        {

            throw new NotImplementedException();
        }

        public IEnumerable<Cursa> findCursaDTOByDestinatie(string destinatie)
        {
			
			sendRequest(new GetFiltredCursaRequest(destinatie));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new FTException(err.Message);
			}
			GetFiltredCursaResponse resp = (GetFiltredCursaResponse)response;
			CursaDto[] dtos = resp.curs;
			Cursa[] cursas = DTOUtils.getFromDTO(dtos);
			return cursas;
        }

        public IEnumerable<RezervareDTO> findRezervareDTO(Cursa c)
        {
			
			Console.WriteLine("sending request getrezervaredto");
			sendRequest(new GetRezervareDTORequest(c.id));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new FTException(err.Message);
			}
			GetRezervareDTOResponse resp = (GetRezervareDTOResponse)response;
			RezervareDto[] dtos = resp.rezervare;
			RezervareDTO[] rezervares = DTOUtils.getDTOFromDTO(dtos);
			return rezervares;
			
        }

        public Oficiu Login(string nume, string parola, IFTObserver client)
        {
			initializeConnection();
			OficiuDto oficiuDto = new OficiuDto( nume, parola);
			
			Console.WriteLine("sending request login");
			sendRequest(new LoginRequest(oficiuDto));
			Response response = readResponse();
			if (response is OkResponse)
			{
				this.client = client;
				return null;
			}
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				closeConnection();
				throw new FTException(err.Message);
			}
			return null;

			
        }

        public void Logout(string nume, IFTObserver client)
        {
			OficiuDto oficiuDto = new OficiuDto( nume, "parola");
			Console.WriteLine("sending request logout");
			sendRequest(new LogoutRequest(oficiuDto));
			Response response = readResponse();
			closeConnection();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new FTException(err.Message);
			}
		}

        public Rezervare saveRezervare(string numeClient, int nrLocuri, int idCursa)
        {
			RezervareDto rezervareDto = new RezervareDto(1, numeClient, nrLocuri, idCursa);
			Console.WriteLine("sending request saverezervare");
			sendRequest(new SaveRezervareRequest(rezervareDto));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new FTException(err.Message);
			}
			SavedRezervareResponse savedRezervare = (SavedRezervareResponse)response;
			return DTOUtils.getFromDTO(savedRezervare.rezervare);
		}



		private void closeConnection()
		{
			finished = true;
			try
			{
				stream.Close();
				//output.close();
				connection.Close();
				_waitHandle.Close();
				client = null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}

		}

		private void sendRequest(Request request)
		{
			try
			{
				formatter.Serialize(stream, request);
				stream.Flush();
			}
			catch (Exception e)
			{
				throw new FTException("Error sending object " + e);
			}

		}

		private Response readResponse()
		{
			Response response = null;
			try
			{
				_waitHandle.WaitOne();
				lock (responses)
				{
					//Monitor.Wait(responses); 
					response = responses.Dequeue();

				}


			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
			return response;
		}
		private void initializeConnection()
		{
			try
			{
				connection = new TcpClient(host, port);
				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				finished = false;
				_waitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}
		private void startReader()
		{
			Thread tw = new Thread(run);
			tw.Start();
		}

		private void handleUpdate(UpdateResponse update)
        {
			if(update is SaveRezervareResponse)
            {
				SaveRezervareResponse response = (SaveRezervareResponse)update;
				RezervareDto rezervareDto=response.rezervare;
				Rezervare rezervare = DTOUtils.getFromDTO(rezervareDto);
                try
                {
					client.saveDRezervare(rezervare);
                }
				catch(FTException e)
                {
					Console.WriteLine(e.StackTrace);
                }
            }

        }

		public virtual void run()
		{
			while (!finished)
			{
				try
				{
					object response = formatter.Deserialize(stream);
					Console.WriteLine("response received " + response);
					if (response is UpdateResponse)
					{
						handleUpdate((UpdateResponse)response);
					}
					else
					{

						lock (responses)
						{


							responses.Enqueue((Response)response);

						}
						_waitHandle.Set();
					}
				}
				catch (Exception e)
				{
					Console.WriteLine("Reading error " + e);
				}

			}
		}
	}
}
