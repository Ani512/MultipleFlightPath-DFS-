import java.io.IOException;
import java.util.Stack;
import java.util.LinkedList;
import java.util.List;

class Connect {
    private final int vertices;

    // Tried to implement a LinkedList of a LinkedList for graph nodes
    private LinkedList<LinkedList<String>> graphNodes;
    private LinkedList<Integer>[] gNodes;

    // Constructor used to set the number of vertices of the Graph
    public Connect(int vertices) {
        this.vertices = vertices;
        adjLinkedList();
    }

    // Function that helps in adding source and destination nodes to the undirected graph
    public void addEdge(int src, int dest) {
        // For directed and Undirected graphs
        gNodes[src].add(dest);
        gNodes[dest].add(src);
    }

    // PathIterator function that iteratively checks repeatedly to find different paths
    private void pathIterator(Integer src, Integer dest, boolean[] flag, List<Integer> localPathList) throws IOException {
        if (src.equals(dest)) {
            // Calling the reverse hashing function to get city names from the Integers passed
            Flight.reverseHashing(localPathList);
            return;
        }
        // Marking Current Node if it has already been iterated through
        flag[src] = true;

        for (Integer i : gNodes[src]) {
            if (!flag[i]) {
                localPathList.add(i);
                pathIterator(i, dest, flag, localPathList);

                localPathList.remove(i);
            }
        }
        flag[src] = false;
    }

    // Function to send all the paths in the Stack to the Iterative function
    public void paths(int src, int dest) throws IOException {
        // Using a flag to check if the code is working iteratively
        boolean[] flag = new boolean[vertices];
        // Using a Stack to add elements to the LinkedList
        Stack<Integer> pathList = new Stack<>();

        pathList.add(src);  // Using Stack functionality to add elements to the LinkedList
        pathIterator(src, dest, flag, pathList);
    }

    private void adjLinkedList() {
        gNodes = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            gNodes[i] = new LinkedList<>();
        }
    }

}