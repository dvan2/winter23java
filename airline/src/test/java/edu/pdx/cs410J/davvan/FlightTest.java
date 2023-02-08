package edu.pdx.cs410J.davvan;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static edu.pdx.cs410J.davvan.Project3.createDate;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {
    /*
  @Test
  void sourceAirportMoreThanThreeLetter() throws Exception{
    //I found a way to test throwed exception online
    //source: https://www.codejava.net/testing/junit-test-exception-examples-how-to-assert-an-exception-is-thrown
    Throwable exception= assertThrows
            (IllegalArgumentException.class, () -> {
      Flight flight = new Flight(123, "ABCC","12:12:2000 12:12", "PDX", "1/1/2022 1:1");
      flight.hasValidCode();
    });
    assertEquals("Source airport code has invalid length. Must be 3 character.",exception.getMessage());
  }

  @Test
  void destinationAirportNotThreeLetter(){
    //source: https://www.codejava.net/testing/junit-test-exception-examples-how-to-assert-an-exception-is-thrown
    Throwable exception= assertThrows
            (IllegalArgumentException.class, () -> {
              Flight flight = new Flight(123, "ABC","12:12:2000 12:12", "PDXX", "1/1/2022 1:1");
              flight.hasValidCode();
            });
    assertEquals("Destination airport code has invalid length. Must be 3 character.",exception.getMessage());
  }
  @Test
  void sourceAirportLessThanThreeLetter(){
  Throwable exception= assertThrows
        (IllegalArgumentException.class, () -> {
          Flight flight = new Flight(123, "P","12:12:2000 12:12", "PD", "1/1/2022 1:1");
          flight.hasValidCode();
        });
  assertEquals("Source airport code has invalid length. Must be 3 character.",exception.getMessage());
  }

  @Test
  void airportCodeHasNumber(){
  Throwable exception= assertThrows
      (IllegalArgumentException.class, () -> {
          Flight flight = new Flight(123, "PD1","12:12:2000 12:12", "PDX", "1/1/2022 1:1");
          flight.hasValidCode();
      });
  assertEquals("Source airport code cannot contain number.",exception.getMessage());
  }
  @Test
  void flightContainsRightInformation(){
    Flight flight = new Flight(123, "ABC","12/12/2000 12:12", "PDX", "1/1/2022 1:1");
    assertThat(flight.toString(), equalTo("Flight 123 departs ABC at 12/12/2000 12:12 arrives PDX at 1/1/2022 1:1"));
  }

     */
    @Test
    void orginalFormatDateProduceCorrectDateBack(){
    String date_string= "12/12/2000 1:00 pm";
    System.out.println(date_string);

    String arrive_string= "1/1/2010 1:20 pm";
    Date depart_date= null;
    Date arrive_date= null;
    try{
        depart_date= createDate(date_string);
        arrive_date= createDate(arrive_string);

    }catch(ParseException e){
        System.err.println("Problem parsing");
    }
    System.out.println("Arrive: "+ arrive_date);
    Flight flight = new Flight(123, "PDX", depart_date, "TUL",arrive_date);
    flight.hasValidCode();
    String result= flight.orginalFormatDate(depart_date, "MM/dd/yyyy hh:mm a");
    String resulttwo= flight.orginalFormatDate(arrive_date, "MM/dd/yyyy hh:mm a");
    assertThat(result, containsString("12/12/2000 01:00 PM"));
    assertThat(resulttwo, containsString("01/01/2010 01:20 PM"));
    }
}
