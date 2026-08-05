package FloydWarshall;

// ─────────────────────────────────────────────
// Digraph.java  —  weighted directed graph
// Nodes are ints: 0 .. numNodes-1
// Stored as adjacency MATRIX (required for Floyd-Warshall)
// weight[v][w] = weight of arc v→w, or INF if no arc
// ─────────────────────────────────────────────
public class Digraph {

    public static final int INF = Integer.MAX_VALUE / 2; // use /2 to avoid overflow in additions

    private final int   numNodes;
    private final int[][] weight; // weight[v][w] = weight of arc v→w

    public Digraph(int numNodes) {
        this.numNodes = numNodes;
        this.weight   = new int[numNodes][numNodes];

        // Initialize: no arc = INF, diagonal = 0
        for (int v = 0; v < numNodes; v++)
            for (int w = 0; w < numNodes; w++)
                weight[v][w] = (v == w) ? 0 : INF;
    }

    /** Add a directed arc from → to with the given weight */
    public void addEdge(int from, int to, int w) {
        weight[from][to] = w;
    }

    public int  numNodes()              { return numNodes; }
    public int  weight(int v, int w)    { return weight[v][w]; }

    /** Iterable over all (v, w) pairs where an arc exists (weight != INF) */
    public Iterable<int[]> edges() {
        java.util.List<int[]> list = new java.util.ArrayList<>();
        for (int v = 0; v < numNodes; v++)
            for (int w = 0; w < numNodes; w++)
                if (v != w && weight[v][w] != INF)
                    list.add(new int[]{v, w, weight[v][w]});
        return list;
    }
}