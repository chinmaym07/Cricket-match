package src.com.cricketgame.DTO.ResponseDTOs;

public class CurrentPlayDTO {
    private String currentBatsmanName;
    private String currentBowlerName;
    private String previousBowlerName;
    private int currentBall;
    private int matchId;
    private int inningsId;
    private double currentOver;

    public String getCurrentBatsmanName() {
        return currentBatsmanName;
    }

    public void setCurrentBatsmanName(String currentBatsmanName) {
        this.currentBatsmanName = currentBatsmanName;
    }

    public String getCurrentBowlerName() {
        return currentBowlerName;
    }

    public void setCurrentBowlerName(String currentBowlerName) {
        this.currentBowlerName = currentBowlerName;
    }

    public String getPreviousBowlerName() {
        return previousBowlerName;
    }

    public void setPreviousBowlerName(String previousBowlerName) {
        this.previousBowlerName = previousBowlerName;
    }

    public int getCurrentBall() {
        return currentBall;
    }

    public void setCurrentBall(int currentBall) {
        this.currentBall = currentBall;
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
