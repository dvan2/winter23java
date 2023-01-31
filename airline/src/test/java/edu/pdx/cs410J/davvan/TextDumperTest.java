package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import org.checkerframework.checker.units.qual.A;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

  @Test
  @Disabled
  void airlineNameIsDumpedInTextFormat() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(airline);

    String text = sw.toString();
    assertThat(text, containsString(airlineName));
  }

  @Test
  @Disabled
  void airlineFlightsAreDumpedInFile(){
    //create a flight first
    Flight flight = new Flight(123, "PDX","12/12/2000 12:12", "PDX", "1/1/2022 1:1");
    Airline test_airline= new Airline("Delta", flight);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(test_airline);

    String text = sw.toString();
    assertThat(text, containsString("Delta 123 PDX 12/12/2000 12:12 PDX 1/1/2022 1:1"));
  }

  @Test
  @Disabled
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
  }

}
