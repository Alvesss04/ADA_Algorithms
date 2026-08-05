package MaxMatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// ─────────────────────────────────────────────
// MaxMatch.java  —  Maximum Matching in a Bipartite Graph
// Slides Cap. XV — Margarida Mamede, ADA 2025/26
//
// APPROACH: Reduction to Max Flow (Transform & Conquer)
//
// Given an undirected bipartite graph G = (V, A)
// with partition {X, Y}, build a directed flow network G':
//
//   V' = V ∪ {f, d}      (add virtual source f and sink d)
//   A' = { (f, x) | x ∈ X }          capacity 1
//      ∪ { (x, y) | (x,y) ∈ A }      capacity 1
//      ∪ { (y, d) | y ∈ Y }          capacity 1
//
// ALL capacities are 1 — each node can be matched at most once.
//
// PROPOSITION (slides p.7):
//   There is a bijection between:
//   - Maximum matchings E in G
//   - Maximum flows φ from f to d in G'
//   Such that:
//     |E| = value of φ
//     (x, y) ∈ E  ⟺  φ(x, y) = 1
//
// The max flow is found with Edmonds-Karp (BFS augmenting paths).
// After the flow, we extract matching edges: arcs (x,y) with flow = 1.
//
// Complexity: O(|V| × (|V| + |A|))
//   (at most |V|/2 iterations, each BFS in O(|V|+|A|))
// ─────────────────────────────────────────────
public class MaxMatch {

    public static final int INF = Integer.MAX_VALUE / 2;

    // ── Result ─────────────────────────────────────────────────────────────
    public static class Result {
        public final int        matchingSize;  // number of matched pairs
        public final List<int[]> matching;     // list of {x, y} pairs (global node ids)

        Result(int matchingSize, List<int[]> matching) {
            this.matchingSize = matchingSize;
            this.matching     = matching;
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // maxMatch — main method
    // ────────────────────────────────────────────────────────────────────────
    public static Result maxMatch(BipartiteGraph graph) {
        int sizeX   = graph.sizeX();
        int sizeY   = graph.sizeY();
        int n       = graph.numNodes(); // sizeX + sizeY

        // Node layout in the flow network:
        //   0 .. sizeX-1         → X nodes  (same ids as graph)
        //   sizeX .. n-1         → Y nodes  (same ids as graph)
        //   n                    → virtual source  f
        //   n+1                  → virtual sink    d
        int source  = n;
        int sink    = n + 1;
        int total   = n + 2;

        // Step 1: Build the flow network (adjacency matrix)
        // ──────────────────────────────────────────────────
        int[][] cap  = new int[total][total];  // capacity matrix
        boolean[][] hasEdge = new boolean[total][total];

        // f → every x in X, capacity 1
        for (int x = 0; x < sizeX; x++) {
            cap[source][x] = 1;
            hasEdge[source][x] = true;
        }

        // x → y for every edge in the bipartite graph, capacity 1
        for (int[] e : graph.edges()) {
            int x = e[0], y = e[1];
            cap[x][y] = 1;
            hasEdge[x][y] = true;
        }

        // every y in Y → d, capacity 1
        for (int y = sizeX; y < n; y++) {
            cap[y][sink] = 1;
            hasEdge[y][sink] = true;
        }

        // Step 2: Add zero-capacity reverse arcs (buildNetwork)
        // ──────────────────────────────────────────────────────
        for (int v = 0; v < total; v++)
            for (int w = 0; w < total; w++)
                if (hasEdge[v][w] && !hasEdge[w][v]) {
                    cap[w][v]     = 0;
                    hasEdge[w][v] = true;
                }

        // Step 3: Edmonds-Karp max flow
        // ─────────────────────────────
        int[][] flow  = new int[total][total];
        int[]   via   = new int[total];
        int flowValue = 0;
        int increment;

        while ((increment = findPath(cap, flow, total, source, sink, via)) != 0) {
            flowValue += increment;
            // Update flow along the augmenting path
            int node = sink;
            while (node != source) {
                int origin = via[node];
                flow[origin][node] += increment;
                flow[node][origin] -= increment;
                node = origin;
            }
        }

        // Step 4: Extract matching — arcs (x, y) with x∈X, y∈Y, flow[x][y] == 1
        // ─────────────────────────────────────────────────────────────────────
        List<int[]> matching = new ArrayList<>();
        for (int[] e : graph.edges()) {
            int x = e[0], y = e[1];
            if (flow[x][y] == 1)
                matching.add(new int[]{x, y});
        }

        return new Result(flowValue, matching);
    }

    // ────────────────────────────────────────────────────────────────────────
    // findPath — BFS on residual graph (Edmonds-Karp style)
    // Returns bottleneck increment, or 0 if no augmenting path exists.
    // ────────────────────────────────────────────────────────────────────────
    private static int findPath(int[][] cap, int[][] flow, int n,
                                int source, int sink, int[] via) {
        Queue<Integer> waiting  = new LinkedList<>();
        boolean[]      found    = new boolean[n];
        int[]          pathIncr = new int[n];

        for (int v = 0; v < n; v++) found[v] = false;

        waiting.add(source);
        found[source]    = true;
        via[source]      = source;
        pathIncr[source] = INF;

        do {
            int origin = waiting.poll();
            for (int destin = 0; destin < n; destin++) {
                int residue = cap[origin][destin] - flow[origin][destin];
                if (!found[destin] && residue > 0) {
                    via[destin]      = origin;
                    pathIncr[destin] = Math.min(pathIncr[origin], residue);
                    if (destin == sink)
                        return pathIncr[destin];
                    waiting.add(destin);
                    found[destin] = true;
                }
            }
        } while (!waiting.isEmpty());

        return 0;
    }
}