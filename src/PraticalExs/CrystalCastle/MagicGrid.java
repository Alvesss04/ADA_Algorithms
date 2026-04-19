package PraticalExs.CrystalCastle;

public class MagicGrid {
    int rows;
    int columns;
    int maxConsecutive;
    int maxJumps;
    long[][][][] dataTable;
    char[][] grid;
    private static final long MODULO = 1_000_000_007L;

    public MagicGrid(int rw, int cl, int mc, int mj){
        this.rows = rw;
        this.columns = cl;
        this.maxConsecutive = mc;
        this.maxJumps = mj;
        dataTable = new long[rw][cl][mc + 1][mj + 1];
        grid = new char[rw][cl];
    }

    public void add(int r, char[] s){
        for (int i = 0; i < s.length; i++){
            grid[r][i] = s[i];
        }
    }

    private boolean isWalkable(char tile){
        return tile != '#';
    }

    private boolean canJump(char tile){
        return tile != 'J';
    }

    private boolean canDiagonalJump(char tile){
        return tile != 'X' && tile  != 'J';
    }

    private void setValues(){
        dataTable[0][0][0][0] = 1;
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                char currentTile = grid[r][c];
                if (isWalkable(currentTile)){
                    for (int m = 0; m <= maxConsecutive; m++){
                        for (int n = 0; n <= maxJumps; n++){
                            long numberOfPaths = dataTable[r][c][m][n];
                            if (numberOfPaths > 0){
                                updateValues(r, c + 1, 0, n, numberOfPaths);   // RIGHT
                                updateValues(r + 1, c, 0, n, numberOfPaths);   // DOWN

                                if (m < maxConsecutive && n < maxJumps){
                                    // DD
                                    if (canJump(currentTile)) updateValues(r + 2, c, m + 1, n + 1, numberOfPaths);
                                    if (canDiagonalJump(currentTile)){
                                        //RD
                                        updateValues(r + 1, c + 1, m + 1, n + 1, numberOfPaths);
                                        //LD
                                        if ((c - 1) >= 0) updateValues(r + 1, c - 1, m + 1, n + 1, numberOfPaths);

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateValues(int tr, int tc, int tm, int tn, long val) {
        if (tr < rows && tc < columns && isWalkable(grid[tr][tc])) {
            dataTable[tr][tc][tm][tn] += val;
            dataTable[tr][tc][tm][tn] %= MODULO;
        }
    }

    public long result(){
        setValues();
        long result = 0;
        for (int j = 0; j <= maxConsecutive; j++)
            for (int k = 0; k <= maxJumps; k++)
                result = (result + dataTable[rows - 1][columns - 1][j][k]) % MODULO;


        return result % MODULO;
    }
}