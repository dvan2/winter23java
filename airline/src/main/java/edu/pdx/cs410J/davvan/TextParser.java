package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.Date;

import static edu.pdx.cs410J.davvan.Project3.createDate;
import static edu.pdx.cs410J.davvan.Project3.isValidDateAndTime;
import static java.lang.Integer.parseInt;

/**
 * This is the implementation of the <code>TextParser</code> class for Project 2.
 * This class is used to parse an airline from a file.
 */
public class TextParser implements AirlineParser<Airline> {
  /**
   * A private field of <code>Reader</code> that is used by the <code>parse()</code> function.
   */
  private final Reader reader;

  /**
   * This constructor accepts a <code>Reader</code> object to read from.
   * @param reader : A stream to read from.
   */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * This airline parses an airline from a file.
   * @return : An airline object which include a name and flights from the file.
   * @throws ParserException : If there is a problem reading from the file a ParserException is thrown.
   */
  @Override
  public Airline parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {
      String airline_name = br.readLine();
      if(airline_name == null){
        throw new ParserException("Missing airline name");
      }
      Airline new_airline = new Airline(airline_name);

      String current_line;
      while((current_line = br.readLine()) != null){
        String [] file_args= current_line.split(" ");
        if(file_args.length != 9){
          throw new ParserException("Error. Arguments mismatch.");
        }
        int flight_num;
        try {
          flight_num = parseInt(file_args[0]);
        }catch(NumberFormatException e){
          throw new ParserException("Flight number from text file was invalid.  Must be a number");
        }
        String source= file_args[1];
        String departure= file_args[2] + " " + file_args[3] + " " + file_args[4];
        String destination= file_args[5];
        String arrival = file_args[6] + " "  +file_args[7] + " " + file_args[8];

        if(!isValidDateAndTime(file_args[2], file_args[3], file_args[4])){
          throw new ParserException(" in the file on information about a flight's departure field.");
        }
        if(!isValidDateAndTime(file_args[6], file_args[7], file_args[8])){
          throw new ParserException(" in the file on information about a flight's arrival field.");
        }

        Date formated_depart;
        Date formated_arrive;
        try{
          formated_depart= createDate(departure);
          formated_arrive= createDate(arrival);
        }catch(ParseException e){
          throw new ParserException(e.getMessage());
        }

        Flight new_flight= new Flight(flight_num, source, formated_depart, destination, formated_arrive);
        try{
          new_flight.hasValidCode();
        }catch(IllegalArgumentException e){
          throw new ParserException("File has invalid flight information." + e.getMessage());
        }
        new_airline.addFlight(new_flight);
      }

      return new_airline;
    }catch(IOException e){
      throw new ParserException("Cannot parse from file. An IOException occured.");
    }catch(ParserException e){
      throw new ParserException(e.getMessage());
    }
  }
}
