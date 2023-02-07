package edu.pdx.cs410J.davvan;
import edu.pdx.cs410J.AbstractFlight;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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
    String good_date= DateFormat.getDateInstance(DateFormat.SHORT).format(depart_date);

    System.out.println("Good date: " +good_date);
    return good_date;
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
    return this.arrive_date.toString();
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
  }

  /**
   * This method is used by airline to get all its flight.
   * @return : String where each flight field is separated by space and each flight starts on new line.
   */
  public String writeFlight(){
    return "\n" + this.flight_number + " " + this.src + " " +
            this.depart_date + " " +this.dest + " " +this.arrive_date;
  }

  @Override
  public int compareTo(Flight o) {
    int result= compareTo(o);
    return result;
  }
}
