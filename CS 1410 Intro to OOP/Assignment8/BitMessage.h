#include <iostream>
#include <string>

/*
 * This class/program decode bits and print a message.
 * Function contracts are in .cpp file
 * 
 * Kyle Kazemini
 * u1127157
 * October 28, 2019
 */


class BitMessage
{

 private:

  std::string message;

  char temp;

  int count;

 public:

  BitMessage ();

  void append(int bit);

  std::string getMessage ();

};
