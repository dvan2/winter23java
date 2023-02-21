package edu.pdx.cs410J.davvan;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.Writer;

public class XmlDumper implements AirlineDumper<Airline> {
    private final Writer writer;

    public XmlDumper(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void dump(Airline airline) throws IOException {
        AirlineXmlHelper helper = new AirlineXmlHelper();

        try {
            Document doc = null;
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            builder.setEntityResolver(helper);
            builder.setErrorHandler(helper);

            DocumentType docType = dom.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
            doc = dom.createDocument(null, "airline", docType);

            Element root = doc.getDocumentElement();

            /*
            Element name = doc.createElement("name");
            root.appendChild(name);

            String air_name = "Delta";
            name.appendChild(doc.createTextNode(airline.getName()));

             */

            doc= airline.dumpAirline(doc, root);
            /*
            Element flight_num = doc.createElement("flight");
            root.appendChild(flight_num);
            flight_num.appendChild(doc.createTextNode("1234"));

             */

            Source src = new DOMSource(doc);
            Result res = new StreamResult(this.writer);

            TransformerFactory xFactory = TransformerFactory.newInstance();
            Transformer xform = xFactory.newTransformer();
            xform.setOutputProperty(OutputKeys.INDENT, "yes");
            xform.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
            xform.transform(src, res);
        }catch (TransformerException | ParserConfigurationException e){
            throw new IOException("Error dumping to file");
        }

    }
}
