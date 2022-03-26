using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Net.Sockets;
using Persistence;
using Services;
using Networking.client;
using ServerTemplate;
using log4net;
using log4net.Repository;
using System.Reflection;
using System.IO;
using Protobuff3;

namespace Server
{
    class StartServer
    {
        private static ILog logger = LogManager.GetLogger(typeof(StartServer));
        private static IOficiuRemository oficiuRepository = new OficiuDBRepository();
        private static ICursaRepository cursaRepository = new CursaDBRepository();
        private static IRezervareRepository rezervareRepository = new RezervareDBRepository();
        private static IService service = new Service(oficiuRepository, cursaRepository, rezervareRepository);

        static void RunObjectServer()
        {
            //SerialServer server = new SerialServer("127.0.0.1", 55555, service);
            ProtoV3FTServer server = new ProtoV3FTServer("127.0.0.1", 55556, service);
            server.Start();
        }

        static void RunRemotingServer()
        {
            ;
        }

        static void Main(string[] args)
        {


            ILoggerRepository repository = log4net.LogManager.GetRepository(Assembly.GetCallingAssembly());
            log4net.Config.XmlConfigurator.Configure(repository, new FileInfo("log4net.xml"));


            Console.WriteLine("Server started ...");
            RunObjectServer();

            //Console.WriteLine("Press <enter> to exit...");
            Console.ReadLine();

            logger.Info("Server started");

        }
    }

    public class SerialServer : ConcurrentServer
    {
        private IService service;
        private ClientObjectWorker worker;

        public SerialServer(string host, int port, IService service) : base(host, port)
        {
            this.service = service;
            Console.WriteLine("SerialChatServer...");
        }

        protected override Thread createWorker(TcpClient client)
        {
            worker = new ClientObjectWorker(service, client);
            return new Thread(new ThreadStart(worker.run));
         
        }
    }

    public class ProtoV3FTServer : ConcurrentServer
    {
        private IService service;
        private ProtoFTWorker worker;

        public ProtoV3FTServer(string host, int port, IService service)
            :base(host,port)
        {
            this.service = service;
            Console.WriteLine("ProtoChatServer...");
        }

        protected override Thread createWorker(TcpClient client)
        {
            worker = new ProtoFTWorker(service, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
