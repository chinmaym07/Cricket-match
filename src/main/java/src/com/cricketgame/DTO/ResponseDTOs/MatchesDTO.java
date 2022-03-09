package src.com.cricketgame.DTO.ResponseDTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;

public class MatchesDTO {

    private int teamAId,teamBId, matchId;
    private String teamAName, teamBName, matchResult;
    private int matchOvers;

    private String teamWhoWonToss;
    private int teamIdWhoWonTheMatch;
    private String matchStatus;
    @JsonIgnore
    public int getTeamIdWhoWonTheMatch() {
        return teamIdWhoWonTheMatch;
    }
    @JsonIgnore
    public void setTeamWhoWonToss(String teamWhoWonToss) {
        this.teamWhoWonToss = teamWhoWonToss;
    }
    @JsonIgnore
    public String getTeamWhoWonToss() {
        return teamWhoWonToss;
    }
    @JsonIgnore
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
