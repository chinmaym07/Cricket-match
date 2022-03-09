package src.com.cricketgame.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int teamId;
    private String teamName, captainName;
    @JsonIgnore
    private List<Player> playersArr = new ArrayList<Player>();

    public Team() {

    }

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
    @JsonIgnore
    @JsonProperty("players")
    public List<Player> getPlayersArr() {
        return playersArr;
    }

    public void setPlayersArr(List<Player> playersArr) {
        this.playersArr = playersArr;
    }
}
