package edu.pdx.cs410J.davvan;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;



/**This class implements information about a <code>Flight</code>.
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {
  /**
   * Flight number.
   */
  private int flight_number;

  /**
   * 3-letter code for source airport.
   */
  private String src;
  /**
   * Flight departure date
   */
  private Date depart_date;

  /**
   * 3-letter code for destination airport.
   */
  private String dest;
  /**
   * Flight arrival date.
   */
  private Date arrive_date;

  /**
   * This constructor can be used to create a new instance of a <code>Flight</code> if passed all these parameters.
   * @param flight_number Flight number.
   * @param source 3-letter code for source airport.
   * @param departure_d Flight departure date in mm/dd/yyyy hh:mm format
   * @param destination 3-letter code for destination airport.
   * @param arrival_d Flight arrival date in mm/dd/yyyy hh:mm format
   */
  Flight(int flight_number, String source, Date departure_d, String destination, Date arrival_d){
    this.flight_number = flight_number;
    this.src= source;
    this.depart_date = departure_d;
    this.dest= destination;
    this.arrive_date= arrival_d;
}
  Flight(){
    this.flight_number= 0;
    this.src= null;
    this.depart_date= null;
    this.dest= null;
    this.arrive_date= null;
  }

  public Flight(int flight_number) {
    this.flight_number= flight_number;
  }

  /**
   * Gets private field: flight_number
   * @return flight_number
   */
  @Override
  public int getNumber() {
    return flight_number;
  }

  /**
   * Gets private field: src
   * @return src
   */
  @Override
  public String getSource() {
    return this.src;
  }

  /**
   * Gets private field: depart_date
   * @return depart_date
   */
  @Override
  public String getDepartureString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(depart_date);
  }

  /**
   * Gets private field: dest
   * @return dest
   */
  @Override
  public String getDestination() {
    return this.dest;
  }

  /**
   * Gets private field: arrive_date
   * @return arrive_date
   */
  @Override
  public String getArrivalString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(arrive_date);
  }
  /**
   * This constructor builds a flight using an existing <code>Flight</code> object.
   * @param flight an existing flight object
   */
  public Flight(Flight flight){
    this.flight_number = flight.flight_number;
    this.src= flight.src;
    this.depart_date = flight.depart_date;
    this.dest= flight.dest;
    this.arrive_date= flight.arrive_date;
  }

  /**
   * This method checks if the airport source and destination is exactly 3 character long.
   * If the strings are not 3 character long, an IllegalArgumentException is thrown.
   * @throws IllegalArgumentException
   */
  public void hasValidCode() throws IllegalArgumentException{
    //Online source to check for a character:
    // https://www.tutorialspoint.com/how-to-check-if-a-given-character-is-a-number-letter-in-java
    if(this.src.length() != 3) {
      throw new IllegalArgumentException("Source airport code has invalid length. Must be 3 character.");
    }
    for (int i=0; i<3; ++i) {
      if (Character.isDigit(src.charAt(i))) {
        throw new IllegalArgumentException("Source airport code cannot contain number.");
      }
    }

    if(this.dest.length() != 3){
      throw new IllegalArgumentException("Destination airport code has invalid length. Must be 3 character.");
    }

    for (int i=0; i<3; ++i){
      if(Character.isDigit(dest.charAt(i))){
        throw new IllegalArgumentException("Source airport code cannot contain number.");
      }
    }

    //check if valid airport code
    if(AirportNames.getName(this.src)== null){
      throw new IllegalArgumentException("Source airport code doesn't exists.");
    }
    if(AirportNames.getName(this.dest)== null){
      throw new IllegalArgumentException("Destination airport code doesn't exists.");
    }

  }

  /**
   * This method is used by airline to get all its flight.
   * @return : String where each flight field is separated by space and each flight starts on new line.
   * @param format_pattern : A SimpleDateFormat pattern
   */
  public String writeFlight(String format_pattern){
    return "\n" + this.flight_number + " " + this.src + " " +
            orginalFormatDate(depart_date, format_pattern) + " " +this.dest + " " + orginalFormatDate(arrive_date, format_pattern);
  }

  /**
   * This method is used by PrettyPrinter to printout flights in a nice formatted way.
   * @param format_pattern : A SimpleDateFormat pattern
   * @return : A string that is formatted in an easy-to-read paragraph about flights.
   */
  public String prettyWriteFlight(String format_pattern){
    return "\nFlight " + this.flight_number + " departs " +
            AirportNames.getName(this.src) + " ("  +this.src + ")" +
            " on " + orginalFormatDate(this.depart_date, format_pattern) +
            "\nFlight " + this.flight_number + " arrives "
            + AirportNames.getName(this.dest) + " ("  +this.dest + ")" +
            " on " + orginalFormatDate(this.arrive_date, format_pattern) +
            "\nTotal Duration of the flight: " + calculateMinutes() + " minutes.\n";
  }

  /**
   * This function compares departure and arrival time.
   * @return total time elapsed between departure and arrival.  Negative if arrival is before departure.
   */
  private long calculateMinutes(){
    //Stack overflow source:
    // https://stackoverflow.com/questions/17940200/how-to-find-the-duration-of-difference-between-two-dates-in-java
    long difference=  this.arrive_date.getTime() - this.depart_date.getTime();

    long total_minutes= TimeUnit.MILLISECONDS.toMinutes(difference);
    return total_minutes;
  }

  /**
   * This function will format the date back into the format specified, which is parsable by the parser
   * @param a_date :A Date typed object
   * @param format_pattern : A SimpleDateFormat pattern
   * @return String in the specified format
   */
  public String orginalFormatDate(Date a_date, String format_pattern){
    SimpleDateFormat orginal_format= new SimpleDateFormat(format_pattern);
    String result= null;
    try{
      result= orginal_format.format(a_date);
    }catch(NullPointerException e){
      System.err.println("Null argument");
    }
    return result;
  }

  /**
   * This method compares the current instance of flight to another instance
   * @param o the flight to be compared.
   * @return : 0 if source airport and depart time is the same. Value Greater than 1 if this object's source is earlier in alphabet or if departure time is earlier.
   * Less than 1 if vice-versa.
   *
   */
  @Override
  public int compareTo(Flight o) {
    if(this.src.equalsIgnoreCase(o.src)){
      return this.depart_date.compareTo(o.depart_date);
    }
    return this.src.compareTo(o.src);
  }

  /**
   * This function checks if the depart date is actually before arrival date.
   * @throws IllegalArgumentException
   */
    public void hasValidDate() throws IllegalArgumentException{
      if(this.depart_date.compareTo(this.arrive_date)>0){
        throw new IllegalArgumentException("Depart date cannot be later than arrival date.");
      }
    }

  /**
   * This method is used to fill in flight information.
   * @param root : Should pass in a flight element as root.
   * @throws ParserException : If parser fails to parse because of bad xml.
   */
  public void fillFlight(Element root) throws ParserException {
      NodeList entries= root.getChildNodes();
      for(int i=0; i<entries.getLength(); i++){
        Node node = entries.item(i);
        if(!(node instanceof Element)){
          continue;
        }
        Element entry= (Element) node;


        try {
          switch (entry.getNodeName()) {
            case "number":
              this.flight_number = getInteger(entry.getFirstChild().getNodeValue());
              break;
            case "src":
              this.src = entry.getFirstChild().getNodeValue();
              break;

            case "depart":
              this.depart_date = fillDate(entry);
              break;
            case "dest":
              this.dest = entry.getFirstChild().getNodeValue();
              break;
            case "arrive":
              this.arrive_date = fillDate(entry);
              break;
          }
        }catch(NullPointerException e){
          throw new ParserException("Bad Xml content. Cannot get null element in Xml");
        }
        catch(DOMException e) {
          throw new ParserException("Bad content in xml.");
        }
      }
      try {
        this.hasValidCode();
      }catch(IllegalArgumentException e){
        throw new ParserException("Bad Xml content in Flight element: " + e.getMessage());
      }
    }

  /**
   * This method provides a way to throw my own exception message to get integers from xml.
   * @param s : String to extract int from
   * @return : int that is extracted
   * @throws ParserException : if string wasn't int.
   */
    private int getInteger(String s) throws ParserException{
      try{
        return Integer.parseInt(s);
      }catch(NumberFormatException e){
        throw new ParserException(s + " is not an integer.");
      }
    }

  /**
   * This method is used by <code>fillFlight</code> to fill date elements.
   * @param root : The root should be a date element
   * @return : A date extracted from the xml.
   * @throws ParserException : If date is invalid throws exception.
   */
  private Date fillDate(Element root) throws ParserException {

    String date = "";
    NodeList entries= root.getChildNodes();
    for(int i=0; i<entries.getLength(); i++) {
      Node node = entries.item(i);
      if (!(node instanceof Element)) {
        continue;
      }
      Element entry = (Element) node;

      switch (entry.getNodeName()) {
        case "date":

          date = entry.getAttribute("month") + "/" +
                  entry.getAttribute("day") + "/" +
                  entry.getAttribute("year") + " ";
          break;

        case "time":
          date += entry.getAttribute("hour") + ":" +
                  entry.getAttribute("minute");
          break;
      }
    }
    Date date_format= null;
    try {

      date_format= createDate(date, "MM/dd/yyyy HH:mm");

    } catch (ParserException e) {
      throw new ParserException("Invalid date in xml");
    }
    return date_format;

  }

  /**
   * This method builds on to an existing doc and appends flights to the doc object
   * @param doc :An existing doc of airline
   * @param root : Airline Root element
   * @return : Document with added flights
   */
  public Document dumpFlights(Document doc, Element root) {
    Element flight_num = doc.createElement("number");
    root.appendChild(flight_num);
    flight_num.appendChild(doc.createTextNode(Integer.toString(flight_number)));

    Element src = doc.createElement("src");
    root.appendChild(src);
    src.appendChild(doc.createTextNode(this.src));

    Element depart_elem = doc.createElement("depart");
    root.appendChild(depart_elem);
    doc = getDate( doc, depart_elem, this.depart_date);

    Element dest_elem = doc.createElement("dest");
    root.appendChild(dest_elem);
    dest_elem.appendChild(doc.createTextNode(this.dest));

    Element arrival_elem = doc.createElement("arrive");
    root.appendChild(arrival_elem);
    doc = getDate(doc, arrival_elem, this.arrive_date);

    return doc;
  }

  /**
   * This method is used by <code>dumpFlights</code> to add date childnodes of src and depart
   * @param doc
   * @param root
   * @param depart_date :The date to extract
   * @return : Document object with added date elements with attributes.
   */
  public Document getDate(Document doc, Element root, Date depart_date){
    Element date = doc.createElement("date");

    String date_string= orginalFormatDate(depart_date, "MM/dd/yyyy/hh/mm");

    String [] date_split = date_string.split("/", -2);
    date.setAttribute("day", date_split[0]);
    date.setAttribute("month", date_split[1]);
    date.setAttribute("year", date_split[2]);

    root.appendChild(date);

    Element time = doc.createElement("time");

    time.setAttribute("hour", date_split[3]);
    time.setAttribute("minute", date_split[4]);
    root.appendChild(time);

    return doc;
  }

  static public Date createDate(String full_date, String format_pattern) throws ParserException {
    SimpleDateFormat my_format= new SimpleDateFormat(format_pattern);
    Date formated_date;
    try {
      formated_date= my_format.parse(full_date);
    }catch(ParseException e){
      throw new ParserException("There was a problem parsing the date: " + full_date);
    }
    return formated_date;
  }

  public void createFlight(String flightNumString, String sourceAirport, String depart, String dest, String arrive) throws ParseException, IOException {

    try {
      this.flight_number = Integer.parseInt(flightNumString);

      //Validify airport code later.
      this.src = sourceAirport;
      this.dest = dest;

      String hour_pattern = "mm/dd/yyyy MM:hh aa";

      this.depart_date = createDate(depart, hour_pattern);
      this.arrive_date = createDate(arrive, hour_pattern);


    }catch(ParserException e){
      throw new IOException("Bad flight argument");
    }catch(NumberFormatException e) {
      throw new IOException(flightNumString + " is not a number.");
    }
  }
}
