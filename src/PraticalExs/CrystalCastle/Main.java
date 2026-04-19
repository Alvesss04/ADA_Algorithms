package PraticalExs.CrystalCastle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int numberOfTests = Integer.parseInt(in.readLine());

        for (int i = 0; i < numberOfTests; i++){
            String[] allInputs = in.readLine().split(" ");
            int row = Integer.parseInt(allInputs[0]);
            int col = Integer.parseInt(allInputs[1]);
            int maxM = Integer.parseInt(allInputs[2]);
            int maxN = Integer.parseInt(allInputs[3]);
            MagicGrid mg = new MagicGrid(row, col, maxM, maxN);
            for (int j = 0; j < row; j++) {
                mg.add(j,in.readLine().toCharArray());
            }
            System.out.println(mg.result());
        }


    }


}
