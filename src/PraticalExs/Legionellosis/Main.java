package PraticalExs.Legionellosis;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int L = sc.nextInt();
        int C = sc.nextInt();

        Legionellosis leg = new Legionellosis(L);

        for (int i = 0; i < C; i++)
            leg.addConnection(sc.nextInt(), sc.nextInt());

        int S = sc.nextInt();
        int[][] patients = new int[S][2];
        for (int i = 0; i < S; i++) {
            patients[i][0] = sc.nextInt(); // home
            patients[i][1] = sc.nextInt(); // max distance
        }

        List<Integer> perilous = leg.perilousLocations(patients);

        if (perilous.isEmpty()) {
            System.out.println(0);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < perilous.size(); i++) {
                if (i > 0) sb.append(" ");
                sb.append(perilous.get(i));
            }
            System.out.println(sb);
        }
    }
}
