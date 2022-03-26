namespace Client
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
            this.rezervareDTOGridView = new System.Windows.Forms.DataGridView();
            this.button2 = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.numeClientTextBox = new System.Windows.Forms.TextBox();
            this.nrLocuriTextBox = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.Rezerva = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.destinatieTextBox = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.curseGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.rezervareDTOGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // curseGridView
            // 
            this.curseGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.curseGridView.Location = new System.Drawing.Point(48, 62);
            this.curseGridView.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.curseGridView.Name = "curseGridView";
            this.curseGridView.RowHeadersWidth = 51;
            this.curseGridView.Size = new System.Drawing.Size(489, 274);
            this.curseGridView.TabIndex = 0;
            // 
            // rezervareDTOGridView
            // 
            this.rezervareDTOGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.rezervareDTOGridView.Location = new System.Drawing.Point(711, 151);
            this.rezervareDTOGridView.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.rezervareDTOGridView.Name = "rezervareDTOGridView";
            this.rezervareDTOGridView.RowHeadersWidth = 51;
            this.rezervareDTOGridView.Size = new System.Drawing.Size(320, 185);
            this.rezervareDTOGridView.TabIndex = 1;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(587, 185);
            this.button2.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(100, 28);
            this.button2.TabIndex = 2;
            this.button2.Text = "Detalii Cursa";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(759, 84);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(71, 17);
            this.label1.TabIndex = 4;
            this.label1.Text = "Destinatie";
            // 
            // numeClientTextBox
            // 
            this.numeClientTextBox.Location = new System.Drawing.Point(180, 378);
            this.numeClientTextBox.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.numeClientTextBox.Name = "numeClientTextBox";
            this.numeClientTextBox.Size = new System.Drawing.Size(132, 22);
            this.numeClientTextBox.TabIndex = 5;
            // 
            // nrLocuriTextBox
            // 
            this.nrLocuriTextBox.Location = new System.Drawing.Point(180, 427);
            this.nrLocuriTextBox.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.nrLocuriTextBox.Name = "nrLocuriTextBox";
            this.nrLocuriTextBox.Size = new System.Drawing.Size(132, 22);
            this.nrLocuriTextBox.TabIndex = 6;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(116, 378);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(45, 17);
            this.label2.TabIndex = 7;
            this.label2.Text = "Nume";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(101, 431);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(70, 17);
            this.label3.TabIndex = 8;
            this.label3.Text = "Nr. Locuri";
            // 
            // Rezerva
            // 
            this.Rezerva.Location = new System.Drawing.Point(192, 481);
            this.Rezerva.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Rezerva.Name = "Rezerva";
            this.Rezerva.Size = new System.Drawing.Size(100, 28);
            this.Rezerva.TabIndex = 9;
            this.Rezerva.Text = "Rezerva";
            this.Rezerva.UseVisualStyleBackColor = true;
            this.Rezerva.Click += new System.EventHandler(this.Rezerva_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(871, 481);
            this.button1.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(100, 28);
            this.button1.TabIndex = 10;
            this.button1.Text = "Logout";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // destinatieTextBox
            // 
            this.destinatieTextBox.Location = new System.Drawing.Point(840, 84);
            this.destinatieTextBox.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.destinatieTextBox.Name = "destinatieTextBox";
            this.destinatieTextBox.Size = new System.Drawing.Size(132, 22);
            this.destinatieTextBox.TabIndex = 11;
            this.destinatieTextBox.TextChanged += new System.EventHandler(this.destinatieTextBox_TextChanged);
            // 
            // MainWindowForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1141, 554);
            this.Controls.Add(this.destinatieTextBox);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.Rezerva);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.nrLocuriTextBox);
            this.Controls.Add(this.numeClientTextBox);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.rezervareDTOGridView);
            this.Controls.Add(this.curseGridView);
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "MainWindowForm";
            this.Text = "MainWindowForm";
            this.Load += new System.EventHandler(this.MainWindowForm_Load_1);
            ((System.ComponentModel.ISupportInitialize)(this.curseGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.rezervareDTOGridView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView curseGridView;
        private System.Windows.Forms.DataGridView rezervareDTOGridView;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox numeClientTextBox;
        private System.Windows.Forms.TextBox nrLocuriTextBox;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button Rezerva;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.TextBox destinatieTextBox;
    }
}