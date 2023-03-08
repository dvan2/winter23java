package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {
    /**
     * Use this string to output no enough arguments.
     */
    public static final String NOT_ENOUGH_ARGS = "There are not enough arguments for a flight.";
    /**
     * Use this string to output too many arguments.
     */
    public static final String TOO_MANY_ARGS = "There is too many arguments for a flight.";

    public static final String MISSING_ARGS = "Missing command line arguments";



    public static void usage() {
        System.out.println("usage: java -jar target/airline-2023.0.0.jar [options] <args>");
        System.out.println("args are (in this order):");
        System.out.printf("airline:\tThe name of the airline\n" +
                "flightNumber:\tThe flight number\n" +
                "src:\tThree-letter code of departure airport\n" +
                "depart:\tDeparture date and time (mm/dd/yyyy HH:mm AM/PM)\n" +
                "dest:\tThree-letter code for arrival airport\n" +
                "arrive:\tArrival date and time (mm/dd/yyyy HH:mm AM/PM\n\n" +
                "Options are (options may appear in any order):\n" +
                "-host:\tHost of the web server" +
                "-port:\tPort of the web server" +
                "-print:\tPrints a description of the newly added flight\n" +
                "-README\tPrints a README for this project and exits.\n");
    }

    public static void openReadme() throws IOException {

        try {
            InputStream read = Project5.class.getResourceAsStream("README.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(read));
            String my_readme;
            while ((my_readme = reader.readLine()) != null) {
                System.out.println(my_readme);
            }
        } catch (IOException e) {
            throw new IOException("Cannot read from file.");
        }
    }



    /**
     * Keep track of the total number of arguments expected
     */
    static final int NUM_ARGS= 10;

    /**
     * Main class
     * @param args : All command line arguments should be passed in args.
     */


    public static void main(String [] args) {
        //My main method:
        if(args.length == 0) {
            usage();
            return;
        }
        int options= 0;
        String host = null;
        String port= null;

        boolean search_airline= false;
        boolean print= false;
        boolean search_source = false;


        int current= 0;
        while (current < args.length && (args[current].charAt(0) == '-')){
            boolean readme_current= args[current].equals("-README");
            boolean host_current= args[current].equals("-host");
            boolean port_current= args[current].equals("-port");
            boolean search_current= args[current].equals("-search");
            boolean print_current = args[current].equals("-print");
            //boolean pretty_print_current = args[current].equals("-pretty");
            if((!readme_current) && (!host_current) && (!port_current) &&(!search_current) && (!print_current)) {
                System.err.println("Unknown option command.  Please check spelling.");
                return;
            }
            if(readme_current){
                try {
                    openReadme();
                }catch (IOException e){
                    e.getMessage();
                }
                return;
            }
            if(host_current){
                if(current + 1 == args.length){
                    System.err.println("Error.  No host name provided.");
                    return;
                }
                host= args[current + 1];
                options+= 2;
                //skip the next argument which will be the file name
                ++current;
            }
            if(port_current){
                if(current + 1 == args.length) {
                    System.err.println("No port name provided.");
                    return;
                }
                port = args[current + 1];
                ++current;
                options += 2;
            }

            if(search_current){
                if(current + 1 == args.length){
                    System.out.println("Error. No airline name provided.");
                    return;
                }
                //if there is 3 arguments after options with search
                if(current + 2 == args.length){
                    search_airline = true;
                }
                if(current + 4 == args.length) {
                    search_source = true;
                }
                ++options;
                ++current;
            }
            if(print_current) {
                print = true;
                options++;
            }
            ++current;
        }

        if(args.length > NUM_ARGS+ options){
            System.err.println(TOO_MANY_ARGS);
            return;
        }
        if(args.length < NUM_ARGS + options && !search_airline &&!search_source){
            System.err.println(NOT_ENOUGH_ARGS);
            return;
        }

        int port_number;
        try {
            port_number = Integer.parseInt( port );

        } catch (NumberFormatException ex) {
            usage("Port \"" + port + "\" must be an integer");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(host, port_number);

        String airline_name = args[0 + options];


        if(search_airline || search_source) {
            try {
                Airline to_pretty = new Airline();
                if (search_airline) {
                    to_pretty = client.getAirline(airline_name);

                } else if (search_source) {
                    String source = args[1 + options];
                    String dest = args[2 + options];

                    to_pretty = client.getAirline(airline_name, source, dest);
                }

                PrettyPrinter dumper = new PrettyPrinter(new PrintWriter(System.out, true));
                dumper.dump(to_pretty);
                return;

            }catch (IOException e) {
                System.err.println("Io exception: " + e.getMessage());
            }catch(ParserException e) {
                System.err.println(e.getMessage());
            } catch(HttpRequestHelper.RestException e) {
                System.err.println("Error.  Cannot match airport.");
                return;
            }
        }

        String source = args[2 + options];
        String dest = args[6 + options];
        Flight flight = new Flight();
        String flight_number= args[1 + options];

        String depart = args[3 + options] + " " +  args[4 + options] + " " + args[5 + options];

        String arrive = args[7 + options] +" " +  args[8 + options] + " " +  args[9 + options];
        try {
            flight.createFlight(flight_number, source, depart, dest, arrive);
        } catch (ParseException | IOException e) {
            System.err.println("Cannot create flight. " + e.getMessage());
            return;
        }


        String message;
        try {
            client.addFlight(airline_name, flight_number, source, depart, dest, arrive);
            message = Messages.definedWordAs(airline_name, flight_number);

            Airline airline= new Airline(airline_name);
            airline.addFlight(flight);

            if(print) {
                airline.displayAirline();
            }

        } catch (IOException ex ) {
            error("While contacting server: " + ex.getMessage());
            return;
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }


    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project5 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();
    }

}