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

/**
 * This class allows airline to be dumped using XmlDumper object
 */
public class XmlDumper implements AirlineDumper<Airline> {
    private final Writer writer;

    /**
     * Constructor sets a write stream.
     * @param writer
     */
    public XmlDumper(Writer writer) {
        this.writer = writer;
    }

    /**
     * This method dumps an airline to an xml file
     * @param airline : Airline to dump.
     * @throws IOException
     */
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

            doc= airline.dumpAirline(doc, root);

            Source src = new DOMSource(doc);
            Result res = new StreamResult(this.writer);

            TransformerFactory xFactory = TransformerFactory.newInstance();
            Transformer xform = xFactory.newTransformer();
            xform.setOutputProperty(OutputKeys.INDENT, "yes");
            xform.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
            xform.transform(src, res);
        }catch (TransformerException | ParserConfigurationException e){
            throw new IOException("Error dumping to file");
        }finally {
            writer.close();
        }
    }
}
