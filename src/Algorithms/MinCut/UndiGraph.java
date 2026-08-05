package MinCut;

// ─────────────────────────────────────────────
// UndiGraph.java  —  undirected weighted graph
// Nodes are ints: 0 .. numNodes-1
// Edges have non-negative integer weights.
// ─────────────────────────────────────────────
import java.util.ArrayList;
import java.util.List;

public class UndiGraph {

    private final int        numNodes;
    private final List<int[]> edges;   // each entry: {v, w, weight}

    public UndiGraph(int numNodes) {
        this.numNodes = numNodes;
        this.edges    = new ArrayList<>();
    }

    /** Add an undirected edge between v and w with the given weight */
    public void addEdge(int v, int w, int weight) {
        edges.add(new int[]{v, w, weight});
    }

    public int        numNodes() { return numNodes; }
    public List<int[]> edges()   { return edges; }
}