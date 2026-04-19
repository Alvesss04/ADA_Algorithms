package PraticalExs.SusBackpack;

import java.util.*;

public class Backpack {

    private final int S;              // Number of suspects
    private final int numNodes;       // Total nodes in the DAG: 2 per suspect (arrival + departure)
    private final List<Integer>[] adj;// Adjacency list of the DAG
    private final int[] inDegree;     // inDegree[i] = number of incoming edges to node i
    // Used by Kahn's algorithm to detect cycles

    // Each suspect i has two nodes:
    //   Arrival node  = i        (suspect i arrives)
    //   Departure node = i + S   (suspect i departs)
    // A physical constraint is that arrival always happens before departure
    @SuppressWarnings("unchecked")
    public Backpack(int S) {
        this.S = S;
        this.numNodes = 2 * S;
        this.adj = new ArrayList[numNodes];
        this.inDegree = new int[numNodes];

        for (int i = 0; i < numNodes; i++) {
            adj[i] = new ArrayList<>();
        }

        // Physical rule: for every suspect i, arrival (i) must happen before departure (i + S)
        // This is always true regardless of witness statements
        for (int i = 0; i < S; i++) {
            addEdge(i, i + S);
        }
    }

    // Helper: adds a directed edge from → to in the DAG and increments inDegree of 'to'
    private void addEdge(int from, int to) {
        adj[from].add(to);
        inDegree[to]++;
    }

    // "Preceding" conjecture: suspect x left BEFORE suspect y arrived
    // This means: departure of x must come before arrival of y
    // Edge: (departure of x) → (arrival of y)
    public void addPreceding(int x, int y) {
        int partidaX  = x + S; // departure node of x
        int chegadaY  = y;     // arrival node of y
        addEdge(partidaX, chegadaY);
    }

    // "Concurrent" conjecture: suspect x and y were present at the same time
    // This means their stays overlap, which requires:
    //   arrival of x before departure of y  →  edge: arrivalX → departureY
    //   arrival of y before departure of x  →  edge: arrivalY → departureX
    public void addConcurrent(int x, int y) {
        int chegadaX  = x;     // arrival node of x
        int partidaX  = x + S; // departure node of x
        int chegadaY  = y;     // arrival node of y
        int partidaY  = y + S; // departure node of y

        addEdge(chegadaX, partidaY); // x arrived before y left
        addEdge(chegadaY, partidaX); // y arrived before x left
    }

    // Kahn's Algorithm: topological sort / cycle detection
    // If we can process all nodes in topological order → no cycle → statements are consistent
    // If a cycle exists → some ordering constraint is contradictory → inconsistent
    public boolean isConsistent() {
        // Copy inDegree so we don't modify the original (allows reuse if needed)
        int[] tempInDegree = Arrays.copyOf(inDegree, numNodes);

        Queue<Integer> ready = new ArrayDeque<>();

        // Enqueue all nodes with no incoming edges (no prerequisites)
        for (int i = 0; i < numNodes; i++) {
            if (tempInDegree[i] == 0) {
                ready.add(i);
            }
        }

        int numProcNodes = 0; // counts how many nodes we successfully process

        while (!ready.isEmpty()) {
            int node = ready.poll();
            numProcNodes++;

            // For each neighbor, remove the edge and check if it's now ready
            for (int neighbor : adj[node]) {
                tempInDegree[neighbor]--;
                if (tempInDegree[neighbor] == 0) {
                    ready.add(neighbor);
                }
            }
        }

        // If all nodes were processed → no cycle → consistent witness statements
        // If some nodes remain (stuck in a cycle) → inconsistent
        return numProcNodes == numNodes;
    }
}