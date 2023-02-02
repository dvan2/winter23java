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
  void airlineNameIsDumpedInTextFormat() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw, true);
    dumper.dump(airline);

    String text = sw.toString();

    assertThat(text, containsString(airlineName));
  }

  @Test
  void airlineFlightsAreDumpedInFile(){
    //create a flight first
    Flight flight = new Flight(123, "PDX","12/12/2000 12:12", "PDX", "1/1/2022 1:1");
    Airline test_airline= new Airline("Delta", flight);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw, true);
    dumper.dump(test_airline);

    String text = sw.toString();
    assertThat(text, containsString("Delta"));
    assertThat(text, containsString("123 PDX 12/12/2000 12:12 PDX 1/1/2022 1:1"));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    Flight flight = new Flight(123, "PDX","12/12/2000 12:12", "PDX", "1/1/2022 1:1");
    Airline test_airline= new Airline("Delta", flight);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile), true);
    dumper.dump(test_airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read= new Airline();
    read= parser.parse();
    assertThat(read.getName(), containsString("Delta"));
    assertThat(read.getAirline(), containsString("123 PDX 12/12/2000 12:12 PDX 1/1/2022 1:1"));
  }


  @Test
  void canDumpMultipleFlights(@TempDir File tempDir) throws IOException, ParserException{
    //create a flight first
    Flight flight = new Flight(123, "PDX","12/12/2000 12:12", "PDX", "1/1/2022 1:1");
    Airline test_airline= new Airline("Delta", flight);

    File textFile = new File(tempDir, "test_file.txt");

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw, true);
    dumper.dump(test_airline);

    //don't print airline name anymore
    dumper.setPrintName(false);
    Flight flight2 = new Flight(123, "TUL","12/12/2000 12:12", "PDX", "1/1/2022 1:10");
    Airline airline2= new Airline("Delta", flight2);
    dumper.dump(airline2);

    Flight flight3 = new Flight(343, "DEN","12/12/2000 12:12", "PDX", "1/1/2022 1:10");
    Airline airline3= new Airline("Delta", flight3);
    dumper.dump(airline3);

    String text= sw.toString();
    assertThat(text, containsString("Delta"));
    assertThat(text, containsString("123 PDX 12/12/2000 12:12 PDX 1/1/2022 1:1"));
    assertThat(text, containsString("123 TUL 12/12/2000 12:12 PDX 1/1/2022 1:10"));
    assertThat(text, containsString("343 DEN 12/12/2000 12:12 PDX 1/1/2022 1:10"));


  }




}