package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.ParseException;
import java.util.Date;

import static edu.pdx.cs410J.davvan.Project4.createDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.io.TempDir;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class XmlParserTest {

    final String FLIGHT1= "123 PDX 12/12/2000 12:12 PDX 03/03/2023 12:12";

    @Test
    void badXmlThrowsError(@TempDir File tempDir) throws ParserException, ParserConfigurationException, IOException, SAXException {
        File textFile= new File(tempDir, "bad.xml");
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

        Flight flight = new Flight(123, "ZZZ", depart_date, "PDX", arrive_date);
        Airline test_airline = new Airline("Delta", flight);
        dumper.dump(test_airline);

        XmlParser parser = new XmlParser(new FileReader(textFile));

        Throwable exception= assertThrows
                (ParserException.class, () -> {
                    Airline airline= null;
                    airline= parser.parse();
                });
        //Not known Airport
        assertEquals("Bad Xml content in Flight element: Source airport code doesn't exists."
                ,exception.getMessage());

        assertEquals("Bad Xml content in Flight element: Source airport code doesn't exists."
                ,exception.getMessage());
    }

    @Test
    void airportCodeLongThrowsError(@TempDir File tempDir) throws IOException, SAXException {
        File textFile= new File(tempDir, "bad.xml");
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

        Flight flight = new Flight(123, "PDXA", depart_date, "PDX", arrive_date);
        Airline test_airline = new Airline("Delta", flight);
        dumper.dump(test_airline);

        XmlParser parser = new XmlParser(new FileReader(textFile));

        Throwable exception= assertThrows
                (ParserException.class, () -> {
                    Airline airline= null;
                    airline= parser.parse();
                });

        //Not known Airport
        assertEquals("Bad Xml content in Flight element: Source airport code has invalid length. Must be 3 character."
                ,exception.getMessage());
    }

}
