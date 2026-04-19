package PraticalExs.DrinaDrone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int numberOfTest = Integer.parseInt(in.readLine());

        for (int i = 0; i < numberOfTest; i++){
            int numberOfOffers = Integer.parseInt(in.readLine());
            DrinasClass dc = new DrinasClass(numberOfOffers);

            for (int j = 0; j < numberOfOffers; j++){
                String[] div = in.readLine().split(" ");
                dc.add(Integer.parseInt(div[0]),Integer.parseInt(div[1]),Integer.parseInt(div[2]));
            }

            System.out.println(dc.solveProblem());
        }

    }
}
