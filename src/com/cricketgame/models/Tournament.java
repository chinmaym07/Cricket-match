package com.cricketgame.models;

import java.util.ArrayList;

public class Tournament {
    private ArrayList<Team> teamsArr = new ArrayList<Team>();
    private ArrayList<Matches> matchesArr = new ArrayList<Matches>();

    public Tournament(ArrayList<Team> teamsArr, ArrayList<Matches> matchesArr) {
        this.teamsArr = teamsArr;
        this.matchesArr = matchesArr;
    }

    public ArrayList<Team> getTeamsArr() {
        return teamsArr;
    }

    public void setTeamsArr(ArrayList<Team> teamsArr) {
        this.teamsArr = teamsArr;
    }

    public ArrayList<Matches> getMatchesArr() {
        return matchesArr;
    }

    public void setMatchesArr(ArrayList<Matches> matchesArr) {
        this.matchesArr = matchesArr;
    }
}
