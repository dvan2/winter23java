package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private Flight flight;

  public Airline(String name){
    this.name= name;
  }

  /** A constructor that also creates a flight for an airline.
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

  public void displayAirline(){
    System.out.print(name + " ");
    if(flight!= null){
      System.out.println(flight);
    }
  }

}
