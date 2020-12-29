/*
 * This program asks for a string from the user and counts vowels,
 * determines if the word is palindrome, and reverses the order of the word.
 *
 * by Kyle Kazemini
 * u1127157
 * September 17, 2019
 */

#include <iostream>

using namespace std;

// Forward declaration of a function.  This declares the function,
//   but does not define it.  (Notice that there is no code, just
//   a function header with a semicolon after it.

int count_vowels (string input);
string reverse_string (string input);
bool palindrome (string input);

/* Our main function.  This is a experiment for lab #4.
 *
 * Parameters:
 *    none
 *
 * Return value:
 *    0 if we complete successfully, 1 if there was an error.
 */
int main ()
{
  string input;

  // Get a word from the user.

  cout << "Enter a word: ";
  cin >> input;

  // Print out the original word.

  cout << "You entered this word: " << input << endl;
  
  // Count the vowels in the word.

  count_vowels (input); /* Declare a variable named 'vowels' to hold the result. */
  int vowels;
  vowels = count_vowels (input);
  cout << "There are " << vowels << " vowels in your word." << endl;

  // Determine if the word is a palindrome.

  bool palindrome_variable = true;
  palindrome_variable = palindrome (input);
  if (palindrome_variable == true)
    cout << "The word is palindrome." << endl;
  else 
    cout << "The word is not palindrome." << endl;

  // Print out the reverse of the word.

  string reverse;
  reverse = reverse_string (input);
  cout << reverse << endl;

  // Done, exit the application.

  return 0;  // no error, so we return a 0.
}

/* This function counts the number of a, e, i, o, or u characters
 * in a text string.
 *
 * Parameters:
 *    string text -- any text string
 *
 * Return value:
 *    int -- the number of vowels in the word
 */
int count_vowels (string word)
{
  int count = 0;
  int position = 0;
  while (position != word.length())
  {
    char ch;
    ch = word[position];
    if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u')
      count++;
    position++;
  }
  return count;
}

/* This function reverses the order of a string
 *
 * Parameters: 
 *   string text - any text string
 *
 * Returns:
 *   output - string
 */
string reverse_string (string input)
{
  int length = input.length();
  for (int i = 0; i < length/2; i++)
    swap(input[i], input[length - i - 1]);
  return input;
}

/* This function determines if a string is palindrome
 *
 * Parameters:  
 *   string text - any text string
 *
 * Returns:
 *   boolean - true if palindrome, false otherwise
 */

bool palindrome (string input)
{
  string palindrome = "palindrome";
  if (input == palindrome)
    return true;
  else 
    return false;
}
