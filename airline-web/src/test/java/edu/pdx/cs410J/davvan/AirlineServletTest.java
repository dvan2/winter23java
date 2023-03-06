package edu.pdx.cs410J.davvan;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {

  @Test
  void gettingNonexistentAirlineNameReturns404() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn("Airline");


    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(pw, never()).println(anyString());
    verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  @Test
  void addFlightInNewAirline() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airline_name = "Airline";
    int flight_number= 123;
    String flight_numberasString = "123";
    String source = "PDX";
    String depart = "12/12/2010 12:12 pm";
    String dest = "DEN";
    String arrive = "12/13/2010 1:01 am";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_name);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(flight_numberasString);
    when(request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(source);
    when(request.getParameter(AirlineServlet.DEPART_PARAMETER)).thenReturn(depart);
    when(request.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.ARRIVE_PARAMETER)).thenReturn(arrive);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    String xml = stringWriter.toString();
    //assertThat(stringWriter.toString(), containsString(Messages.definedWordAs(airline_name, flight_number)));
    assertThat(xml, containsString(airline_name));
    assertThat(xml, containsString(flight_numberasString));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    Airline airline = servlet.getAirline(airline_name);
    assertThat(airline.getName(), equalTo(airline_name));

    Flight flight = airline.getFlights().iterator().next();
    assertThat(flight.getNumber(), equalTo(flight_number));

    HttpServletRequest request1 = mock(HttpServletRequest.class);
    when(request1.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_name);

    HttpServletResponse response1 = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter1 = new StringWriter();
    PrintWriter pw1 = new PrintWriter(stringWriter1, true);

    when(response1.getWriter()).thenReturn(pw1);

    servlet.doGet(request1, response1);
    System.out.println(stringWriter1.toString());

  }

  @Test
  void getFlightWithValidName() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airline_name = "Airline";
    int flight_number= 123;
    String flight_numberasString = "123";

    HttpServletRequest request = mock(HttpServletRequest.class);

    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_name);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);
    String xml = stringWriter.toString();
    System.out.println(xml);
  }

}
