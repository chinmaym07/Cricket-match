package src.com.cricketgame.DTO.ResponseDTOs;

import src.com.cricketgame.models.Innings;

public class InningsDTO {
    private int inningsId;
    private int totalScore;
    private int wicketsFallen;
    private int extraRuns;
    private int noOfNoBalls;
    private int noOfWideBalls;
    private double oversBatted;
    private String battingTeamName, bowlingTeamName, inningsStatus;

    public String getInningsStatus() {
        return inningsStatus;
    }

    public void setInningsStatus(String inningsStatus) {
        this.inningsStatus = inningsStatus;
    }

    public int getInningsId() {
        return inningsId;
    }

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getWicketsFallen() {
        return wicketsFallen;
    }

    public void setWicketsFallen(int wicketsFallen) {
        this.wicketsFallen = wicketsFallen;
    }

    public int getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(int extraRuns) {
        this.extraRuns = extraRuns;
    }

    public int getNoOfNoBalls() {
        return noOfNoBalls;
    }

    public void setNoOfNoBalls(int noOfNoBalls) {
        this.noOfNoBalls = noOfNoBalls;
    }

    public int getNoOfWideBalls() {
        return noOfWideBalls;
    }

    public void setNoOfWideBalls(int noOfWideBalls) {
        this.noOfWideBalls = noOfWideBalls;
    }

    public double getOversBatted() {
        return oversBatted;
    }

    public void setOversBatted(double oversBatted) {
        this.oversBatted = oversBatted;
    }

    public String getBattingTeamName() {
        return battingTeamName;
    }

    public void setBattingTeamName(String battingTeamName) {
        this.battingTeamName = battingTeamName;
    }

    public String getBowlingTeamName() {
        return bowlingTeamName;
    }

    public void setBowlingTeamName(String bowlingTeamName) {
        this.bowlingTeamName = bowlingTeamName;
    }

    public void convertInningsToInningsDTO(Innings innings, int teamAId, int teamBId, String teamAName, String teamBName) {
        if (innings.getBattingTeamId() == teamAId) {
            this.battingTeamName = teamAName;
            this.bowlingTeamName = teamBName;
        } else {
            this.battingTeamName = teamBName;
            this.bowlingTeamName = teamAName;
        }
        this.inningsId = innings.getInningsId();
        this.totalScore = innings.getTotalScore();
        this.wicketsFallen = innings.getWicketsFallen();
        this.extraRuns = innings.getExtraRuns();
        this.noOfNoBalls = innings.getNoOfNoBalls();
        this.noOfWideBalls = innings.getNoOfWideBalls();
        this.oversBatted = innings.getOversBatted();
        this.inningsStatus = innings.getInningsStatus();
    }
}
