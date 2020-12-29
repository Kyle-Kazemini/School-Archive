/* 
 * Starting code for counting vowels in lab #4.
 */

#include <iostream>
#include <string>

using namespace std;

// Forward declaration of a function.  This declares the function,
//   but does not define it.  (Notice that there is no code, just
//   a function header with a semicolon after it.

int count_vowels (string input);

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

  // Print out the reverse of the word.

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
