using System;

namespace TipCalculator
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.EnterTotalBill = new System.Windows.Forms.Label();
            this.calculateTip = new System.Windows.Forms.Button();
            this.billBox = new System.Windows.Forms.TextBox();
            this.tipBox = new System.Windows.Forms.TextBox();
            this.tipPercentage = new System.Windows.Forms.Label();
            this.percentageBox = new System.Windows.Forms.TextBox();
            this.total = new System.Windows.Forms.Label();
            this.totalBox = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // EnterTotalBill
            // 
            this.EnterTotalBill.AccessibleName = "Enter total bill:";
            this.EnterTotalBill.AutoSize = true;
            this.EnterTotalBill.Location = new System.Drawing.Point(355, 208);
            this.EnterTotalBill.Name = "EnterTotalBill";
            this.EnterTotalBill.Size = new System.Drawing.Size(168, 32);
            this.EnterTotalBill.TabIndex = 0;
            this.EnterTotalBill.Text = "Enter total bill:";
            this.EnterTotalBill.Click += new System.EventHandler(this.totalBox_Click);
            // 
            // calculateTip
            // 
            this.calculateTip.Location = new System.Drawing.Point(355, 494);
            this.calculateTip.Name = "calculateTip";
            this.calculateTip.Size = new System.Drawing.Size(150, 46);
            this.calculateTip.TabIndex = 1;
            this.calculateTip.Text = "Compute";
            this.calculateTip.UseVisualStyleBackColor = true;
            this.calculateTip.Click += new System.EventHandler(this.CalculateTip_Click);
            // 
            // billBox
            // 
            this.billBox.Location = new System.Drawing.Point(833, 208);
            this.billBox.Name = "billBox";
            this.billBox.Size = new System.Drawing.Size(200, 39);
            this.billBox.TabIndex = 2;
            this.billBox.TextChanged += new System.EventHandler(this.BillBox_TextChanged);
            // 
            // tipBox
            // 
            this.tipBox.Location = new System.Drawing.Point(833, 480);
            this.tipBox.Name = "tipBox";
            this.tipBox.Size = new System.Drawing.Size(200, 39);
            this.tipBox.TabIndex = 3;
            // 
            // tipPercentage
            // 
            this.tipPercentage.AutoSize = true;
            this.tipPercentage.Location = new System.Drawing.Point(355, 300);
            this.tipPercentage.Name = "tipPercentage";
            this.tipPercentage.Size = new System.Drawing.Size(179, 32);
            this.tipPercentage.TabIndex = 4;
            this.tipPercentage.Text = "Tip percentage:";
            // 
            // percentageBox
            // 
            this.percentageBox.Location = new System.Drawing.Point(833, 300);
            this.percentageBox.Name = "percentageBox";
            this.percentageBox.Size = new System.Drawing.Size(200, 39);
            this.percentageBox.TabIndex = 5;
            this.percentageBox.TextChanged += new System.EventHandler(this.PercentageBox_TextChanged);
            // 
            // total
            // 
            this.total.AutoSize = true;
            this.total.Location = new System.Drawing.Point(355, 622);
            this.total.Name = "total";
            this.total.Size = new System.Drawing.Size(70, 32);
            this.total.TabIndex = 6;
            this.total.Text = "Total:";
            // 
            // totalBox
            // 
            this.totalBox.Location = new System.Drawing.Point(833, 622);
            this.totalBox.Name = "totalBox";
            this.totalBox.Size = new System.Drawing.Size(200, 39);
            this.totalBox.TabIndex = 7;
            // 
            // Form1
            // 
            this.AccessibleName = "";
            this.AutoScaleDimensions = new System.Drawing.SizeF(13F, 32F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1552, 902);
            this.Controls.Add(this.totalBox);
            this.Controls.Add(this.total);
            this.Controls.Add(this.percentageBox);
            this.Controls.Add(this.tipPercentage);
            this.Controls.Add(this.tipBox);
            this.Controls.Add(this.billBox);
            this.Controls.Add(this.calculateTip);
            this.Controls.Add(this.EnterTotalBill);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        private void totalBox_Click(object sender, EventArgs e)
        {
            throw new NotImplementedException();
        }

        #endregion

        private System.Windows.Forms.Label EnterTotalBill;
        private System.Windows.Forms.Button calculateTip;
        private System.Windows.Forms.TextBox billBox;
        private System.Windows.Forms.TextBox tipBox;
        private System.Windows.Forms.Label tipPercentage;
        private System.Windows.Forms.TextBox percentageBox;
        private System.Windows.Forms.Label total;
        private System.Windows.Forms.TextBox totalBox;
    }
}

