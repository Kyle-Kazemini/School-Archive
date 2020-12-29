/*
 * This program reads a data file into arrays and computes some statistics.
 * 
 * by Kyle Kazemini
 * u1127157
 * September 26, 2019
 */

#include <iostream>
#include <fstream>

using namespace std;

typedef struct
{
  string date;
  int high;
  int low;
}  temperature;

// Application entry point
int main ()
{

  cout << "Assignment #6" << endl 
  << "CS 1410-030" << endl 
  << "Kyle Kazemini" << endl << endl;

  temperature temp[365];

  ifstream datafile("data.txt");

  for (int pos = 0; pos < 365; pos++)
    datafile >> temp[pos].date >> temp[pos].high >> temp[pos].low;

  // Finds temperature high

  int pos_high = 0;
  for (int pos = 0; pos < 365; pos++)
    if (temp[pos].high > temp[pos_high].high)
      pos_high = pos;

  cout << "The maximum high temperature was " 
       << temp[pos_high].high << " on " << temp[pos_high].date << "." << endl;

  // Finds temperature low

  int pos_low = temp[0].low;
  for (int pos = 0; pos < 365; pos++)
    if (temp[pos].low < temp[pos_low].low)
      pos_low = pos;

  cout << "The minimum low temperature was "
       << temp[pos_low].low << " on " << temp[pos_low].date << "." << endl;

  // Finds number of perfect days

  int perfect_day = 0;
  for (int pos = 0; pos < 365; pos++)
    if (temp[pos].low >= 55 && temp[pos].high < 81)
      perfect_day ++;

  if (perfect_day == 1)
    cout << "There was " << perfect_day << " perfect day in the year." << endl;
    else
      cout << "There were " << perfect_day << " perfect days in the year." << endl;

  // Finds number of significant cold fronts

  int cold_fronts = 0;
  for (int pos = 0; pos < 365; pos++)
    if ((temp[pos].low - temp[pos - 1].low) <= -15 ||
	(temp[pos].high - temp[pos - 1].high <= -15))
      cold_fronts++;

  if (cold_fronts == 1)
    cout << "There was " << cold_fronts << " significant cold front this year." << endl;
    else
      cout << "There were " << cold_fronts << " significant cold fronts this year." << endl;

  // Finds most common daily high temperature

  int number = temp[0].high;
  int mode = number;
  int count = 1;
  int count_mode = 1;

  for (int pos = 0; pos < 365; pos++)
  {
    if (temp[pos].high == number)
      count ++;
    else if (count > count_mode)
    {
      count_mode = count;
      mode = number;
    }
    else 
    {
      count = 1;
      number = temp[pos].high;
    }
  }

  if (mode == number && mode < number)
    cout << "The most common daily high temperature was " 
         << number << ". " << endl;
  else 
    cout << "The most common daily high temperature was " 
         << mode << ". "  << endl;

  return 0;
}
