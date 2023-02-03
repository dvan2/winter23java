David Van
Like Project 1, in Project2, the user is still able to enter information about an airline and use print or readme option to tell the program what to do.

In Project2, however, new functionality is added so that the airline entered can be saved in a file.
A user can create a new airline and optionally use the textfile option to save that airline.
If the file already exists, the program will check if the airline name matches.  If matches, the flight will be added to the file.
If the file exists but the airline name doesn't match, the program will not add the flight to the file.

The options should come before the arguments.  Available options are -README and -print -textFile <filename>.  These commands must come before the information about the airlines.
To use this program properly, ensure that the options are spelled correctly and the arguments comes in the order that is specified.

The 6 arguments and their orders are:
1. Airline name
2. The flight number
3. The source departure airport (3 letter code)
4. The departure date in the format mm/dd/yyyy hh:mm
5. The destination airport (3 letter code)
6. The arrival date in the format mm/dd/yyyy hh:mm

Separate these arguments with a space.