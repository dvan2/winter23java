package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

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
   * @param reader
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
        if(file_args.length != 7){
          throw new ParserException("Error. Arguments mismatch.");
        }
        int flight_num= parseInt(file_args[0]);
        String source= file_args[1];
        String departure= file_args[2] + " " + file_args[3];
        String destination= file_args[4];
        String arrival = file_args[5] + " "  +file_args[6];

        Flight new_flight= new Flight(flight_num, source, departure, destination, arrival);
        new_airline.addFlight(new_flight);
      }

      return new_airline;
    }catch(IOException e){
      System.out.println("Here");
      throw new ParserException("Cannot parse from file.");
    }catch(ParserException e){
      throw new ParserException("Cannot parse the airline info.");
    }
  }
}
