package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {


  @Test
  void validTextFileCanBeParsed() throws ParserException {

    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Delta"));
  }
  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  @Test
  void canParseFlight() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Delta"));
    assertThat(airline.getAirline("MM/dd/yyyy hh:mm a"), containsString("123 PDX 12/12/2000 01:00 PM PDX 01/01/2022 01:10 PM"));
  }

  @Test
  void canParseMultipleFlights() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Delta"));
    assertThat(airline.getAirline("MM/dd/yyyy hh:mm a"), containsString("123 PDX 10/10/2000 01:12 PM DEN 11/11/2010 02:00 PM"));

    assertThat(airline.getAirline("MM/dd/yyyy hh:mm a"), containsString("123 PDX 12/12/2000 01:00 PM PDX 01/01/2022 01:10 PM"));
  }
}
