package edu.pdx.cs410J.davvan;

import com.google.common.annotations.VisibleForTesting;

import static java.lang.Integer.parseInt;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

  public static void main(String[] args) {
    //System.err.println("Missing command line arguments");

    if(args.length == 0) {
      System.err.println("Missing command line arguments");
      //System.exit(1);
    }else {
      int len = args.length;
      Flight flight = new Flight(parseInt(args[0]), args[1]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath

      flight.display();
    }





  }

}