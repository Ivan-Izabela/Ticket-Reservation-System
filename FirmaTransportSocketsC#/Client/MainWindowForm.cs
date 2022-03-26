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
using Model;

namespace Client
{
    public partial class MainWindowForm : Form
    {
        private readonly FirmaTranspotCtrl ctrl;
        private readonly IList<Cursa> curse;

        public MainWindowForm(FirmaTranspotCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
            this.curse = ctrl.findAllCurse().ToList();
            curseGridView.DataSource = curse;
            try
            {
                curseGridView.DataSource = curse;

            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "DataSource: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            ctrl.updateEvent += CursaUpdate;
        }

        

        private void LoadData()
        {
            curseGridView.DataSource = ctrl.findAllCurse();
        }

        private void MainWindowForm_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            Console.WriteLine("ChatWindow closing ");
            this.Hide();
            //loginForm.Show();
            Application.Exit();
            ctrl.logout();
        }

        private void destinatieTextBox_TextChanged(object sender, EventArgs e)
        {
            string dsetinatie = destinatieTextBox.Text;
            curseGridView.DataSource = ctrl.findByDestinatie(dsetinatie);

        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                Cursa cursa = curseGridView.SelectedRows[0].DataBoundItem as Cursa;
                if (cursa != null)
                {
                    rezervareDTOGridView.DataSource = ctrl.findRezervareDTO(cursa);
                }
                else
                {
                    MessageBox.Show("Selectati o cursa");
                }
            }
            catch (Exception ex)
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
                    ctrl.saveRezervare(nume, nr, cursa.id);
                    numeClientTextBox.Text = "";
                    nrLocuriTextBox.Text = "";
                    //curseGridView.DataSource = ctrl.findAllCurse();
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Numar de locuri invalid");

                }

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void CursaUpdate(object sender, FTUserEventArgs c)
        {
            if(c.UserEventType== FTUserEvent.saveDRezervare)
            {
                UpdateTables((Rezervare)c.Data);
                curseGridView.BeginInvoke(new UpdateDataGridView(this.updateDataGridView), new object[] { curseGridView, curse });
            }
            
        }

        //for updating the GUI

        //1. define a method for updating the DataGridView
        private void updateDataGridView(DataGridView dataGridView, IList<Cursa> newdata)
        {
            rezervareDTOGridView.DataSource = null;
            dataGridView.DataSource = null;
            dataGridView.DataSource = newdata;
        }

        //2. define a delegate to be called back by the GUI Thread
        public delegate void UpdateDataGridView(DataGridView dataGridView, IList<Cursa> newdata);

        //3. in the other thread call like this:
        /*
         * list.Invoke(new UpdateListBoxCallback(this.updateListBox), new Object[]{list, data});
         * 
         * */

        private void UpdateTables(Rezervare rezervare)
        {
            for (int i = 0; i < curse.Count(); i++)
            {
                if (curse[i].id == rezervare.idCursa)
                {
                    curse[i].locuriDisponibile -= rezervare.nrLocuri;
                }
            }
        }

        private void MainWindowForm_Load_1(object sender, EventArgs e)
        {

        }

        
    }
}
