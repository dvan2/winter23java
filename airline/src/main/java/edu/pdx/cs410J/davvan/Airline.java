package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private Flight flight;

  /**
   * A constructor that creates airline with just a name.
   * @param name
   */
  public Airline(String name){
    this.name= name;
  }

  /** A constructor for <code>Airline</code> that creates an airline, provided a name and a <code>Flight</code> object.
   *
   * @param name
   * @param flight
   */
  public Airline(String name, Flight flight) {
    this.name = name;
    this.flight= new Flight(flight);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public Collection<Flight> getFlights() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * This method displays the name of an airline and its flight that it has.
   */
  public void displayAirline(){
    System.out.print(name + " ");
    if(flight!= null){
      System.out.println(flight);
    }
  }

}
