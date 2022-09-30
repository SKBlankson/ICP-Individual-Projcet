RouteFinder
=========

A java program that takes in a text file containing the start and destiantion and returns
a solution path in the file
```java
public static double distance(double startLat, double startLong,
                              double endLat, double endLong)
```

### How to use
Use the RouteFinder class to run the program:
The code below is used to read all the csv files, so provide their locations as the argument
```java
DataReader.readRoutes("routes.csv");
DataReader.readAirlines("airlines.csv");
DataReader.readAirports("airports.csv");


```                   
The code below is used to instantiate the route finder. provide the location of the input text file as the argument 
The bfss method call performs the bfs.

The sout simply prints the solution node itself
```java
 RouteFinder roundFind1 = new RouteFinder("testcase.txt");

        // perform the bfs and return the solution
        Node solutionNode = roundFind1.bfs(DataReader.airportDict);
        
        //print the solution node in terminal
        System.out.println(solutionNode);

```
The code below returns the solution path, so provide the solution node as the argument for solutionpath()
and provide solution path as the argument for writetofile
```java
// The solution path
        ArrayList<Route> solutionPath = roundFind1.solutionPath(solutionNode);

        //Writing the solution path to a file
        roundFind1.writeToFile(solutionPath);
```