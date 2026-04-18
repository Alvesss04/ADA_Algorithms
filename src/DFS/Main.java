package DFS;

// ─────────────────────────────────────────────
// Main.java  —  demo using the slide example
// Same graph as BFS slides (Capítulo IV):
//   0 → 1, 3, 4
//   1 → 2
//   3 → 4
//   4 → 2, 0
//   2 → 5
//   5 → 6, 2
//   6 → 5
//
// Recursive DFS from 0:  0 1 2 5 6 3 4
// Iterative DFS from 0:  0 4 2 3 1 5 6
// (iterative reverses the successor order on the stack,
//  so the visit order differs — both are valid DFS orders)
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

        System.out.print("Recursive DFS: ");
        DFS.dfsTraversalRec(g);
        // → 0 1 2 5 6 3 4

        System.out.print("\nIterative DFS: ");
        DFS.dfsTraversalIter(g);
        // → 0 4 2 3 1 5 6
    }
}
