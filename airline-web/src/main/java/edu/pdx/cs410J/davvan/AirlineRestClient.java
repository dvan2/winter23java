package edu.pdx.cs410J.davvan;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";
    private static final String ARRIVAL= "arrive";

    private final HttpRequestHelper http;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        //this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
        this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
      this.http = http;
    }

  /**
   * Returns the definition for the given word
   */
  public Airline getAirline(String airline_name) throws IOException, ParserException {
    Response response = http.get(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airline_name));
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();

    edu.pdx.cs410J.davvan.XmlParser parser = new XmlParser(new StringReader(content));
    return parser.parse();
  }

    /**
     * This class search for an airline with specified source and destination airport code
     * @param airline_name : The airline to search for
     * @param source : The source code airport
     * @param dest : The destination code airport
     * @return An airline if found
     * @throws IOException
     * @throws ParserException
     */
  public Airline getAirline(String airline_name, String source, String dest) throws IOException, ParserException {
      Response response = http.get(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airline_name,
              AirlineServlet.SOURCE_PARAMETER, source,
              AirlineServlet.DEST_PARAMETER, dest));
      throwExceptionIfNotOkayHttpStatus(response);
      String content = response.getContent();
      XmlParser parser = new XmlParser(new StringReader(content));
      return parser.parse();
  }

  public void addFlight(String airline_name, String flight_Number, String src, String depart, String dest, String arrive) throws IOException {
    Response response = http.post(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airline_name,
            AirlineServlet.FLIGHT_NUMBER_PARAMETER, flight_Number , AirlineServlet.SOURCE_PARAMETER, src ,
            AirlineServlet.DEPART_PARAMETER, depart, AirlineServlet.DEST_PARAMETER, dest,
            AirlineServlet.ARRIVE_PARAMETER, arrive));
    throwExceptionIfNotOkayHttpStatus(response);
  }

  public void removeAllDictionaryEntries() throws IOException {
    Response response = http.delete(Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  private void throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getHttpStatusCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
  }

}
