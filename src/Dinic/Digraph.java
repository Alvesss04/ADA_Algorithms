package Dinic;

// ─────────────────────────────────────────────
// Digraph.java  —  weighted directed graph
// Nodes are ints: 0 .. numNodes-1
// Stored as adjacency MATRIX for O(1) lookups
// (capacity[v][w] and flow[v][w] accesses)
// ─────────────────────────────────────────────
import java.util.ArrayList;
import java.util.List;

public class Digraph {

    private final int       numNodes;
    private final int[][]   capacity;
    private final boolean[][] hasEdge;

    public Digraph(int numNodes) {
        this.numNodes = numNodes;
        this.capacity = new int[numNodes][numNodes];
        this.hasEdge  = new boolean[numNodes][numNodes];
    }

    public void addEdge(int from, int to, int cap) {
        capacity[from][to] = cap;
        hasEdge[from][to]  = true;
    }

    public int     numNodes()               { return numNodes; }
    public int     capacity(int v, int w)   { return capacity[v][w]; }
    public boolean edgeExists(int v, int w) { return hasEdge[v][w]; }

    public List<int[]> edges() {
        List<int[]> list = new ArrayList<>();
        for (int v = 0; v < numNodes; v++)
            for (int w = 0; w < numNodes; w++)
                if (hasEdge[v][w])
                    list.add(new int[]{v, w, capacity[v][w]});
        return list;
    }

    public Digraph clone() {
        Digraph copy = new Digraph(numNodes);
        for (int v = 0; v < numNodes; v++)
            for (int w = 0; w < numNodes; w++) {
                copy.capacity[v][w] = this.capacity[v][w];
                copy.hasEdge[v][w]  = this.hasEdge[v][w];
            }
        return copy;
    }
}