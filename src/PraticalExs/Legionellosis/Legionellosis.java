package PraticalExs.Legionellosis;

import java.util.*;

public class Legionellosis {

    private final int L;                // Total number of locations in the map
    private final List<Integer>[] adj;  // Adjacency list: adj[i] = list of neighbors of location i

    // Constructor: initializes the graph with L locations and empty adjacency lists
    @SuppressWarnings("unchecked")
    public Legionellosis(int L) {
        this.L = L;
        adj = new ArrayList[L + 1]; // index 0 unused, locations go from 1 to L
        for (int i = 1; i <= L; i++)
            adj[i] = new ArrayList<>();
    }

    // Adds a two-way connection (edge) between locations l1 and l2
    // Since paths go both ways, we add each location to the other's neighbor list
    public void addConnection(int l1, int l2) {
        adj[l1].add(l2);
        adj[l2].add(l1);
    }

    // BFS (Breadth-First Search) from a patient's home location
    // Returns a boolean array where result[v] = true means location v
    // is reachable from home with distance <= maxDist
    public boolean[] reachable(int home, int maxDist) {
        boolean[] visited = new boolean[L + 1]; // tracks if a location was already visited
        int[] dist = new int[L + 1];            // dist[v] = shortest distance from home to v
        Arrays.fill(dist, -1);                  // -1 means not yet reached

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(home);       // start BFS from the patient's home
        dist[home] = 0;        // distance from home to itself is 0
        visited[home] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll(); // take the next location to process

            // If we've already reached maxDist, don't go further from this node
            if (dist[cur] >= maxDist) continue;

            // Explore all neighbors of the current location
            for (int neighbor : adj[cur]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    dist[neighbor] = dist[cur] + 1; // each connection has length 1
                    queue.add(neighbor);
                }
            }
        }

        // Build the result: mark locations that were reached within maxDist
        boolean[] result = new boolean[L + 1];
        for (int i = 1; i <= L; i++)
            if (dist[i] >= 0 && dist[i] <= maxDist) // reached AND within allowed distance
                result[i] = true;

        return result;
    }

    // Computes the perilous locations: locations reachable by ALL patients
    // patients[i] = { home, maxDist } for patient i
    public List<Integer> perilousLocations(int[][] patients) {

        // Start assuming every location is perilous
        // We will eliminate locations that are NOT reachable by any patient
        boolean[] perilous = new boolean[L + 1];
        Arrays.fill(perilous, true);

        // For each patient, run BFS and remove locations they couldn't reach
        for (int[] patient : patients) {
            int home    = patient[0]; // where this patient lives
            int maxDist = patient[1]; // how far they travelled at most

            boolean[] reach = reachable(home, maxDist);

            // Intersection: if a location is not in this patient's region, it's not perilous
            for (int i = 1; i <= L; i++)
                if (!reach[i])
                    perilous[i] = false;
        }

        // Collect all remaining perilous locations in sorted order (naturally sorted since we go 1..L)
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= L; i++)
            if (perilous[i])
                result.add(i);

        return result;
    }
}