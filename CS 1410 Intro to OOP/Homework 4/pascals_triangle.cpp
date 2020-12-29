/*
 * This program computes the first 14 rows of Pascal's triangle
 *
 * by Kyle Kazemini
 * u1127157
 * September 18, 2019
 */

#include <iostream>

using namespace std;
int pascal (int row, int col);

/* Application entry point
 */
int main ()
{
  int row,col = 0;

  while (row < 14)
  {
    while (col <= row)
    {
      int result = pascal (row, col);
      cout << result << " ";
      col++;
    }
    cout << endl;
    col = 0;
    row++;
  }
  return 0;
}

/* This function computes the first 14 rows of Pascal's triangle
 * 
 * Parameters:
 *    int col - number of columns
 *    int row - number of rows
 * 
 * Return: int - numbers in triangle
 */

int pascal (int row, int col)
{

  if (col == 0 || row == col)
    return 1;
  else
    return pascal(row - 1, col - 1) + pascal(row - 1, col);

}
