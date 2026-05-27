package MaxMatch;

// ─────────────────────────────────────────────
// BipartiteGraph.java  —  undirected bipartite graph
// Partition: X = {0, 1, ..., sizeX-1}
//            Y = {sizeX, sizeX+1, ..., sizeX+sizeY-1}
//
// Nodes are numbered 0 .. sizeX+sizeY-1 following
// the slides' convention: X = {0..n-1}, Y = {n...|V|-1}
//
// Edges are undirected between X and Y only.
// ─────────────────────────────────────────────
import java.util.ArrayList;
import java.util.List;

public class BipartiteGraph {

    private final int sizeX;
    private final int sizeY;
    private final List<int[]> edges; // each entry: {x, y} with x in X, y in Y

    public BipartiteGraph(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.edges = new ArrayList<>();
    }

    /**
     * Add an undirected edge between node x (in X) and node y (in Y).
     * x is a local index 0..sizeX-1
     * y is a local index 0..sizeY-1  (stored internally as sizeX+y)
     */
    public void addEdge(int x, int y) {
        if (x < 0 || x >= sizeX) throw new IllegalArgumentException("x out of range: " + x);
        if (y < 0 || y >= sizeY) throw new IllegalArgumentException("y out of range: " + y);
        edges.add(new int[]{x, sizeX + y}); // store as global node ids
    }

    public int sizeX()           { return sizeX; }
    public int sizeY()           { return sizeY; }
    public int numNodes()        { return sizeX + sizeY; }
    public List<int[]> edges()   { return edges; }

    /** True if node v belongs to partition X */
    public boolean inX(int v)    { return v < sizeX; }
    /** True if node v belongs to partition Y */
    public boolean inY(int v)    { return v >= sizeX; }
}