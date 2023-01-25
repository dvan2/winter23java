package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.InvokeMainTestCase;
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

    //a variable to test README
    final String readme_test= "David Van";

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
  void readmeOnlyArgument(){
      MainMethodResult result = invokeMain(Project1.class,"-README");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }
  @Test
  void readmeAndPrintOnly(){
      MainMethodResult result = invokeMain(Project1.class,"-README", "-print");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

  @Test
  void printOnly(){
      MainMethodResult result = invokeMain(Project1.class, "-print");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is not enough arguments for a flight."));
  }

  @Test
  void readmeFirstOptionWithPrint(){
      MainMethodResult result = invokeMain(Project1.class,"-README", "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

  @Test
  void readmeFileSecondOption(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

  @Test
  void tooManyArgumentsWithPrint(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX","TUL", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is too many arguments for a flight."));
  }

  @Test
  void testTooManyArgumentsWithPrintAndReadme(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "PDX","TUL", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

  @Test
  void testTooManyArgumentsWithoutOptions(){
      MainMethodResult result = invokeMain(Project1.class,  "Delta", "343", "DEN", "12/12/2000","12:12","PDX","TUL", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is too many arguments for a flight."));
  }

  @Test
  void notEnoughArguments(){
      MainMethodResult result = invokeMain(Project1.class, "Delta", "343", "DEN", "12/12/2000","12:12", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardError(), containsString("There is not enough arguments for a flight."));
  }

  @Test
  void notEnoughArgumentsWithPrintAndReadme(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "-README", "Delta", "343", "DEN", "12/12/2000","12:12", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString(readme_test));
  }

    @Test
    void notEnoughArgumentsWithPrint(){
        MainMethodResult result = invokeMain(Project1.class, "-print", "Delta", "343", "DEN", "12/12/2000","12:12", "1/1/2022", "1:1");
        assertThat(result.getTextWrittenToStandardError(), containsString("There is not enough arguments for a flight."));
    }

    @Test
    void notNumberFlight(){
        MainMethodResult result = invokeMain(Project1.class, "-print", "Delta", "abc", "DEN", "12/12/2000","12:12", "PDX", "1/1/2022", "1:1");
        assertThat(result.getTextWrittenToStandardError(), containsString("Flight number must be a number."));
    }

    @Test
    void invalidDate(){
        MainMethodResult result = invokeMain(Project1.class, "-print", "Delta", "123", "DEN", "13/12/0","12:12", "PDX", "1/1/2022", "1:1");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid date input"));
    }

    @Test
    void invalidTime(){
        MainMethodResult result = invokeMain(Project1.class, "-print", "Delta", "123", "DEN", "12/12/2000","13:12", "PDX", "1/1/2022", "1:1");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid hour input."));
    }
}
