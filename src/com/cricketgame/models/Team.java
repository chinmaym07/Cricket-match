package com.cricketgame.models;

import java.util.ArrayList;

public class Team {
    private ArrayList<Coach> coachesArr = new ArrayList<Coach>();
    private ArrayList<Bowler> bowlersArr = new ArrayList<Bowler>();
    private ArrayList<Batsman> batsmanArr = new ArrayList<Batsman>();
    private int points;

    public ArrayList<Coach> getCoachesArr() {
        return coachesArr;
    }

    public void setCoachesArr(ArrayList<Coach> coachesArr) {
        this.coachesArr = coachesArr;
    }

    public ArrayList<Bowler> getBowlersArr() {
        return bowlersArr;
    }

    public void setBowlersArr(ArrayList<Bowler> bowlersArr) {
        this.bowlersArr = bowlersArr;
    }

    public ArrayList<Batsman> getBatsmanArr() {
        return batsmanArr;
    }

    public void setBatsmanArr(ArrayList<Batsman> batsmanArr) {
        this.batsmanArr = batsmanArr;
    }

    public void setPoints(int p) {
        points = p;
    }

    public int getPoints() {
        return points;
    }

    public void addBowler(Bowler b) {
        bowlersArr.add(b);
    }

    public void addBatsman(Batsman b) {
        batsmanArr.add(b);
    }

    public String removeBatsman(Batsman b) {
        if (batsmanArr.contains(b)) {
            batsmanArr.remove(b);
            return "Batsman removed from the team";
        }
        return "Batsman not found!!";
    }

    public String removeBowler(Bowler b) {
        if (bowlersArr.contains(b)) {
            bowlersArr.remove(b);
            return "Bowler removed from the team";
        }
        return "Bowler not found!!";
    }

    boolean isTeamComplete() {
        return (batsmanArr.size() + bowlersArr.size() >= 11);
    }

}
