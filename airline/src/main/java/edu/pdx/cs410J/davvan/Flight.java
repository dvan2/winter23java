package edu.pdx.cs410J.davvan;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
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
  private final int flight_number;

  /**
   * 3-letter code for source airport.
   */
  private final String src;
  /**
   * Flight departure date
   */
  private final Date depart_date;



  /**
   * 3-letter code for destination airport.
   */
  private final String dest;
  /**
   * Flight arrival date.
   */
  private final Date arrive_date;

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
   * @param format_pattern
   */
  public String writeFlight(String format_pattern){
    return "\n" + this.flight_number + " " + this.src + " " +
            orginalFormatDate(depart_date, format_pattern) + " " +this.dest + " " + orginalFormatDate(arrive_date, format_pattern);
  }

  public String prettyWriteFlight(String format_pattern){
    return "\nFlight " + this.flight_number + " departs " + this.src +
            " on " + orginalFormatDate(this.depart_date, format_pattern) +
            "\nFlight " + this.flight_number + " arrives " + this.dest +
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
   * This function will format the date back into the format of MM/dd/yyyy hh:mm aa.
   * Which is parsable by the parser
   * @param a_date
   * @param format_pattern
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

  @Override
  public int compareTo(Flight o) {
    if(this.src.equalsIgnoreCase(o.src)){
      return this.depart_date.compareTo(o.depart_date);
    }
    return this.src.compareTo(o.src);
  }
}
