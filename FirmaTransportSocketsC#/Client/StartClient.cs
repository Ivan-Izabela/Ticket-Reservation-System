using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Configuration;
using System.Windows.Forms;
using Model;
using Persistence;
using Services;
using Server;
using log4net.Config;
using Networking.server;
namespace Client
{
    static class StartClient
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]



        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            IService service = new ServerObjectProxy("127.0.0.1", 55555);
            FirmaTranspotCtrl ctrl = new FirmaTranspotCtrl(service);
            LoginForm loginForm = new LoginForm(ctrl);
            Application.Run(loginForm); 
        }
    }
}
