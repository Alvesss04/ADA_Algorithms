package PraticalExs.PolygonPhobia;

public class Polygon {

    private final int[] parent; // parent[i] = parent of node i in the DSU forest
    // if parent[i] == i, then i is a root (representative)
    private final int[] size;   // size[i] = number of nodes in the tree rooted at i
    // used to keep trees balanced (union by size)

    // Coordinates go from 0 to 999, so we encode each point (x,y) as a unique integer:
    // index = y * 1000 + x  →  max index = 999*1000 + 999 = 999_999
    // Domain size = 1_000_000 covers all possible points
    public Polygon() {
        int domain = 1_000_000;
        parent = new int[domain];
        size = new int[domain];
        for (int i = 0; i < domain; i++) {
            parent[i] = i; // each point starts as its own root (singleton component)
            size[i] = 1;   // each component starts with size 1
        }
    }

    // Converts a 2D point (x, y) into a unique integer index
    private int encodePoint(int x, int y) {
        return y * 1000 + x;
    }

    // Finds the root (representative) of the component containing x
    // Uses path-halving compression: every other node on the path points to its grandparent
    // This flattens the tree over time, making future finds faster
    private int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]]; // path compression: skip one level up
            x = parent[x];
        }
        return x; // x is the root of its component
    }

    // Merges the components of x and y into one
    // Uses union by size: attach the smaller tree under the larger one
    // This keeps the trees shallow and find() efficient
    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (size[rootX] >= size[rootY]) {
            parent[rootY] = rootX;      // rootY becomes a child of rootX
            size[rootX] += size[rootY]; // update size of the merged component
        } else {
            parent[rootX] = rootY;      // rootX becomes a child of rootY
            size[rootY] += size[rootX];
        }
    }

    // Tries to paint a segment from (x1,y1) to (x2,y2)
    // A segment closes a polygon if BOTH endpoints are already in the same connected component
    // (meaning there's already a chain connecting them — adding this segment would close it)
    // Returns true and paints it if safe, false if it would close a polygon
    public boolean tryPaint(int x1, int y1, int x2, int y2) {
        int p1 = encodePoint(x1, y1);
        int p2 = encodePoint(x2, y2);
        if (find(p1) == find(p2)) {
            return false; // both endpoints already connected → would form a closed chain
        }
        union(p1, p2); // safe to paint: merge the two components
        return true;
    }
}