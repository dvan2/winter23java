package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.StringWriter;
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
    int flightNumber = 123;

    String airline_name = "Airline";
    int flight_number = 123;
    String flight_numberasString = "123";
    String source = "PDX";
    String depart = "12/8/2010 1:10 PM";
    String dest = "DEN";
    String arrive = "12/10/2010 12:13 AM";

    client.addFlight(airline_name, flight_numberasString, source, depart, dest, arrive);

    Airline airline = new Airline();
    airline = client.getAirline(airline_name);
    //System.out.println(airline.getPrettyAirline("mm/dd/yyyy hh:MM aa"));
    String s = airline.getPrettyAirline("MM/dd/yyyy hh:mm a");

    assertThat(airline.getName(), equalTo(airline_name));
    assertThat(airline.getFlights().iterator().next().getNumber(), equalTo(flight_number));
    assertThat(s, containsString(source));
    assertThat(s, containsString("12/08/2010 01:10 PM"));
    assertThat(s, containsString(dest));
    assertThat(s, containsString("12/10/2010 12:13 AM"));
  }

  /*
  @Test
  void test4EmptyWordThrowsException() {
    AirlineRestClient client = newAirlineRestClient();
    String emptyString = "";

    HttpRequestHelper.RestException ex =
      assertThrows(HttpRequestHelper.RestException.class, () -> client.addFlight(emptyString, emptyString));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    assertThat(ex.getMessage(), equalTo(Messages.missingRequiredParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)));
  }}

   */
}