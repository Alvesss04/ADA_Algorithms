package PraticalExs.SusBackpack;

import java.util.*;

public class Backpack {
    private final int S;
    private final int numNodes;
    private final List<Integer>[] adj;
    private final int[] inDegree;

    @SuppressWarnings("unchecked")
    public Backpack(int S) {
        this.S = S;
        this.numNodes = 2 * S; // 2 nós por suspeito (chegada e partida)
        this.adj = new ArrayList[numNodes];
        this.inDegree = new int[numNodes];

        for (int i = 0; i < numNodes; i++) {
            adj[i] = new ArrayList<>();
        }

        // 1. Regra da Física: A Chegada (i) acontece antes da Partida (i + S)
        // Adicionamos logo estas arestas no construtor
        for (int i = 0; i < S; i++) {
            addEdge(i, i + S);
        }
    }

    // Método auxiliar privado para ligar arestas e atualizar os graus
    private void addEdge(int from, int to) {
        adj[from].add(to);
        inDegree[to]++;
    }

    // 2. Preceding Conjectures: X partiu antes de Y chegar
    public void addPreceding(int x, int y) {
        int partidaX = x + S;
        int chegadaY = y;
        addEdge(partidaX, chegadaY);
    }

    // 3. Concurrent Conjectures: X e Y ao mesmo tempo
    // Chegada X antes de Partida Y   E   Chegada Y antes de Partida X
    public void addConcurrent(int x, int y) {
        int chegadaX = x;
        int partidaX = x + S;
        int chegadaY = y;
        int partidaY = y + S;

        addEdge(chegadaX, partidaY);
        addEdge(chegadaY, partidaX);
    }

    // Aplica o Algoritmo de Kahn (Teste à Aciclicidade)
    // Devolve true se for consistente (sem ciclos), false se for inconsistente
    public boolean isConsistent() {
        Queue<Integer> ready = new ArrayDeque<>();

        // 1º Ciclo: Meter na fila quem não tem dependências (grau de entrada 0)
        for (int i = 0; i < numNodes; i++) {
            if (inDegree[i] == 0) {
                ready.add(i);
            }
        }

        int numProcNodes = 0; // Contador de nós válidos

        // 2º Ciclo: Processar os nós prontos
        while (!ready.isEmpty()) {
            int node = ready.poll();
            numProcNodes++;

            for (int vizinho : adj[node]) {
                inDegree[vizinho]--;
                if (inDegree[vizinho] == 0) {
                    ready.add(vizinho);
                }
            }
        }

        // Se o número de nós processados for igual ao total de nós, não há mentiras!
        return numProcNodes == numNodes;
    }
}
