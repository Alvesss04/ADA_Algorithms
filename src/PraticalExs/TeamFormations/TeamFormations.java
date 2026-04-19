package PraticalExs.TeamFormations;

public class TeamFormations {
    int roles;
    int players;
    int[][] mm;
    long[][] matrix;

    public TeamFormations(int r, int p){
        this.roles = r;
        this.players = p;
        this.mm = new int[r][2];
        this.matrix = new long[r][p+1];
    }

    public void add(int role, int type, int value){
        if (type == 0){
            mm[role][0] = value;
            mm[role][1] = players;
        }else{
            mm[role][0] = 0;
            mm[role][1] = value;
        }
    }

    public long numFormations(){
        for (int i = 0; i <= players ; i++){
            if (i < mm[0][0] || i > mm[0][1] ) matrix[0][i] = 0;
            else matrix[0][i] = 1;

        }
        for (int i = 1; i < roles;i++){
            for (int j = 0; j <= players; j++){
                long sum = 0;
                for (int k = mm[i][0] ; k <= Math.min(mm[i][1],j) ; k++){
                    sum +=  matrix[i-1][j-k];
                }
                matrix[i][j] = sum;
            }
        }

        return matrix [roles-1][players];
    }

}
