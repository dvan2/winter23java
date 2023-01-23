package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractFlight;

/**This class implements information about a <code>Flight</code>.
 */
public class Flight extends AbstractFlight {



  private  int flight_number;
  private  String src;
  private  String depart_date;
  private String dest;
  private String arrive_date;

  /**This constructor can be used to create a new instance of a <code>Flight</code>.
   *
   * @param flight_number The flight number.
   *
   * @param source 3-letter code of Source airport.
   */
  Flight(int flight_number, String source, String departure_d, String destination, String arrival_d){
    this.flight_number = flight_number;
    this.src= source;
    this.depart_date = departure_d;
    this.dest= destination;
    this.arrive_date= arrival_d;

}
  @Override
  public int getNumber() {
    return flight_number;
  }

  @Override
  public String getSource() {
    return this.src;
  }

  @Override
  public String getDepartureString() {
    return this.depart_date;
  }

  @Override
  public String getDestination() {
    return this.dest;
  }

  @Override
  public String getArrivalString() {
    return this.arrive_date;
  }

  /**
   * Displays the contents of a flight.
   */
  public void display(){
    System.out.println(this.flight_number + ", "+ this.src + ", "+ this.depart_date + ", " +
      this.dest+ " " + this.arrive_date);
  }

  /**
   * This constructor builds a flight using an existing Flight.
   * @param flight
   */
  public Flight(Flight flight){
    this.flight_number = flight.flight_number;
    this.src= flight.src;
    this.depart_date = flight.depart_date;
    this.dest= flight.dest;
    this.arrive_date= flight.arrive_date;
  }

  public void hasValidCode() throws IllegalArgumentException{
    if(this.src.length() != 3)
      throw new IllegalArgumentException("Source airport code has invalid length. Must be 3 character.");

    if(this.dest.length() != 3)
      throw new IllegalArgumentException("Destination airport code has invalid length. Must be 3 character.");
  }

}
