package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;

import static edu.pdx.cs410J.davvan.Project3.createDate;
import static edu.pdx.cs410J.davvan.TextDumperTest.FLIGHT_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class XmlDumperTest {
    @Test
    void canDumpXml(@TempDir File tempDir) throws ParserException, ParserConfigurationException, TransformerException, IOException {
        File textFile= new File("xdumper.xml");
        XmlDumper dumper= new XmlDumper(new FileWriter(textFile));
        String date_string = "6/13/2000 1:00 pm";

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
        dumper.dump(test_airline);
    }

    @Test
    void canDumpMultipleFlights(@TempDir File tempDir) throws IOException {
        File textFile= new File("xdumper.xml");
        XmlDumper dumper= new XmlDumper(new FileWriter(textFile));
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

        //don't print airline name anymore
        Flight flight2 = new Flight(123, "TUL", depart_date2, "PDX", arrive_date);
        test_airline.addFlight(flight2);

        Flight flight3 = new Flight(343, "DEN", depart_date, "PDX", arrive_date2);
        test_airline.addFlight(flight3);

        dumper.dump(test_airline);



    }
}
