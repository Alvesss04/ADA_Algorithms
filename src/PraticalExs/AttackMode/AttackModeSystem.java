package PraticalExs.AttackMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class AttackModeSystem {

    private final int Q;               // number of squares
    private final int A;               // maximum attacks allowed
    private final List<Street>[] adj;  // adjacency list

    @SuppressWarnings("unchecked")
    public AttackModeSystem(int Q, int A) {
        this.Q   = Q;
        this.A   = A;
        this.adj = new ArrayList[Q];
        for (int i = 0; i < Q; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public void addStreet(int from, int to, int time, int canAttack) {
        adj[from].add(new Street(to, time, canAttack == 1));
    }

    public int bestTime(int start, int end) {
        final int INF = Integer.MAX_VALUE / 2;

        // dist[node][attacksUsed] = best time to reach node having used k attacks
        int[][] dist = new int[Q][A + 1];
        for (int[] row : dist) Arrays.fill(row, INF);
        dist[start][0] = 0;

        PriorityQueue<State> heap = new PriorityQueue<>();
        heap.add(new State(0, start, 0));

        while (!heap.isEmpty()) {
            State cur = heap.poll();

            // Stale entry — a cheaper path to this state was already found
            if (cur.cost > dist[cur.node][cur.attacksUsed]) continue;

            for (Street s : adj[cur.node]) {

                // Option 1: travel WITHOUT attack mode
                int newCost = cur.cost + s.time;
                if (newCost < dist[s.to][cur.attacksUsed]) {
                    dist[s.to][cur.attacksUsed] = newCost;
                    heap.add(new State(newCost, s.to, cur.attacksUsed));
                }

                // Option 2: travel WITH attack mode
                // Requires: street allows it AND we still have attacks left
                if (s.attack && cur.attacksUsed < A) {
                    int newCostAttack = cur.cost + s.time / 2; // speed×2 → time÷2
                    int newK          = cur.attacksUsed + 1;
                    if (newCostAttack < dist[s.to][newK]) {
                        dist[s.to][newK] = newCostAttack;
                        heap.add(new State(newCostAttack, s.to, newK));
                    }
                }
            }
        }

        // Best time reaching `end` regardless of how many attacks were used
        int best = INF;
        for (int k = 0; k <= A; k++) {
            if (dist[end][k] < best) best = dist[end][k];
        }
        return best;
    }
}
