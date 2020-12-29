/*
 * This program displays the number of days in a month that the user inputs.
 * 
 * by Kyle Kazemini
 * u1127157
 * August 31, 2019
 */

#include <iostream>
#include <cmath>

int main ()
{

  int month;
  int year;
  std::cout << "Enter a month: ";
  std::cin >> month;
  if (!(0 <= month && 12 >= month))
   {
      std::cout << "Error";
      return 0;
   }
  std::cout << "Enter a year: ";
  std::cin >> year;
  
  if (year % 4 == 0 && month == 2){
    std::cout << "February " << year << " has 29 days.";
    }
  if (month == 1){
    std::cout << "January " << year << " has 31 days.";
    }
  if (month == 2){
    std::cout << "February " << year << " has 28 days.";
  }
  if (month == 3){
    std::cout << "March " << year << " has 31 days.";
  }
  if (month == 4){
    std::cout << "April " << year << " has 30 days.";
  }
  if (month == 5){
    std::cout << "May " << year << " has 31 days.";
  }
  if (month == 6){
    std::cout << "June " << year << " has 30 days.";
  }
  if (month == 7){
    std::cout << "July " << year << " has 31 days.";
  }
  if (month == 8){
    std::cout << "August " << year << " has 31 days.";
  }
  if (month == 9){
    std::cout << "September " << year << " has 30 days.";
  }
  if (month == 10){
    std::cout << "October " << year << " has 31 days.";
  }
  if (month == 11){
    std::cout << "November " << year << " has 30 days.";
  }
  if (month == 12){
    std::cout << "December " << year << " has 31 days.";
  }
}

