using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Services;

namespace Client
{
    public partial class LoginForm : Form
    {
        //private IService service;
        private readonly FirmaTranspotCtrl ctrl;

        

        public LoginForm(FirmaTranspotCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
        }

        

        private void LoginForm_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string nume = textBoxNume.Text;
            string parola = textBoxParola.Text;
            textBoxParola.Text = "";
            try {
                ctrl.login(nume, parola);
                MainWindowForm mainWin = new MainWindowForm(ctrl);
                mainWin.Text = nume;
                mainWin.Show();
                this.Hide();
            }             
            catch(Exception ex)
            {
                MessageBox.Show("Invalid username or password");
            }
        }

        private void LoginForm_Load_1(object sender, EventArgs e)
        {

        }
    }
}
