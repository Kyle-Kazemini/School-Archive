/*
 * This program uses a function to compute ascii code and number of vowels in a string
 *
 * by Kyle Kazemini
 * u1127157
 * September 18, 2019
 */

#include <iostream>
#include <cmath>
#include <string>

using namespace std;

int count_vowels (string input);

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

  while (count != size)
  {
    outputLetter = input[count];
    outputNumber = (int)input[count];
    count++;
    cout << outputLetter << "  " << outputNumber << endl;
  }
   
  int vowels;
  vowels =  count_vowels (input);
 
  cout << endl << "There are " << vowels << " vowels.";
  return 0;
}

/*
 * This function counts the number of vowels in a string
 * 
 * Parameters:
 *    string - any string
 * 
 * Returns:
 *    int - number of vowels
*/

int count_vowels (string input)
{
  int size = input.length();
  int count = 0;
  int vowels = 0;

 while (count != size)
  {

  if(input[count]=='a' || input[count]=='A' || input[count]=='i' || input[count]=='I'
     || input[count]=='e' || input[count]=='E' || input[count]=='o' || input[count]=='O'
     || input[count]=='u' || input[count]=='U')
    vowels ++;
  count ++;
  }
 return vowels;
}
