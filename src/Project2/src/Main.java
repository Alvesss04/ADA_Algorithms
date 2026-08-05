import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numTests = Integer.parseInt(br.readLine().trim());

        for (int i = 0; i < numTests; i++) {
            StringTokenizer sizeStr = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(sizeStr.nextToken());
            int c = Integer.parseInt(sizeStr.nextToken());

            StringTokenizer removalStr = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(removalStr.nextToken());
            int l = Integer.parseInt(removalStr.nextToken());

            int b = Integer.parseInt(br.readLine().trim());

            MagicBeams mb = new MagicBeams(r, c, n, l, b);

            for (int j = 1; j <= b; j++) {
                StringTokenizer beamStr = new StringTokenizer(br.readLine());
                int row    = Integer.parseInt(beamStr.nextToken());
                int column = Integer.parseInt(beamStr.nextToken());
                int length = Integer.parseInt(beamStr.nextToken());
                char dir   = beamStr.nextToken().charAt(0);

                mb.addBeam(j, row, column, length, dir);
            }

            System.out.println(mb.result());
        }
    }
}