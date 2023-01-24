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
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  @Test
  @Disabled
  void testProgramWithPrintOption(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "Delta", "123", "DEN", "12:12:2000","12:12", "PDX", "1/1/2022", "1:1");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Delta Flight 123 departs abc at 12/12/2000 12:12 arrives PDX at 1/1/2022 10:10"));
  }

}