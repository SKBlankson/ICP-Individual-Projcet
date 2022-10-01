RouteFinder
=========

A java program that takes in a text file containing the Start and Destination and returns
a solution path in a file.<br/>
Since this uses a Breadth-First Search, it always finds the shortest route in terms of no of flights !


### How to use
The RouteFinder class is the main "body" of this program.
Four function calls are required to perform the search:

1. DataReader.readRoutes("routes.csv")
2. DataReader.readAirlines("airlines.csv");
3. DataReader.readAirports("airports.csv");

These first 3 function calls create the database/graph of information needed to perform the search.These first 3 function calls create the database/graph of information needed to perform the search.

They should be called in the main of the RouteFinder class. <br/>The arguments for each call should be the location of
their respective files

4.  RouteFinder.performSearch("testcase.txt");

This 4th function calls all the other necessary sub-methods to perform the route search. <br/> Ideally, this is the only call 
in main that you need to change. Simply call it and provide the location of the input file as the argument

### Sample Code to Perform the search
```
        DataReader.readRoutes("routes.csv");
        DataReader.readAirlines("airlines.csv");
        DataReader.readAirports("airports.csv");
        RouteFinder.performSearch("testcase.txt");
```                   
The first 3 lines have the locations of the the csv files as arguments <br/>
The final line performs the search and takes the location of the input file (source and destination) as the argument <br/>
You can also edit the "testcase.txt" file directly so that you do not have to make any changes to the function calls

### Notes
1. Always check the console after a search. It will tell you if a solution was found and any other relevant messages.
2. If the search takes more than a few seconds, there is probably no solution path. BFS always tries every possible path 
and thus might take long to run sometimes

