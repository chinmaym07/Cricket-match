package src.com.cricketgame.models;

import java.util.HashMap;

public class PlayerStats {

    private int runsScored = 0, wicketsTaken = 0, ballsFaced = 0, runsGiven = 0, noOfNoBalls = 0, maidenOvers = 0, noOfWideBalls = 0, ballsBowled = 0;
    private double averageStrikeRate = 0.0, oversBowled = 0.0, economy = 0.0;
    private HashMap<Integer, Integer> eachRunFreq = new HashMap<Integer, Integer>();


    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public int getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public int getBallsFaced() {
        return ballsFaced;
    }

    public void setBallsFaced(int ballsFaced) {
        this.ballsFaced = ballsFaced;
    }

    public double getAverageStrikeRate() {
        return averageStrikeRate;
    }

    public void setAverageStrikeRate(double averageStrikeRate) {
        this.averageStrikeRate = averageStrikeRate;
    }

    public int getRunsGiven() {
        return runsGiven;
    }

    public void setRunsGiven(int runsGiven) {
        this.runsGiven = runsGiven;
    }

    public int getBallsBowled() {
        return ballsBowled;
    }

    public void setBallsBowled(int ballsBowled) {
        this.ballsBowled = ballsBowled;
    }

    public int getNoOfWideBalls() {
        return noOfWideBalls;
    }

    public void setNoOfWideBalls(int noOfWideBalls) {
        this.noOfWideBalls = noOfWideBalls;
    }

    public double getEconomy() {
        return economy;
    }

    public void setEconomy(double economy) {
        this.economy = economy;
    }

    public double getOversBowled() {
        return oversBowled;
    }

    public void setOversBowled(double oversBowled) {
        this.oversBowled = oversBowled;
    }

    public int getNoOfNoBalls() {
        return noOfNoBalls;
    }

    public void setNoOfNoBalls(int noOfNoBalls) {
        this.noOfNoBalls = noOfNoBalls;
    }

    public int getMaidenOvers() {
        return maidenOvers;
    }

    public void setMaidenOvers(int maidenOvers) {
        this.maidenOvers = maidenOvers;
    }

    public HashMap<Integer, Integer> getEachRunFreq() {
        return eachRunFreq;
    }

    public void setEachRunFreq(HashMap<Integer, Integer> eachRunFreq) {
        this.eachRunFreq = eachRunFreq;
    }

    public int getNumberOfRunsFreq(int runs) {
        if (eachRunFreq.containsKey(runs))
            return eachRunFreq.get(runs);
        else
            return 0;
    }
}