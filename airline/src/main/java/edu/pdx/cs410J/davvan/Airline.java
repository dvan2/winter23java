package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.ParserException;
import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Airline class stores information about an airline
 */
public class Airline extends AbstractAirline<Flight> {
  /**
   * name of the airport.
   */
  private final String name;

  /**
   * An airline stores a collection of flights.
   */
  private ArrayList<Flight> flightList;

  /**
   * A constructor that creates airline with just a name.
   * @param name name of the airport.
   */
  public Airline(String name){
    this.name= name;
    this.flightList= null;
  }

  /**
   * default constructor sets name and collection to null
   */
  public Airline(){
    this.name = null;
    this.flightList= null;
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

  /**
   * This method adds a flight to the collection of flights in an airline
   * @param flight : A flight object to add.
   */
  @Override
  public void addFlight(Flight flight) {
    if(flightList== null){
      flightList= new ArrayList<>();
      flightList.add(flight);
      return;
    }
    flightList.add(flight);
  }

  public void fillInFlights(Element root) throws ParserException {
    NodeList flight_entries= root.getElementsByTagName("flight");
    for(int i=0; i< flight_entries.getLength(); i++) {
      Flight flight= new Flight();
      Node node= flight_entries.item(i);
      if(!(node instanceof Element)){
        continue;
      }
      Element entry = (Element) node;
      try {
        flight.fillFlight(entry);
      }catch(ParserException e){
        throw new ParserException(e.getMessage());
      }
      addFlight(flight);
    }
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

  /**
   * This method returns an all flights in an airline as a string.
   * Each field is separated by a space and different flights starts on a new line.
   * @return : Formatted string containing all flights of an airline object.
   * @param format_pattern : a SimpleDateFormat pattern
   */
  public String getAirline(String format_pattern){
    String result= "";
    if(flightList== null){
      return null;
    }
    for(Flight flight : flightList){
      result+= flight.writeFlight(format_pattern);
    }
    return result;
  }

  /**
   * This function is used by <code>PrettyPrinter</code> to format the flights.
   * @param format_pattern : A string for the SimpleDateFormat
   * @return : returns all flights of an airline by calling <code>prettyWriteFlight</code>
   */
  public String getPrettyAirline(String format_pattern){
    String result= "";
    if(flightList== null){
      return null;
    }
    Collections.sort(flightList);
    for(Flight flight : flightList){
      result+= flight.prettyWriteFlight(format_pattern);
    }
    return result;
  }

  public Document dumpAirline(Document doc, Element root) {

    Element name = doc.createElement("name");
    root.appendChild(name);
    name.appendChild(doc.createTextNode(this.name));

    for(Flight flight : flightList){


      Element flight_element = doc.createElement("flight");
      root.appendChild(flight_element);
      doc = flight.dumpFlights(doc, flight_element);
    }
    return doc;
  }

  public void addAirline(Airline addedAir) throws IllegalArgumentException{
    if(!this.name.equals(addedAir.getName())){
      throw new IllegalArgumentException("Error.  Airline name in Xml didn't match.");

    }
    for(Flight flight: addedAir.flightList){
      this.flightList.add(flight);
    }
  }
}
