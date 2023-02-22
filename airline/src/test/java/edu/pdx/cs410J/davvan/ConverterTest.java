package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class ConverterTest extends InvokeMainTestCase{
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Converter.class, args );
    }
    @Test
    public void testUsage(){
        MainMethodResult result= invokeMain();
        MatcherAssert.assertThat(result.getTextWrittenToStandardOut(), Matchers.containsString("usage: java -cp target/airline-2023.0.0.jar"));
    }

    @Test
    public void cantOpenInvalidFileText() {
        MainMethodResult result = invokeMain(Converter.class , " " , "bad.xml");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Unable to open text file."));
    }

    @Test
    public void invalidXmlFile() {
        MainMethodResult result = invokeMain(Converter.class , " " , "bad.xml");
        MatcherAssert.assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Unable to open text file."));
    }

}
