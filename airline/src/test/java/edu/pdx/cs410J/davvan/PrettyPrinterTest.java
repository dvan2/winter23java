package edu.pdx.cs410J.davvan;

import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;

import static edu.pdx.cs410J.davvan.Project2.createDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class PrettyPrinterTest {
    @Test
    void prettyPrintFormatedCorrectway(){
        String date_string = "12/12/2010 1:00 pm";

        String arrive_string = "12/12/2010 1:20 pm";
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
        PrettyPrinter dumper = new PrettyPrinter(sw);
        dumper.dump(test_airline);

        String text = sw.toString();
        assertThat(text, containsString("Delta Airlines:"));
        assertThat(text, containsString("Flight 123 departs TUL on Dec 12, 2010 1:00 PM"));
        assertThat(text, containsString("Flight 123 arrives PDX on Dec 12, 2010 1:20 PM"));
        assertThat(text, containsString("20 minutes"));
    }
}
