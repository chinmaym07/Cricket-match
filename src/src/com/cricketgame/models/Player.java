package src.com.cricketgame.models;

public class Player {
    private String playerRole, name;
    private int playerId;
    private PlayerStats playerStats;

    public Player(int id, String playerName, String role) {
        name = playerName;
        playerRole = role;
        playerId = id;
        playerStats = new PlayerStats();
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(String playerRole) {
        this.playerRole = playerRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

}
