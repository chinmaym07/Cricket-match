package src.com.cricketgame.models;

public class Match {
    private int matchId = 0;
    private Team teamA = new Team();
    private Team teamB = new Team();
    private Toss toss;
    private int matchOvers;
    private Player teamABestPlayer, teamBBestPlayer;
    private Enum matchWinner;
    private Innings firstInnings, secondInnings;
    private String teamNameWhoWonToss;
    private Enum matchStatus;

    public Match() {

    }

    public Match(int matchIdCount) {
        this.matchId = matchIdCount + 1;
    }

    public Toss getToss() {
        return toss;
    }

    public String getTeamNameWhoWonToss() {
        return teamNameWhoWonToss;
    }

    public void setTeamNameWhoWonToss(String teamNameWhoWonToss) {
        this.teamNameWhoWonToss = teamNameWhoWonToss;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getMatchId() {
        return matchId;
    }

    public Innings getFirstInnings() {
        return firstInnings;
    }

    public void setFirstInnings(Innings firstInnings) {
        this.firstInnings = firstInnings;
    }

    public Innings getSecondInnings() {
        return secondInnings;
    }

    public void setSecondInnings(Innings secondInnings) {
        this.secondInnings = secondInnings;
    }

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

    public void setMatchOvers(int matchOvers) {
        this.matchOvers = matchOvers;
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

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public Enum getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(Enum matchStatus) {
        this.matchStatus = matchStatus;
    }
}
