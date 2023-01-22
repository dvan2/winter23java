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


    Flight flight = new Flight(parseInt(args[1]), args[2], args[3]);

    if(!isValidDateAndTime(args[3], args[4]))
      return;
    Airline an_airline= new Airline(args[0], flight);
    an_airline.displayAirline();

  }

}