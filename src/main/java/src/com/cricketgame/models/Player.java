package src.com.cricketgame.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Player {
    private String role;
    private String name;
    private int playerId;
    private PlayerStats playerStats;

    public Player() {

    }
    public Player(int id, String playerName, String role) {
        this.name = playerName;
        this.role = role;
        this.playerId = id;
        this.playerStats = new PlayerStats();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String playerRole) {
        this.role = playerRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @JsonProperty("stats")
    public PlayerStats getPlayerStats() {
        return playerStats;
    }

}
