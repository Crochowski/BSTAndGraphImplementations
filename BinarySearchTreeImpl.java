package assignment;

import java.util.LinkedList;

public class BinarySearchTreeImpl<T> {
    protected static class Node<T> {
        public Node(int key, T value)
        {
            this.key = key;
            this.value = value;
        }
        public int key;
        public T value;
        public Node<T> parent = null;
        public Node<T> left = null;
        public Node<T> right = null;
    }

    protected Node<T> root = null;

    protected void insert(Node<T> x, int key, T value) {
        // TODO: Task 3-A
        if (this.root == null) {                    // If the tree is empty
            this.root = new Node<>(key, value);     // Create new node and make it the root
        }

        else if (key == x.key) {                    // If the key is the same as the current node's key
            x.value = value;                        // Replace the value at the current node
        }

        else if (key < x.key) {                     // If the key is less than the current node's key
            if (x.left == null) {                   // If there is no left child at the current node
                x.left = new Node<>(key, value);    // Create a new node and place it as the left child of the current node
                x.left.parent = x;                  // Point the new node back to its parent
            }

            else {                                  // If there is a left child at the current node
                insert(x.left, key, value);         // Start searching again from the left child
            }
        }

        else {                                      // If the key is greater than the current node's key
            if (x.right == null) {                  // If there is no right child at the current node
                x.right = new Node<>(key, value);   // Create a new node and place it as the right child of the current node
                x.right.parent = x;                 // Point the new node back to its parent
            }

            else {                                  // If there is a right child at the current node
                insert(x.right, key, value);        // Start searching again from the right child
            }
        }

    }

    protected LinkedList<T> inorderTreeWalk(Node<T> x)
    {
        // TODO: Task 3-B
        if (x != null) {                                // If there is a node at x
            LinkedList<T> l = inorderTreeWalk(x.left);  // Get a list of nodes on the left subtree
            LinkedList<T> r = inorderTreeWalk(x.right); // Get a list of nodes on the right subtree
            l.add(x.value);                             // Add value of x at end of l list
            l.addAll(r);                                // Add all values in r to end of l list
            return l;                                   // Return complete list

            }

        else {
            return new LinkedList<T>();                 // If there is no node at x, return empty list
        }
    }

    protected Node<T> search(Node<T> x, int key) {
        // TODO: Task 3-C
        if (x == null || key == x.key) {               // If the current node is null or the node's key = passed in key
            return x;                                  // Return the node or null
        }
        if (key < x.key) {                             // If the key < the key of the current node
            return search(x.left, key);                // Search the left subtree
        }
                                                       // If the key > the key of the current node
        return search(x.right, key);                   // Search the right subtree
    }

    protected int depth(Node<T> x) {
        // TODO: Task 3-D
        int left, right;                // Counters to store depths
        if (x == null) {
            return -1;
        }

        left = depth(x.left);           // Search the left and right subtrees until null is passed in
        right = depth(x.right);

        return Math.max(left, right) + 1;   // return the larger subtree count, plus 1 to account for level of depth
    }

    protected Node<T> minimum(Node<T> x) {
        // TODO: Task 3-E
        while (x.left != null) {            // Until we reach the leftmost node
            x = x.left;                     // Move to the next node left
        }
        return x;                           // Return the leftmost node
    }

    protected Node<T> maximum(Node<T> x) {
        // TODO: Task 3-F
        while (x.right != null) {           // Until we reach the rightmost node
            x = x.right;                    // Move to the next node right
        }
        return x;                           // Return the rightmost node
    }

    protected Node<T> successor(Node<T> x)
    {
        // TODO: Task 3-G
        if (x.right != null) {              // If a right child exists
            return minimum(x.right);        // The successor is the minimum of the subtree rooted at the right child
        }

        Node<T> y = x.parent;               // Otherwise, Get the parent of x
        while (y != null && x == y.right) { // While a parent exists and x is the right child of y
            x = y;                          // Move x up to y's position
            y = y.parent;                   // Move y up to its parent
        }
        return y;                           // The successor is found the first time y is a left child
    }

    protected Node<T> predecessor(Node<T> x)
    {
        // TODO: Task 3-H
        if (x.left != null) {               // If a left child exists
            return maximum(x.left);         // The predecessor is the maximum of the subtree rooted at the left child
        }

        Node<T> y = x.parent;               // Otherwise, get the parent of x
        while (y!= null && x == y.left) {   // While a parent exists and x is the left child of y
            x = y;                          // Move x up to y's position
            y = y.parent;                   // Move y up to its parent
        }
        return y;                           // The predecessor is found the first time y is a right child
    }

    protected void delete(Node<T> z) {
        // TODO: Task 3-I
        if (z.left == null) {                   // If z has no left child
            transplant(z, z.right);             // Replace z with its right child if it exists, or null otherwise
        }
        else if (z.right == null) {             // Otherwise, if z has no right child
            transplant(z, z.left);              // Replace z with its left child if it exists, or null otherwise
        }
        else {                                   // Otherwise, if z has both children
            Node<T> y = minimum(z.right);        // Get z's successor
            if (y != z.right) {                  // If y is not z's right child
                transplant(y, y.right);          // Remove y and replace it with its child
                y.right = z.right;               // Make y's new right child that of z's
                y.right.parent = y;              // Point y's new right child back up at its new parent y (Insert y between z and its child)
            }
            transplant(z, y);                    // Replace z with y
            y.left = z.left;                     // z's old left child is now y's left child
            y.left.parent = y;                   // Point the left child back up to its new parent y
        }
    }

    private void transplant(Node<T> u, Node<T> v) {
        if (u.parent == null) {             // If u is the root
            root = v;                       // v is now the root
        }
        else {
            if (u == u.parent.left) {       // If u is the left child of its parent
                u.parent.left = v;          // v is now the left child of that parent
            }
            else {                          // If u is the right child of its parent
                u.parent.right = v;         // v is now the right child of that parent
            }
        }
        if (v != null) {                    // If v exists
            v.parent = u.parent;            // Set parent of v to that of u
        }
    }
}
