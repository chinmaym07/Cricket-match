package src.com.cricketgame.DTO.ResponseDTOs;

import src.com.cricketgame.models.Toss;

public class TossDTO {
    private String teamWhoWonTheToss,teamWhoTookTheCall, teamWhoWillBat, teamWhoWillBowl, tossOutcome, callersChoice;

    public String getTeamWhoWonTheToss() {
        return teamWhoWonTheToss;
    }

    public void setTeamWhoWonTheToss(String teamWhoWonTheToss) {
        this.teamWhoWonTheToss = teamWhoWonTheToss;
    }

    public String getTeamWhoTookTheCall() {
        return teamWhoTookTheCall;
    }

    public void setTeamWhoTookTheCall(String teamWhoTookTheCall) {
        this.teamWhoTookTheCall = teamWhoTookTheCall;
    }

    public String getTeamWhoWillBat() {
        return teamWhoWillBat;
    }

    public void setTeamWhoWillBat(String teamWhoWillBat) {
        this.teamWhoWillBat = teamWhoWillBat;
    }

    public String getTeamWhoWillBowl() {
        return teamWhoWillBowl;
    }

    public void setTeamWhoWillBowl(String teamWhoWillBowl) {
        this.teamWhoWillBowl = teamWhoWillBowl;
    }

    public String getTossOutcome() {
        return tossOutcome;
    }

    public void setTossOutcome(String tossOutcome) {
        this.tossOutcome = tossOutcome;
    }

    public String getCallersChoice() {
        return callersChoice;
    }

    public void setCallersChoice(String callersChoice) {
        this.callersChoice = callersChoice;
    }

    public void convertTossToTossDTO(Toss toss, int teamAId,int teamBId, String teamAName,String teamBName) {
        if (toss.getTossWinner() == teamAId)
            this.setTeamWhoWonTheToss(teamAName);
        else
            this.setTeamWhoWonTheToss(teamBName);
        if (toss.getWhoTookTheCall() == teamAId)
            this.setTeamWhoTookTheCall(teamAName);
        else
            this.setTeamWhoTookTheCall(teamBName);
        if (toss.getWhoWillBat() == teamAId) {
            this.setTeamWhoWillBat(teamAName);
            this.setTeamWhoWillBowl(teamBName);
        } else {
            this.setTeamWhoWillBat(teamBName);
            this.setTeamWhoWillBowl(teamAName);
        }
        this.setTossOutcome(toss.getTossOutcome());
        this.setCallersChoice(toss.getCallersChoice());

    }
}
