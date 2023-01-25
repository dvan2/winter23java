package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
      MainMethodResult result = invokeMain();
      assertThat(result.getTextWrittenToStandardOut(), containsString("To use this program enter information about a flight in this order:"));
  }

  @Test
  void testProgramWithPrintOption() {
      MainMethodResult result = invokeMain(Project1.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 343 departs DEN at 12/12/2000 12:12 arrives PDX at 1/1/2022 1:1"));
  }

  @Test
  void testPrintWithPrintAndReadmeOption(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 343 departs DEN at 12/12/2000 12:12 arrives PDX at 1/1/2022 1:1"));
  }
  @Test
  void testPrintWithReadmeAndPrintOption(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 343 departs DEN at 12/12/2000 12:12 arrives PDX at 1/1/2022 1:1"));
  }

  @Test
  void testReadmeFileOpens(){
      MainMethodResult result = invokeMain(Project1.class,"-README", "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Project 1 is a simple program where the user is able to use the command line arguments to create a flight."));
  }

  @Test
  void ReadmeFileSecondOption(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Project 1 is a simple program where the user is able to use the command line arguments to create a flight."));
  }


  @Test
  void testTooManyArgumentsWithPrint(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX","TUL", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is too many arguments for a flight."));
  }

  @Test
  void testTooManyArgumentsWithPrintAndReadme(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX","TUL", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is too many arguments for a flight."));
  }

  @Test
  void testTooManyArgumentsWithoutOptions(){
      MainMethodResult result = invokeMain(Project1.class,  "Delta", "343", "DEN", "12/12/2000","12:12","PDX","TUL", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is too many arguments for a flight."));
  }

  @Test
  @Disabled
  void notEnoughArguments(){
      MainMethodResult result = invokeMain(Project1.class, "Delta", "343", "DEN", "12/12/2000","12:12", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is not enough arguments for a flight."));
  }




}