package src.com.cricketgame.models;

import src.com.cricketgame.repo.DB;

import java.sql.SQLException;

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

    public Match() throws SQLException {
        matchId = DB.getMatchIdCount()+1;
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

    public void startToss() throws SQLException {
        toss = new Toss();
        toss.tossSetup(teamA,teamB);
        if (toss.getTossWinner() == teamA.getTeamId()) setTeamNameWhoWonToss(teamA.getTeamName());
        else setTeamNameWhoWonToss(teamB.getTeamName());

        Innings firstInnings = new Innings();
        firstInnings.setInningsId(DB.getInningsIdCount()+1);
        Innings secondInnings = new Innings();
        secondInnings.setInningsId(DB.getInningsIdCount()+2);
        if (toss.getWhoWillBat() ==  teamA.getTeamId()) {
            firstInnings.setBattingTeamId(teamA.getTeamId());
            firstInnings.setBowlingTeamId(teamB.getTeamId());
            secondInnings.setBattingTeamId(teamB.getTeamId());
            secondInnings.setBowlingTeamId(teamA.getTeamId());
        } else {
            firstInnings.setBattingTeamId(teamB.getTeamId());
            firstInnings.setBowlingTeamId(teamA.getTeamId());
            secondInnings.setBattingTeamId(teamA.getTeamId());
            secondInnings.setBowlingTeamId(teamB.getTeamId());
        }
        firstInnings.setMatchId(getMatchId());
        setFirstInnings(firstInnings);
        secondInnings.setMatchId(getMatchId());
        setSecondInnings(secondInnings);
    }

}
