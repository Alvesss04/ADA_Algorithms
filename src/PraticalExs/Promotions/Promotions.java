package PraticalExs.Promotions;

import java.util.*;

public class Promotions {

    private final int E;                 // Total number of employees
    private final List<Integer>[] fwd;   // fwd[x] = list of employees that x outperformed
    // edge x→y means: y can only be promoted if x is promoted
    private final List<Integer>[] rev;   // rev[y] = list of employees that outperformed y
    // reverse graph: used to find ancestors of a node
    private int[] mustSize;              // mustSize[v] = size of the smallest valid promotion set containing v
    // = v itself + all ancestors of v (everyone who must be promoted if v is)
    private int[] descSize;              // descSize[v] = v itself + all descendants of v
    // = all nodes that require v to be promoted if they are

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

    // Registers that x outperformed y:
    // y may only be promoted if x is promoted first
    // We store the edge in both directions for later BFS traversals
    public void addEdge(int x, int y) {
        fwd[x].add(y); // forward: x → y (x outperformed y)
        rev[y].add(x); // reverse: y ← x (y needs x)
    }

    // Precomputes mustSize and descSize for every employee using BFS
    // Must be called once after all edges are added, before any queries
    public void solve() {
        mustSize = new int[E];
        descSize = new int[E];
        for (int v = 0; v < E; v++) {
            mustSize[v] = bfsCount(v, rev); // BFS on reverse graph → counts v + all ancestors
            descSize[v] = bfsCount(v, fwd); // BFS on forward graph → counts v + all descendants
        }
    }

    // Returns how many employees are CERTAINLY promoted when there are K promotion slots
    // An employee v is certainly promoted with K slots if:
    //   1. mustSize[v] <= K  → v's required set fits within K slots (v CAN be promoted)
    //   2. (E - descSize[v]) < K  → the largest valid set that EXCLUDES v has fewer than K people,
    //      meaning every valid set of size K must include v
    public int certainCount(int K) {
        int count = 0;
        for (int v = 0; v < E; v++) {
            if (mustSize[v] <= K && (E - descSize[v]) < K) {
                count++;
            }
        }
        return count;
    }

    // Returns how many employees have NO possibility of being promoted with K slots
    // Employee v is impossible to promote if mustSize[v] > K:
    //   promoting v would require more people than K slots allow
    public int impossibleCount(int K) {
        int count = 0;
        for (int v = 0; v < E; v++) {
            if (mustSize[v] > K) {
                count++;
            }
        }
        return count;
    }

    // BFS from 'source' on the given adjacency list
    // Returns the total number of nodes reachable from source (including source itself)
    // Used to compute both mustSize (on rev graph) and descSize (on fwd graph)
    private int bfsCount(int source, List<Integer>[] adj) {
        boolean[] visited = new boolean[E];
        Queue<Integer> queue = new ArrayDeque<>();
        visited[source] = true;
        queue.add(source);
        int count = 1; // count source itself
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