package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class ConverterTest extends InvokeMainTestCase{
    private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }
    @Test
    public void testUsage(){
        MainMethodResult result= invokeMain();
        assertThat(result.getTextWrittenToStandardOut(), containsString("usage"));
    }

    @Test
    public void canParseFromText(){
        MainMethodResult result= invokeMain(Converter.class, "airline.txt", "my.xml");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Delta"));
    }

    @Test
    public void canDumpToXml(){
        MainMethodResult result= invokeMain(Converter.class, "airline.txt", "convert.xml");
        assertThat(result.getTextWrittenToStandardOut(), containsString("parsed"));
    }
}
