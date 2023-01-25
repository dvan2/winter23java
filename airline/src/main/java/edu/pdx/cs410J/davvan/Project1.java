package edu.pdx.cs410J.davvan;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
  /**
   * This function is used to make sure date arguments are in the right format.
   * @param date
   * @param time
   * @return
   */
  @VisibleForTesting
  static boolean isValidDateAndTime(String date, String time) {
    SimpleDateFormat date_format= new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat hour_format= new SimpleDateFormat("hh:mm");
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
    boolean readme= false;

    //verify there's at least 1 argument for README
    //Implement so that user can use -README option alone to open readme.
    if(args.length == 1 && !args[0].equals("-README")){
      System.err.println("Not enough arguments for flight.  Use -README to read ");
    }



    //assume options always comes first
    if(args[0].equals("-README") || args[1].equals("-README")) {
      readme = true;
      options++;
    }
    if(args[0].equals("-print") || args[1].equals("-print")) {
      print= true;
      options++;
    }


    if(args.length > NUM_ARGS+ options){
      System.err.println("There is too many arguments for a flight.");
      return;
    }

    if(args.length < NUM_ARGS + options){
      System.err.println("There is not enough arguments for a flight.");
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
      System.out.println(e);
      return;
    }

    Airline an_airline= new Airline(args[0 + options], flight);

    if(print) {
      an_airline.displayAirline();
    }
    if(readme){
      try{
        InputStream read= Project1.class.getResourceAsStream("README.txt");
        BufferedReader reader= new BufferedReader(new InputStreamReader(read));
        String my_readme= reader.readLine();
        System.out.println(my_readme);
      }catch(IOException e){
        System.err.println("Error in trying to access Readme file.");
      }
    }
  }
}