/*
 * This program plays a single game of rock-paper-scissors with the user.
 * 
 * by Kyle Kazemini
 * u1127157
 * September 3, 2019
 */

#include <iostream>
#include <cmath>
#include <cstdlib>
using namespace std;

int main ()
{

  string userChoice;
  cout << "Welcome to Rock-Paper-Scissors." << endl;
  cout << "Please choose rock, paper, or scissors." << endl;
  cin >> userChoice;

  int myChoice = rand() % 3 + 1;
  // rock = 1 paper = 2 scissors = 3

  if (userChoice == "rock")
    {
      if (myChoice == 1)
	cout << "You chose: rock." << endl << "I chose rock." << endl << "We tied!" << endl;
      else if (myChoice == 2)
        cout << "You chose: rock." << endl << "I chose paper." << endl << "I win!" << endl;
      else if (myChoice == 3)
        cout << "You chose: rock." << endl << "I chose scissors." << endl << "You win!" << endl;
    }

  if (userChoice == "paper")
    {
      if (myChoice == 1)
	cout << "You chose: paper." << endl << "I chose rock." << endl << "You win!" << endl;
      else if (myChoice == 2)
        cout << "You chose: paper." << endl << "I chose paper." << endl << "We tied!" << endl;
      else if (myChoice == 3)
        cout << "You chose: paper." << endl << "I chose scissors." << endl << "I win!" << endl;
    }

 if (userChoice == "scissors")
    {
      if (myChoice == 1)
	cout << "You chose: scissors." << endl << "I chose rock." << endl << "I win!" << endl;
      else if (myChoice == 2)
        cout << "You chose: scissors." << endl << "I chose paper." << endl << "You win!" << endl;
      else if (myChoice == 3)
        cout << "You chose: scissors." << endl << "I chose scissors." << endl << "We tied!" << endl;
    }





}
