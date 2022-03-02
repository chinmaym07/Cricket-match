package src.com.cricketgame.models;

import java.util.ArrayList;

public class Team {
    private int teamId;
    private String teamName, captainName;
    private ArrayList<Player> playersArr = new ArrayList<Player>();


    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
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
}
