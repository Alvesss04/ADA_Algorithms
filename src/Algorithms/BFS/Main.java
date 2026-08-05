package BFS;

// ─────────────────────────────────────────────
// Main.java  —  demo using the slide example
// Graph from slides p.2-19:
//   0 → 1, 3, 4
//   1 → 2
//   3 → 4
//   4 → 2, 0
//   2 → 5
//   5 → 6, 2
//   6 → 5
// Expected BFS order from 0: 0 1 3 4 2 5 6
// ─────────────────────────────────────────────
public class Main {
    public static void main(String[] args) {
        Digraph g = new Digraph(7);

        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(3, 4);
        g.addEdge(4, 2);
        g.addEdge(4, 0);
        g.addEdge(2, 5);
        g.addEdge(5, 6);
        g.addEdge(5, 2);
        g.addEdge(6, 5);

        System.out.print("BFS order: ");
        BFS.bfsTraversal(g);
        // → 0 1 3 4 2 5 6
    }
}
