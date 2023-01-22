package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractFlight;

/**This class implements information about a <code>Flight</code>.
 */
public class Flight extends AbstractFlight {



  private  int flightNumber;
  private  String src;
  private  String date;

  /**This constructor can be used to create a new instance of a <code>Flight</code>.
   *
   * @param flight_number The flight number.
   *
   * @param source 3-letter code of Source airport.
   */
  Flight(int flight_number, String source, String date){
    this.flightNumber= flight_number;
    this.src= source;
    this.date= date;
}
  @Override
  public int getNumber() {
    return 42;
  }

  @Override
  public String getSource() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDepartureString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDestination() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getArrivalString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * Displays the contents of a flight.
   */
  public void display(){
    System.out.println(this.flightNumber + ", "+ this.src + ", "+ this.date);
  }

  /**
   * This constructor builds a flight using an existing Flight.
   * @param flight
   */
  public Flight(Flight flight){
    this.flightNumber= flight.flightNumber;
    this.src= flight.src;
    this.date= flight.date;
  }
}
