package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractFlight;

/**This class implements information about a <code>Flight</code>.
 */
public class Flight extends AbstractFlight {


  /**
   * Flight number.
   */
  private  int flight_number;

  /**
   * 3-letter code for source airport.
   */
  private  String src;
  /**
   * Flight departure date
   */
  private  String depart_date;
  /**
   * 3-letter code for destination airport.
   */
  private String dest;
  /**
   * Flight arrival date.
   */
  private String arrive_date;

  /**
   * This constructor can be used to create a new instance of a <code>Flight</code> if passed all these parameters.
   * @param flight_number Flight number.
   * @param source 3-letter code for source airport.
   * @param departure_d Flight departure date in mm/dd/yyyy hh:mm format
   * @param destination 3-letter code for destination airport.
   * @param arrival_d Flight arrival date in mm/dd/yyyy hh:mm format
   */
  Flight(int flight_number, String source, String departure_d, String destination, String arrival_d){
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
    return this.depart_date;
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
   * Gets private field
   * @return arrive_date
   */
  @Override
  public String getArrivalString() {
    return this.arrive_date;
  }


  /**
   * This constructor builds a flight using an existing <code>Flight</code> object.
   * @param flight
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
    if(this.src.length() != 3)
      throw new IllegalArgumentException("Source airport code has invalid length. Must be 3 character.");

    if(this.dest.length() != 3)
      throw new IllegalArgumentException("Destination airport code has invalid length. Must be 3 character.");
  }

}
