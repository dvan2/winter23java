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
  public static final String AM_PM_PATTERN = "MM/dd/yyyy hh:mm a";

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
  void test2CreateFlights() throws IOException, ParserException{
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
    String s = airline.getPrettyAirline(AM_PM_PATTERN);

    assertThat(airline.getName(), equalTo(airline_name));
    assertThat(airline.getFlights().iterator().next().getNumber(), equalTo(flight_number));
    assertThat(s, containsString(source));
    assertThat(s, containsString("12/08/2010 01:10 PM"));
    assertThat(s, containsString(dest));
    assertThat(s, containsString("12/10/2010 12:13 AM"));

    //test new airline
    String airline_name1 = "Delta";
    int flight_number1 = 343;
    String flight_numberasString1 = "343";
    String source1 = "TUL";
    String depart1 = "12/8/2010 12:30 PM";
    String dest1 = "LAX";
    String arrive1 = "12/10/2010 1:20 AM";

    client.addFlight(airline_name1, flight_numberasString1, source1, depart1, dest1, arrive1);

    Airline airline1 = new Airline();
    airline1 = client.getAirline(airline_name1);
    //System.out.println(airline.getPrettyAirline("mm/dd/yyyy hh:MM aa"));
    String s1 = airline1.getPrettyAirline(AM_PM_PATTERN);

    System.out.println("String \n " + s1);
    assertThat(airline1.getName(), equalTo(airline_name1));
    assertThat(airline1.getFlights().iterator().next().getNumber(), equalTo(flight_number1));
    assertThat(s1, containsString(source1));
    assertThat(s1, containsString("12/08/2010 12:30 PM"));
    assertThat(s1, containsString(dest1));
    assertThat(s1, containsString("12/10/2010 01:20 AM"));

  }

  @Test
  void test3createNewAirline() throws IOException, ParserException, ParseException {
    AirlineRestClient client = newAirlineRestClient();
    int flightNumber = 123;

    String airline_name = "Delta";
    int flight_number = 343;
    String flight_numberasString = "343";
    String source = "TUL";
    String depart = "12/8/2010 12:30 PM";
    String dest = "LAX";
    String arrive = "12/10/2010 1:20 AM";

    client.addFlight(airline_name, flight_numberasString, source, depart, dest, arrive);

    Airline airline = new Airline();
    airline = client.getAirline(airline_name);
    //System.out.println(airline.getPrettyAirline("mm/dd/yyyy hh:MM aa"));
    String s = airline.getPrettyAirline(AM_PM_PATTERN);


    System.out.println("String \n " + s);
    assertThat(airline.getName(), equalTo(airline_name));
    assertThat(airline.getFlights().iterator().next().getNumber(), equalTo(flight_number));
    assertThat(s, containsString(source));
    assertThat(s, containsString("12/08/2010 12:30 PM"));
    assertThat(s, containsString(dest));
    assertThat(s, containsString("12/10/2010 01:20 AM"));
  }

  @Test
  void searchFlightBySourceAndDest() throws IOException, ParserException {
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

    //test new airline
    String airline_name1 = "Delta";
    int flight_number1 = 343;
    String flight_numberasString1 = "343";
    String source1 = "TUL";
    String depart1 = "12/8/2010 12:30 PM";
    String dest1 = "LAX";
    String arrive1 = "12/10/2010 1:20 AM";

    client.addFlight(airline_name1, flight_numberasString1, source1, depart1, dest1, arrive1);
    Airline airline1 = new Airline();

    Airline airline = new Airline();
    airline = client.getAirline(airline_name1, source1, dest1);
    //System.out.println(airline.getPrettyAirline("mm/dd/yyyy hh:MM aa"));
    String s = airline.getPrettyAirline(AM_PM_PATTERN);

    assertThat(airline.getName(), equalTo(airline_name1));
    assertThat(airline.getFlights().iterator().next().getNumber(), equalTo(flight_number1));
    assertThat(s, containsString(source1));
    assertThat(s, containsString("12/08/2010 12:30 PM"));
    assertThat(s, containsString(dest1));
    assertThat(s, containsString("12/10/2010 01:20 AM"));
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