package PraticalExs.Promotions;

import java.util.*;

public class Promotions {

    private final int E;
    private final List<Integer>[] fwd; // fwd[x] = y where x outperformed y
    private final List<Integer>[] rev; // rev[y] = x where x outperformed y
    private int[] mustSize;  // min upset size containing v (v + ancestors)
    private int[] descSize;  // v + all descendants of v

    @SuppressWarnings("unchecked")
    public Promotions(int E) {
        this.E = E;
        fwd = new ArrayList[E];
        rev = new ArrayList[E];
        for (int i = 0; i < E; i++) {
            fwd[i] = new ArrayList<>();
            rev[i] = new ArrayList<>();
        }
    }

    // x outperformed y: y can only be promoted if x is promoted
    public void addEdge(int x, int y) {
        fwd[x].add(y);
        rev[y].add(x);
    }

    // Precompute mustSize and descSize for all employees
    public void solve() {
        mustSize = new int[E];
        descSize = new int[E];
        for (int v = 0; v < E; v++) {
            mustSize[v] = bfsCount(v, rev); // ancestors
            descSize[v] = bfsCount(v, fwd); // descendants
        }
    }

    // Employees certainly promoted with K slots:
    // mustSize[v] <= K  (v fits in K slots)
    // AND (E - descSize[v]) < K  (no valid upset of size K can exclude v)
    public int certainCount(int K) {
        int count = 0;
        for (int v = 0; v < E; v++) {
            if (mustSize[v] <= K && (E - descSize[v]) < K) {
                count++;
            }
        }
        return count;
    }

    // Employees impossible to promote with K slots:
    // mustSize[v] > K  (v's required ancestors alone exceed K)
    public int impossibleCount(int K) {
        int count = 0;
        for (int v = 0; v < E; v++) {
            if (mustSize[v] > K) {
                count++;
            }
        }
        return count;
    }

    // BFS from source on adj, returns number of reachable nodes (including source)
    private int bfsCount(int source, List<Integer>[] adj) {
        boolean[] visited = new boolean[E];
        Queue<Integer> queue = new ArrayDeque<>();
        visited[source] = true;
        queue.add(source);
        int count = 1;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int neighbor : adj[cur]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    count++;
                    queue.add(neighbor);
                }
            }
        }
        return count;
    }
}
