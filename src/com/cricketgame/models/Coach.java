package com.cricketgame.models;

public class Coach {
    private static int c = 1;
    private String coachName, coachType, coachTeam;
    private int coachId;

    Coach(String cName, String cType, String cTeam) {
        coachId = c;
        coachName = cName;
        coachType = cType;
        coachTeam = cTeam;
        c++;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachType() {
        return coachType;
    }

    public void setCoachType(String coachType) {
        this.coachType = coachType;
    }

    public String getCoachTeam() {
        return coachTeam;
    }

    public void setCoachTeam(String coachTeam) {
        this.coachTeam = coachTeam;
    }
}
