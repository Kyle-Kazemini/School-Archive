
#include <iostream>
#include <fstream>
#include "BitMessage.h"

using namespace std;

int main (int argc, char** argv)
{

  BitMessage decode;
  int s;
  char filename;

  if (argc == 2)
  {
    ifstream in_file(argv[1]);
    // in_file.open (argv[1]);

    while (true)
    {
      in_file >> s;
     
      decode.append(s);
       if (in_file.fail())
      	break;
    }
    cout << decode.getMessage();
  }
  else 
  {
    cout << "An error occured. Please try again. " << endl;
    return 0;
  }
}
