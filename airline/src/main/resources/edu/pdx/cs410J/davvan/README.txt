David Van
Project 3, extends the functionality of Project 1 and 2.  The main new functionality of Project3 is that you can now format an airline's information.
The PrettyPrinter class prints information about an airline that is to be read by human.

If -pretty is present in the options menu followed by a - symbol, then the program will output the airline created by the command line arguments to standard out.
If -pretty is present in the options followed by a valid file location, the program will output the contents of airlines from a valid airline file into a new file in a nice readable format.

The options should come before the arguments.  Available options are -README, -print, -textFile <filename>, -pretty - or -pretty <filename>.  These commands must come before the information about the airlines.
To use this program properly, ensure that the options are spelled correctly and the arguments comes in the order that is specified.

The 6 arguments and their orders are:
1. Airline name
2. The flight number
3. The source departure airport (3 letter code)
4. The departure date in the format mm/dd/yyyy hh:mm AM/PM(AM or PM)
5. The destination airport (3 letter code)
6. The arrival date in the format mm/dd/yyyy hh:mm AM/PM(AM or PM)

Separate these arguments with a space.