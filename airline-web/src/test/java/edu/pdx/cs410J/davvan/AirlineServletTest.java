package edu.pdx.cs410J.davvan;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
  void addFlightInNewAirlineAndGetFlight() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airline_name = "Airline";
    int flight_number= 123;
    String flight_numberasString = "123";
    String source = "PDX";
    String depart = "12/9/2010 8:12 pm";
    String dest = "DEN";
    String arrive = "12/13/2010 1:01 am";

    HttpServletRequest request = createRequest(airline_name, flight_numberasString, source, depart, dest, arrive);


    HttpServletResponse response = null;
    response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    String xml = stringWriter.toString();
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

    //Test Get Method here
    HttpServletRequest request1 = mock(HttpServletRequest.class);
    when(request1.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_name);

    HttpServletResponse response1 = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter1 = new StringWriter();
    PrintWriter pw1 = new PrintWriter(stringWriter1, true);

    when(response1.getWriter()).thenReturn(pw1);

    servlet.doGet(request1, response1);
  }

  private static HttpServletRequest createRequest(String airline_name, String flight_numberasString, String source, String depart, String dest, String arrive) {
    HttpServletRequest request;
    request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_name);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(flight_numberasString);
    when(request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(source);
    when(request.getParameter(AirlineServlet.DEPART_PARAMETER)).thenReturn(depart);
    when(request.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.ARRIVE_PARAMETER)).thenReturn(arrive);
    return request;
  }

  @Test
  void addTwoFlightsAndGetFlights() throws IOException {
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

    //2nd flight
    int flight_number1= 343;
    String flight_numberasString1 = "343";
    String source1 = "TUL";
    String depart1 = "1/1/2010 12:12 pm";
    String dest1 = "LAX";
    String arrive1 = "2/10/2010 1:01 am";

    HttpServletRequest request2 = mock(HttpServletRequest.class);
    when(request2.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_name);
    when(request2.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(flight_numberasString1);
    when(request2.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(source1);
    when(request2.getParameter(AirlineServlet.DEPART_PARAMETER)).thenReturn(depart1);
    when(request2.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(dest1);
    when(request2.getParameter(AirlineServlet.ARRIVE_PARAMETER)).thenReturn(arrive1);

    HttpServletResponse response2 = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter2 = new StringWriter();
    PrintWriter pw2 = new PrintWriter(stringWriter2, true);

    when(response2.getWriter()).thenReturn(pw2);

    servlet.doPost(request2, response2);

    //Test Get Method here
    HttpServletRequest request1 = mock(HttpServletRequest.class);
    when(request1.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_name);

    HttpServletResponse response1 = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter1 = new StringWriter();
    PrintWriter pw1 = new PrintWriter(stringWriter1, true);

    when(response1.getWriter()).thenReturn(pw1);

    servlet.doGet(request1, response1);
    //System.out.println("stringWriter1: " + stringWriter1);


    assertThat(xml, containsString(airline_name));
    assertThat(xml, containsString(flight_numberasString));



    Airline airline2 = servlet.getAirline(airline_name);
    assertThat(airline2.getName(), equalTo(airline_name));

    assertThat(stringWriter1.toString(), containsString("LAX"));

  }
  @Test
  void addMultipleFlightsMultipleAirlineAndGetFlights() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airline_name = "Airline";
    int flight_number= 123;
    String flight_numberasString = "123";
    String source = "PDX";
    String depart = "12/13/2010 12:12 pm";
    String dest = "DEN";
    String arrive = "12/14/2010 1:01 am";

    HttpServletRequest request = createRequest(airline_name,flight_numberasString, source,depart, dest, arrive);

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

    //2nd flight
    int flight_number1= 343;
    String flight_numberasString1 = "343";
    String source1 = "TUL";
    String depart1 = "1/1/2010 12:12 pm";
    String dest1 = "LAX";
    String arrive1 = "2/10/2010 1:01 am";

    HttpServletRequest request2 = createRequest(airline_name, flight_numberasString1, source1, depart1, dest1, arrive1);

    HttpServletResponse response2 = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter2 = new StringWriter();
    PrintWriter pw2 = new PrintWriter(stringWriter2, true);

    when(response2.getWriter()).thenReturn(pw2);

    servlet.doPost(request2, response2);

    //test new airline

    //2nd airline
    String airline_new = "Delta";
    int flight_number2= 111;
    String flight_numberasString2 = "111";
    String source2 = "PDX";
    String depart2 = "8/1/2012 9:12 pm";
    String dest2 = "BNA";
    String arrive2 = "8/2/2012 1:01 am";

    HttpServletRequest request3 = createRequest(airline_new, flight_numberasString2, source2, depart2, dest2, arrive2);

    HttpServletResponse response3 = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter3 = new StringWriter();
    PrintWriter pw3 = new PrintWriter(stringWriter3, true);

    when(response3.getWriter()).thenReturn(pw3);

    servlet.doPost(request3, response3);

    String xml3 = stringWriter.toString();
    //assertThat(stringWriter.toString(), containsString(Messages.definedWordAs(airline_name, flight_number)));
    assertThat(xml3, containsString(airline_name));
    assertThat(xml3, containsString(flight_numberasString));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode3 = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode3.capture());

    assertThat(statusCode3.getValue(), equalTo(HttpServletResponse.SC_OK));

    Airline airline3 = servlet.getAirline(airline_name);
    assertThat(airline3.getName(), equalTo(airline_name));

    //Test Get Method here
    HttpServletRequest get_request = mock(HttpServletRequest.class);
    when(get_request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_name);

    HttpServletResponse get_response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter1 = new StringWriter();
    PrintWriter pw1 = new PrintWriter(stringWriter1, true);

    when(get_response.getWriter()).thenReturn(pw1);

    servlet.doGet(get_request, get_response);
    assertThat(stringWriter1.toString(), containsString("Airline"));
    assertThat(stringWriter1.toString(), containsString(flight_numberasString));
    assertThat(stringWriter1.toString(), containsString(source));
    assertThat(stringWriter1.toString(), containsString("day=\"13\""));

    //Test Get for second airline: airline_new
    HttpServletRequest get_request2 = mock(HttpServletRequest.class);
    when(get_request2.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_new);

    HttpServletResponse get_response2 = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriterSecondAir = new StringWriter();
    PrintWriter pwSecondAirine = new PrintWriter(stringWriterSecondAir, true);

    when(get_response2.getWriter()).thenReturn(pwSecondAirine);

    servlet.doGet(get_request2, get_response2);

    assertThat(stringWriterSecondAir.toString(), containsString("Delta"));
    assertThat(stringWriterSecondAir.toString(), containsString("111"));
    assertThat(stringWriterSecondAir.toString(), containsString("PDX"));
    assertThat(stringWriterSecondAir.toString(), containsString("day=\"01\""));
  }

  @Test
  void getAirlineWithSourceAndDestination () throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airline_name = "Airline";
    int flight_number= 123;
    String flight_numberasString = "123";
    String source = "PDX";
    String depart = "12/12/2010 12:12 pm";
    String dest = "DEN";
    String arrive = "12/13/2010 1:01 am";

    HttpServletRequest request = createRequest(airline_name,flight_numberasString, source,depart, dest, arrive);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    //2nd flight
    int flight_number1= 343;
    String flight_numberasString1 = "343";
    String source1 = "TUL";
    String depart1 = "1/1/2010 12:12 pm";
    String dest1 = "LAX";
    String arrive1 = "12/12/2010 1:01 am";

    HttpServletRequest request2 = createRequest(airline_name, flight_numberasString1, source1, depart1, dest1, arrive1);

    HttpServletResponse response2 = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter2 = new StringWriter();
    PrintWriter pw2 = new PrintWriter(stringWriter2, true);

    when(response2.getWriter()).thenReturn(pw2);

    servlet.doPost(request2, response2);


    HttpServletRequest get_request = mock(HttpServletRequest.class);
    when(get_request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airline_name);
    when(get_request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(source1);
    when(get_request.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(dest1);

    HttpServletResponse get_response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter1 = new StringWriter();
    PrintWriter pw1 = new PrintWriter(stringWriter1, true);

    when(get_response.getWriter()).thenReturn(pw1);

    servlet.doGet(get_request, get_response);
    assertThat(stringWriter1.toString(), containsString("Airline"));
    assertThat(stringWriter1.toString(), not(containsString(flight_numberasString)));
    assertThat(stringWriter1.toString(), containsString(source1));
    assertThat(stringWriter1.toString(), containsString("day=\"01\""));

  }

  @Test
  void badFlightIsCaught() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airline_name = "Airline";
    int flight_number= 123;
    String flight_numberasString = "abc";
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

    try {
      servlet.doPost(request, response);
    }catch(IOException e){
      ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
      verify(response).setStatus(statusCode.capture());

      assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_NOT_ACCEPTABLE));
      //System.err.println(e.getMessage());
    }
  }
}
