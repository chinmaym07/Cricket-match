package src.com.cricketgame.models;

import java.util.ArrayList;

public class Innings {
    private int battingTeamId, bowlingTeamId, matchId, inningsId;
    private ArrayList<ArrayList<String>> ballSummary = new ArrayList<ArrayList<String>>();
    private int totalScore = 0, fallOfWickets = 0, extraRuns = 0, noOfWideBalls = 0, noOfNoBalls = 0;
    private double oversBatted = 0.0;
    private ArrayList<WicketsHistory> wicketsFallenHistory = new ArrayList<WicketsHistory>();

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public int getInningsId() {
        return inningsId;
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int score) {
        this.totalScore = score;
    }

    public int getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(int extraRuns) {
        this.extraRuns = extraRuns;
    }


    public int getFallOfWickets() {
        return fallOfWickets;
    }

    public void setFallOfWickets(int fallOfWickets) {
        this.fallOfWickets = fallOfWickets;
    }

    public ArrayList<WicketsHistory> getWicketsFallenHistory() {
        return wicketsFallenHistory;
    }

    public void setWicketsFallenHistory(ArrayList<WicketsHistory> wicketsFallenHistory) {
        this.wicketsFallenHistory = wicketsFallenHistory;
    }

    public ArrayList<ArrayList<String>> getBallSummary() {
        return ballSummary;
    }

    public void setBallSummary(ArrayList<ArrayList<String>> ballSummary) {
        this.ballSummary = ballSummary;
    }

    public int getBattingTeamId() {
        return battingTeamId;
    }

    public void setBattingTeamId(int battingTeamId) {
        this.battingTeamId = battingTeamId;
    }

    public int getBowlingTeamId() {
        return bowlingTeamId;
    }

    public void setBowlingTeamId(int bowlingTeamId) {
        this.bowlingTeamId = bowlingTeamId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public double getOversBatted() {
        return oversBatted;
    }

    public void setOversBatted(double oversBatted) {
        this.oversBatted = oversBatted;
    }
}
