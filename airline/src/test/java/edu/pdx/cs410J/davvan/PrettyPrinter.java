package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AirlineDumper;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

public class PrettyPrinter implements AirlineDumper<Airline> {
    private final Writer writer;

    public PrettyPrinter(Writer writer){
        this.writer= writer;
    }
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
            pw.flush();
        }
    }
}
