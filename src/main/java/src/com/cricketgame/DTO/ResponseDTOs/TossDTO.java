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
        if (toss.getTeamIdWhoWonTheToss() == teamAId)
            setTeamWhoWonTheToss(teamAName);
        else if (toss.getTeamIdWhoWonTheToss() == teamBId)
            setTeamWhoWonTheToss(teamBName);
        if (toss.getTeamIdWhoTookTheCall() == teamAId)
            setTeamWhoTookTheCall(teamAName);
        else if (toss.getTeamIdWhoWonTheToss() == teamBId)
            setTeamWhoTookTheCall(teamBName);

        if (toss.getTeamIdWhoWillBat() == teamAId) {
            setTeamWhoWillBat(teamAName);
            setTeamWhoWillBowl(teamBName);
        } else {
            setTeamWhoWillBat(teamBName);
            setTeamWhoWillBowl(teamAName);
        }
        setTossOutcome(toss.getTossOutcome());
        setCallersChoice(toss.getCallersChoice());

    }
}
