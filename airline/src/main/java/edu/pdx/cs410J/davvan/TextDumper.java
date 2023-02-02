package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AirlineDumper;

import java.io.*;

/**
 * This class is the implementation of the <code>TextDumper</code> class for Project 2.
 * This class is used to dump an airline to a file.
 */
public class TextDumper implements AirlineDumper<Airline> {
  /**
   * A private writer object that is utilized by the code<>dump</code> method.
   */
  private final Writer writer;
  /**
   * This private boolean tells if the dumper should write the airline name to the file or not.
   */
  private boolean print_airline;

  /**
   * This constructor creates a new dumper with two argument
   * @param writer : A file writer object can be passed to tell where to write.
   * @param print_airline : A boolean can be provided to signify if the dumper should print airline name.
   */
  public TextDumper(Writer writer, boolean print_airline) {
    this.writer = writer;
    this.print_airline = print_airline;
  }

  /**
   * This method can set the <code>print_airline</code> private field to signify if dumper should print airline name.
   * @param to_set
   */
  public void setPrintName(boolean to_set){
    this.print_airline = to_set;
  }

  /**
   * This method dumps the content of an airline.
   * If <code>print_airline</code> is true, it will print the airline name otherwise it will only print the flights.
   * @param airline
   */
  @Override
  public void dump(Airline airline) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
      ) {

      if(print_airline){
        pw.print(airline.getName());
      }
      pw.print(airline.getAirline());

      pw.flush();
    }
  }
}
