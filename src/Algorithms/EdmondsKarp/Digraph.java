package EdmondsKarp;

// ─────────────────────────────────────────────
// Digraph.java  —  weighted directed graph
// Nodes are ints: 0 .. numNodes-1
// Stored as adjacency MATRIX for O(1) edge lookup
// (needed for flow[v][w] and capacity[v][w])
// ─────────────────────────────────────────────
import java.util.ArrayList;
import java.util.List;

public class Digraph {

    public static final int NO_EDGE = -1; // sentinel: no arc exists

    private final int     numNodes;
    private final int[][] capacity; // capacity[v][w] = capacity of arc v→w (or 0 if none)
    private final boolean[][] hasEdge; // hasEdge[v][w] = true if arc v→w is in original graph

    public Digraph(int numNodes) {
        this.numNodes = numNodes;
        this.capacity = new int[numNodes][numNodes];
        this.hasEdge  = new boolean[numNodes][numNodes];
    }

    /** Add a directed arc from → to with the given capacity */
    public void addEdge(int from, int to, int cap) {
        capacity[from][to] = cap;
        hasEdge[from][to]  = true;
    }

    public int     numNodes()               { return numNodes; }
    public int     capacity(int v, int w)   { return capacity[v][w]; }
    public boolean edgeExists(int v, int w) { return hasEdge[v][w]; }

    /** Returns all nodes w such that arc v→w exists (capacity > 0 or was added) */
    public List<Integer> outAdjacentNodes(int v) {
        List<Integer> adj = new ArrayList<>();
        for (int w = 0; w < numNodes; w++)
            if (capacity[v][w] > 0 || hasEdge[v][w])
                adj.add(w);
        return adj;
    }

    /** Returns a shallow copy of this graph (same capacity matrix) */
    public Digraph clone() {
        Digraph copy = new Digraph(numNodes);
        for (int v = 0; v < numNodes; v++)
            for (int w = 0; w < numNodes; w++) {
                copy.capacity[v][w] = this.capacity[v][w];
                copy.hasEdge[v][w]  = this.hasEdge[v][w];
            }
        return copy;
    }

    /** All arcs (v, w) where capacity[v][w] > 0 or arc was added with 0 */
    public List<int[]> edges() {
        List<int[]> list = new ArrayList<>();
        for (int v = 0; v < numNodes; v++)
            for (int w = 0; w < numNodes; w++)
                if (hasEdge[v][w])
                    list.add(new int[]{v, w, capacity[v][w]});
        return list;
    }
}