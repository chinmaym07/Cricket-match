package src.com.cricketgame.models;

import java.util.ArrayList;
import java.util.List;

public class Innings {
    private int battingTeamId;
    private int bowlingTeamId;
    private int matchId;
    private int inningsId;
    private int totalScore = 0;
    private int wicketsFallen = 0;
    private int extraRuns = 0;
    private int noOfWideBalls = 0;
    private int noOfNoBalls = 0;
    private double oversBatted = 0.0;
    private String inningsStatus;
    private List<List<String>> ballSummary = new ArrayList<List<String>>();
    private List<WicketsHistory> wicketsFallenHistory = new ArrayList<WicketsHistory>();

    public int getWicketsFallen() {
        return wicketsFallen;
    }

    public void setWicketsFallen(int wicketsFallen) {
        this.wicketsFallen = wicketsFallen;
    }

    public String getInningsStatus() {
        return inningsStatus;
    }

    public void setInningsStatus(String inningsStatus) {
        this.inningsStatus = inningsStatus;
    }

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


    public List<WicketsHistory> getWicketsFallenHistory() {
        return wicketsFallenHistory;
    }

    public void setWicketsFallenHistory(List<WicketsHistory> wicketsFallenHistory) {
        this.wicketsFallenHistory = wicketsFallenHistory;
    }

    public List<List<String>> getBallSummary() {
        return ballSummary;
    }

    public void setBallSummary(List<List<String>> ballSummary) {
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
