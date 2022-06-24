package src.com.cricketgame.DTO.ResponseDTOs;

public class PlayerStatsDTO {
    private int playerId;
    private int matchId;
    private int runsScored = 0;
    private int wicketsTaken = 0;
    private int ballsFaced = 0;
    private int runsGiven = 0;
    private int noOfNoBalls = 0;
    private int maidenOvers = 0;
    private int noOfWideBalls = 0;
    private int ballsBowled = 0;

    private String name;
    private String role;
    private String teamName;
    private String playingStatus = "NOT_OUT";
    private double averageStrikeRate = 0.0;
    private double oversBowled = 0.0;
    private double economy = 0.0;
    private EachRunfreqDTO eachRunFreq;

    public String getPlayingStatus() {
        return playingStatus;
    }

    public void setPlayingStatus(String playingStatus) {
        this.playingStatus = playingStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public int getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public int getBallsFaced() {
        return ballsFaced;
    }

    public void setBallsFaced(int ballsFaced) {
        this.ballsFaced = ballsFaced;
    }

    public int getRunsGiven() {
        return runsGiven;
    }

    public void setRunsGiven(int runsGiven) {
        this.runsGiven = runsGiven;
    }

    public int getNoOfNoBalls() {
        return noOfNoBalls;
    }

    public void setNoOfNoBalls(int noOfNoBalls) {
        this.noOfNoBalls = noOfNoBalls;
    }

    public int getMaidenOvers() {
        return maidenOvers;
    }

    public void setMaidenOvers(int maidenOvers) {
        this.maidenOvers = maidenOvers;
    }

    public int getNoOfWideBalls() {
        return noOfWideBalls;
    }

    public void setNoOfWideBalls(int noOfWideBalls) {
        this.noOfWideBalls = noOfWideBalls;
    }

    public int getBallsBowled() {
        return ballsBowled;
    }

    public void setBallsBowled(int ballsBowled) {
        this.ballsBowled = ballsBowled;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getAverageStrikeRate() {
        return averageStrikeRate;
    }

    public void setAverageStrikeRate(double averageStrikeRate) {
        this.averageStrikeRate = averageStrikeRate;
    }

    public double getOversBowled() {
        return oversBowled;
    }

    public void setOversBowled(double oversBowled) {
        this.oversBowled = oversBowled;
    }

    public double getEconomy() {
        return economy;
    }

    public void setEconomy(double economy) {
        this.economy = economy;
    }

    public EachRunfreqDTO getEachRunFreq() {
        return eachRunFreq;
    }

    public void setEachRunFreq(EachRunfreqDTO eachRunFreq) {
        this.eachRunFreq = eachRunFreq;
    }
}
