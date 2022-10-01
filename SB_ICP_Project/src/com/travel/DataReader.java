package com.travel;
import java.io.*;
import java.util.*;



/** A class that reads the relevant files and creates the objects */
public class DataReader {
    static HashMap<String, ArrayList<Airport>> airportDict = new HashMap<>();
    static HashMap<String, ArrayList<Airline>> airlineDict = new HashMap<>();
    static HashMap<String, ArrayList<Route>> routesDict = new HashMap<>();
    static HashMap<String, Airport> airportMap = new HashMap<>();


    /** A class that reads the airport file and creates airport objects stored in a hashmap
     * it also maps IATA codes to airport objects in a separate map called airportMap
     * @param airportSource the location of the airport file
     */
    public static void readAirports(String airportSource)  {
        Airport newAirport = null;
        try {
            // Open the file
            FileReader reader = new FileReader(airportSource);
            BufferedReader buffReader = new BufferedReader(reader);
            String newLine = "";
            String[] newLineArray;

            while ((newLine = buffReader.readLine()) != null) {
                // Read the new line
                newLineArray = newLine.split(",");

                // If that line has clean data, create an airport object
                if (newLineArray.length == 14 && !newLineArray[9].equals("\\N")) {
                    newAirport = new Airport(Integer.parseInt(newLineArray[0]),
                            newLineArray[1],
                            newLineArray[2],
                            newLineArray[3],
                            newLineArray[4],
                            newLineArray[5],
                            (Float.parseFloat(newLineArray[6])),
                            (Float.parseFloat(newLineArray[7])),
                            (Float.parseFloat(newLineArray[8])),
                            (Float.parseFloat(newLineArray[9])),
                            newLineArray[10],
                            newLineArray[11],
                            newLineArray[12],
                            newLineArray[13]);

                    // Putting the new airport object in the ariport hashmap
                    String city = newLineArray[2];
                    String country = newLineArray[3];

                    //Check if hashmap already has the object and act accordingly
                    if (airportDict.containsKey(city + country)) {
                        airportDict.get(city + country).add(newAirport);
                    } else {
                        airportDict.put(city + country, new ArrayList<Airport>());
                        airportDict.get(city + country).add(newAirport);
                    }

                    // Updating the iataCode-Airport object map
                    if(newLineArray[4]!=null){
                        String newAita = newLineArray[4];
                        airportMap.put(newAita,newAirport);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** A class that reads the airline file and creates airline objects stored in a hashmap
     * @param airlineSource the source of the airline file
     */
    public static void readAirlines(String airlineSource){
        Airline newAirline = null;
        try {
            // Open the file
            FileReader reader = new FileReader(airlineSource);
            BufferedReader buffReader = new BufferedReader(reader);
            String newLine = "";
            String[] newLineArray;

            while((newLine = buffReader.readLine()) !=null) {
                // Read the new line
                newLineArray = newLine.split(",");

                // If that line has clean data, create an airline object
                if(newLineArray.length == 8) {
                    newAirline = new Airline(Integer.parseInt(newLineArray[0]),
                            newLineArray[1],
                            newLineArray[2],
                            newLineArray[3],
                            newLineArray[4],
                            newLineArray[5],
                            newLineArray[6],
                            newLineArray[7]);


                    // Putting the new airline object in the airline Hashmap
                    String country = newLineArray[6];

                    //Check if hashmap already has the object and act accordingly
                    if (airlineDict.containsKey(country)) {
                        airlineDict.get(country).add(newAirline);
                    } else {
                        airlineDict.put(country, new ArrayList<Airline>());
                        airlineDict.get(country).add(newAirline);
                    }
                }

            }
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }

    }


    /** A class that reads the routes file and creates route objects stored in a hashmapp
     * @param routeSource the location of the route file
     */
    public static void readRoutes(String routeSource) {

        try {
            // Open the file
            FileReader reader = new FileReader(routeSource);
            BufferedReader buffReader = new BufferedReader(reader);
            String newLine = "";
            String[] newLineArray;

            while ((newLine = buffReader.readLine()) != null) {
                // Read the new line
                newLineArray = newLine.split(",");


                // If that line has clean data, create an airline object
                if (newLineArray.length == 9) {
                    //System.out.println(Arrays.toString(newLineArray));
                    Route newRoute = new Route(newLineArray[0],
                            newLineArray[1],
                            newLineArray[2],
                            newLineArray[3],
                            newLineArray[4],
                            newLineArray[5],
                            newLineArray[6],
                            Integer.parseInt(newLineArray[7]),
                            newLineArray[8]);

                    // Putting new route object in the route Hashmap
                    String IATA = newLineArray[2];

                    //Check if hashmap already has the route object and act accordingly
                    if(routesDict.containsKey(IATA)){
                        ArrayList<Route> additinon = routesDict.get(IATA);
                        additinon.add(newRoute);
                        routesDict.put(IATA,additinon);
                    }else {
                            DataReader.routesDict.put(IATA, new ArrayList<Route>());
                        DataReader.routesDict.get(IATA).add(newRoute);
                        }
                }
                }
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {


    }
}
