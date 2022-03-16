package src.com.cricketgame.models;

public class CurrentPlay {
    private int matchId;
    private int inningsId;

    private int currentBatsmanId;
    private int currentBowlerId;
    private int currentBall;
    private int previousBowlerId;

    private double currentOver;

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
}
