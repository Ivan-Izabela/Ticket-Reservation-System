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
using FirmaTransport.domain;


namespace FirmaTransport
{
    public partial class MainWindowForm : Form
    {
        private Service service;
        private LoginForm loginForm;

        public MainWindowForm()
        {
            InitializeComponent();
        }

        internal void Set(Service service, LoginForm loginForm)
        {
            this.service = service;
            this.loginForm = loginForm;
            LoadData();
        }

        private void LoadData()
        {
            curseGridView.DataSource = service.findAllCursa();
        }

        private void MainWindowForm_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Hide();
            loginForm.Show();
        }

        private void destinatieTextBox_TextChanged(object sender, EventArgs e)
        {
            string dsetinatie = destinatieTextBox.Text;
            curseGridView.DataSource = service.findCursaDTOByDestinatie(dsetinatie);

        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                Cursa cursa = curseGridView.SelectedRows[0].DataBoundItem as Cursa;
                if (cursa != null)
                {
                    rezervareDTOGridView.DataSource = service.findRezervareDTO(cursa);
                }
                else
                {
                    MessageBox.Show("Selectati o cursa");
                }
            }
            catch(Exception ex)
            {
                MessageBox.Show("Selectati o cursa");
            }

            
        }

        private void Rezerva_Click(object sender, EventArgs e)
        {
            try
            {
                Cursa cursa = curseGridView.SelectedRows[0].DataBoundItem as Cursa;
                string nume = numeClientTextBox.Text;
                string nrLocuri = nrLocuriTextBox.Text;
                try
                {
                    int nr = int.Parse(nrLocuri);
                    service.saveRezervare(nume, nr, cursa.id);
                    curseGridView.DataSource = service.findAllCursa();
                }
                catch(Exception ex)
                {
                    MessageBox.Show("Numar de locuri invalid");

                }

            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
