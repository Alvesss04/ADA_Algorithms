package PraticalExs.AttackMode;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Q = squares, S = streets, A = max attacks, T = test cases
        int Q = sc.nextInt();
        int S = sc.nextInt();
        int A = sc.nextInt();
        int T = sc.nextInt();

        // Build the graph inside AttackMode
        AttackModeSystem race = new AttackModeSystem(Q, A);

        for (int i = 0; i < S; i++) {
            int q1 = sc.nextInt();
            int q2 = sc.nextInt();
            int t  = sc.nextInt();
            int m  = sc.nextInt();
            race.addStreet(q1, q2, t, m);
        }

        // Run one Dijkstra per test case
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < T; i++) {
            int start = sc.nextInt();
            int end   = sc.nextInt();
            sb.append(race.bestTime(start, end));
            if (i < T - 1) sb.append('\n');
        }

        System.out.println(sb);
        sc.close();
    }
}
