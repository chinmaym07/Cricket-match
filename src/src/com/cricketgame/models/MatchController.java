package src.com.cricketgame.models;

import java.util.ArrayList;

public class MatchController {
    private Team teamA = new Team();
    private Team teamB = new Team();
    private int matchOvers;
    private Player teamAplayer, teamBplayer;

    public void setMatchOvers(int matchOvers) {
        this.matchOvers = matchOvers;
    }

    public void createTwoTeams(String nameA, String nameB) {
        teamA.createTeam(); // creating 11 Player for Team A
        teamB.createTeam(); // creating 11 Player for Team B
        teamA.setTeamName(nameA); // setting the TeamA's Name
        teamB.setTeamName(nameB); // setting the TeamB's Name
        teamA.setFallOfWickets(0); // setting the current TeamA's fall of wickets to 0
        teamB.setFallOfWickets(0); // setting the current TeamA's fall of wickets to 0
        teamA.setScore(0); // setting the current TeamA's score to 0
        teamB.setScore(0); // setting the current TeamB's score to 0
    }

    private void printScorecard(ArrayList<Player> arr) {
        for (int i = 0; i < arr.size(); i++) {
            Player p = arr.get(i);
            if (p.getBallsFaced() > 0)
                System.out.println(p.getName() + " scored " + p.getRunsScored() + " in " + p.getBallsFaced() + " balls at a strike rate of "+ p.getAverageStrikeRate());
        }
    }
    // There are seven outcomes on each ball {"0", "1", "2", "3", "4", "5", "6", "W"}
    // therefore I am considering that if the Random function generates a number 7 then I consider as a wicket fall on that ball
    // else all the other ball constitutes runs which will be added to the final score.

    private Player startBowling(ArrayList<Player> teamPlayersArr, Team tm, boolean innings) {
        // This function will return the best player who scored most runs.
        int i = 0,j = 0,k = 0,flg = -1;
        Player teamPlayer=null;
        while (i < matchOvers - 1) {
            Player p = teamPlayersArr.get(k);
            for (j = 0; j < 6; j++) {
                if (teamPlayer != null && p.getRunsScored() > teamPlayer.getRunsScored())
                    teamPlayer = p;
                else if (teamPlayer == null)
                    teamPlayer = p;
                if(innings) // This will be used in 2nd team will bat
                {
                    if (teamB.getScore() > teamA.getScore()) {
                        flg = 1;
                        break;
                    }
                }
                if (tm.getFallOfWickets() < 10) {
                    int ballOutcome = (int) (Math.random() * 8);
                    if (ballOutcome == 7) {
                        tm.setFallOfWickets(tm.getFallOfWickets() + 1);
                        p.setBallsFaced(p.getBallsFaced() + 1);
                        p.setAverageStrikeRate((double)p.getRunsScored()*100/p.getBallsFaced());
                        k++;
                    } else {
                        tm.setScore(tm.getScore() + ballOutcome);
                        p.setRunsScored(p.getRunsScored() + ballOutcome);
                        p.setBallsFaced(p.getBallsFaced() + 1);
                    }
                } else
                    break;
            }
            if (flg == 1)
                break;
            i++;
        }
        double overs = i + (j) * 0.1;
        tm.setOversBatted(overs);
        return teamPlayer;
    }

    public void playMatch() {
        int i = 0, j = 0;
        int flg = -1;
        int k = 0; // will point to the players;
        ArrayList<Player> teamAPlayersArr = teamA.getPlayersArr(); // getting the Player arr of Team A
        ArrayList<Player> teamBPlayersArr = teamB.getPlayersArr(); // getting the Player arr of Team B
        System.out.println("*****************************************************************************************************************");
        System.out.println("Game has Started & " + teamA.getTeamName() + " have won the toss & choose to bat first !");
        System.out.println("*****************************************************************************************************************");
        teamAplayer = startBowling(teamAPlayersArr, teamA,false); // Team A will be batting first
        System.out.println("*****************************************************************************************************************");
        System.out.println(teamA.getTeamName() + " player's were able to set the target of " + teamA.getScore() + " runs at the cost of " + teamA.getFallOfWickets() + " wickets in " + teamA.getOversBatted() + " overs.\nLet's see whether " + teamB.getTeamName() + " will be able to achieve that target in " + matchOvers + " overs.");
        System.out.println("*****************************************************************************************************************");
        teamBplayer = startBowling(teamBPlayersArr, teamB,false); // Team B will be batting
        System.out.println("*****************************************************************************************************************");
        if (flg == 1) {
            System.out.println(teamB.getTeamName() + " player's were able to achieve the target & scored " + teamB.getScore() + " runs at the cost of " + teamB.getFallOfWickets() + " wickets in " + teamB.getOversBatted() + " overs.");
            System.out.println(teamBplayer.getName() + " is choosen to be the Man of the Match as he scored " + teamBplayer.getRunsScored() + " in " + teamBplayer.getBallsFaced() + " balls.");
        } else {
            System.out.println(teamA.getTeamName() + " won the match !!");
            System.out.println(teamB.getTeamName() + " player's were not able to achieve the target of " + (teamA.getScore() + 1) + " runs & were able to score only " + teamB.getScore() + " at the cost of " + teamB.getFallOfWickets() + " wickets in " + teamB.getOversBatted() + " overs.");
            System.out.println(teamAplayer.getName() + " is choosen to be the Man of the Match as he scored " + teamAplayer.getRunsScored() + " in " + teamAplayer.getBallsFaced() + " balls.");
        }
        System.out.println("*****************************************************************************************************************");
        System.out.println("*****************************************************************************************************************");
        System.out.println("Printing Both teams Scorecard ");
        System.out.println("*****************************************************************************************************************");
        System.out.println(teamA.getTeamName() + " Scorecard: ");
        printScorecard(teamAPlayersArr);
        System.out.println("*****************************************************************************************************************");
        System.out.println(teamB.getTeamName() + " Scorecard: ");
        printScorecard(teamBPlayersArr);
        System.out.println("*****************************************************************************************************************");
    }

}
