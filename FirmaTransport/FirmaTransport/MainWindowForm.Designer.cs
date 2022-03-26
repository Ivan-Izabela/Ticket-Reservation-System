namespace FirmaTransport
{
    partial class MainWindowForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.curseGridView = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.destinatieTextBox = new System.Windows.Forms.TextBox();
            this.rezervareDTOGridView = new System.Windows.Forms.DataGridView();
            this.button2 = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.numeClientTextBox = new System.Windows.Forms.TextBox();
            this.nrLocuriTextBox = new System.Windows.Forms.TextBox();
            this.Rezerva = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.curseGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.rezervareDTOGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // curseGridView
            // 
            this.curseGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.curseGridView.Location = new System.Drawing.Point(12, 55);
            this.curseGridView.Name = "curseGridView";
            this.curseGridView.Size = new System.Drawing.Size(365, 169);
            this.curseGridView.TabIndex = 0;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(692, 367);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 1;
            this.button1.Text = "Logout";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(524, 58);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(54, 13);
            this.label1.TabIndex = 2;
            this.label1.Text = "Destinatie";
            // 
            // destinatieTextBox
            // 
            this.destinatieTextBox.Location = new System.Drawing.Point(600, 55);
            this.destinatieTextBox.Name = "destinatieTextBox";
            this.destinatieTextBox.Size = new System.Drawing.Size(117, 20);
            this.destinatieTextBox.TabIndex = 3;
            this.destinatieTextBox.TextChanged += new System.EventHandler(this.destinatieTextBox_TextChanged);
            // 
            // rezervareDTOGridView
            // 
            this.rezervareDTOGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.rezervareDTOGridView.Location = new System.Drawing.Point(527, 106);
            this.rezervareDTOGridView.Name = "rezervareDTOGridView";
            this.rezervareDTOGridView.Size = new System.Drawing.Size(240, 150);
            this.rezervareDTOGridView.TabIndex = 4;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(420, 106);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 5;
            this.button2.Text = "Detalii Cursa";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(50, 278);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(35, 13);
            this.label2.TabIndex = 6;
            this.label2.Text = "Nume";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(32, 325);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(53, 13);
            this.label3.TabIndex = 7;
            this.label3.Text = "Nr. Locuri";
            // 
            // numeClientTextBox
            // 
            this.numeClientTextBox.Location = new System.Drawing.Point(91, 275);
            this.numeClientTextBox.Name = "numeClientTextBox";
            this.numeClientTextBox.Size = new System.Drawing.Size(100, 20);
            this.numeClientTextBox.TabIndex = 8;
            // 
            // nrLocuriTextBox
            // 
            this.nrLocuriTextBox.Location = new System.Drawing.Point(91, 325);
            this.nrLocuriTextBox.Name = "nrLocuriTextBox";
            this.nrLocuriTextBox.Size = new System.Drawing.Size(100, 20);
            this.nrLocuriTextBox.TabIndex = 9;
            // 
            // Rezerva
            // 
            this.Rezerva.Location = new System.Drawing.Point(113, 367);
            this.Rezerva.Name = "Rezerva";
            this.Rezerva.Size = new System.Drawing.Size(75, 23);
            this.Rezerva.TabIndex = 10;
            this.Rezerva.Text = "Rezerva";
            this.Rezerva.UseVisualStyleBackColor = true;
            this.Rezerva.Click += new System.EventHandler(this.Rezerva_Click);
            // 
            // MainWindowForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.Rezerva);
            this.Controls.Add(this.nrLocuriTextBox);
            this.Controls.Add(this.numeClientTextBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.rezervareDTOGridView);
            this.Controls.Add(this.destinatieTextBox);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.curseGridView);
            this.Name = "MainWindowForm";
            this.Text = "MainWindowForm";
            this.Load += new System.EventHandler(this.MainWindowForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.curseGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.rezervareDTOGridView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView curseGridView;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox destinatieTextBox;
        private System.Windows.Forms.DataGridView rezervareDTOGridView;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox numeClientTextBox;
        private System.Windows.Forms.TextBox nrLocuriTextBox;
        private System.Windows.Forms.Button Rezerva;
    }
}