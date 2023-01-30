package edu.pdx.cs410J.davvan;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
  /**
   * Use this string to output no enough arguments.
   */
  public static final String NOT_ENOUGH_ARGS = "There are not enough arguments for a flight.";
  /**
   * Use this string to output too many arguments.
   */
  public static final String TOO_MANY_ARGS = "There is too many arguments for a flight.";
  public static void usage() {
    System.out.println("usage: java -jar target/airline-2023.0.0.jar [options] <args>");
    System.out.println("args are (in this order):");
    System.out.printf("airline:\tThe name of the airline\n" +
            "flightNumber:\tThe flight number\n" +
            "src:\tThree-letter code of departure airport\n" +
            "depart:\tDeparture date and time (mm/dd/yyyy HH:mm)\n" +
            "dest:\tThree-letter code for arrival airport\n" +
            "arrive:\tArrival date and time (mm/dd/yyyy HH:mm\n\n" +
            "Options are (options may appear in any order):\n" +
            "-print:\tPrints a description of the newly added flight\n" +
            "-README\tPrints a README for this project and exits.\n");
  }

  /**
   * This function is used to make sure date arguments are in the right format.
   * If date and time is not valid an error is displayed.
   * @param date Check if this string is in mm/dd/yyyy format
   * @param time Check if this string is in HH:mm format
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
      System.err.print("Invalid date input");
      return false;
    }
    try{
      hour_format.parse(time);
    }catch(ParseException e){
      System.err.print("Invalid hour input");
      return false;
    }
    int countslash= 0;
    for(int i=0; i<date.length(); i++){
      if(date.charAt(i) == '/'){
        ++countslash;
      }
    }
    if(date.length() > 10 || countslash>2){
      System.err.print("Date is malformed");
      return false;
    }
    if(time.length()> 5){
      System.err.print("Time is malformed");
      return false;
    }
    return true;
  }

  /**
   * This method opens the README.txt which exists in resources.
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
      usage();
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

    String raw_depart_date = args[3 + options];
    String raw_depart_time = args[4 + options];
    if(!isValidDateAndTime(raw_depart_date, raw_depart_time)){
      System.err.println(" in departure fields.");
      return;
    }

    String raw_arrival_date = args[6 + options];
    String raw_arrival_time = args[7 + options];
    if(!isValidDateAndTime(raw_arrival_date, raw_arrival_time)){
      System.err.println(" in arrival fields.");
      return;
    }

    String full_depart_d= raw_depart_date + " " + raw_depart_time;
    String full_arrive_d= raw_arrival_date + " " + raw_arrival_time;

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
    try {
      FileWriter file= new FileWriter("airline.txt");
      TextDumper my_dumper= new TextDumper(file);
      my_dumper.dump(an_airline);
    }catch(IOException e){
      System.err.println("Error reading from file.");
    }

  }
}