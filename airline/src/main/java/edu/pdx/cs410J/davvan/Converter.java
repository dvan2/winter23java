package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * This class will convert a TextFile to an XML file.
 */
public class Converter {

    public static void usage() {
        System.out.println("usage: java -cp target/airline-2023.0.0.jar edu.pdx.cs410J.<your-login-id>.Converter <textfile> <xmlFile>");
    }

    public static void main(String[] args) {
        if(args.length != 2){
            usage();
        }
        String file_name= args[0];
        String xml_file= args[1];
        Airline to_xml= null;

        try{
            FileReader to_read = new FileReader(file_name);
            TextParser in = new TextParser(to_read);
            to_xml = in.parse();
            System.out.println("We got airline" + to_xml);
        } catch (ParserException e) {
            System.err.println(e.getMessage());
            System.err.println("Unable to parse from the file.");
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file.");
        }

        AirlineXmlHelper helper = new AirlineXmlHelper();

        File textFile= new File(xml_file);
        XmlDumper dumper= null;
        try {
            dumper = new XmlDumper(new FileWriter(textFile));
            dumper.dump(to_xml);
        } catch (IOException e) {
            System.err.println("Cannot dump to xml file.");
        }

    }
}
