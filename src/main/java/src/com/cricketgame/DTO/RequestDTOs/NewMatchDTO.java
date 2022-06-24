package src.com.cricketgame.DTO.RequestDTOs;

public class NewMatchDTO {
    private String teamAName;
    private String teamBName;
    private int matchOvers;

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public void setMatchOvers(int matchOvers) {
        this.matchOvers = matchOvers;
    }

    public int getMatchOvers() {
        return matchOvers;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }
}
