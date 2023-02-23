package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project4} main class.
 */
class Project4IT extends InvokeMainTestCase {

    /**
     * Use this constant to output not enough arguments.
     */
    public static final String NOT_ENOUGH_ARGS = "There are not enough arguments for a flight.";
    public static final String INVALID_HR_DEPART = "Invalid hour input in departure fields.";


    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project4.class, args );
    }

    //a variable to test README
    final String readme_test= "Project 3, extends";

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
      MainMethodResult result = invokeMain();
      MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("usage: java -jar target/airline-2023.0.0.jar [options] <args>"));
  }

  @Test
  void testProgramWithPrintOption() {
      MainMethodResult result = invokeMain(Project4.class, "-print", "Delta", "343", "DEN", "12/12/2000", "12:12", "PM", "PDX", "1/1/2022", "1:1", "am");
      MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Delta Flight 343 departs DEN at 12/12/00, 12:12 PM arrives PDX at 1/1/22, 1:01 AM"));
  }

  @Test
  void readmeOnlyArgument(){
      MainMethodResult result = invokeMain(Project4.class,"-README");
      MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
  }

  @Test
  void readmeAndPrintOnly(){
      MainMethodResult result = invokeMain(Project4.class,"-README", "-print");
      MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
  }

  @Test
  void printOnly(){
      MainMethodResult result = invokeMain(Project4.class, "-print");
      MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(NOT_ENOUGH_ARGS));
  }

  @Test
  void readmeFirstOptionWithPrint(){
      MainMethodResult result = invokeMain(Project4.class,"-README", "-print", "Delta", "343", "DEN", "12/12/2000", "PM","12:12", "PDX", "1/1/2022", "1:1");
      MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
  }

  @Test
  void readmeFileSecondOption(){
      MainMethodResult result = invokeMain(Project4.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000", "PM","12:12", "PDX", "1/1/2022", "1:1");
      MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
  }


  @Test
  void tooManyArgumentsWithPrint(){
      MainMethodResult result = invokeMain(Project4.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "PDX","TUL", "1/1/2022", "1:1", "AM");
      MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("There is too many arguments for a flight."));
  }

  @Test
  void testTooManyArgumentsWithPrintAndReadme(){
      MainMethodResult result = invokeMain(Project4.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "PDX","TUL", "1/1/2022", "1:1", "AM");
      MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
  }

  @Test
  void testTooManyArgumentsWithoutOptions(){
      MainMethodResult result = invokeMain(Project4.class,  "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "PM","PDX","TUL", "1/1/2022", "1:1" , "AM");
      MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("There is too many arguments for a flight."));
  }

  @Test
  void notEnoughArguments(){
      MainMethodResult result = invokeMain(Project4.class, "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "1/1/2022", "1:1", "AM");
      MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(NOT_ENOUGH_ARGS));
  }

  @Test
  void notEnoughArgumentsWithPrintAndReadme(){
      MainMethodResult result = invokeMain(Project4.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "1/1/2022", "1:1", "AM");
      MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(readme_test));
  }

    @Test
    void notEnoughArgumentsWithPrint(){
        MainMethodResult result = invokeMain(Project4.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PM", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(NOT_ENOUGH_ARGS));
    }

    @Test
    void notNumberFlight(){
        MainMethodResult result = invokeMain(Project4.class, "-print", "Delta", "abc", "DEN", "12/12/2000","12:12", "PM", "PDX", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Flight number must be a number."));
    }

    @Test
    void invalidDate(){
        MainMethodResult result = invokeMain(Project4.class, "-print", "Delta", "123", "DEN", "13/12/0","12:12", "PM", "PDX", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Invalid date input"));
    }

    @Test
    void invalidTime(){
        MainMethodResult result = invokeMain(Project4.class, "-print", "Delta", "123", "DEN", "12/12/2000","24:12", "PM", "PDX", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(INVALID_HR_DEPART));
    }

    @Test
    void dateMalformed(){
        MainMethodResult result = invokeMain(Project4.class, "-print", "Delta", "123", "DEN", "12/12/20/0","1:12", "PM", "PDX", "1/1/2022", "1:1", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Date is malformed"));
    }


    @Test
    void futureDate(){
        MainMethodResult result = invokeMain(Project4.class, "-print", "Delta", "123", "DEN", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Delta"));
    }
    @Test
    void lessThanThreeLetterSourceAirport(){
        MainMethodResult result = invokeMain(Project4.class, "Test10", "123", "P", "12/12/2023","12:12", "PM", "PDX", "03/03/2023", "10:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Source airport code has invalid length. Must be 3 character."));
    }

    @Test
    void writesToFile(@TempDir File tempDir) {
        String temp_file= "airline1.txt";
        File temp= new File(tempDir, temp_file);
        MainMethodResult result = invokeMain(Project4.class, "-print" ,"-textFile", temp_file, "Delta", "123", "DEN", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Delta Flight 123"));
    }

    @Test
    void dontWriteIfBadArgument(@TempDir File tempDir) {
        String temp_file= "airline1.txt";
        File temp= new File(tempDir, temp_file);
        MainMethodResult result = invokeMain(Project4.class, "-print" ,"-textFile", temp_file, "Delta", "123", "DENN", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Source airport code has invalid length. Must be 3 character."));
    }

    @Test
    void noFileProvided(@TempDir File tempDir) {
        String temp_file= "airline1.txt";
        File temp= new File(tempDir, temp_file);
        MainMethodResult result = invokeMain(Project4.class, "-print" , temp_file, "Delta", "123", "DEN", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("There is too many arguments for a flight"));
    }

    @Test
    void writesToFileWithPrintOptionAfterFile() {
        String temp_file= "airlinetest.txt";
        MainMethodResult result = invokeMain(Project4.class  ,"-textFile", temp_file, "-print", "Delta", "123", "DEN", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Delta Flight 123"));
    }

    @Test
    void prettyPrintsAirline(@TempDir File tempDir) {
        String temp_file= "prettyTest.txt";
        MainMethodResult result = invokeMain(Project4.class  ,"-pretty" , "-", "Delta", "123", "PDX", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Delta Airlines"));
    }

    @Test
    void prettyPrintAirlinesToFile(){
        MainMethodResult result = invokeMain(Project4.class  ,"-textFile", "airline.txt","-pretty" , "prettyTest1.txt", "Delta", "123", "PDX", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        //assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Airlines"));
        //check file
    }

    /*
    @Test
    void badPrettyFile(){
        MainMethodResult result = invokeMain(Project3.class  ,"-pretty" , " ", "Delta", "123", "PDX", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Unable open the file."));
        //check file
    }

     */

    @Test
    void xmlAndtextFileThrowsException (){
        MainMethodResult result = invokeMain(Project4.class  ,"-textFile", "airline.txt", "-xmlFile", "bad.xml","-pretty" , "prettyTest1.txt", "Delta", "123", "PDX", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Cannot have -textFile and -xmlFile at the same time."));
    }

    @Test
    void noXmlFile(){
        MainMethodResult result = invokeMain(Project4.class , "-xmlFile");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Error. No xml file name provided."));
    }

    @Test
    void unknownCommandThrowsException() {
        MainMethodResult result = invokeMain(Project4.class  ,"-hi", "airline.txt", "-xmlFile", "bad.xml","-pretty" , "prettyTest1.txt", "Delta", "123", "PDX", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Unknown option command.  Please check spelling."));

    }

    @Test
    void xmlFilePresent() {
        MainMethodResult result = invokeMain(Project4.class  ,"-xmlFile", "xdump.xml", "Delta", "123", "PDX", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        //assertThat(result.getTextWrittenToStandardOut(), containsString("Unknown option command.  Please check spelling."));
    }

    /*
    @Test
    void xmlBadFile() {
        MainMethodResult result = invokeMain(Project3.class  ,"-xmlFile", " ", "Delta", "123", "PDX", "12/12/2000","12:12", "PM", "PDX", "03/03/2023", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Cannot dump to file."));
    }
     */

    @Test
    void validDateFail() {
        MainMethodResult result = invokeMain(Project4.class , "Delta", "123", "PDX", "12/12/2000","12:12", "PM", "PDX", "03/03", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Invalid date input in arrival fields."));

    }

    @Test
    void timeMalformed() {
        MainMethodResult result = invokeMain(Project4.class , "Delta", "123", "PDX", "12/12/2000","12:12:1", "PM", "PDX", "03/03", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Time is malformed in departure fields."));

    }

    @Test
    void createDateProblem() {
        MainMethodResult result = invokeMain(Project4.class , "Delta", "123", "PDX", "12/12/2000","12:12:1", "PM", "PDX", "03/03", "12:12", "AM");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Time is malformed in departure fields."));

    }


}
