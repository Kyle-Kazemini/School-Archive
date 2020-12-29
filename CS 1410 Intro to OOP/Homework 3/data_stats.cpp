/*
 * This program reads through a file and prints out info about the data
 *
 * by Kyle Kazemini
 * u1127157
 * September 9, 2019
 */

#include <iostream>
#include <fstream>

using namespace std;

int main()
{

  ifstream data("data.txt");
  int count;
  double sum = 0;
  double i;
  double largest = 0;

  while (data >> i)  
  {         
    count++;
    sum = i + sum;
    if (i > largest)
      largest = i;
  }
  cout <<  "There are " << count << " numbers in the file." << endl;
  cout << "The sum of the numbers is " << sum << endl;

  double average = sum/count;
  cout << "The average of the numbers is " << average << endl;
  cout << "The largest number is " << largest << endl;

}
