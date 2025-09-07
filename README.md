# BST and Graph Implementations

A Java implementation of the following ADTs:

- Binary Search Tree (BST)
- Graph (Adjacency List and Adjacency Matrix representations)

## Project Structure

### Provided by University Lecturer
- `SearchTree.java` – Interface for a BST
- `BinarySearchTree.java` – Public facing subclass of BinarySearchTreeImpl.java, used to test the methods of the parent class.
- `Graph.java` – Graph interface
- `AbstractGraph.java` – Base class for graph implementations

### Implemented by myself
- `BinarySearchTreeImpl.java` – Implementation of the binary search tree
- `GraphAdjList.java` – Graph implementation using an adjacency list
- `GraphAdjMatrix.java` – Graph implementation using an adjacency matrix

## Binary Search Tree Features

- Insert nodes
- Search for values
- In-order traversal
- Find successor/predecessor of a node
- Find the depth of a BST
- Delete nodes (handles leaf, one child, or two children cases)

## Graph Features

- Add and remove edges
- Check adjacency
- Return weight of an edge
- Return the number of edges
- Find the degree of a vertex
- Check if a path exists between a list of provided edges
- Print structure (list or matrix form depending on implementation)

## How to run

### Prerequisites
- Java 17 or later
- An IDE such as IntelliJ IDEA or Eclipse

### Running
1. Clone the repository:
   ```bash
   git clone https://github.com/Crochowski/BSTAndGraphImplementations.git
   cd BSTAndGraphImplementations
2. Open the project in your IDE.
3. Navigate to AbstractGraph.java or BinarySearchTree.java and run.
