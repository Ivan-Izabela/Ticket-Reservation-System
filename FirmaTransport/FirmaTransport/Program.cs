using FirmaTransport.domain;
using FirmaTransport.repository;
using log4net.Config;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using FirmaTransport.service;

namespace FirmaTransport
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]


        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }


        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            //configurare jurnalizare folosind log4net
            XmlConfigurator.Configure(new System.IO.FileInfo(args[0]));
            Console.WriteLine("Configuration Settings for firma_transport.db {0}", GetConnectionStringByName("firma_transport"));
            IDictionary<String, string> properties = new SortedList<String, String>();
            properties.Add("ConnectionString", GetConnectionStringByName("firma_transport"));

            IOficiuRemository oficiuRepository = new OficiuDBRepository(properties);
            ICursaRepository cursaRepository = new CursaDBRepository(properties);
            IRezervareRepository rezervareRepository = new RezervareDBRepository(properties);

            Console.WriteLine("Oficiile din db");            
            foreach(Oficiu o in oficiuRepository.findAll())
            {
                Console.WriteLine(o);
            }

            Console.WriteLine("Cursele din db");
            foreach (Cursa o in cursaRepository.findAll())
            {
                Console.WriteLine(o);
            }

            Console.WriteLine("Rezervarile din db");
            foreach (Rezervare o in rezervareRepository.findAll())
            {
                Console.WriteLine(o);
            }

            Service service = new Service(oficiuRepository,cursaRepository,rezervareRepository);
            
            LoginForm loginForm = new LoginForm();
            MainWindowForm mainWindowForm = new MainWindowForm();

            loginForm.Set(service, mainWindowForm);
            mainWindowForm.Set(service, loginForm);

            Application.Run(loginForm);
        }
    }
}
