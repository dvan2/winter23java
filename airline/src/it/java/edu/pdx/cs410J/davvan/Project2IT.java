package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Use this constant to output not enough arguments.
     */
    public static final String NOT_ENOUGH_ARGS = "There are not enough arguments for a flight.";
    public static final String INVALID_HR_DEPART = "Invalid hour input in departure fields.";


    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    //a variable to test README
    final String readme_test= "Like Project 1,";

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  @Disabled
  void testNoCommandLineArguments() {
      MainMethodResult result = invokeMain();
      assertThat(result.getTextWrittenToStandardOut(), containsString("usage: java -jar target/airline-2023.0.0.jar [options] <args>"));
  }
  /*

  @Test
  void testProgramWithPrintOption() {
      MainMethodResult result = invokeMain(Project2.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 343 departs DEN at 12/12/2000 12:12 arrives PDX at 1/1/2022 1:1"));
  }

  @Test
  void readmeOnlyArgument(){
      MainMethodResult result = invokeMain(Project2.class,"-README");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }
  @Test
  void readmeAndPrintOnly(){
      MainMethodResult result = invokeMain(Project2.class,"-README", "-print");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

  @Test
  void printOnly(){
      MainMethodResult result = invokeMain(Project2.class, "-print");
      assertThat(result.getTextWrittenToStandardError(), containsString(NOT_ENOUGH_ARGS));
  }

  @Test
  void readmeFirstOptionWithPrint(){
      MainMethodResult result = invokeMain(Project2.class,"-README", "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

  @Test
  void readmeFileSecondOption(){
      MainMethodResult result = invokeMain(Project2.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

  @Test
  void tooManyArgumentsWithPrint(){
      MainMethodResult result = invokeMain(Project2.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX","TUL", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is too many arguments for a flight."));
  }

  @Test
  void testTooManyArgumentsWithPrintAndReadme(){
      MainMethodResult result = invokeMain(Project2.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX","TUL", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

  @Test
  void testTooManyArgumentsWithoutOptions(){
      MainMethodResult result = invokeMain(Project2.class,  "Delta", "343", "DEN", "12/12/2000","12:12","PDX","TUL", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is too many arguments for a flight."));
  }

  @Test
  void notEnoughArguments(){
      MainMethodResult result = invokeMain(Project2.class, "Delta", "343", "DEN", "12/12/2000","12:12", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString(NOT_ENOUGH_ARGS));
  }

  @Test
  void notEnoughArgumentsWithPrintAndReadme(){
      MainMethodResult result = invokeMain(Project2.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

    @Test
    void notEnoughArgumentsWithPrint(){
        MainMethodResult result = invokeMain(Project2.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "1/1/2022", "1:1");
        assertThat(result.getTextWrittenToStandardError(), containsString(NOT_ENOUGH_ARGS));
    }

    @Test
    void notNumberFlight(){
        MainMethodResult result = invokeMain(Project2.class, "-print", "Delta", "abc", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
        assertThat(result.getTextWrittenToStandardError(), containsString("Flight number must be a number."));
    }

    @Test
    void invalidDate(){
        MainMethodResult result = invokeMain(Project2.class, "-print", "Delta", "123", "DEN", "13/12/0","12:12", "PDX", "1/1/2022", "1:1");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid date input"));
    }

    @Test
    void invalidTime(){
        MainMethodResult result = invokeMain(Project2.class, "-print", "Delta", "123", "DEN", "12/12/2000","24:12", "PDX", "1/1/2022", "1:1");
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_HR_DEPART));
    }

    @Test
    void futureDate(){
        MainMethodResult result = invokeMain(Project2.class, "-print", "Delta", "123", "DEN", "12/12/2000","12:12", "PDX", "03/03/2023", "12:12");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Delta"));
    }
    @Test
    void lessThanThreeLetterSourceAirport(){
        MainMethodResult result = invokeMain(Project2.class, "Test10", "123", "P", "12/12/2023","12:12", "PDX", "03/03/2023", "16:12");
        assertThat(result.getTextWrittenToStandardError(), containsString("Source airport code has invalid length. Must be 3 character."));
    }

    @Test
    void writesToFile(@TempDir File tempDir) {
        String temp_file= "airline1.txt";
        File temp= new File(tempDir, temp_file);
        MainMethodResult result = invokeMain(Project2.class, "-print" ,"-textFile", temp_file, "Delta", "123", "DEN", "12/12/2000","12:12", "PDX", "03/03/2023", "12:12");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 123"));
    }

    @Test
    void dontWriteIfBadArgument(@TempDir File tempDir) {
        String temp_file= "airline1.txt";
        File temp= new File(tempDir, temp_file);
        MainMethodResult result = invokeMain(Project2.class, "-print" ,"-textFile", temp_file, "Delta", "123", "DENN", "12/12/2000","12:12", "PDX", "03/03/2023", "12:12");
        assertThat(result.getTextWrittenToStandardError(), containsString("Source airport code has invalid length. Must be 3 character."));
    }

    @Test
    void noFileProvided(@TempDir File tempDir) {
        String temp_file= "airline1.txt";
        File temp= new File(tempDir, temp_file);
        MainMethodResult result = invokeMain(Project2.class, "-print" , temp_file, "Delta", "123", "DEN", "12/12/2000","12:12", "PDX", "03/03/2023", "12:12");
        assertThat(result.getTextWrittenToStandardError(), containsString("There is too many arguments for a flight"));
    }

    @Test
    void writesToFileWithPrintOptionAfterFile(@TempDir File tempDir) {
        String temp_file= "src/test/resources/edu/pdx/cs410J/davvan/airlinetest.txt";
        File temp= new File(tempDir, temp_file);
        MainMethodResult result = invokeMain(Project2.class  ,"-textFile", temp_file, "-print", "Delta", "123", "DEN", "12/12/2000","12:12", "PDX", "03/03/2023", "12:12");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 123"));
    }


    */


}
