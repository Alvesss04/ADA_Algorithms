package EdmondsKarp;

// ─────────────────────────────────────────────
// Main.java  —  demo using the slide example
// Graph from Cap. XIII (nodes 0..3):
//
//   0→1: cap 20    0→2: cap 10 (slide shows arc leaving 0)
//   Actually from slides:
//   source=0, sink=3
//   0→1: 20,  0→3: 10  (wait — let's map exactly)
//
// Slide graph (checking p.13-16):
//   0→1: 20,  1→3: 10,  1→2: 30 (diagonal)
//   0→3: 10 (vertical)
//   2→3: 20,  2→1: 5 (reverse-ish)
//
// Re-reading slides carefully:
//   Arcs and caps: 0→1:20, 0→3:10, 1→3:10, 1→2:30, 2→3:20, 2→1:5
//   source=0, sink=3
//   Expected max flow = 30  (iterations produce 10, 10, 10)
// ─────────────────────────────────────────────
public class Main {

    public static void main(String[] args) {

        // ── Graph from the slides (Cap. XIII, p.13-16) ────────────────────
        // Nodes: 0=source, 3=sink
        // The slides show 4 nodes (0,1,2,3) with these capacities:
        //   flow/capacity notation: current_flow/capacity
        //   0→1: /20,  0→3: /10,  1→3: /10,  1→2: /30,  2→3: /20,  2→1: /5
        Digraph g = new Digraph(4);
        g.addEdge(0, 1, 20);
        g.addEdge(0, 3, 10);
        g.addEdge(1, 3, 10);
        g.addEdge(1, 2, 30);
        g.addEdge(2, 3, 20);
        g.addEdge(2, 1,  5);

        int source = 0, sink = 3;

        System.out.println("=== Edmonds-Karp Max Flow ===");
        System.out.println("Source: " + source + "  Sink: " + sink);
        System.out.println();

        EdmondsKarp.Result result = EdmondsKarp.edmondsKarp(g, source, sink);

        System.out.println("Max flow value: " + result.flowValue);
        System.out.println();

        System.out.println("=== Final Flow on Each Arc ===");
        System.out.printf("%-12s %8s %8s%n", "Arc", "Flow", "Capacity");
        System.out.println("-".repeat(30));
        for (int[] e : g.edges()) {
            int v = e[0], w = e[1], cap = e[2];
            System.out.printf("  %d → %d       %6d %8d%n",
                    v, w, result.flow[v][w], cap);
        }

        // ── Second example: classic textbook graph ────────────────────────
        System.out.println("\n=== Second Example (Classic 6-node graph) ===");
        // source=0, sink=5
        //   0→1:16,  0→2:13
        //   1→2:10,  1→3:12
        //   2→1:4,   2→4:14
        //   3→2:9,   3→5:20
        //   4→3:7,   4→5:4
        Digraph g2 = new Digraph(6);
        g2.addEdge(0, 1, 16);
        g2.addEdge(0, 2, 13);
        g2.addEdge(1, 2, 10);
        g2.addEdge(1, 3, 12);
        g2.addEdge(2, 1,  4);
        g2.addEdge(2, 4, 14);
        g2.addEdge(3, 2,  9);
        g2.addEdge(3, 5, 20);
        g2.addEdge(4, 3,  7);
        g2.addEdge(4, 5,  4);

        EdmondsKarp.Result result2 = EdmondsKarp.edmondsKarp(g2, 0, 5);
        System.out.println("Max flow value: " + result2.flowValue); // expected: 23

        System.out.println("\n=== Final Flow on Each Arc ===");
        System.out.printf("%-12s %8s %8s%n", "Arc", "Flow", "Capacity");
        System.out.println("-".repeat(30));
        for (int[] e : g2.edges()) {
            int v = e[0], w = e[1], cap = e[2];
            System.out.printf("  %d → %d       %6d %8d%n",
                    v, w, result2.flow[v][w], cap);
        }
    }
}