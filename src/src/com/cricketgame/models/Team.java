package src.com.cricketgame.models;

import java.util.ArrayList;

public class Team {
    private static int c = 1, ch = 65;
    private int teamId,score, fallOfWickets;
    private double oversBatted;
    private String teamName;
    private ArrayList<Player> playersArr = new ArrayList<Player>();

        public double getOversBatted() {
        return oversBatted;
    }

    public void setOversBatted(double oversBatted) {
        this.oversBatted = oversBatted;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFallOfWickets() {
        return fallOfWickets;
    }

    public void setFallOfWickets(int fallOfWickets) {
        this.fallOfWickets = fallOfWickets;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<Player> getPlayersArr() {
        return playersArr;
    }

    public void setPlayersArr(ArrayList<Player> playersArr) {
        this.playersArr = playersArr;
    }
    public void createTeam() {
        teamId = c;
        c++;
        if(ch == 91)
            ch = 97;
        else if(ch == 122)
            ch = 65;

        for(int i = 0;i < 11;i++) {
            String n = "" + ((char) ch);
            ch++;
            Player p;
            if(i < 6)
                p = new Player(n,"Batsman");
            else
                p = new Player(n,"Bowler");
            playersArr.add(p);
        }
    }
}
