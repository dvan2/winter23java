package edu.pdx.cs410J.davvan;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{

  @Test
  void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
    assertThat(pat.getName(), equalTo(name));
  }
  @Test
  void allStudentSayThisClassIsTooMuchWork(){
    Student student = new Student("Name", new ArrayList<>(), 0.0, "Doesn't matter");
    assertThat(student.says(), equalTo("This class is too much work"));
  }

  @Test
  //disable skips this test for now
  @Disabled
  void daveStudentSaysWhatIsExpected(){
    //Arrange (Given)
    ArrayList<String> classes= new ArrayList<>();
    classes.add("Algorithm");
    classes.add("Operation Systems");
    classes.add("Java");
    Student dave = new Student("Dave", classes, 3.64, "male");

    //Act (When)
    String daveString= dave.toString();

    //Assert (Then)
    assertThat(daveString, equalTo
            ("The result is Dave has a GPA of 3.64 and is taking 3 classes: Algorithms, Operating Systems, and Java. He says \"This class is too much work\""));
  }

}
