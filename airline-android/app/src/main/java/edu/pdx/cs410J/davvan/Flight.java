package edu.pdx.cs410J.davvan;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

    @Override
    public int getNumber() {
        return 42;
    }

    @Override
    public String getSource() {
        return "PDX";
    }

    @Override
    public String getDepartureString() {
        return "3/7/2022 6:200 PM";
    }

    @Override
    public String getDestination() {
        return "LAX";
    }

    @Override
    public String getArrivalString() {
        return "3/7/2022 6:200 PM";
    }

}
