package assignment;

import java.util.Iterator;
import java.util.LinkedList;

public class GraphAdjList extends AbstractGraph {
    private record Edge(int destination, double weight) {
    }

    private final LinkedList<Edge>[] neighbours;

    public GraphAdjList(int noOfVertices, boolean directed) {
        super(noOfVertices, directed);
        // TODO: Task 2-A
        this.neighbours = new LinkedList[noOfVertices];     // Initialise neighbours array to hold a linked list for each vertex
        for (int i = 0; i < noOfVertices; i++) {
            neighbours[i] = new LinkedList<Edge>();         // Create a linked list for each vertex to store edges
        }
    }

    public void addEdge(int source, int destination, double weight) {
        if (!directed && (source == destination)) {          // If the graph is undirected, do not allow self loops
            System.out.println("No self loops!");
            return;
        }

        Edge edge = new Edge(destination, weight);           // Create the edge

        for (Edge e: neighbours[source]) {                   // Iterate over each edge in the source vtx list of edges
            if (e.destination == edge.destination) {         // If this edge already exists, do not add it again
                System.out.println("Edge already exists!");
                return;
            }
        }

        this.neighbours[source].add(edge);                  // Add the edge
        if (!directed) {                                    // If the graph is undirected
            edge = new Edge(source, weight);                // Create an edge from destination to source
            this.neighbours[destination].add(edge);         // Add this edge to the destination edge list
        }
    }

    public void removeEdge(int source, int destination) {
        // TODO: Task 2-C
        LinkedList<Edge> srcVertex = neighbours[source];             // Store the source edge list

        Iterator<Edge> iterator = srcVertex.iterator();              // Create iterator for srcVertex
        while (iterator.hasNext()) {                                 // Iterate over the edges in srcVertex
            if (iterator.next().destination == destination) {        // When the edge with matching destination is found
                iterator.remove();                                   // Remove that edge
                break;                                               // Stop searching once edge has been removed
            }
        }

        if (!directed) {                                             // If the graph is undirected, we must remove the symmetric edge
            srcVertex = neighbours[destination];                     // Repeat same process as above for this edge
            iterator = srcVertex.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().destination == source) {
                    iterator.remove();
                    break;
                }
            }
        }

    }

    public double getWeight(int source, int destination) {
        // TODO: Task 2-D
        LinkedList<Edge> srcVertex = neighbours[source];               // Store the list of edges for the source vertex
        for (Edge edge : srcVertex) {                                  // Search through the list of edges
            if (edge.destination == destination) {                     // If the edge is found
                return edge.weight;                                    // Return the weight
            }
        }
        return Double.NaN;                                             // If the edge isn't found, return NaN
    }


    public int[] getNeighbours(int vertex) {
        // TODO: Task 2-E
        LinkedList<Edge> edges = neighbours[vertex];              // Store the list of edges for the provided vertex
        int[] arrayOfEdges = new int[edges.size()];               // Create an array to store the destination vertices
        int i=0;                                                  // Index counter for arrayOfEdges

        for (Edge edge: edges) {                                  // Iterate through the list of edges
            arrayOfEdges[i] = edge.destination;                   // Store the neighbour in arrayOfEdges
            i++;
        }

        return arrayOfEdges;                                      // Return array of edges
    }

    public int getDegree(int vertex) {
        // TODO: Task 2-F
        LinkedList<Edge> edges = neighbours[vertex];              // Store list of edges for this vertex
        int outDegree = edges.size();                             // The outdegree is the total num of edges in this list
        int inDegree = 0;                                         // To store indegree for directed graphs

        if (!this.directed) {                                     // If graph is undirected
            return outDegree;                                     // The degree is the number of edges
        }
                                                                  // If directed, we need indegree as well
        for (LinkedList<Edge> currentVertex : neighbours) {       // Traverse neighbours
            if (currentVertex == edges) {                         // Skip the list for the provided vtx
                continue;
            }
            for (Edge edge : currentVertex) {                     // Traverse list of edges for each vtx
                if (edge.destination == vertex) {                 // If current destination vtx matches provided vtx
                    inDegree++;                                   // Increment the in degree
                }
            }
        }

        return outDegree + inDegree;                              // Return total degree
    }

    public boolean isPath(int[] nodes) {
        // TODO: Task 2-G
        LinkedList<Edge>[] nodesInPath = new LinkedList[nodes.length];       // Create array to store lists stored @ node nums
        for (int i = 0; i < nodes.length; i++) {                             // Traverse nodes array
            nodesInPath[i] = neighbours[nodes[i]];                           // Add each Vertex list of edges to nodesInPath
        }

        for (int j = 0; j < nodesInPath.length - 1; j++) {                  // Traverse nodesInPath from start to second last
            LinkedList<Edge> currentVertex = nodesInPath[j];                // Store current list of edges
            Iterator<Edge> iterator = currentVertex.iterator();             // Iterator for currentVertex
            int next_node = nodes[j + 1];                                   // Store next node in path

            while (iterator.hasNext()) {                                    // Traverse edges in currentVertex
                if (iterator.next().destination == next_node) {             // If next node is found
                    break;                                                  // Move on to next node in path
                }
                if (!iterator.hasNext()) {                                  // If we reach the end of an edge list
                    return false;                                           // There is no path
                }
            }
        }
        return true;                                                        // If a connection is always found, a path exists
    }


    public int getNoOfEdges() {
        // TODO: Task 2-H
        int numEdges = 0;

        for (LinkedList<Edge> neighbour : neighbours) { // For every vertex in neighbours
            numEdges += neighbour.size();               // Count the edges
        }
        if (directed) {                                 // If the graph is directed
            return numEdges;                            // return the number of edges
        } else {                                        // If the graph is undirected
            return numEdges / 2;                        // Each edge was counted twice, so return numEdges/2
        }
    }
}

