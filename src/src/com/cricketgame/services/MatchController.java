package src.com.cricketgame.services;

import src.com.cricketgame.models.Match;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.models.Team;

import java.util.ArrayList;

public class MatchController {
    private Match match;

    private enum MatchWinner {
        TEAMA, TEAMB
    }

    public void createTwoTeams(String nameA, String nameB, int matchOvers) {
        match = new Match();
        Team teamB = match.getTeamB(); // get Team B
        Team teamA = match.getTeamA(); // get Team A
        match.setMatchOvers(matchOvers); // setting match overs
        teamA.createTeam(); // creating 11 Player for Team A
        teamB.createTeam(); // creating 11 Player for Team B
        teamA.setTeamName(nameA); // setting the TeamA's Name
        teamB.setTeamName(nameB); // setting the TeamB's Name
        teamA.setFallOfWickets(0); // setting the current TeamA's fall of wickets to 0
        teamB.setFallOfWickets(0); // setting the current TeamA's fall of wickets to 0
        teamA.setScore(0); // setting the current TeamA's score to 0
        teamB.setScore(0); // setting the current TeamB's score to 0
    }

    private void printScorecard(ArrayList<Player> playerArr) {
        for (Player currentPlayer : playerArr) {
            // System.out.println(currentPlayer.getName() + " played "+currentPlayer.getBallsFaced() +" balls.");
            if (currentPlayer.getBallsFaced() > 0)
                System.out.printf(currentPlayer.getName() + " scored " + currentPlayer.getRunsScored() + " in " + currentPlayer.getBallsFaced() + " balls at a strike rate of %4.1f\n", currentPlayer.getAverageStrikeRate());
        }
    }
    // There are seven outcomes on each ball {"0", "1", "2", "3", "4", "5", "6", "W"}
    // therefore I am considering that if the Random function generates a number 7 then I consider as a wicket fall on that ball
    // else all the other ball constitutes runs which will be added to the final score.

    private Player startBowling(ArrayList<Player> teamPlayersArr, Team team, boolean secondInnings) {
        // This function will return the best player who scored most runs.
        int currentOver = 0, currentBall = 0, indOfPlayerOnStrike = 0;
        boolean isTeamBScoreGreaterThanTeamA = false;
        Player teamPlayerWhoScoredMostRuns = null;

        Team teamB = match.getTeamB(); // get Team B
        Team teamA = match.getTeamA(); // get Team A
        Player currentPlayer = teamPlayersArr.get(indOfPlayerOnStrike);
        while (currentOver <= match.getMatchOvers() - 1 && team.getFallOfWickets() < 11) {
            for (currentBall = 0; currentBall < 6; currentBall++) {
                // we check for the best player in a team i.e, who scored more runs
                if (teamPlayerWhoScoredMostRuns != null && currentPlayer.getRunsScored() > teamPlayerWhoScoredMostRuns.getRunsScored())
                    teamPlayerWhoScoredMostRuns = currentPlayer;
                else if (teamPlayerWhoScoredMostRuns == null) teamPlayerWhoScoredMostRuns = currentPlayer;

                if (secondInnings) // This will be used in 2nd team will bat
                {
                    if (teamB.getScore() > teamA.getScore()) {
                        isTeamBScoreGreaterThanTeamA = true;
                        break;
                    }
                }
                if (team.getFallOfWickets() < 11) {
                    int ballOutcome = (int) (Math.random() * 8);
                    currentPlayer.setBallsFaced(currentPlayer.getBallsFaced() + 1); // increasing the ball count

                    // if outcome is 7 it means the batsman is out then we call the next player to bat
                    // else we will add the run scored on that ball to the player's scorecard as well as to the team's scorecard
                    if (ballOutcome == 7) {
                        team.setFallOfWickets(team.getFallOfWickets() + 1);
                        currentPlayer.setAverageStrikeRate(((double) currentPlayer.getRunsScored() * 100.0) / currentPlayer.getBallsFaced()); // Calculating the current player's strikerate after each ball
                        indOfPlayerOnStrike++;
                        if (team.getFallOfWickets() < 11) currentPlayer = teamPlayersArr.get(indOfPlayerOnStrike);
                    } else {
                        team.setScore(team.getScore() + ballOutcome);
                        currentPlayer.setRunsScored(currentPlayer.getRunsScored() + ballOutcome);
                    }
                    currentPlayer.setAverageStrikeRate(((double) currentPlayer.getRunsScored() * 100.0) / currentPlayer.getBallsFaced()); // Calculating the current player's strikerate after each ball
                } else break;
            }
            if (secondInnings && isTeamBScoreGreaterThanTeamA) break;
            currentOver++;
        }
        // If teamB score is greater than team A then teamB is winner
        if (secondInnings) {
            if (isTeamBScoreGreaterThanTeamA) match.setMatchWinner(MatchWinner.TEAMB);
            else
                // team A is winner.
                match.setMatchWinner(MatchWinner.TEAMA);
        }

        double overs;
        if (currentBall < 6) overs = currentOver + (currentBall * 0.1) - 1;
        else overs = currentOver;
        team.setOversBatted(overs);
        return teamPlayerWhoScoredMostRuns;
    }

    public void playMatch() {
        Team teamB = match.getTeamB(); // get Team B
        Team teamA = match.getTeamA(); // get Team A
        Player teamAplayer; // Reference variable to Team B best player
        Player teamBplayer; // Reference variable to Team A best player

        ArrayList<Player> teamAPlayersArr = teamA.getPlayersArr(); // getting the Player arr of Team A
        ArrayList<Player> teamBPlayersArr = teamB.getPlayersArr(); // getting the Player arr of Team B
        System.out.println("*****************************************************************************************************************");
        System.out.println("Game has Started & " + teamA.getTeamName() + " have won the toss & choose to bat first !");
        System.out.println("*****************************************************************************************************************");
        teamAplayer = startBowling(teamAPlayersArr, teamA, false); // Team A will be batting first
        match.setTeamABestPlayer(teamAplayer);
        System.out.println("*****************************************************************************************************************");
        System.out.println(teamA.getTeamName() + " player's were able to set the target of " + teamA.getScore() + " runs at the cost of " + teamA.getFallOfWickets() + " wickets in " + teamA.getOversBatted() + " overs.\nLet's see whether " + teamB.getTeamName() + " will be able to achieve that target in " + match.getMatchOvers() + " overs.");
        System.out.println("*****************************************************************************************************************");
        teamBplayer = startBowling(teamBPlayersArr, teamB, true); // Team B will be batting
        match.setTeamBBestPlayer(teamBplayer);
        System.out.println("*****************************************************************************************************************");
        if (match.getMatchWinner() == MatchWinner.TEAMB) { // Checking the Match Winner
            System.out.printf(teamB.getTeamName() + " player's were able to achieve the target & scored " + teamB.getScore() + " runs at the cost of " + teamB.getFallOfWickets() + " wickets in %.1f\n overs.", teamB.getOversBatted());
            System.out.println(teamBplayer.getName() + " is choosen to be the Man of the Match as he scored " + teamBplayer.getRunsScored() + " in " + teamBplayer.getBallsFaced() + " balls.");
        } else {
            System.out.println(teamA.getTeamName() + " won the match !!");
            System.out.printf(teamB.getTeamName() + " player's were not able to achieve the target of " + (teamA.getScore() + 1) + " runs & were able to score only " + teamB.getScore() + " at the cost of " + teamB.getFallOfWickets() + " wickets in %.1f overs.\n", teamB.getOversBatted());
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
