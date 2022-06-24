package src.com.cricketgame.models;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class Innings {
    @ApiModelProperty(notes = "Batting Team Id",name="battingTeamId",required=true)
    private int battingTeamId;
    @ApiModelProperty(notes = "Bowling Team Id",name="bowlingTeamId",required=true)
    private int bowlingTeamId;
    @ApiModelProperty(notes = "Current Match Id",name="matchId",required=true)
    private int matchId;
    @ApiModelProperty(notes = "Current Innings Id",name="inningsId",required=true)
    private int inningsId;
    @ApiModelProperty(notes = "Innings Total Score",name="totalScore")
    private int totalScore = 0;
    @ApiModelProperty(notes = "Wickets Fallen in Innings",name="wicketsFallen")
    private int wicketsFallen = 0;
    @ApiModelProperty(notes = "Extra Runs in Innings",name="extraRuns")
    private int extraRuns = 0;
    @ApiModelProperty(notes = "Number of Wide Balls in Innings",name="noOfWideBalls")
    private int noOfWideBalls = 0;
    @ApiModelProperty(notes = "Number of No Balls in Innings",name="noOfNoBalls")
    private int noOfNoBalls = 0;
    @ApiModelProperty(notes = "Overs Batted in Innings",name="oversBatted")
    private double oversBatted = 0.0;
    @ApiModelProperty(notes = "Innings Status",name="inningsStatus")
    private String inningsStatus;
    @ApiModelProperty(notes = "Ball Summary",name="ballSummary")
    private List<List<String>> ballSummary = new ArrayList<List<String>>();
    @ApiModelProperty(notes = "Wickets Fallen History",name="wicketsHistory")
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
