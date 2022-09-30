package com.travel;

import java.io.*;
import java.util.*;

/** A class that is used to read the start and destination and performs a Breadth-first search (BFS) */
public class RouteFinder {
    public String startCity;
    public String startCountry;
    public String endCity;
    public String endCountry;
    public static HashMap<String, ArrayList<Route>> staticWorldMap;
    public int sol_pathCost = 0;
    public Airport destinationAirport;
    ArrayList<Node> startingNodes;

/** A constructor for the RouteFinder that takes the input file with the start and destination */
    public RouteFinder(String sourceFile) {
        try {
            this.readInput(sourceFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**A method that reads the input file and sets the start and end variables for the BFS
     *
     * @param sourceFile the location of the file with the start and destination
     * @throws FileNotFoundException
     */
    public void readInput(String sourceFile) throws FileNotFoundException {
        try {
            // Open the file
            FileReader reader = new FileReader(sourceFile);
            BufferedReader buffReader = new BufferedReader(reader);

            // Reading the start point
            String newLine = buffReader.readLine();
            String[] startPoint = newLine.split(",");
            this.startCity = startPoint[0];
            this.startCountry = startPoint[1];

            // Reading the end point/destination
            newLine = buffReader.readLine();
            String[] endPoint = newLine.split(",");
            this.endCity = endPoint[0];
            this.endCountry = endPoint[1];

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** A method that generates nodes of all the airports in the starting location. These are used
     * to initialize the bfs frontier
     * @param airportDict a dictionary/hashmap containing all the airport objects
     * @return an array list of starting airport nodes
     */
    public ArrayList<Node> getStartFromCollection(HashMap<String, ArrayList<Airport>> airportDict) {
        ArrayList<Airport> startingAirports = airportDict.get(this.startCity + this.startCountry);
        ArrayList<Node> startingNodes = new ArrayList<>();
        for (Airport airport : startingAirports) {
            Node newNode = new Node(airport, null, 0, null);
            startingNodes.add(newNode);
        }
        return startingNodes;
    }

    /** the goal test to see if the goal city and country has been reached
     *
     * @param node the node which is to be evalulated
     * @return true if goal, false if not
     */
    public boolean goalTest(Node node) {
        if (node.state.city.equals(endCity) && node.state.country.equals(endCountry)) {
            return true;
        }
        return false;
    }

    /** A method that gives all the possible actions/routes from a given location
     *
     * @param state the current airport state of the search tree
     * @return returns all the possibe successor routes
     */
    public ArrayList<Route> actions(Airport state) {
        String iata = state.getIataCode();
        ArrayList<Route> succRoutes = new ArrayList<>();
        if (DataReader.routesDict.containsKey(iata)) {
            succRoutes = DataReader.routesDict.get(iata);
        }
        return succRoutes;
    }


    /** a class that performs a bfs to find the shortest path to the destination */
    public Node bfs(HashMap<String, ArrayList<Airport>> airportDict) {
        // The frontier and explored set for the bfs
        Queue<Node> frontier = new LinkedList<>();
        HashSet<Node> explored = new HashSet<>();


            // validating airport data
            try {
                startingNodes = this.getStartFromCollection(airportDict);
                if (startingNodes != null) {
                    ;
                }
            } catch (NullPointerException npe) {
                System.out.println("Please check your input! The start or destination does not exist");
                return null;
            }

        try {
            // put all the airports in the start country on the frontier
            for (Node startingNode : startingNodes) {
                if (this.goalTest(startingNode)) {
                    System.out.println("Found a solution!, You are already there");
                    return startingNode;
                }
                frontier.add(startingNode);
            }

            while (frontier.size() > 0) {
                Node currNode = frontier.poll();
                explored.add(currNode);

                //Generate successor actions
                ArrayList<Route> actions = actions(currNode.getState());

                // Convert each action to a node
                for (Route action : actions) {

                    String destinationIata = action.getDestinationCode();
                    Airport destinationPort = DataReader.airportMap.get(destinationIata);
                    Node child = new Node(destinationPort, currNode, currNode.getPathCost() + 1, action);

                    // Check if child is already explored, on the frontier or the goal
                    if (!explored.contains(child) && !frontier.contains(child)) {
                        if (goalTest(child)) {
                            System.out.println("Found a solution");
                            sol_pathCost = child.getPathCost();
                            return child; //sol path
                        }
                        frontier.add(child);
                    }
                }
            }

            return null;
        }catch (Exception e){
            System.out.println("There is no solution! i cant get there");
        }
        return null;
    }

    /** A method that finds the solution path that was used to reach the destination
     *
     * @param solution the Node that was returned at the end of the bfs
     * @return a list of all the routes taken to reach destination
     */
    public ArrayList<Route> solutionPath(Node solution){
        ArrayList<Route> previouRoutes = new ArrayList<>();
        previouRoutes.add(solution.action);

        Node baseRoute = solution.getParent();
        while (baseRoute.getParent()!=null){
            previouRoutes.add(baseRoute.getAction());
            baseRoute = baseRoute.getParent();
        }

        previouRoutes.add(solution.action);
        Collections.reverse(previouRoutes);
        previouRoutes.remove(0);
        return previouRoutes;
    }

    /** A method that writes the solution path to the output file */
    public void writeToFile(ArrayList<Route> solPath) throws FileNotFoundException {
        int counter = 0;
        int totalStops=0;
        PrintWriter writer = null;
        String fileName = startCity+"-"+endCity+"_output"+".txt";
        writer = new PrintWriter(new File(fileName));

        for(Route action:solPath){
            writer.write(
                    Integer.toString(counter+=1)+"."+" "+action.getAirlineCode()+" from "+ action.sourceAirportCode
                           + " to " + action.getDestinationCode() +" "+ action.getStops() + " stops" +"\n"
            );
            totalStops+=action.getStops();
        }
        writer.write("Total Flights: "+ Integer.parseInt(String.valueOf(sol_pathCost))+"\n");
        writer.write("Total additional stops: "+ Integer.parseInt(String.valueOf(totalStops)));
        writer.close();


    }




    public static void main (String[]args) throws FileNotFoundException {
        // Reading the file and creating the "Database" of airports, routes and airlines needed for bfs
        //set arguments to location of the respective files
        DataReader.readRoutes("routes.csv");
        DataReader.readAirlines("airlines.csv");
        DataReader.readAirports("airports.csv");

        // instantiate the routefinder with the input file
        RouteFinder roundFind1 = new RouteFinder("testcase.txt");

        // perform the bfs and return the solution
        Node solutionNode = roundFind1.bfs(DataReader.airportDict);
        //print the solution node in terminal
        System.out.println(solutionNode);

        // The solution path
        ArrayList<Route> solutionPath = roundFind1.solutionPath(solutionNode);

        //Writing the solution path to a file
        roundFind1.writeToFile(solutionPath);
    }
}



