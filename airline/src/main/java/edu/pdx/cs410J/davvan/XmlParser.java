package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;

public class XmlParser implements AirlineParser {

    private final Element root;

    public XmlParser(Element root){
        this.root= root;
    }

    public Airline parse() throws ParserException{
        NodeList entries= root.getElementsByTagName("name");
        System.out.println("airlines:" + entries.getLength());

        Node node = entries.item(0);
        Element entry = (Element) node;
        Node text = entry.getFirstChild();
        String air_name= text.getNodeValue();
        Airline result= new Airline(air_name);
        result.fillInFlights(root);
        System.out.println("Result airline: " + result);

        return null;
    }
}
