package PraticalExs.TeamFormations;

public class TeamFormations {

    int roles;      // Number of roles (positions) in the team
    int players;    // Total number of players to distribute across all roles
    int[][] mm;     // mm[role][0] = min players for that role
    // mm[role][1] = max players for that role
    long[][] matrix;// DP table: matrix[i][j] = number of ways to fill roles 0..i
    // using exactly j players in total

    // Constructor: initializes roles, players, and the DP structures
    public TeamFormations(int r, int p) {
        this.roles = r;
        this.players = p;
        this.mm = new int[r][2];           // min/max constraints per role
        this.matrix = new long[r][p + 1];  // DP table, columns 0..players
    }

    // Sets the constraint for a given role:
    // type == 0 → constraint is a MINIMUM: at least 'value' players, at most all remaining
    // type == 1 → constraint is a MAXIMUM: at least 0 players, at most 'value' players
    public void add(int role, int type, int value) {
        if (type == 0) {
            mm[role][0] = value;    // minimum = value
            mm[role][1] = players;  // maximum = all players (no upper bound)
        } else {
            mm[role][0] = 0;        // minimum = 0 (no lower bound)
            mm[role][1] = value;    // maximum = value
        }
    }

    // Dynamic Programming: counts the number of valid team formations
    // matrix[i][j] = number of ways to assign exactly j players to roles 0..i
    //
    // Base case (role 0):
    //   matrix[0][i] = 1 if mm[0][0] <= i <= mm[0][1] (valid count for role 0)
    //   matrix[0][i] = 0 otherwise
    //
    // Transition (role i, total players j):
    //   For each valid number k of players assigned to role i (mm[i][0] <= k <= mm[i][1]):
    //     add matrix[i-1][j-k]  (ways to fill roles 0..i-1 with the remaining j-k players)
    //
    // Answer: matrix[roles-1][players] = ways to fill all roles using exactly 'players' players
    public long numFormations() {

        // Base case: fill the DP for role 0
        for (int i = 0; i <= players; i++) {
            if (i < mm[0][0] || i > mm[0][1])
                matrix[0][i] = 0; // i players is outside allowed range for role 0
            else
                matrix[0][i] = 1; // exactly one way to assign i players to role 0
        }

        // Fill remaining roles using previously computed results
        for (int i = 1; i < roles; i++) {
            for (int j = 0; j <= players; j++) {
                long sum = 0;
                // k = number of players assigned to role i
                // must be between mm[i][0] and min(mm[i][1], j) — can't use more than j total
                for (int k = mm[i][0]; k <= Math.min(mm[i][1], j); k++) {
                    sum += matrix[i - 1][j - k]; // add ways to fill previous roles with remaining players
                }
                matrix[i][j] = sum;
            }
        }

        // Final answer: ways to fill all roles using exactly 'players' players
        return matrix[roles - 1][players];
    }
}