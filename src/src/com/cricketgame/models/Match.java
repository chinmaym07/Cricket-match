package src.com.cricketgame.models;

public class Match {
    private Team teamA = new Team();
    private Team teamB = new Team();
    private int matchOvers;
    private Player teamABestPlayer, teamBBestPlayer;
    private Enum matchWinner;

    public Enum getMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(Enum matchWinner) {
        this.matchWinner = matchWinner;
    }

    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public int getMatchOvers() {
        return matchOvers;
    }

    public Player getTeamABestPlayer() {
        return teamABestPlayer;
    }

    public void setTeamABestPlayer(Player teamABestPlayer) {
        this.teamABestPlayer = teamABestPlayer;
    }

    public Player getTeamBBestPlayer() {
        return teamBBestPlayer;
    }

    public void setTeamBBestPlayer(Player teamBBestPlayer) {
        this.teamBBestPlayer = teamBBestPlayer;
    }

    public void setMatchOvers(int matchOvers) {
        this.matchOvers = matchOvers;
    }

}
