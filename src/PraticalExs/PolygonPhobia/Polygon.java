package PraticalExs.PolygonPhobia;

public class Polygon {

    private final int[] parent;
    private final int[] size;

    // Domain: coordinates 0..999, encode point (x,y) as y*1000+x
    // Max index = 999*1000 + 999 = 999999
    public Polygon() {
        int domain = 1_000_000;
        parent = new int[domain];
        size = new int[domain];
        for (int i = 0; i < domain; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    private int encodePoint(int x, int y) {
        return y * 1000 + x;
    }

    private int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]]; // path compression (halving)
            x = parent[x];
        }
        return x;
    }

    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (size[rootX] >= size[rootY]) {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        } else {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
        }
    }

    // Returns true if the segment can be painted (does not close a polygon)
    public boolean tryPaint(int x1, int y1, int x2, int y2) {
        int p1 = encodePoint(x1, y1);
        int p2 = encodePoint(x2, y2);
        if (find(p1) == find(p2)) {
            return false; // would close a chain
        }
        union(p1, p2);
        return true;
    }
}
