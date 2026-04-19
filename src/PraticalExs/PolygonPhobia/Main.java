package PraticalExs.PolygonPhobia;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int S = sc.nextInt();

        Polygon polygon = new Polygon();
        int painted = 0;

        for (int i = 0; i < S; i++) {
            int x1 = sc.nextInt(), y1 = sc.nextInt();
            int x2 = sc.nextInt(), y2 = sc.nextInt();
            if (polygon.tryPaint(x1, y1, x2, y2)) {
                painted++;
            }
        }

        System.out.println(painted);
    }
}