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



  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */
  /*
  @Test

  void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight();
    assertThat(flight.getNumber(), equalTo(42));
  }
*/
  @Test
  @Disabled
  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight(0, "1");
    assertThat(flight.getDeparture(), is(nullValue()));
  }


  
}
