package PraticalExs.Promotions;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int A = sc.nextInt();
        int B = sc.nextInt();
        int E = sc.nextInt();
        int P = sc.nextInt();

        Promotions promotions = new Promotions(E);

        for (int i = 0; i < P; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            promotions.addEdge(x, y);
        }

        promotions.solve();

        System.out.println(promotions.certainCount(A));
        System.out.println(promotions.certainCount(B));
        System.out.println(promotions.impossibleCount(B));
    }
}
