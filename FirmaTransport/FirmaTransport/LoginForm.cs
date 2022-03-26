using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using FirmaTransport.service;

namespace FirmaTransport
{
    public partial class LoginForm : Form
    {
        private Service service;
        private MainWindowForm mainWindowForm;

        public LoginForm()
        {
            InitializeComponent();
        }

        internal void Set(Service service, MainWindowForm mainWindowForm)
        {
            this.service = service;
            this.mainWindowForm = mainWindowForm;
        }

        private void LoginForm_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string nume = textBoxNume.Text;
            string parola = textBoxParola.Text;
            textBoxParola.Text = "";
            if (service.Login(nume, parola) == true)
            {
                this.Hide();
                mainWindowForm.Show();
            }
            else
            {
                MessageBox.Show("Invalid username or password");
            }
        }
    }
}
