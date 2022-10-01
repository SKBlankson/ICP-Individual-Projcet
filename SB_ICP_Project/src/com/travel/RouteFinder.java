package com.travel;

import java.io.*;
import java.util.*;

/** A class that is used to read the start and destination and performs a Breadth-first search
 * to move from the start to destination */
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
            this.startCountry = startPoint[1].trim();

            // Reading the end point/destination
            newLine = buffReader.readLine();
            String[] endPoint = newLine.split(",");
            this.endCity = endPoint[0];
            this.endCountry = endPoint[1].trim();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(DataReader.airportDict.get(this.startCity+this.startCountry)==null ||
                DataReader.airportDict.get(this.endCity+this.endCountry)==null
        ){
            System.out.println("Start or End location is invalid! \nPlease try again " +
                    "with valid locations \nTerminating Search...");
            System.exit(0);
        }

    }


    /** A method that generates nodes for all the airports in the starting location. These nodes are used
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
            System.out.println("Performing search..");
        }
        return startingNodes;
    }

    /** test to see if the goal city and country has been reached
     * @param node the node which is to be evaluated
     * @return true if goal, false if not
     */
    public boolean goalTest(Node node) {
        if(node.state==null){
            return false;
        }
        if (node.state.city.equals(endCity) && node.state.country.equals(endCountry)) {
            return true;
        }
        return false;
    }

    /** A method that gives all the possible actions/routes from a given location
     *
     * @param iata the current airport state of the search tree
     * @return returns all the possibe successor routes
     */
    public ArrayList<Route> actions(String iata) {
//        String iata = state.getIataCode();
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

        // Generate starting nodes
        startingNodes = this.getStartFromCollection(airportDict);


        // put all the starting airports on the frontier
        for (Node startingNode : startingNodes) {
                if (this.goalTest(startingNode)) {
                    System.out.println("Found a solution!, You are already there");
                    return startingNode;
                }
                frontier.add(startingNode);
        }

                // bfs logic
                while (!frontier.isEmpty()) {
                    Node currNode = frontier.peek();
                    frontier.remove(currNode);
                    explored.add(currNode);

                    //If current node has complete data, expand it. Else, skip it
                    if (currNode.getState() != null) {
                        ArrayList<Route> actions = actions(currNode.getState().getIataCode());

                        // Convert each action to a node
                        for (Route action : actions) {
                            String destinationIata = action.getDestinationCode();
                            Airport destinationPort = DataReader.airportMap.get(destinationIata);
                            Node child = new Node(destinationPort, currNode, currNode.getPathCost() + 1, action);

                            // If child not explored or on frontier, add to frontier
                            if (!explored.contains(child) && !frontier.contains(child)) {
                                if (goalTest(child)) {
                                    System.out.println("Found a solution");
                                    sol_pathCost = child.getPathCost();
                                    return child; //sol path
                                }
                                frontier.add(child);
                            }
                        }
                }else {;}
                }
        System.out.println("No solution");
        return null;
    }

    /** A method that finds the solution path that was used to reach the destination
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

        Collections.reverse(previouRoutes);
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
        writer.write("Total additional stops: "+ Integer.parseInt(String.valueOf(totalStops))+"\n");
        writer.write("Optimality Criteria: No of Flights");
        writer.close();
    }

    /** a class thtat performs all the function calls necessary to perform the the search
     * @param sourceFile location of the input file
     * @throws FileNotFoundException
     */
    public static void performSearch(String sourceFile) throws FileNotFoundException {
        // instantiate the routefinder with the input file
        RouteFinder findRoute = new RouteFinder(sourceFile);

        // perform the bfs and return the solution
        Node solutionNode = findRoute.bfs(DataReader.airportDict);

        //print the solution node in terminal
        System.out.println(solutionNode);

        // get the solution path
        ArrayList<Route> solutionPath = findRoute.solutionPath(solutionNode);

        //Writing the solution path to a file
        findRoute.writeToFile(solutionPath);
    }




    public static void main (String[]args) throws FileNotFoundException {

        // Reading the files and creating the "Database" of airports, routes and airlines needed for bfs
        //set arguments to location of the respective files
        DataReader.readRoutes("routes.csv");
        DataReader.readAirlines("airlines.csv");
        DataReader.readAirports("airports.csv");

        /** Call this function to perform the search
         the argument is the location of the input file */
        RouteFinder.performSearch("testcase.txt");
    }
}



