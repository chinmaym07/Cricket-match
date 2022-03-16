package src.com.cricketgame.utils;

import java.util.List;

public class BallSummary {
    private int matchId;
    private int ballId;
    private int inningsId;
    private int currentOver;
    private String outcomeOnBall;

    public int getMatchId() {
        return matchId;
    }

    public int getInningsId() {
        return inningsId;
    }

    public int getBallId() {
        return ballId;
    }

    public String getOutcomeOnBall() {
        return outcomeOnBall;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public void setBallId(int ballId) {
        this.ballId = ballId;
    }

    public void setOutcomeOnBall(String outcomeOnBall) {
        this.outcomeOnBall = outcomeOnBall;
    }

    public int getCurrentOver() {
        return currentOver;
    }

    public void setCurrentOver(int currentOver) {
        this.currentOver = currentOver;
    }

    public static void printBallSummary(List<List<String>> ballSummary) {
        System.out.println("Current Over \t\tOutcome on Balls");
        for (int currentOver = 0; currentOver < ballSummary.size(); currentOver++) {
            System.out.print(currentOver + "\t\t\t\t\t");
            for (String outcome : ballSummary.get(currentOver)) {
                System.out.print(outcome + " ");
            }
            System.out.println();
        }
    }
}
