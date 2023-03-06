package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class XmlParser implements AirlineParser<Airline> {

    private final Reader reader;

    public XmlParser(Reader reader){
        this.reader= reader;
    }


    public Airline parse() throws ParserException{
        AirlineXmlHelper helper = new AirlineXmlHelper();
        Document doc = null;

        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.isIgnoringComments();
            factory.setNamespaceAware(true);

            DocumentBuilder builder =
                    factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);

            doc = builder.parse(new InputSource(this.reader));
        } catch (ParserConfigurationException e) {
            throw new ParserException("Cannot parse from xml file.");
        } catch (IOException e) {
            throw new ParserException("Cannot parse from xml file.  Bad arguments.");
        } catch (SAXException e) {
            throw new ParserException("Cannot build documents.  Sax exception.");
        }

        Element root = (Element) doc.getChildNodes().item(1);


        NodeList entries= root.getElementsByTagName("name");

        Node node = entries.item(0);
        Element entry = (Element) node;
        Node text = entry.getFirstChild();
        String air_name= text.getNodeValue();
        Airline result= new Airline(air_name);
        result.fillInFlights(root);

        return result;
    }

    private Document getDocument(File file) throws ParserConfigurationException, SAXException, IOException {
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


        Document doc= builder.parse(new InputSource(new FileReader(file)));
        return doc;
    }


}
