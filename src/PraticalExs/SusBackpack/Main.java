package PraticalExs.SusBackpack;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return; // Prevenção contra ficheiros vazios

        // Ler os parâmetros base
        int S = sc.nextInt(); // Número de Suspeitos
        int P = sc.nextInt(); // Número de Preceding Conjectures
        int C = sc.nextInt(); // Número de Concurrent Conjectures

        // Criar o nosso objeto com a lógica
        Backpack backpack = new Backpack(S);

        // Ler as pistas de Precedência (um antes do outro)
        for (int i = 0; i < P; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            backpack.addPreceding(x, y);
        }

        // Ler as pistas de Concorrência (os dois ao mesmo tempo)
        for (int i = 0; i < C; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            backpack.addConcurrent(x, y);
        }

        // Perguntar ao objeto se o cenário é possível e imprimir o veredito
        if (backpack.isConsistent()) {
            System.out.println("Consistent conjectures");
        } else {
            System.out.println("Inconsistent conjectures");
        }

        sc.close();
    }
}
