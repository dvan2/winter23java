package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;

import static edu.pdx.cs410J.davvan.Project3.isValidDateAndTime;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project3Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project3.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("David Van"));
    }
  }

  @Test
  void invalidAMPMReturnsFalse(){
    boolean result= isValidDateAndTime("11/11/2022", "12:12", "PMM");
    assertEquals(result, false);
  }

  @Test
  void createDateParserProblem(){


    Throwable exception= assertThrows
            (ParseException.class, () -> {
              Date date = null;
              date = Project3.createDate("12/12/2000/2 1:1 pm");
            });

    assertEquals("There was a problem parsing the date at: "
            ,exception.getMessage());

  }

  @Test
  void createDateParserProblemWithFormat() throws ParseException {


    Throwable exception= assertThrows
            (ParseException.class, () -> {
              Date date = null;
              date = Project3.createDate("12/12/2000/2 1:1 pm", "MM/dd/yyyy hh:mm aa");
            });

    assertEquals("There was a problem parsing the date at: "
            ,exception.getMessage());

  }

}
