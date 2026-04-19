package PraticalExs.MaximumNumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int nos = Integer.parseInt(input.readLine());

        int max = Integer.MIN_VALUE;
        String[] lista;
        for(int i = 0; i < nos; i++) {
            lista = input.readLine().split(" ");
            int numSticks = Integer.parseInt(lista[0]);

            for (int j = 1; j <= numSticks ; j++) {
                max = Math.max(max, Integer.parseInt(lista[j]));
            }
        }
        System.out.println(max);
        }
    }


