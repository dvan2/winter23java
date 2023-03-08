package edu.pdx.cs410J.davvan;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
  static final String AIRLINE_NAME_PARAMETER = "airline";
  static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";
  static final String SOURCE_PARAMETER = "src";
  static final String DEPART_PARAMETER = "depart";
  static final String DEST_PARAMETER = "dest";
  static final String ARRIVE_PARAMETER = "arrive";

  private final Map<String, Airline> airlines = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing the definition of the
   * word specified in the "word" HTTP parameter to the HTTP response.  If the
   * "word" parameter is not specified, all the entries in the dictionary
   * are written to the HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airline_name = getParameter(AIRLINE_NAME_PARAMETER, request );
      String source = getParameter(SOURCE_PARAMETER, request);
      String dest = getParameter(DEST_PARAMETER, request);

      if (airline_name != null && source== null && dest== null) {
          writeFlights(airline_name, response);
      }else if(airline_name != null && source != null && dest != null){
          writeFlights(airline_name, response, source, dest);
      }
  }

  /**
   * Handles an HTTP POST request by storing the dictionary entry for the
   * "word" and "definition" request parameters.  It writes the dictionary
   * entry to the HTTP response.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airline_name = getParameter(AIRLINE_NAME_PARAMETER, request );
      if (airline_name == null) {
          missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
          return;
      }

      String flight_num_string = getParameter(FLIGHT_NUMBER_PARAMETER, request );
      if ( flight_num_string == null) {
          missingRequiredParameter( response, FLIGHT_NUMBER_PARAMETER);
          return;
      }

      String source_airport = getParameter(SOURCE_PARAMETER, request);
      String depart = getParameter(DEPART_PARAMETER, request);
      String dest = getParameter(DEST_PARAMETER, request);
      String arrive = getParameter(ARRIVE_PARAMETER, request);


      Flight flight = new Flight();
      try {
          flight.createFlight(flight_num_string, source_airport, depart, dest, arrive);
      } catch (ParseException | IOException e) {
          response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
          throw new IOException("Can't create flight. " + e.getMessage());
      }

      Airline airline = new Airline(airline_name);
      airline.addFlight(flight);

      if(airlines.containsKey(airline_name)) {
          airline = airlines.get(airline_name);
          airline.addFlight(flight);
          this.airlines.put(airline_name, airline);
      }
      else {
          this.airlines.put(airline_name, airline);
      }

      PrintWriter pw = response.getWriter();
      pw.println(Messages.definedWordAs(airline_name, flight_num_string));
      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK);
  }

  /**
   * Handles an HTTP DELETE request by removing all dictionary entries.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/plain");

      this.airlines.clear();

      PrintWriter pw = response.getWriter();
      pw.println(Messages.allDictionaryEntriesDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = Messages.missingRequiredParameter(parameterName);
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Writes a flights of the given airline name to the HTTP response.
   *
   * The text of the message is formatted with {@link XmlDumper}
   */
  private void writeFlights(String airline_name, HttpServletResponse response) throws IOException {
    Airline airline = this.airlines.get(airline_name);

    if (airline == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      //response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cannot find airport name");

    } else {
      PrintWriter pw = response.getWriter();

      XmlDumper dumper = new XmlDumper(pw);
      dumper.dump(airline);

      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

    private void writeFlights(String airline_name, HttpServletResponse response, String source, String dest) throws IOException {
        Airline airline = this.airlines.get(airline_name);

        if (airline == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            //airline name exists but check for source and dest now
            if(airline.flightFound(source, dest)) {
                //if the flight exists with src, dest write it
                PrintWriter pw = response.getWriter();

                XmlDumper dumper = new XmlDumper(pw);
                dumper.dump(airline, source, dest);

                response.setStatus(HttpServletResponse.SC_OK);

            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        }

    }


  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

  @VisibleForTesting
  Airline getAirline(String airlineName) {
      return this.airlines.get(airlineName).getAirline();
  }
}
