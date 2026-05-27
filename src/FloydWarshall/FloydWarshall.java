package FloydWarshall;

import java.util.ArrayList;
import java.util.List;

// ─────────────────────────────────────────────
// FloydWarshall.java  —  All-Pairs Shortest Paths
// Uses DYNAMIC PROGRAMMING with 3 nested loops.
//
// KEY IDEA:
//   L(v, w, k) = shortest path from v to w using
//   only {0, 1, ..., k} as intermediate nodes.
//
//   Recurrence:
//   L(v, w, -1) = weight[v][w]  (direct arc or INF)
//   L(v, w,  k) = min( L(v,w,k-1),  L(v,k,k-1) + L(k,w,k-1) )
//
// Two matrices are built:
//   length[v][w] = shortest path distance v → w
//   via[v][w]    = last intermediate node on that path
//                  (-1 = direct arc, no intermediate)
//
// Complexity:
//   Time:  Θ(|V|³)
//   Space: Θ(|V|²)
// ─────────────────────────────────────────────
public class FloydWarshall {

    // ── Inner class to return both result matrices ──────────────────────────
    public static class Result {
        public final int[][]  length; // length[v][w] = shortest dist v→w
        public final int[][]  via;    // via[v][w]    = intermediate node (-1 if direct)

        Result(int[][] length, int[][] via) {
            this.length = length;
            this.via    = via;
        }
    }

    // ── Exception for negative-weight cycles ────────────────────────────────
    public static class NegativeWeightCycleException extends RuntimeException {
        public NegativeWeightCycleException() {
            super("Graph contains a negative-weight cycle.");
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // floydWarshall
    //
    // Computes all-pairs shortest paths.
    // Throws NegativeWeightCycleException if a negative cycle is detected.
    // ────────────────────────────────────────────────────────────────────────
    public static Result floydWarshall(Digraph graph)
            throws NegativeWeightCycleException {

        int n = graph.numNodes();

        // Step 1: Create and initialize the length and via matrices
        // ──────────────────────────────────────────────────────────
        int[][] length = new int[n][n];
        int[][] via    = new int[n][n];

        // Initialize length to INF, via to -1
        for (int v = 0; v < n; v++)
            for (int w = 0; w < n; w++) {
                length[v][w] = Digraph.INF;
                via[v][w]    = -1;
            }

        // Diagonal: distance from a node to itself is 0
        for (int v = 0; v < n; v++)
            length[v][v] = 0;

        // Direct arcs: L(v, w, -1)
        for (int v = 0; v < n; v++)
            for (int w = 0; w < n; w++)
                if (v != w && graph.weight(v, w) != Digraph.INF)
                    length[v][w] = graph.weight(v, w);
        // via[v][w] stays -1  →  direct arc, no intermediate node

        // Step 2: Main triple loop — try each node k as intermediate
        // ──────────────────────────────────────────────────────────
        // For each intermediate node k (in order 0 .. n-1):
        //   For each source v and destination w:
        //     If going through k is shorter, update length and via.
        //
        //   L(v, w, k) = min( L(v,w,k-1), L(v,k,k-1) + L(k,w,k-1) )
        for (int k = 0; k < n; k++) {
            for (int v = 0; v < n; v++) {
                for (int w = 0; w < n; w++) {
                    // Check for potential overflow before summing
                    if (length[v][k] != Digraph.INF && length[k][w] != Digraph.INF) {
                        int through_k = length[v][k] + length[k][w];
                        if (through_k < length[v][w]) {
                            length[v][w] = through_k;
                            via[v][w]    = k;  // k is the "last" intermediate on this path
                        }
                    }
                }
            }
        }

        // Step 3: Detect negative-weight cycles
        // ──────────────────────────────────────
        // A negative cycle exists if any node v has length[v][v] < 0
        // (the shortest "path" from v to itself became negative)
        for (int v = 0; v < n; v++)
            if (length[v][v] < 0)
                throw new NegativeWeightCycleException();

        return new Result(length, via);
    }

    // ────────────────────────────────────────────────────────────────────────
    // getPath
    //
    // Reconstructs the shortest path from origin to destination.
    // Assumes floydWarshall has already been called and length[origin][dest] < INF.
    // ────────────────────────────────────────────────────────────────────────
    public static List<Integer> getPath(int[][] via, int origin, int destination) {
        List<Integer> path;

        if (origin == destination) {
            path = new ArrayList<>();
        } else {
            path = pathWithoutLast(via, origin, destination);
        }

        path.add(destination);
        return path;
    }

    // ── Helper: builds the path from orig to dest, EXCLUDING dest ──────────
    // via[orig][dest] == -1 → direct arc, just add orig
    // via[orig][dest] == k  → path goes orig→...→k→...→dest;
    //                         recurse on both halves, then concatenate
    private static List<Integer> pathWithoutLast(int[][] via, int orig, int dest) {
        List<Integer> path;
        int interm = via[orig][dest];

        if (interm == -1) {
            // Direct arc: path is just [orig]
            path = new ArrayList<>();
            path.add(orig);
        } else {
            // Go from orig to interm, then from interm to dest
            path = pathWithoutLast(via, orig, interm);
            path.addAll(pathWithoutLast(via, interm, dest));
        }

        return path;
    }
}