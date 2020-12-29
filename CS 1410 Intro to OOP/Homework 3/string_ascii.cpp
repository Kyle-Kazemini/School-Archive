/*
 * This program displays ascii values and determines the number of vowels for a user input.
 *
 * by Kyle Kazemini
 * u1127157
 * September 9, 2019
 */

#include <iostream>
#include <cmath>
#include <string>

using namespace std;

int main()
{

  string input;
  cout << "This program computes ascii code." << endl;
  cout << "Enter any word (without spaces in it): " << endl;
  cin >> input;
  int size = input.length();
  int count = 0;
  char outputLetter;
  int outputNumber= 0;
  int vowels = 0;

  while (count != size)
  {
    outputLetter = input[count];
    outputNumber = (int)input[count];
    count++;
    cout << outputLetter << "  " << outputNumber << endl;
  
  if(input[count]=='a' || input[count]=='A' || input[count]=='i' || input[count]=='I'
     || input[count]=='e' || input[count]=='E' || input[count]=='o' || input[count]=='O'
     || input[count]=='u' || input[count]=='U')
      vowels = vowels + 1;
  }

cout << endl << "There are " << vowels << " vowels.";

}
