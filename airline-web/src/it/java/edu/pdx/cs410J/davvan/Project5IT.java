package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
//@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");
    public static final String NOT_ENOUGH_ARGS = "There are not enough arguments for a flight.";


    private String readme_test= "Project 5 extends";

    @Test
    void test0RemoveAllMappings() throws IOException {
      AirlineRestClient client = new AirlineRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllDictionaryEntries();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project5.class );
        assertThat(result.getTextWrittenToStandardOut(), containsString("usage: java -jar"));
    }


    @Test
    void notEnoughArguments() {
        MainMethodResult result = invokeMain( Project5.class, HOSTNAME, PORT );

        //assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String out = result.getTextWrittenToStandardError();
        assertThat(out, containsString("There are not enough arguments for a flight."));
    }

    @Test
    void readmeOnlyArgument(){
        MainMethodResult result = invokeMain(Project5.class,"-README");
        MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
    }

    @Test
    void printAnAirlineCallsToString() {
        MainMethodResult result = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-print",
                "Delta", "123", "PDX", "12/12/2010" , "12:10", "AM", "ORD", "12/13/2010" , "1:00", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 123 departs PDX at 12/12/10, 12:10 AM arrives ORD at 12/13/10, 1:00 AM"));
    }

    @Test
    void missingPortThrowsError() {
        MainMethodResult result = invokeMain(Project5.class,"-host" , HOSTNAME, "-port");
        assertThat(result.getTextWrittenToStandardError(), containsString("No port name provided."));
    }

    @Test
    void searchAnAirline() {
        String airline_name= "Delta";
        String src = "PDX";
        String dest= "ORD";
        MainMethodResult result = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010" , "12:10", "AM", dest, "12/13/2010" , "1:00", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 123 departs PDX at 12/12/10, 12:10 AM arrives ORD at 12/13/10, 1:00 AM"));

        MainMethodResult result3 = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010" , "12:10", "AM", dest, "12/13/2010" , "1:00", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 123 departs PDX at 12/12/10, 12:10 AM arrives ORD at 12/13/10, 1:00 AM"));

        MainMethodResult result2 = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-search" , airline_name, src, dest);
        assertThat(result2.getTextWrittenToStandardOut(), containsString("Flight 123 departs Portland, OR (PDX) on Dec 12, 2010 12:10 AM"));
        assertThat(result2.getTextWrittenToStandardOut(), containsString("Flight 123 arrives Chicago, IL (O'Hare) (ORD) on Dec 13, 2010 1:00 AM"));
    }


    @Test
    void searchNonExistsAirline() {
        MainMethodResult result2 = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-search" , "Delt");
        assertThat(result2.getTextWrittenToStandardError(), containsString("Error.  Cannot match airport."));
    }

    @Test
    void searchWithOnlySource() {
        MainMethodResult result2 = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-search" , "Delt", "PDX");
        assertThat(result2.getTextWrittenToStandardError(), containsString("There are not enough arguments for a flight."));

    }

    @Test
    void searchAnAirlineWithSourceAndDest() {
        String airline_name= "Delta";
        String src = "PDX";
        String dest= "ORD";
        MainMethodResult result = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010" , "12:10", "AM", dest, "12/13/2010" , "1:00", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 123 departs PDX at 12/12/10, 12:10 AM arrives ORD at 12/13/10, 1:00 AM"));

        MainMethodResult result2 = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-search" , airline_name, src, dest);
        assertThat(result2.getTextWrittenToStandardOut(), containsString("Flight 123 departs Portland, OR (PDX) on Dec 12, 2010 12:10 AM"));
        assertThat(result2.getTextWrittenToStandardOut(), containsString("Flight 123 arrives Chicago, IL (O'Hare) (ORD) on Dec 13, 2010 1:00 AM"));
    }

    @Test
    void printOnly(){
        MainMethodResult result = invokeMain(Project5.class, "-print");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(NOT_ENOUGH_ARGS));
    }

    @Test
    void readmeFirstOptionWithPrint(){
        MainMethodResult result = invokeMain(Project5.class,"-README", "-print", "Delta", "343", "DEN", "12/12/2000", "PM","12:12", "PDX", "1/1/2022", "1:1");
        MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
    }

    @Test
    void tooManyArgumentsWithPrint(){
        MainMethodResult result = invokeMain(Project5.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "PDX","TUL", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("There is too many arguments for a flight."));
    }

    @Test
    void testTooManyArgumentsWithPrintAndReadme(){
        MainMethodResult result = invokeMain(Project5.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "PDX","TUL", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
    }

    @Test
    void missingAirlineDestination(){
        MainMethodResult result = invokeMain(Project5.class, "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(NOT_ENOUGH_ARGS));
    }

    @Test
    void notEnoughArgumentsWithPrintAndReadme(){
        MainMethodResult result = invokeMain(Project5.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
    }

    @Test
    void notEnoughArgumentsWithPrint(){
        MainMethodResult result = invokeMain(Project5.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(NOT_ENOUGH_ARGS));
    }

    @Test
    void notNumberFlight(){
        String airline_name= "Delta";
        String src = "PDX";
        String dest= "ORD";
        MainMethodResult result = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-print",
                airline_name, "abc", src, "12/12/2010" , "12:10", "AM", dest, "12/13/2010" , "1:00", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Cannot create flight. abc is not a number."));
    }


    @Test
    void invalidDepartDate(){
        String airline_name= "Delta";
        String src = "PDX";
        String dest= "ORD";
        MainMethodResult result = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/20/0" , "12:10", "AM", dest, "12/13/2010" , "1:00", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Cannot create flight. Depart Date is invalid: 12/12/20/0 12:10 AM"));
    }

    @Test
    void invalidArrivalDate(){
        String airline_name= "Delta";
        String src = "PDX";
        String dest= "ORD";
        MainMethodResult result = invokeMain(Project5.class,"-host" , HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010" , "12:10", "AM", dest, "12/13/20/0" , "1:00", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Cannot create flight. Arrive Date is invalid: 12/13/20/0 1:00 AM"));
    }

    @Test
    void invalidArrivalTime() {
        String airline_name = "Delta";
        String src = "PDX";
        String dest = "ORD";
        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010", "12:10", "AM", dest, "12/13/2010", "1:a0", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Cannot create flight. Arrive Date is invalid: 12/13/2010 1:a0 AM"));
    }


    @Test
    void invalidDepartTime() {
        String airline_name = "Delta";
        String src = "PDX";
        String dest = "ORD";
        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010", "12::0", "AM", dest, "12/13/2010", "1:10", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Cannot create flight. Depart Date is invalid: 12/12/2010 12::0 AM"));
    }

    @Test
    void departDateAfterArrival(){
        String airline_name = "Delta";
        String src = "PDX";
        String dest = "ORD";
        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010", "12:10", "AM", dest, "12/13/2000", "1:10", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot create flight. Depart date (Sun Dec 12 00:10:00 PST 2010) cannot be later than arrival date (Wed Dec 13"));
    }

    @Test
    void lessThanThreeLetterSourceAirport(){
        String airline_name = "Delta";
        String src = "PD";
        String dest = "ORD";
        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010", "12:10", "AM", dest, "12/13/2010", "1:10", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot create flight. Source airport code has invalid length. Must be 3 character."));
    }

    @Test
    void moreThanThreeLetterDestinationAirport() {
        String airline_name = "Delta";
        String src = "PDX";
        String dest = "ORDD";
        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010", "12:10", "AM", dest, "12/13/2010", "1:10", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot create flight. Destination airport code has invalid length. Must be 3 character."));
    }

    @Test
    void unkownAirport() {
        String airline_name = "Delta";
        String src = "PDD";
        String dest = "ORD";
        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010", "12:10", "AM", dest, "12/13/2010", "1:10", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot create flight. Source airport code doesn't exists."));
    }

    @Test
    void unknownCommandThrowsException() {
        String airline_name = "Delta";
        String src = "PDD";
        String dest = "ORD";
        MainMethodResult result = invokeMain(Project5.class, "-hi", "-host", HOSTNAME, "-port", PORT, "-print",
                airline_name, "123", src, "12/12/2010", "12:10", "AM", dest, "12/13/2010", "1:10", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Unknown option command.  Please check spelling."));
    }


    /*

    @Test
    void test3NoDefinitionsThrowsAppointmentBookRestException() {
        String word = "WORD";
        try {
            invokeMain(Project5.class, HOSTNAME, PORT, word);
            fail("Should have thrown a RestException");

        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }

     */

    /*
    @Test
    void test4AddDefinition() {
        String word = "WORD";
        String definition = "DEFINITION";

        MainMethodResult result = invokeMain( Project5.class, HOSTNAME, PORT, word, definition );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.definedWordAs(word, definition)));

        result = invokeMain( Project5.class, HOSTNAME, PORT, word );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(PrettyPrinter.formatDictionaryEntry(word, definition)));

        result = invokeMain( Project5.class, HOSTNAME, PORT );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(PrettyPrinter.formatDictionaryEntry(word, definition)));
    }

     */
}