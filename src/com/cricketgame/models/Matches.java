package com.cricketgame.models;

public class Matches {
    private static int c = 1;
    private int matchId, matchOvers, currentOver;
    private String toss, stepChoseOnWiningToss, matchType, matchTiming, matchLocation, matchStatus;
    private Team teamA, teamB;

    public Matches(int matchOvers, int currentOver, String toss, String stepChoseOnWiningToss, String matchType, String matchTiming, String matchLocation, String matchStatus, Team teamA, Team teamB) {
        this.matchOvers = matchOvers;
        this.currentOver = currentOver;
        this.toss = toss;
        this.stepChoseOnWiningToss = stepChoseOnWiningToss;
        this.matchType = matchType;
        this.matchTiming = matchTiming;
        this.matchLocation = matchLocation;
        this.matchStatus = matchStatus;
        this.teamA = teamA;
        this.teamB = teamB;
        matchId = c;
        c++;

    }

    public int getMatchOvers() {
        return matchOvers;
    }

    public void setMatchOvers(int matchOvers) {
        this.matchOvers = matchOvers;
    }

    public int getCurrentOver() {
        return currentOver;
    }

    public void setCurrentOver(int currentOver) {
        this.currentOver = currentOver;
    }

    public String getToss() {
        return toss;
    }

    public void setToss(String toss) {
        this.toss = toss;
    }

    public String getStepChoseOnWiningToss() {
        return stepChoseOnWiningToss;
    }

    public void setStepChoseOnWiningToss(String stepChoseOnWiningToss) {
        this.stepChoseOnWiningToss = stepChoseOnWiningToss;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchTiming() {
        return matchTiming;
    }

    public void setMatchTiming(String matchTiming) {
        this.matchTiming = matchTiming;
    }

    public String getMatchLocation() {
        return matchLocation;
    }

    public void setMatchLocation(String matchLocation) {
        this.matchLocation = matchLocation;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public void startPlaying() {

    }

    public void checkWinner() {

    }

    public void endGame() {

    }
}
