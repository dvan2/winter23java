package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractAirline;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Airline extends AbstractAirline<Flight> {
  /**
   * name of the airport.
   */
  private final String name;

  private ArrayList<Flight> flightList;

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
    if(flightList==null) {
      this.addFlight(flight);
    }
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
    if(flightList== null){
      flightList= new ArrayList<>();
      flightList.add(flight);
      return;
    }
    flightList.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    return flightList;
  }

  /**
   * This method displays the name of an airline and its flight that it has.
   */
  public void displayAirline(){
    System.out.print(name + " ");
    if(flightList!= null){
      Iterator<Flight> i= flightList.iterator();
      while(i.hasNext()){
        System.out.println(i.next());
      }
    }
  }

  public String getAirline(){
    String result= "";
    if(this.name != null){
      result += this.name + " ";
    }
    for(Flight flight : flightList){
      result+= flight.writeFlight();
    }

    return result;
  }
}
