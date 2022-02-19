package src.com.cricketgame.services;

import src.com.cricketgame.models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchController {
    private Match match;
    private Toss toss = new Toss();

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
        /* teamA.setFallOfWickets(0); // setting the current TeamA's fall of wickets to 0
        teamB.setFallOfWickets(0); // setting the current TeamA's fall of wickets to 0
        teamA.setScore(0); // setting the current TeamA's score to 0
        teamB.setScore(0); // setting the current TeamB's score to 0 */

        toss.tossSetup();
        if (toss.getTossWinner() == 0) match.setTeamNameWhoWonToss(teamA.getTeamName());
        else match.setTeamNameWhoWonToss(teamB.getTeamName());

        Innings firstInnings = new Innings();
        Innings secondInnings = new Innings();
        if (toss.getWhoWillBat() == 0) {
            firstInnings.setBattingTeamId(teamA.getTeamId());
            firstInnings.setBowlingTeamId(teamB.getTeamId());
            secondInnings.setBattingTeamId(teamB.getTeamId());
            secondInnings.setBowlingTeamId(teamA.getTeamId());
        } else {
            firstInnings.setBattingTeamId(teamB.getTeamId());
            firstInnings.setBowlingTeamId(teamA.getTeamId());
            secondInnings.setBattingTeamId(teamA.getTeamId());
            secondInnings.setBowlingTeamId(teamB.getTeamId());
        }
        firstInnings.setMatchId(match.getMatchId());
        match.setFirstInnings(firstInnings);
        secondInnings.setMatchId(match.getMatchId());
        match.setSecondInnings(secondInnings);
    }

    private void printBattingScorecard(ArrayList<Player> playerArr, Innings innings) {
        int playersDidNotBat = 0;
        System.out.println("Batter \t\t\t\t Runs Score \t\t\t\t Ball's Faced \t\t\t\t 4's \t\t\t\t 6's \t\t\t\t StrikeRate\n");
        for (Player currentPlayer : playerArr) {
            // System.out.println(currentPlayer.getName() + " played "+currentPlayer.getBallsFaced() +" balls.");
            if (currentPlayer.getBallsFaced() > 0)
                // System.out.printf(currentPlayer.getName() + " scored " + currentPlayer.getRunsScored() + " in " + currentPlayer.getBallsFaced() + " balls at a strike rate of %4.1f\n", currentPlayer.getAverageStrikeRate());
                System.out.printf(currentPlayer.getName() + "\t\t\t\t\t\t" + currentPlayer.getRunsScored() + "\t\t\t\t\t\t\t" + currentPlayer.getBallsFaced() + "\t\t\t\t\t\t  " + currentPlayer.getNumberOfRunsFreq(4) + "\t\t\t\t\t  " + currentPlayer.getNumberOfRunsFreq(6) + "\t\t\t\t\t   %4.1f\n", currentPlayer.getAverageStrikeRate());
            else playersDidNotBat++;
        }
        System.out.println("Extras \t\t\t\t\t" + innings.getExtraRuns() + "(nb " + innings.getNoOfNoBalls() + ", wb " + innings.getNoOfWideBalls() + ")");
        System.out.println("Total \t\t\t\t\t" + innings.getTotalScore() + "(" + innings.getFallOfWickets() + " wckts, " + innings.getOversBatted() + " Ov.)");
        if (playersDidNotBat > 0) {
            System.out.println("Did not Bat");
            int count = 0; // checking the count as this will be used for printing comma
            for (Player currentPlayer : playerArr) {
                if (currentPlayer.getBallsFaced() == 0) {
                    System.out.print(currentPlayer.getName());
                    count++;
                    if (count != playersDidNotBat) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println();
        }
    }

    private void printWicketsHitory(ArrayList<WicketsHistory> wicketsHistory) {
        int count = 0;
        System.out.println("Fall of Wickets");
        for (WicketsHistory wickets : wicketsHistory) {
            System.out.printf(wickets.getRunScored() + "-" + wickets.getWicketsDown() + " (" + wickets.getBatsmanName() + ", %4.1f)", wickets.getWickerFallenInOver());
            count++;
            if (count != wicketsHistory.size()) System.out.printf(", ");
        }
        System.out.println("\n");
    }

    private void printBowlingStats(ArrayList<Player> players) {
        System.out.println("Bowler \t\t Over's Bowled \t\t Maiden Overs \t\t Runs Given \t\t  Wicket's Taken \t\t NoBall's \t\t WideBall's \t\t Economy\n");
        for (Player currentPlayer : players) {
            // System.out.println(currentPlayer.getName() + " played "+currentPlayer.getBallsBowled() +" balls.");

            if (currentPlayer.getBallsBowled() > 0)
                System.out.printf(currentPlayer.getName() + "\t\t\t\t %4.1f" + "\t\t\t\t" + currentPlayer.getMaidenOvers() + "\t\t\t\t\t" + currentPlayer.getRunsGiven() + "\t\t\t\t\t" + currentPlayer.getWicketsTaken() + "\t\t\t\t\t\t" + currentPlayer.getNoOfNoBalls() + "\t\t\t\t" + currentPlayer.getNoOfWideBalls() + "\t\t\t\t   %4.1f\n", currentPlayer.getOversBowled(), currentPlayer.getEconomy());
        }
    }

    // There are seven outcomes on each ball {"0", "1", "2", "3", "4", "5", "6", "W","Wide Ball", "No Ball"}
    // therefore I am considering that if the Random function generates a number 7 then I consider as a wicket fall on that ball
    // else all the other ball constitutes runs which will be added to the final score.
    // For Wide ball we will add 1 run to the extras & total score
    // For No Ball we will add 1 extra ball & 1 run.

    private Player startBowling(ArrayList<Player> battingTeamArr, ArrayList<Player> bowlingTeamArr, boolean isSecondInnings, Innings innings) {
        // This function will return the best player who scored most runs.
        int currentOver = 0, currentBall = 0, indOfPlayerOnStrike = 0;
        boolean isTeamWhoBatted2ndScoreGreaterThanTeamWhoBatted1st = false;
        int wideBall = 0, noBall = 0, prevBowler = -1;
        Player teamPlayerWhoScoredMostRuns = null;
        Team teamA = match.getTeamA(); // get Team A
        Team teamB = match.getTeamB(); // get Team B

        Player currentBatsman = battingTeamArr.get(indOfPlayerOnStrike);
        while (currentOver <= match.getMatchOvers() - 1 && innings.getFallOfWickets() < 11) {
            wideBall = 0;
            noBall = 0;
            int indOfBowler = (int) (Math.random() * 5) + 6;
            // I have assumed that the lower order players from number 6 will have Bowling as primary skill, so we will have bowlers from index 6 to 10
            // therefore, we generate a random number less than 5, we will also take care that the prevBowler is not the same as new Bowler
            // as 1 bowler cannot bowl two overs contiguously
            while (prevBowler == indOfBowler) indOfBowler = (int) (Math.random() * 5) + 6;

            Player currentBowler = bowlingTeamArr.get(indOfBowler);
            int runsInCurrentover = 0;
            for (currentBall = 0; currentBall < 6 + wideBall + noBall;currentBall++) {

                // we check for the best player in a team i.e, who scored more runs
                if (teamPlayerWhoScoredMostRuns != null && currentBatsman.getRunsScored() > teamPlayerWhoScoredMostRuns.getRunsScored())
                    teamPlayerWhoScoredMostRuns = currentBatsman;
                else if (teamPlayerWhoScoredMostRuns == null) teamPlayerWhoScoredMostRuns = currentBatsman;

                if (isSecondInnings) // This will be used in 2nd team will bat
                {
                    Innings innings1 = match.getFirstInnings();
                    if (innings.getTotalScore() > innings1.getTotalScore()) {
                        isTeamWhoBatted2ndScoreGreaterThanTeamWhoBatted1st = true;
                        break;
                    }
                }
                if (innings.getFallOfWickets() < 11) {
                    int ballOutcome = (int) (Math.random() * 10);
                    currentBatsman.setBallsFaced(currentBatsman.getBallsFaced() + 1); // increasing the ball count
                    // if outcome is 7 it means the batsman is out then we call the next player to bat
                    // else we will add the run scored on that ball to the player's scorecard as well as to the team's scorecard
                    if (ballOutcome == 7) {
                        innings.setFallOfWickets(innings.getFallOfWickets() + 1);
                        WicketsHistory wc = new WicketsHistory();
                        wc.setBatsmanId(currentBatsman.getPlayerId());
                        wc.setBatsmanName(currentBatsman.getName());
                        wc.setBowlerId(currentBowler.getPlayerId());
                        wc.setBowlerName(currentBowler.getName()); // set the bowler name who took the wicket
                        currentBowler.setEconomy(((double) currentBowler.getRunsGiven()) / currentBowler.getOversBowled());
                        currentBowler.setWicketsTaken(currentBowler.getWicketsTaken() + 1);
                        currentBowler.setBallsBowled(currentBowler.getBallsBowled() + 1);
                        currentBowler.setOversBowled((double) (currentBowler.getBallsBowled() / 6) + ((currentBowler.getBallsBowled()) % 6) * 0.1);
                        double cov = 0.0;// get current over
                        if (currentBall - wideBall - noBall < 6)
                            cov = currentOver + ((currentBall+1 - wideBall - noBall) * 0.1);
                        else cov = currentOver;
                        wc.setWickerFallenInOver(cov); // over in which the wicket fall
                        wc.setWicketsDown(innings.getFallOfWickets()); // number of wickets down at that instant
                        wc.setRunScored(innings.getTotalScore()); // Total score at that instant when wicket fall
                        ArrayList<WicketsHistory> wicketsArr = innings.getWicketsFallenHistory();
                        wicketsArr.add(wc);
                        innings.setWicketsFallenHistory(wicketsArr);
                        currentBatsman.setAverageStrikeRate(((double) currentBatsman.getRunsScored() * 100.0) / currentBatsman.getBallsFaced()); // Calculating the current player's strikerate after each ball
                        indOfPlayerOnStrike++;
                        if (innings.getFallOfWickets() < 11) currentBatsman = battingTeamArr.get(indOfPlayerOnStrike);
                    } else if (ballOutcome == 8) // for wide ball
                    {
                        wideBall++;
                        innings.setTotalScore(innings.getTotalScore() + 1);
                        innings.setExtraRuns(innings.getExtraRuns() + 1);
                        innings.setNoOfWideBalls(innings.getNoOfWideBalls() + 1);
                        currentBowler.setRunsGiven(currentBowler.getRunsGiven() + 1);
                        currentBowler.setNoOfWideBalls(currentBowler.getNoOfWideBalls() + 1);
                        runsInCurrentover++;
                    } else if (ballOutcome == 9) // for No ball
                    {
                        noBall++;
                        innings.setTotalScore(innings.getTotalScore() + 1);
                        innings.setExtraRuns(innings.getExtraRuns() + 1);
                        innings.setNoOfNoBalls(innings.getNoOfNoBalls() + 1);
                        currentBowler.setRunsGiven(currentBowler.getRunsGiven() + 1);
                        currentBowler.setNoOfNoBalls(currentBowler.getNoOfNoBalls() + 1);
                        runsInCurrentover++;
                    } else {
                        innings.setTotalScore(innings.getTotalScore() + ballOutcome);
                        currentBatsman.setRunsScored(currentBatsman.getRunsScored() + ballOutcome);
                        currentBowler.setBallsBowled(currentBowler.getBallsBowled() + 1);
                        currentBowler.setOversBowled((double) (currentBowler.getBallsBowled() / 6) + ((currentBowler.getBallsBowled()) % 6) * 0.1);
                        HashMap<Integer, Integer> currentBatsmanEachRunFreq = currentBatsman.getEachRunFreq();
                        if (currentBatsmanEachRunFreq.containsKey(ballOutcome))
                            currentBatsmanEachRunFreq.put(ballOutcome, currentBatsmanEachRunFreq.get(ballOutcome) + 1);
                        else
                            currentBatsmanEachRunFreq.put(ballOutcome, 1);
                        currentBatsman.setEachRunFreq(currentBatsmanEachRunFreq);
                        currentBowler.setRunsGiven(currentBowler.getRunsGiven() + ballOutcome);
                        runsInCurrentover += ballOutcome;
                    }
                    currentBowler.setEconomy(((double) currentBowler.getRunsGiven()) / currentBowler.getOversBowled());
                    currentBatsman.setAverageStrikeRate(((double) currentBatsman.getRunsScored() * 100.0) / currentBatsman.getBallsFaced()); // Calculating the current player's strikerate after each ball
                } else break;
            }
            if (runsInCurrentover == 0)
                currentBowler.setMaidenOvers(currentBowler.getMaidenOvers() + 1);
            prevBowler = indOfBowler;
            if (isSecondInnings && isTeamWhoBatted2ndScoreGreaterThanTeamWhoBatted1st) break;
            currentOver++;
        }
        // If team who batted second score greater than team who batted 1st then team 2nd batter Team is winner
        if (isSecondInnings) {
            Innings innings1 = match.getFirstInnings();
            if (isTeamWhoBatted2ndScoreGreaterThanTeamWhoBatted1st)
                if (innings.getBattingTeamId() == teamA.getTeamId())
                    match.setMatchWinner(MatchWinner.TEAMA);
                else
                    match.setMatchWinner(MatchWinner.TEAMB);
            else {
                if (innings1.getBattingTeamId() == teamA.getTeamId())
                    // 1st Batter Team is winner.
                    match.setMatchWinner(MatchWinner.TEAMA);
                else
                    match.setMatchWinner(MatchWinner.TEAMB);
            }

        }

        double overs;
        if (currentBall - wideBall - noBall < 6) overs = currentOver + ((currentBall - wideBall - noBall) * 0.1) - 1;
        else overs = currentOver;
        innings.setOversBatted(overs);
        return teamPlayerWhoScoredMostRuns;
    }

    public void playMatch() {
        Team teamB = match.getTeamB(); // get Team B
        Team teamA = match.getTeamA(); // get Team A
        Player teamAplayer = null; // Reference variable to Team A best player
        Player teamBplayer = null; // Reference variable to Team B best player

        ArrayList<Player> teamAPlayersArr = teamA.getPlayersArr(); // getting the Player arr of Team A
        ArrayList<Player> teamBPlayersArr = teamB.getPlayersArr(); // getting the Player arr of Team B
        System.out.println("**********************************************************************************************************************************************");
        Innings firstInnings = match.getFirstInnings();
        Innings secondInnings = match.getSecondInnings();
        System.out.println("Game has Started & " + match.getTeamNameWhoWonToss() + " have won the toss & choose to " + toss.getWinnerChoseTo() + " first !");
        System.out.println("**********************************************************************************************************************************************");
        if (match.getTeamNameWhoWonToss().equals(teamA.getTeamName())) {
            if (toss.getWinnerChoseTo().equals("Bat")) {
                teamAplayer = startBowling(teamAPlayersArr, teamBPlayersArr, false, firstInnings); // Team A will be batting first
                match.setTeamABestPlayer(teamAplayer);
            } else {
                teamBplayer = startBowling(teamBPlayersArr, teamAPlayersArr, false, firstInnings); // Team B will be bowl first
                match.setTeamBBestPlayer(teamBplayer);
            }
        } else {
            if (toss.getWinnerChoseTo().equals("Bat")) {
                teamBplayer = startBowling(teamBPlayersArr, teamAPlayersArr, false, firstInnings); // Team B will be batting first
                match.setTeamBBestPlayer(teamBplayer);
            } else {
                teamAplayer = startBowling(teamAPlayersArr, teamBPlayersArr, false, firstInnings); // Team A will be bowl first
                match.setTeamABestPlayer(teamAplayer);
            }

        }


        System.out.println("**********************************************************************************************************************************************");
        if (firstInnings.getBattingTeamId() == teamA.getTeamId()) {
            System.out.println(teamA.getTeamName() + " player's were able to set the target of " + firstInnings.getTotalScore() + " runs at the cost of " + firstInnings.getFallOfWickets() + " wickets in " + firstInnings.getOversBatted() + " overs.\nLet's see whether " + teamB.getTeamName() + " will be able to achieve that target in " + match.getMatchOvers() + " overs.");
        } else {
            System.out.println(teamB.getTeamName() + " player's were able to set the target of " + firstInnings.getTotalScore() + " runs at the cost of " + firstInnings.getFallOfWickets() + " wickets in " + firstInnings.getOversBatted() + " overs.\nLet's see whether " + teamA.getTeamName() + " will be able to achieve that target in " + match.getMatchOvers() + " overs.");
        }
        System.out.println("**********************************************************************************************************************************************");
        if (firstInnings.getBowlingTeamId() == teamA.getTeamId()) {
            teamAplayer = startBowling(teamAPlayersArr, teamBPlayersArr, true, secondInnings); // Team B will be batting
            match.setTeamABestPlayer(teamAplayer);
        } else {
            teamBplayer = startBowling(teamBPlayersArr, teamAPlayersArr, true, secondInnings); // Team B will be batting
            match.setTeamBBestPlayer(teamBplayer);
        }

        System.out.println("**********************************************************************************************************************************************");
        if (match.getMatchWinner() == MatchWinner.TEAMB) { // Checking the Match Winner
            if (firstInnings.getBattingTeamId() == teamB.getTeamId()) {
                System.out.println(teamB.getTeamName() + " won the match by " + (firstInnings.getTotalScore() - secondInnings.getTotalScore()) + " runs.");
                System.out.printf(teamA.getTeamName() + " player's were not able to achieve the target of " + (firstInnings.getTotalScore() + 1) + " runs & were able to score only " + secondInnings.getTotalScore() + " at the cost of " + secondInnings.getFallOfWickets() + " wickets in %.1f overs.\n", secondInnings.getOversBatted());
            } else {
                System.out.printf(teamB.getTeamName() + " player's were able to achieve the target of " + (firstInnings.getTotalScore() + 1) + " & scored " + secondInnings.getTotalScore() + " runs at the cost of " + secondInnings.getFallOfWickets() + " wickets in %.1f\n overs.", secondInnings.getOversBatted());
            }
            System.out.println(teamBplayer.getName() + " is choosen to be the Man of the Match as he scored " + teamBplayer.getRunsScored() + " in " + teamBplayer.getBallsFaced() + " balls.");
        } else {
            if (firstInnings.getBattingTeamId() == teamA.getTeamId()) {
                System.out.println(teamA.getTeamName() + " won the match by " + (firstInnings.getTotalScore() - secondInnings.getTotalScore()) + " runs.");
                System.out.printf(teamB.getTeamName() + " player's were not able to achieve the target of " + (firstInnings.getTotalScore() + 1) + " runs & were able to score only " + secondInnings.getTotalScore() + " at the cost of " + secondInnings.getFallOfWickets() + " wickets in %.1f overs.\n", secondInnings.getOversBatted());
            } else {
                System.out.printf(teamA.getTeamName() + " player's were able to achieve the target of " + (firstInnings.getTotalScore() + 1) + " & scored " + secondInnings.getTotalScore() + " runs at the cost of " + secondInnings.getFallOfWickets() + " wickets in %.1f\n overs.", secondInnings.getOversBatted());
            }
            System.out.println(teamAplayer.getName() + " is choosen to be the Man of the Match as he scored " + teamAplayer.getRunsScored() + " in " + teamAplayer.getBallsFaced() + " balls.");
        }
        System.out.println("**********************************************************************************************************************************************");
        System.out.println("**********************************************************************************************************************************************");
        System.out.println("Printing Both Innings Stats");
        System.out.println("**********************************************************************************************************************************************");
        if (firstInnings.getBattingTeamId() == teamA.getTeamId()) {
            System.out.println("Innings 1");
            System.out.println("**********************************************************************************************************************************************");
            System.out.println(teamA.getTeamName() + " Scorecard: ");
            printBattingScorecard(teamAPlayersArr, firstInnings);
            printWicketsHitory(firstInnings.getWicketsFallenHistory());
            printBowlingStats(teamBPlayersArr);
            System.out.println("**********************************************************************************************************************************************");
            System.out.println("Innings 2");
            System.out.println("**********************************************************************************************************************************************");
            System.out.println(teamB.getTeamName() + " Scorecard: ");
            printBattingScorecard(teamBPlayersArr, secondInnings);
            printWicketsHitory(secondInnings.getWicketsFallenHistory());
            printBowlingStats(teamAPlayersArr);
            System.out.println("**********************************************************************************************************************************************");

        } else {
            System.out.println("Innings 1");
            System.out.println("**********************************************************************************************************************************************");
            System.out.println(teamB.getTeamName() + " Scorecard: ");
            printBattingScorecard(teamBPlayersArr, firstInnings);
            printWicketsHitory(firstInnings.getWicketsFallenHistory());
            printBowlingStats(teamAPlayersArr);
            System.out.println("**********************************************************************************************************************************************");
            System.out.println("Innings 2");
            System.out.println("**********************************************************************************************************************************************");
            System.out.println(teamA.getTeamName() + " Scorecard: ");
            printBattingScorecard(teamAPlayersArr, secondInnings);
            printWicketsHitory(secondInnings.getWicketsFallenHistory());
            printBowlingStats(teamBPlayersArr);
            System.out.println("**********************************************************************************************************************************************");
        }


    }
}
