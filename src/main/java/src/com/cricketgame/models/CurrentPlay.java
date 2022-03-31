package src.com.cricketgame.models;

import io.swagger.annotations.ApiModelProperty;

public class CurrentPlay {
    @ApiModelProperty(notes = "Current match ID", name = "matchId", required = true)
    private int matchId;
    @ApiModelProperty(notes = "Current innings ID", name = "inningsId", required = true)
    private int inningsId;
    @ApiModelProperty(notes = "Current Batsman ID", name = "currentBatsmanId", required = true)
    private int currentBatsmanId;
    @ApiModelProperty(notes = "Current Bowler ID", name = "currentBowlerId", required = true)
    private int currentBowlerId;
    @ApiModelProperty(notes = "Current Ball", name = "Current Bowl", required = true)
    private int currentBall;
    @ApiModelProperty(notes = "Previous Bowler ID", name = "previousBowlerId", required = true)
    private int previousBowlerId;
    @ApiModelProperty(notes = "Current Over", name = "currentOver", required = true)
    private double currentOver;
    @ApiModelProperty(notes = "Runs in Current Over", name = "runsInCurrentOver", required = true)
    private int runsInCurrentOver = 0;

    public int getCurrentBatsmanId() {
        return currentBatsmanId;
    }

    public void setCurrentBatsmanId(int currentBatsmanId) {
        this.currentBatsmanId = currentBatsmanId;
    }

    public int getCurrentBowlerId() {
        return currentBowlerId;
    }

    public void setCurrentBowlerId(int currentBowlerId) {
        this.currentBowlerId = currentBowlerId;
    }

    public int getCurrentBall() {
        return currentBall;
    }

    public void setCurrentBall(int currentBall) {
        this.currentBall = currentBall;
    }

    public int getPreviousBowlerId() {
        return previousBowlerId;
    }

    public void setPreviousBowlerId(int previousBowlerId) {
        this.previousBowlerId = previousBowlerId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getInningsId() {
        return inningsId;
    }

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public double getCurrentOver() {
        return currentOver;
    }

    public void setCurrentOver(double currentOver) {
        this.currentOver = currentOver;
    }

    public int getRunsInCurrentOver() {
        return runsInCurrentOver;
    }

    public void setRunsInCurrentOver(int runsInCurrentOver) {
        this.runsInCurrentOver = runsInCurrentOver;
    }
}
