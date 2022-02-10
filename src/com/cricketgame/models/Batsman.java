package com.cricketgame.models;

public class Batsman extends Player {
    private static int c = 1;
    private String batsmanType;
    private int batsmanId, runsScored, numberOf50s, numberOf100s, matchesPlayed;
    private double average;

    Batsman(String n, String phno, String em, String gn, String db, String ptype, String batsmType, int r, int number50s, int number100s, int mplay) {
        super(n, phno, em, gn, db, ptype);
        batsmanType = batsmType;
        batsmanId = c;
        runsScored = r;
        numberOf50s = number50s;
        numberOf100s = number100s;
        matchesPlayed = mplay;
        average = runsScored / matchesPlayed;
        c++;
    }

    public double getAverage() {
        return average;
    }

    public String getBatsmanType() {
        return batsmanType;
    }

    public void setBatsmanType(String batsmanType) {
        this.batsmanType = batsmanType;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public int getNumberOf50s() {
        return numberOf50s;
    }

    public void setNumberOf50s(int numberOf50s) {
        this.numberOf50s = numberOf50s;
    }

    public int getNumberOf100s() {
        return numberOf100s;
    }

    public void setNumberOf100s(int numberOf100s) {
        this.numberOf100s = numberOf100s;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }
}
