package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.text.ParseException;
import java.util.Date;

import static edu.pdx.cs410J.davvan.Project4.createDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {


  public static final String FLIGHT_TEST = "123 TUL 12/12/2000 01:00 PM PDX 01/01/2010 01:20 PM";

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
  void airlineFlightsAreDumpedInFile() {
    String date_string = "12/12/2000 1:00 pm";

    String arrive_string = "1/1/2010 1:20 pm";
    Date depart_date = null;
    Date arrive_date = null;
    try {
      depart_date = createDate(date_string);
      arrive_date = createDate(arrive_string);

    } catch (ParseException e) {
      System.err.println("Problem parsing");
    }
    //create a flight first

    Flight flight = new Flight(123, "TUL", depart_date, "PDX", arrive_date);
    Airline test_airline = new Airline("Delta", flight);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw, true);
    dumper.dump(test_airline);

    String text = sw.toString();
    assertThat(text, containsString("Delta"));
    assertThat(text, containsString(FLIGHT_TEST));
  }


  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String date_string = "12/12/2000 1:00 pm";
    System.out.println(date_string);

    String arrive_string = "1/1/2010 1:20 pm";
    Date depart_date = null;
    Date arrive_date = null;
    try {
      depart_date = createDate(date_string);
      arrive_date = createDate(arrive_string);

    } catch (ParseException e) {
      System.err.println("Problem parsing");
    }
    Flight flight = new Flight(123, "TUL", depart_date, "PDX", arrive_date);
    Airline test_airline = new Airline("Delta", flight);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile), true);
    dumper.dump(test_airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = new Airline();
    read = parser.parse();
    assertThat(read.getName(), containsString("Delta"));
    assertThat(read.getAirline("MM/dd/yyyy hh:mm a"), containsString(FLIGHT_TEST));
  }


  @Test
  void canDumpMultipleFlights(@TempDir File tempDir) {
    String date_string = "12/12/2000 1:00 pm";

    String arrive_string = "1/1/2010 1:20 pm";
    String arrive_string2 = "11/11/2020 12:01 AM";
    String depart_string2 = "12/12/2022 12:12 PM";
    Date depart_date = null;
    Date arrive_date = null;
    Date arrive_date2= null;
    Date depart_date2 = null;
    try {
      depart_date = createDate(date_string);
      arrive_date = createDate(arrive_string);
      arrive_date2= createDate(arrive_string2);
      depart_date2 = createDate(depart_string2);

    } catch (ParseException e) {
      System.err.println("Problem parsing");
    }

    //create a flight first
    Flight flight = new Flight(123, "TUL", depart_date, "PDX", arrive_date);
    Airline test_airline = new Airline("Delta", flight);

    File textFile = new File(tempDir, "test_file.txt");

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw, true);
    dumper.dump(test_airline);

    //don't print airline name anymore
    dumper.setPrintName(false);
    Flight flight2 = new Flight(123, "TUL", depart_date2, "PDX", arrive_date);
    Airline airline2 = new Airline("Delta", flight2);
    dumper.dump(airline2);

    Flight flight3 = new Flight(343, "DEN", depart_date, "PDX", arrive_date2);
    Airline airline3 = new Airline("Delta", flight3);
    dumper.dump(airline3);

    String text = sw.toString();
    assertThat(text, containsString("Delta"));
    assertThat(text, containsString("123 TUL 12/12/2000 01:00 PM PDX 01/01/2010 01:20 PM"));
    assertThat(text, containsString("123 TUL 12/12/2022 12:12 PM PDX 01/01/2010 01:20 PM"));
    assertThat(text, containsString("343 DEN 12/12/2000 01:00 PM PDX 11/11/2020 12:01 AM"));

  }


}

