package src.com.cricketgame.models;

import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;

import java.util.HashMap;


public class PlayerStats {

    private int playerId;
    private int matchId;
    private int runsScored = 0;
    private int wicketsTaken = 0;
    private int ballsFaced = 0;
    private int runsGiven = 0;
    private int noOfNoBalls = 0;
    private int maidenOvers = 0;
    private int noOfWideBalls = 0;
    private int ballsBowled = 0;

    private double averageStrikeRate = 0.0;
    private double oversBowled = 0.0;
    private double economy = 0.0;
    private EachRunfreqDTO eachRunFreq;
    private String playingStatus;

    public void setEachRunFreq(EachRunfreqDTO eachRunFreq) {
        this.eachRunFreq = eachRunFreq;
    }

    public EachRunfreqDTO getEachRunFreq() {
        return eachRunFreq;
    }

    public String getPlayingStatus() {
        return playingStatus;
    }

    public void setPlayingStatus(String playingStatus) {
        this.playingStatus = playingStatus;
    }

    public int getMatchId() {
        return matchId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

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

    public int getNumberOfRunsFreq(int run) {
        HashMap<Integer, Integer> runsFreq = eachRunFreq.getEachRunFreq();
        return runsFreq.get(run);
    }
}
