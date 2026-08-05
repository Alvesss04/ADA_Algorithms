package Dinic;

// ─────────────────────────────────────────────
// Main.java  —  demo using the slide example
//
// Graph from Cap. XIV (nodes 0..5):
//   source = 0, sink = 5
//   0→1: 10,  0→3: 20
//   1→2: 7,   1→3: 7 (diagonal)  [slide shows arcs from 1]
//   2→5: 12
//   3→4: 6,   3→2: 5 (partial)
//   4→5: 9,   4→2: 3 (partial)   [slide shows arcs into 4]
//
// Exactly from the slides (Cap XIV p.4-10):
//   0→1:10, 0→3:20, 1→2:7, 2→5:12, 3→2:5, 3→4:6, 4→5:9
//   Expected max flow = 19  (10 + 9 = 19, flows through all phases)
//
// Verification with known result:
//   Phase 1: BFS finds levels, DFS finds paths
//   Algorithm ends when level[sink] = INF
// ─────────────────────────────────────────────
public class Main {

    public static void main(String[] args) {

        // ── Slide example (Cap. XIV) ──────────────────────────────────────
        // 6 nodes: source=0, sink=5
        // Arcs from the slides:
        //   0→1:10, 0→3:20,  1→2:7,  2→5:12
        //   3→2:5,  3→4:6,   4→5:9,  4→2:3 (slide p.4)
        System.out.println("=== Dinic Max Flow — Slide Example ===");
        Digraph g = new Digraph(6);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 3, 20);
        g.addEdge(1, 2,  7);
        g.addEdge(2, 5, 12);
        g.addEdge(3, 2,  5);
        g.addEdge(3, 4,  6);
        g.addEdge(4, 5,  9);
        g.addEdge(4, 2,  3);

        int source = 0, sink = 5;
        Dinic.Result r = Dinic.dinic(g, source, sink);

        System.out.println("Source: " + source + "  Sink: " + sink);
        System.out.println("Max flow: " + r.flowValue);
        printFlow(g, r.flow);

        // ── Same classic 6-node graph used in Edmonds-Karp demo ──────────
        // Expected max flow = 23
        System.out.println("\n=== Classic 6-node Graph ===");
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

        Dinic.Result r2 = Dinic.dinic(g2, 0, 5);
        System.out.println("Max flow: " + r2.flowValue); // expected 23
        printFlow(g2, r2.flow);

        // ── Simple 4-node graph from Edmonds-Karp demo ───────────────────
        // Expected max flow = 30
        System.out.println("\n=== 4-node Graph (EK Demo) ===");
        Digraph g3 = new Digraph(4);
        g3.addEdge(0, 1, 20);
        g3.addEdge(0, 3, 10);
        g3.addEdge(1, 3, 10);
        g3.addEdge(1, 2, 30);
        g3.addEdge(2, 3, 20);
        g3.addEdge(2, 1,  5);

        Dinic.Result r3 = Dinic.dinic(g3, 0, 3);
        System.out.println("Max flow: " + r3.flowValue); // expected 30
        printFlow(g3, r3.flow);
    }

    private static void printFlow(Digraph g, int[][] flow) {
        System.out.printf("  %-10s %6s %8s%n", "Arc", "Flow", "Capacity");
        System.out.println("  " + "-".repeat(27));
        for (int[] e : g.edges()) {
            int v = e[0], w = e[1], cap = e[2];
            System.out.printf("  %d → %d        %5d %8d%n", v, w, flow[v][w], cap);
        }
    }
}