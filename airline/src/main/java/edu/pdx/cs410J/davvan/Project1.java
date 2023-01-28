package edu.pdx.cs410J.davvan;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
  public static final String NOT_ENOUGH_ARGS = "There are not enough arguments for a flight.";
  public static final String TOO_MANY_ARGS = "There is too many arguments for a flight.";

  /**
   * This function is used to make sure date arguments are in the right format.
   * If date and time is not valid an error is displayed.
   * @param date: Check if this string is in mm/dd/yyyy format
   * @param time: Check if this string is in hh:mm format
   * @return returns true if date and time is valid and false otherwise.
   */
  @VisibleForTesting
  static boolean isValidDateAndTime(String date, String time) {
    //Used online resources: https://www.javatpoint.com/java-simpledateformat
    SimpleDateFormat date_format= new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat hour_format= new SimpleDateFormat("HH:mm");
    date_format.setLenient(false);
    hour_format.setLenient(false);
    try{
      date_format.parse(date);
    }catch(ParseException e){
      System.err.println("Invalid date input");
      return false;
    }
    try{
      hour_format.parse(time);
    }catch(ParseException e){
      System.err.println("Invalid hour input.");
      return false;
    }
    return true;
  }

  /**
   * This method opens the README.txt which exists in resources
   * Throws an IOException if there is an error reading from file
   * @throws IOException
   */
  public static void openReadme() throws IOException{

    try {
      InputStream read = Project1.class.getResourceAsStream("README.txt");
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
  static final int NUM_ARGS= 8;

  public static void main(String[] args) {
    if(args.length == 0) {
      System.out.println("To use this program enter information about a flight in this order: ");
      System.out.println("Airline name, flight number, 3 letter source airport code, departure date in the format of mm/dd/yyyy hh:mm," +
              " 3 letter destination airport code and arrival date in the format mm/dd/yyyy hh:mm.");
      System.out.println("Use the -print or -README options before the arguments to display information about a flight or " +
              "read information about the program.");
      return;
    }
    int options= 0;
    boolean print= false;

    int current= 0;
    while (current < args.length && (args[current].charAt(0) == '-')){
      boolean readme_current= args[current].equals("-README");
      boolean print_current = args[current].equals("-print");
      if((!readme_current) && (!print_current)) {
        System.err.println("There is an invalid option.  Please check spelling.");
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
      if(print_current){
        print = true;
        ++options;
      }
      ++current;
    }

    if(args.length > NUM_ARGS+ options){
      System.err.println(TOO_MANY_ARGS);
      return;
    }
    if(args.length < NUM_ARGS + options){
      System.err.println(NOT_ENOUGH_ARGS);
      return;
    }

    //check flight number
    try{
      parseInt(args[1 + options]);
    }catch(NumberFormatException e){
      System.err.println("Flight number must be a number.");
      return;
    }

    if(!isValidDateAndTime(args[3 + options], args[4+ options]))
      return;
    if(!isValidDateAndTime(args[6+ options], args[7 + options]))
      return;

    String full_depart_d= args[3+ options] + " " + args[4+ options];
    String full_arrive_d= args[6 + options] + " " + args[7 + options];

    Flight flight = new Flight(parseInt(args[1 + options]), args[2 + options], full_depart_d,
            args[5 + options], full_arrive_d);
    try{
      flight.hasValidCode();
    }catch(IllegalArgumentException e){
      System.err.println(e.getMessage());
      return;
    }

    Airline an_airline= new Airline(args[0 + options], flight);
    if(print) {
      an_airline.displayAirline();
    }
  }
}