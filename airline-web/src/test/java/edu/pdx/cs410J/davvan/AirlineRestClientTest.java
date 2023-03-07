package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A unit test for the REST client that demonstrates using mocks and
 * dependency injection
 */
public class AirlineRestClientTest {


  @Test
  void getAllDictionaryEntriesPerformsHttpGetWithNoParameters() throws ParserException, IOException, ParseException {
    String airlineName= "Airline";
    int flightNumber = 123;

    String airline_name = "Airline";
    int flight_number= 123;
    String flight_numberasString = "123";
    String source = "PDX";
    String depart = "12/12/2010 12:12 pm";

    String dest = "DEN";
    String arrive = "12/13/2010 1:01 am";
    Airline airline = new Airline(airlineName);
    Flight flight= new Flight();
    flight.createFlight(flight_numberasString, source, depart, dest, arrive);

    airline.addFlight(flight);

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName)))).thenReturn(airlineAsText(airline));

    AirlineRestClient client = new AirlineRestClient(http);

    Airline read = client.getAirline(airlineName);
    assertThat(read.getName(), equalTo(airlineName));
    assertThat(read.getFlights().iterator().next().getNumber(), equalTo(flightNumber));
  }


  private HttpRequestHelper.Response airlineAsText(Airline airline) throws IOException {
    StringWriter writer = new StringWriter();
    new XmlDumper(writer).dump(airline);

    return new HttpRequestHelper.Response(writer.toString());



  }



}
