package src.com.cricketgame.models;

public class Match {
    private static int matchCount = 1;
    private int matchId = 0;
    private Team teamA = new Team();
    private Team teamB = new Team();
    private Toss toss;
    private int matchOvers;
    private Player teamABestPlayer, teamBBestPlayer;
    private Enum matchWinner;
    private Innings firstInnings, secondInnings;
    private String teamNameWhoWonToss;

    public Toss getToss() {
        return toss;
    }

    public void setTeamNameWhoWonToss(String teamNameWhoWonToss) {
        this.teamNameWhoWonToss = teamNameWhoWonToss;
    }

    public String getTeamNameWhoWonToss() {
        return teamNameWhoWonToss;
    }

    public Match() {
        matchId = matchCount++;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setFirstInnings(Innings firstInnings) {
        this.firstInnings = firstInnings;
    }

    public void setSecondInnings(Innings secondInnings) {
        this.secondInnings = secondInnings;
    }

    public Innings getFirstInnings() {
        return firstInnings;
    }

    public Innings getSecondInnings() {
        return secondInnings;
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

    public void startToss() {
        toss = new Toss();
        toss.tossSetup();
        if (toss.getTossWinner() == 0) setTeamNameWhoWonToss(teamA.getTeamName());
        else setTeamNameWhoWonToss(teamB.getTeamName());

        Innings firstInnings = new Innings();
        Innings secondInnings = new Innings();
        if (toss.getWhoWillBat() == 0) {
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
