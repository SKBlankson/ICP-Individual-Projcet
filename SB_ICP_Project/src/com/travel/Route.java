package com.travel;

import java.util.Objects;

/** A class that models the routes for an airline */
public class Route {
    String airlineCode;
    String airlineID;
    String sourceAirportCode;
    String sourceAirportID;
    String destinationCode;
    String destinationID;
    String codeShare;
    int stops;
    String eqipment;


    /** A constructor for the route class */
    public Route(String airlineCode, String airlineID, String sourceAirportCode,
                 String getSourceAirportID, String destinationCode,
                 String destinationID, String codeShare, int stops, String equipment) {
        this.airlineCode = airlineCode;
        this.airlineID = airlineID;
        this.sourceAirportCode = sourceAirportCode;
        this.sourceAirportID = getSourceAirportID;
        this.destinationCode = destinationCode;
        this.destinationID = destinationID;
        this.codeShare = codeShare;
        this.stops = stops;
        this.eqipment = equipment;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(String airlineID) {
        this.airlineID = airlineID;
    }

    public String getSourceAirportCode() {
        return sourceAirportCode;
    }

    public void setSourceAirportCode(String sourceAirportCode) {
        this.sourceAirportCode = sourceAirportCode;
    }

    public String getSourceAirportID() {
        return sourceAirportID;
    }

    public void setSourceAirportID(String sourceAirportID) {
        this.sourceAirportID = sourceAirportID;
    }

    public String getDestinationCode() {return destinationCode;}

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(String destinationID) {
        this.destinationID = destinationID;
    }

    public String getCodeShare() {
        return codeShare;
    }

    public void setCodeShare(String codeShare) {
        this.codeShare = codeShare;
    }

    public int getStops() {return stops;}

    public void setStops(int stops) {
        this.stops = stops;
    }

    public String getEqipment() {
        return eqipment;
    }

    public void setEqipment(String eqipment) {
        this.eqipment = eqipment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return stops == route.stops && Objects.equals(airlineCode, route.airlineCode) &&
                Objects.equals(airlineID, route.airlineID) && Objects.equals(sourceAirportCode, route.sourceAirportCode)
                && Objects.equals(sourceAirportID, route.sourceAirportID)
                && Objects.equals(destinationCode, route.destinationCode)
                && Objects.equals(destinationID, route.destinationID)
                && Objects.equals(codeShare, route.codeShare)
                && Objects.equals(eqipment, route.eqipment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airlineCode, airlineID, sourceAirportCode,
                sourceAirportID, destinationCode, destinationID, codeShare, stops, eqipment);
    }

    @Override
    public String toString() {
        return "Route{" +
                "sourceAirportCode='" + sourceAirportCode + '\'' +
                ", destinationCode='" + destinationCode + '\'' +
                ", stops=" + stops +
                '}';
    }
}
