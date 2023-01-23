package edu.pdx.cs410J.davvan;

import com.google.common.annotations.VisibleForTesting;

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



  public static void main(String[] args) {
    if(args.length == 0) {
      System.err.println("Missing command line arguments");
      return;
    }

    //check flight number
    try{
      parseInt(args[1]);
    }catch(NumberFormatException e){
      System.err.println("Flight number must be a number.");
      return;
    }

    if(!isValidDateAndTime(args[3], args[4]))
      return;
    if(!isValidDateAndTime(args[6], args[7]))
      return;

    String full_depart_d= args[3] + " " + args[4];
    String full_arrive_d= args[6] + " " + args[7];


    Flight flight = new Flight(parseInt(args[1]), args[2], full_depart_d,
            args[5], full_arrive_d);
    try{
      flight.hasValidCode();
    }catch(IllegalArgumentException e){
      System.out.println(e);
      return;
    }

    Airline an_airline= new Airline(args[0], flight);
    an_airline.displayAirline();
  }
}