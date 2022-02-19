package src.com.cricketgame.models;

import java.util.ArrayList;

public class Team {
    private static int c = 1, ch = 65;
    private int teamId;
    private String teamName, captainName;
    private ArrayList<Player> playersArr = new ArrayList<Player>();


    public int getTeamId() {
        return teamId;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<Player> getPlayersArr() {
        return playersArr;
    }

    public void setPlayersArr(ArrayList<Player> playersArr) {
        this.playersArr = playersArr;
    }

    public void createTeam() {
        teamId = c;
        c++;
        if (ch == 91)
            ch = 97;
        else if (ch == 122)
            ch = 65;

        for (int currentPlayerInd = 0; currentPlayerInd < 11; currentPlayerInd++) {
            String n = "" + ((char) ch);
            ch++;
            Player p;
            if (currentPlayerInd < 6)
                p = new Player(n, "Batsman");
            else
                p = new Player(n, "Bowler");
            if(currentPlayerInd == 5)
                setCaptainName(p.getName());
            playersArr.add(p);
        }

    }
}
