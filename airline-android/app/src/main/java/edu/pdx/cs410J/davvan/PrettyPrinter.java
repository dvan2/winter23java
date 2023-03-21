package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AirlineDumper;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

/**
 * This class implements an AirlineDumper interface to pretty print an airline.
 */
public class PrettyPrinter implements AirlineDumper<Airline> {
  private final Writer writer;

  /**
   * This constructor sets the writer private field, given a place to write to.
   * @param writer
   */
  public PrettyPrinter(Writer writer){
    this.writer= writer;
  }

  /**
   * This method takes an airline and dumps its content in a pretty format by calling airline functions.
   * @param airline : Takes an Airline object as parameter.
   */
  @Override
  public void dump(Airline airline){
    String format= "MMM d, yyyy h:mm a";
    //SimpleDateFormat pretty_format= new SimpleDateFormat("MMM d yyyy h a");
    try (
            PrintWriter pw = new PrintWriter(this.writer)
    ) {
      pw.print(airline.getName());
      pw.print(" Airlines:");
      pw.print(airline.getPrettyAirline(format));
    }
  }
}
