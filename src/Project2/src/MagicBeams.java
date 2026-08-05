import java.util.Arrays;
import java.util.PriorityQueue;

public class MagicBeams {

    private static final int EMPTY = -1;

    private final int rows, cols, corridorStart, corridorWidth, numBeams;
    private final int[][] grid;
    private final Beam[] beams;
    private final int[] beamLen;

    private int[] dependencyFrom, dependencyTo;
    private int dependencyCount;

    public MagicBeams(int rows, int cols, int corridorWidth, int corridorStart, int numBeams) {
        this.rows = rows;
        this.cols = cols;
        this.corridorWidth = corridorWidth;
        this.corridorStart = corridorStart;
        this.numBeams = numBeams;
        this.beams = new Beam[numBeams + 1];
        this.beamLen = new int[numBeams + 1];
        this.grid = initiateGrid(rows, cols);
    }

    public void addBeam(int id, int r, int c, int length, char dirChar) {
        Direction dir = Direction.deLetra(dirChar);
        int headR = r + dir.dr * (length - 1);
        int headC = c + dir.dc * (length - 1);
        registerBeam(id, r, c, dir, length);
        beams[id] = new Beam(id, headR, headC, dir);
        beamLen[id] = length;
    }

    public String result() {
        boolean[] mustFree = markCorridorBeams();
        if (!anyBeamMarked(mustFree)) return "False alarm";

        int[] inDegree = new int[numBeams + 1];
        int[][] adj = buildDependencyGraph(mustFree, inDegree);
        int totalToFree = countMarked(mustFree);
        return kahnTopologicalSort(mustFree, inDegree, adj, totalToFree);
    }

    private static int[][] initiateGrid(int rows, int cols) {
        int[][] grid = new int[rows][cols];
        for (int[] row : grid) Arrays.fill(row, EMPTY);
        return grid;
    }

    private void registerBeam(int id, int startR, int startC, Direction dir, int length) {
        int r = startR, c = startC;
        for (int i = 0; i < length; i++) {
            grid[r][c] = id;
            r += dir.dr;
            c += dir.dc;
        }
    }

    private boolean[] markCorridorBeams() {
        boolean[] mustFree = new boolean[numBeams + 1];
        for (int col = corridorStart; col < corridorStart + corridorWidth; col++) {
            for (int row = 0; row < rows; row++) {
                int id = grid[row][col];
                if (id != EMPTY) mustFree[id] = true;
            }
        }
        return mustFree;
    }

    private int[][] buildDependencyGraph(boolean[] mustFree, int[] inDegree) {
        dependencyFrom = new int[Math.max(numBeams * 2, 16)];
        dependencyTo = new int[dependencyFrom.length];
        dependencyCount = 0;
        runBFS(mustFree, inDegree);
        return toAdjacencyArray();
    }

    private void runBFS(boolean[] mustFree, int[] inDegree) {
        boolean[] visited = new boolean[numBeams + 1];
        int[] queue = new int[numBeams + 1];
        int qHead = 0, qTail = 0;

        for (int id = 1; id <= numBeams; id++) {
            if (mustFree[id]) queue[qTail++] = id;
        }

        while (qHead < qTail) {
            int id = queue[qHead++];
            if (visited[id]) continue;
            visited[id] = true;
            qTail = findBlockersAhead(id, mustFree, inDegree, queue, qTail);
        }
    }

    private int findBlockersAhead(int id, boolean[] mustFree, int[] inDegree,
                                  int[] queue, int qTail) {
        Beam beam = beams[id];
        Direction dir = beam.dir;
        int row = beam.headR + dir.dr;
        int col = beam.headC + dir.dc;

        while (isInsideGrid(row, col)) {
            int blocker = grid[row][col];

            if (blocker == EMPTY) {
                row += dir.dr;
                col += dir.dc;
                continue;
            }

            appendDependency(blocker, id);
            inDegree[id]++;
            if (!mustFree[blocker]) {
                mustFree[blocker] = true;
                queue[qTail++] = blocker;
            }

            Beam blockerBeam = beams[blocker];
            int blockerLen = beamLen[blocker];
            int exitRow = blockerBeam.headR - blockerBeam.dir.dr * (blockerLen - 1);
            int exitCol = blockerBeam.headC - blockerBeam.dir.dc * (blockerLen - 1);
            if (dir.dr != 0) {
                exitRow = (dir.dr > 0) ? Math.max(blockerBeam.headR, exitRow) : Math.min(blockerBeam.headR, exitRow);
                exitCol = col;
            } else {
                exitCol = (dir.dc > 0) ? Math.max(blockerBeam.headC, exitCol) : Math.min(blockerBeam.headC, exitCol);
                exitRow = row;
            }
            row = exitRow + dir.dr;
            col = exitCol + dir.dc;
        }
        return qTail;
    }

    private void appendDependency(int from, int to) {
        if (dependencyCount == dependencyFrom.length) {
            dependencyFrom = Arrays.copyOf(dependencyFrom, dependencyFrom.length * 2);
            dependencyTo = Arrays.copyOf(dependencyTo, dependencyTo.length * 2);
        }
        dependencyFrom[dependencyCount] = from;
        dependencyTo[dependencyCount] = to;
        dependencyCount++;
    }

    private int[][] toAdjacencyArray() {
        int[] outDegree = new int[numBeams + 1];
        for (int i = 0; i < dependencyCount; i++) outDegree[dependencyFrom[i]]++;

        int[][] adj = new int[numBeams + 1][];
        for (int id = 1; id <= numBeams; id++) adj[id] = new int[outDegree[id]];

        Arrays.fill(outDegree, 0);
        for (int i = 0; i < dependencyCount; i++) {
            int from = dependencyFrom[i];
            adj[from][outDegree[from]++] = dependencyTo[i];
        }
        return adj;
    }

    private String kahnTopologicalSort(boolean[] mustFree, int[] inDegree,
                                       int[][] adj, int totalToFree) {
        PriorityQueue<Integer> ready = new PriorityQueue<>();
        for (int id = 1; id <= numBeams; id++) {
            if (mustFree[id] && inDegree[id] == 0) ready.add(id);
        }

        StringBuilder result = new StringBuilder();
        int processed = 0;

        while (!ready.isEmpty()) {
            int beam = ready.poll();
            if (result.length() > 0) result.append(' ');
            result.append(beam);
            processed++;

            for (int successor : adj[beam]) {
                inDegree[successor]--;
                if (inDegree[successor] == 0) ready.add(successor);
            }
        }

        if (processed < totalToFree) return "Disaster";
        return result.toString();
    }

    private boolean isInsideGrid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private static boolean anyBeamMarked(boolean[] mustFree) {
        for (int id = 1; id < mustFree.length; id++) if (mustFree[id]) return true;
        return false;
    }

    private static int countMarked(boolean[] mustFree) {
        int count = 0;
        for (int id = 1; id < mustFree.length; id++) if (mustFree[id]) count++;
        return count;
    }
}