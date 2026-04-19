package PraticalExs.TeamFormations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int players = Integer.parseInt(br.readLine());
        int roles = Integer.parseInt(br.readLine());

        TeamFormations team = new TeamFormations(roles, players);

        for (int i = 0; i < roles; i++) {
            String[] str = br.readLine().split(" ");
            if (str[0].equals("MIN")) {
                team.add(i, 0, Integer.parseInt(str[1]));
            } else {
                team.add(i, 1, Integer.parseInt(str[1]));
            }
        }
        System.out.println(team.numFormations());
    }
}

