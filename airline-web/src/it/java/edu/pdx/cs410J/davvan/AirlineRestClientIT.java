package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@TestMethodOrder(MethodName.class)
class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

  @Test
  void test0RemoveAllAirlines() throws IOException {
    AirlineRestClient client = newAirlineRestClient();
    client.removeAllDictionaryEntries();
  }

  @Test
  void test1EmptyServerContainsNoAirlines() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    Map<String, String> dictionary = client.getAllDictionaryEntries();
    assertThat(dictionary.size(), equalTo(0));
  }

  @Test
  void test2CreateFirstFlight() throws IOException, ParserException, ParseException {
    AirlineRestClient client = newAirlineRestClient();
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

    assertThat(airline.getName(), equalTo(airline_name));
    assertThat(airline.getFlights().iterator().next().getNumber(), equalTo(flight_number));
  }

  @Test
  void test4EmptyWordThrowsException() {
    AirlineRestClient client = newAirlineRestClient();
    String emptyString = "";

    HttpRequestHelper.RestException ex =
      assertThrows(HttpRequestHelper.RestException.class, () -> client.addFlight(emptyString, emptyString));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    assertThat(ex.getMessage(), equalTo(Messages.missingRequiredParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)));
  }}
