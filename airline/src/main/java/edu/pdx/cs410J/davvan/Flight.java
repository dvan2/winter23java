package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  //A flight's data:
  private final int flightNumber;
  private final String src;

  Flight(int flightnumber, String source){
  this.flightNumber= flightnumber;
  this.src= source;

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

  public void display(){
    System.out.println(this.flightNumber + this.src);
  }
}
