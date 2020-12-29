using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TipCalculator
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void CalculateTip_Click(object sender, EventArgs e)
        {
            double bill = Convert.ToDouble(billBox.Text);
            double percentage = Convert.ToDouble(percentageBox.Text);
            double tip = bill * (percentage / 100);
            double total = bill + tip;

            totalBox.Text = total.ToString();
            tipBox.Text = tip.ToString();
        }

        private void BillBox_TextChanged(object sender, EventArgs e)
        {
            if (!(billBox.Text == billBox.Text))
                throw new Exception();
        }

        private void PercentageBox_TextChanged(object sender, EventArgs e)
        {
            if (!(percentageBox.Text == percentageBox.Text))
                throw new Exception();
        }
    }
}
