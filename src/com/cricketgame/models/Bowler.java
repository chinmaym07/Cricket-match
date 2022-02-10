package com.cricketgame.models;

public class Bowler extends Player {
    private static int c = 1;
    private String bowlerType;
    private int wicketsTaken, numberOf5wc, numberOf10wc, matchesPlayed, bowlerId;
    private double avergae;

    Bowler(String n, String phno, String em, String gn, String db, String ptype, String bType, int wickets, int number5wc, int number10wc, int mPlayed) {
        super(n, phno, em, gn, db, ptype);
        bowlerId = c;
        bowlerType = bType;
        numberOf5wc = number5wc;
        numberOf10wc = number10wc;
        wicketsTaken = wickets;
        matchesPlayed = mPlayed;
        avergae = wickets / matchesPlayed;
        c++;
    }

    public double getAvergae() {
        return avergae;
    }

    public String getBowlerType() {
        return bowlerType;
    }

    public void setBowlerType(String bowlerType) {
        this.bowlerType = bowlerType;
    }

    public int getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public int getNumberOf5wc() {
        return numberOf5wc;
    }

    public void setNumberOf5wc(int numberOf5wc) {
        this.numberOf5wc = numberOf5wc;
    }

    public int getNumberOf10wc() {
        return numberOf10wc;
    }

    public void setNumberOf10wc(int numberOf10wc) {
        this.numberOf10wc = numberOf10wc;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }


}
