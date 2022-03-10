package src.com.cricketgame.DTO.ResponseDTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import src.com.cricketgame.models.Innings;

public class MatchesDTO {

    private int teamAId, teamBId, matchId;
    private String teamAName, teamBName, matchResult;
    private int matchOvers;
    private String teamWhoWonToss;

    private int teamIdWhoWonTheMatch;
    @JsonIgnore
    private int teamIdWhoWonTheToss;
    private String matchStatus;

    public int getTeamIdWhoWonTheToss() {
        return teamIdWhoWonTheToss;
    }

    public void setTeamIdWhoWonTheToss(int teamIdWhoWonTheToss) {
        this.teamIdWhoWonTheToss = teamIdWhoWonTheToss;
    }

    @JsonIgnore
    private Innings firstInnings, secondInnings;

    public Innings getFirstInnings() {
        return firstInnings;
    }

    public Innings getSecondInnings() {
        return secondInnings;
    }

    public void setFirstInnings(Innings firstInnings) {
        this.firstInnings = firstInnings;
    }

    public void setSecondInnings(Innings secondInnings) {
        this.secondInnings = secondInnings;
    }

    public int getTeamIdWhoWonTheMatch() {
        return teamIdWhoWonTheMatch;
    }

    public void setTeamWhoWonToss(String teamWhoWonToss) {
        this.teamWhoWonToss = teamWhoWonToss;
    }

    public String getTeamWhoWonToss() {
        return teamWhoWonToss;
    }

    public void setTeamIdWhoWonTheMatch(int teamIdWhoWonTheMatch) {
        this.teamIdWhoWonTheMatch = teamIdWhoWonTheMatch;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public int getTeamAId() {
        return teamAId;
    }

    public void setTeamAId(int teamAId) {
        this.teamAId = teamAId;
    }

    public int getTeamBId() {
        return teamBId;
    }

    public void setTeamBId(int teamBId) {
        this.teamBId = teamBId;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public int getMatchOvers() {
        return matchOvers;
    }

    public void setMatchOvers(int matchOvers) {
        this.matchOvers = matchOvers;
    }


}
