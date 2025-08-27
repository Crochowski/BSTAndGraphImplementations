package assignment;

import java.util.Arrays;

public class GraphAdjMatrix extends AbstractGraph {
    private final double[][] adjMatrix;

    public GraphAdjMatrix(int noOfVertices, boolean directed) {
        super(noOfVertices, directed);
        // TODO: Task 1-A
        this.adjMatrix = new double[noOfVertices][noOfVertices];
        for (int i=0; i<noOfVertices; i++) {                      // Iterate over rows
            for (int j = 0; j < noOfVertices; j++) {              // Iterate over columns
                this.adjMatrix[i][j] = Double.NaN;                // No edges have yet been added
            }
        }
    }

    public void addEdge(int source, int destination, double weight) {
        // TODO: Task 1-B
        if (directed) {
            this.adjMatrix[source][destination] = weight;       // Store the weight at the correct location
        }

        else {                                                  // If the graph is undirected
            if (source == destination) {                        // If the source and destination are the same
                System.out.println("No self loops!");           // It is a self loop, which is not allowed
                return;
            }
            this.adjMatrix[source][destination] = weight;       // Add the edge
            this.adjMatrix[destination][source] = weight;       // Store the weight at the corresponding symmetric location
        }
    }

    public void removeEdge(int source, int destination) {
        // TODO: Task 1-C
        this.adjMatrix[source][destination] = Double.NaN;       // Remove the edge
        if (!directed) {                                        // If the graph is undirected
            this.adjMatrix[destination][source] = Double.NaN;   // Remove the edge at the corresponding symmetric location
        }
    }

    public double getWeight(int source, int destination) {
        // TODO: Task 1-D
        return this.adjMatrix[source][destination];                 // Return the weight at this location
    }

    public int[] getNeighbours(int vertex) {
        // TODO: Task 1-E
        int[] neighbours = new int[this.adjMatrix[vertex].length];  // To store the neighbours
        int j=0;                                                    // To track where neighbours index stops

        for (int i=0; i<this.adjMatrix[vertex].length; i++) {       // Traverse the row
            if (!Double.isNaN(this.adjMatrix[vertex][i])) {         // If column has weight
                neighbours[j] = i;                                  // Add that vertex number to neighbours
                j++;
            }
        }
        return Arrays.copyOf(neighbours, j);                        // Resize array to fit number of neighbours
    }

    public int getDegree(int vertex) {
        // TODO: Task 1-F
        int degree = 0;

        // Get the outdegree
        for (int i=0; i<this.adjMatrix[vertex].length; i++) {       // Traverse over vtx edges
            if (!Double.isNaN(this.adjMatrix[vertex][i])) {         // If there is an edge
                degree ++;                                          // Increment the degree
            }
        }

        if (directed) {                                             // If the graph is directed we also need the indegree
            for (int i=0; i<this.adjMatrix.length; i++) {           // Check each vtx for an edge back to the source vtx
                if (!Double.isNaN(this.adjMatrix[i][vertex])) {     // If an edge exists back to the source
                    degree ++;                                      // Increment the degree
                }
            }
            return degree;                                          // Return the total degree for directed graph
        }
        return degree;                                              // Or return total degree for undirected graph
}

    public boolean isPath(int[] nodes) {
        // TODO: Task 1-G
        for (int i=0; i<nodes.length-1; i++) {                          // Traverse over first n-1 items in nodes
            int currentNode = nodes[i];                                 // Store the current node
            int nextNode = nodes[i+1];                                  // store the next node
            if (Double.isNaN(this.adjMatrix[currentNode][nextNode])) {  // If at any point there is no link from current to next
                return false;                                           // There is no path
            }
        }
        return true;                                                    // If a link is always found, a path exists
    }

    public int getNoOfEdges() {
        // TODO: Task 1-H
        int numEdges = 0;
        for (int i=0; i<this.adjMatrix.length; i++) {                   // Traverse over each vertex
            for (int j=0; j<this.adjMatrix[i].length; j++) {            // Traverse each edge in vertex
                if (!Double.isNaN(this.adjMatrix[i][j])) {              // If an edge exists
                    numEdges++;                                         // Increment numEdges
                }
            }
        }

        if (directed) {                                                 // If the graph is directed
            return numEdges;                                            // Return the number of edges
        }

        return numEdges/2;                                              // If undirected, each edge was counted twice
    }
}
