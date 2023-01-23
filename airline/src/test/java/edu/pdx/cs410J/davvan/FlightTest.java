package edu.pdx.cs410J.davvan;

import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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

  @Test
  void sourceAirportNotThreeLetter() throws Exception{
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
  void flightContainsRightInformation(){
    Flight flight = new Flight(123, "ABC","12:12:2000 12:12", "PDX", "1/1/2022 1:1");
    assertThat(flight.toString(), equalTo("Flight 123 departs ABC at 12:12:2000 12:12 arrives PDX at 1/1/2022 1:1"));
  }


  
}
