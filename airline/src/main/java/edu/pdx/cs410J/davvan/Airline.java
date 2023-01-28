package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  /**
   * name of the airport.
   */
  private final String name;
  /**
   * An airline contains a flight object.
   */
  private Flight flight;

  /**
   * A constructor that creates airline with just a name.
   * @param name name of the airport.
   */
  public Airline(String name){
    this.name= name;
  }

  /** A constructor for <code>Airline</code> that creates an airline, provided a name and a <code>Flight</code> object.
   *
   * @param name name of the airport.
   * @param flight An existing <code>Flight</code> object.
   */
  public Airline(String name,Flight flight) {
    this.name = name;
    this.flight= new Flight(flight);
  }

  /**
   * returns private field: name
   * @return name
   */
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
