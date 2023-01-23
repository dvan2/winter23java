package edu.pdx.cs410J.davvan;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  @Test
  @Disabled
  void onlyThreeLetterCode(){
    Flight flight = new Flight(123, "ABCD","12:12:2000 12:12", "PDXX", "1/1/2022 1:1");
    assertThat(flight.toString(), equalTo("Flight 123 departs ABC at 12:12:2000 12:12 arrives PDX at 1/1/2022 1:1"));
  }

  @Test
  void flightContainsInformation(){
    Flight flight = new Flight(123, "ABC","12:12:2000 12:12", "PDX", "1/1/2022 1:1");
    assertThat(flight.toString(), equalTo("Flight 123 departs ABC at 12:12:2000 12:12 arrives PDX at 1/1/2022 1:1"));
  }


  
}
