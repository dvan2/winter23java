package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParserTest {

    @Test
    void validXmlFileCanBeParsed() throws ParserException, ParserConfigurationException, IOException, SAXException {
        AirlineXmlHelper helper = new AirlineXmlHelper();

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.isIgnoringComments();
        factory.setNamespaceAware(true);

        DocumentBuilder builder =
                factory.newDocumentBuilder();
        builder.setErrorHandler(helper);
        builder.setEntityResolver(helper);

        Document doc= builder.parse(this.getClass().getResourceAsStream("valid-airline.xml"));

        Element root = (Element) doc.getChildNodes().item(1);


        XmlParser parser = new XmlParser(root);
        parser.parse();
        //assertThat(airline.getName(), equalTo("Valid Airlines"));

    }
}
