package FloydWarshall;

// ─────────────────────────────────────────────
// Main.java  —  demo using the slide example
// Graph from Cap. XII (nodes 0..4):
//   0→4: 6        1→0: 7   1→2: 1   1→3: -3
//   2→3: 7        3→4: -2  4→1: 7   4→2: -4  4→3: 5
//
// Expected final L(v,w,4) matrix:
//   0: [  0, 13,  2,  9,  6 ]
//   1: [  7,  0, -9, -3, -5 ]
//   2: [ 19, 12,  0,  7,  5 ]
//   3: [ 12,  5, -6,  0, -2 ]
//   4: [ 14,  7, -4,  3,  0 ]
// ─────────────────────────────────────────────
public class Main {

    public static void main(String[] args) {

        // ── Build the graph from the slides ──────────────────────────────
        Digraph g = new Digraph(5);
        g.addEdge(0, 4,  6);
        g.addEdge(1, 0,  7);
        g.addEdge(1, 2,  1);
        g.addEdge(1, 3, -3);
        g.addEdge(2, 3,  7);
        g.addEdge(3, 4, -2);
        g.addEdge(4, 1,  7);
        g.addEdge(4, 2, -4);
        g.addEdge(4, 3,  5);

        // ── Run Floyd-Warshall ────────────────────────────────────────────
        try {
            FloydWarshall.Result result = FloydWarshall.floydWarshall(g);

            // ── Print the length matrix ───────────────────────────────────
            System.out.println("=== Length Matrix (Shortest Paths) ===");
            printMatrix(result.length, g.numNodes());

            // ── Print the via matrix ──────────────────────────────────────
            System.out.println("\n=== Via Matrix (Intermediate Nodes) ===");
            printViaMatrix(result.via, g.numNodes());

            // ── Reconstruct some paths ────────────────────────────────────
            System.out.println("\n=== Example Path Reconstructions ===");
            printPath(result.via, 0, 3);  // 0 → 3
            printPath(result.via, 1, 4);  // 1 → 4
            printPath(result.via, 3, 2);  // 3 → 2
            printPath(result.via, 2, 0);  // 2 → 0

        } catch (FloydWarshall.NegativeWeightCycleException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // ── Negative cycle detection demo ─────────────────────────────────
        System.out.println("\n=== Negative Cycle Detection Demo ===");
        Digraph negCycle = new Digraph(3);
        negCycle.addEdge(0, 1,  1);
        negCycle.addEdge(1, 2, -3);
        negCycle.addEdge(2, 0,  1); // cycle 0→1→2→0 has weight 1-3+1 = -1
        try {
            FloydWarshall.floydWarshall(negCycle);
            System.out.println("No negative cycle found (unexpected!)");
        } catch (FloydWarshall.NegativeWeightCycleException e) {
            System.out.println("Correctly detected: " + e.getMessage());
        }
    }

    // ── Utility: print a matrix ───────────────────────────────────────────
    private static void printMatrix(int[][] m, int n) {
        System.out.print("     ");
        for (int w = 0; w < n; w++) System.out.printf("%5d", w);
        System.out.println();
        for (int v = 0; v < n; v++) {
            System.out.printf("  %2d:", v);
            for (int w = 0; w < n; w++) {
                if (m[v][w] >= Digraph.INF)
                    System.out.printf("%5s", "+∞");
                else
                    System.out.printf("%5d", m[v][w]);
            }
            System.out.println();
        }
    }

    private static void printViaMatrix(int[][] via, int n) {
        System.out.print("     ");
        for (int w = 0; w < n; w++) System.out.printf("%5d", w);
        System.out.println();
        for (int v = 0; v < n; v++) {
            System.out.printf("  %2d:", v);
            for (int w = 0; w < n; w++) {
                if (via[v][w] == -1)
                    System.out.printf("%5s", "-");
                else
                    System.out.printf("%5d", via[v][w]);
            }
            System.out.println();
        }
    }

    private static void printPath(int[][] via, int from, int to) {
        java.util.List<Integer> path = FloydWarshall.getPath(via, from, to);
        System.out.print("Path " + from + " → " + to + ": ");
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) System.out.print(" → ");
            System.out.print(path.get(i));
        }
        System.out.println();
    }
}