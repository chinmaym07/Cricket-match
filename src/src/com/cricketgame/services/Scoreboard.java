package src.com.cricketgame.services;

import src.com.cricketgame.models.*;

import java.util.ArrayList;

public class Scoreboard {
    private static void printBattingScorecard(ArrayList<Player> playerArr, Innings innings) {
        int playersDidNotBat = 0;
        System.out.println("Batter \t\t\t\t Runs Score \t\t\t\t Ball's Faced \t\t\t\t 4's \t\t\t\t 6's \t\t\t\t StrikeRate\n");
        for (Player currentPlayer : playerArr) {
            // System.out.println(currentPlayer.getName() + " played "+currentPlayer.getBallsFaced() +" balls.");
            PlayerStats currentPlayerStats = currentPlayer.getPlayerStats();
            if (currentPlayerStats.getBallsFaced() > 0)
                // System.out.printf(currentPlayer.getName() + " scored " + currentPlayer.getRunsScored() + " in " + currentPlayer.getBallsFaced() + " balls at a strike rate of %4.1f\n", currentPlayer.getAverageStrikeRate());
                System.out.printf(currentPlayer.getName().substring(0,8) + "\t\t\t\t" + currentPlayerStats.getRunsScored() + "\t\t\t\t\t\t\t" + currentPlayerStats.getBallsFaced() + "\t\t\t\t\t\t\t  " + currentPlayerStats.getNumberOfRunsFreq(4) + "\t\t\t\t\t  " + currentPlayerStats.getNumberOfRunsFreq(6) + "\t\t\t\t\t   %4.1f\n", currentPlayerStats.getAverageStrikeRate());
            else playersDidNotBat++;
        }
        System.out.println("Extras \t\t\t\t\t" + innings.getExtraRuns() + "(nb " + innings.getNoOfNoBalls() + ", wb " + innings.getNoOfWideBalls() + ")");
        System.out.println("Total \t\t\t\t\t" + innings.getTotalScore() + "(" + innings.getFallOfWickets() + " wckts, " + innings.getOversBatted() + " Ov.)");
        if (playersDidNotBat > 0) {
            System.out.println("Did not Bat");
            int count = 0; // checking the count as this will be used for printing comma
            for (Player currentPlayer : playerArr) {
                PlayerStats currentPlayerStats = currentPlayer.getPlayerStats();
                if (currentPlayerStats.getBallsFaced() == 0) {
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

    private static void printWicketsHistory(ArrayList<WicketsHistory> wicketsHistory) {
        int count = 0;
        System.out.println("Fall of Wickets");
        for (WicketsHistory wickets : wicketsHistory) {
            System.out.printf(wickets.getRunScored() + "-" + wickets.getWicketsDown() + " (" + wickets.getBatsmanName() + ", %4.1f)", wickets.getWickerFallenInOver());
            count++;
            if (count != wicketsHistory.size()) System.out.printf(", ");
        }
        System.out.println("\n");
    }

    private static void printBowlingStats(ArrayList<Player> players) {
        System.out.println("Bowler \t\t\t\t Over's Bowled \t\t Maiden Overs \t\t Runs Given \t\t  Wicket's Taken \t\t NoBall's \t\t WideBall's \t\t Economy\n");
        for (Player currentPlayer : players) {
            // System.out.println(currentPlayer.getName() + " played "+currentPlayer.getBallsBowled() +" balls.");
            PlayerStats currentPlayerStats = currentPlayer.getPlayerStats();
            if (currentPlayerStats.getBallsBowled() > 0)
                System.out.printf(currentPlayer.getName().substring(0,8) + "\t\t\t %4.1f" + "\t\t\t\t" + currentPlayerStats.getMaidenOvers() + "\t\t\t\t\t\t" + currentPlayerStats.getRunsGiven() + "\t\t\t\t\t" + currentPlayerStats.getWicketsTaken() + "\t\t\t\t\t\t" + currentPlayerStats.getNoOfNoBalls() + "\t\t\t\t" + currentPlayerStats.getNoOfWideBalls() + "\t\t\t\t   %4.1f\n", currentPlayerStats.getOversBowled(), currentPlayerStats.getEconomy());
        }
    }

    private static void printingScoreCard(String teamName, ArrayList<Player> battingTeamPlayersArr, Innings innings, ArrayList<Player> bowlingTeamPlayersArr, boolean secondInnings) {
        if (secondInnings)
            System.out.println("Innings 2");
        else
            System.out.println("Innings 1");
        System.out.println("**********************************************************************************************************************************************");
        System.out.println(teamName + " Scorecard: ");
        System.out.println(innings.getOversBatted());
        if (innings.getOversBatted() > 0) {
            Scoreboard.printBattingScorecard(battingTeamPlayersArr, innings);
            Scoreboard.printWicketsHistory(innings.getWicketsFallenHistory());
            Scoreboard.printBowlingStats(bowlingTeamPlayersArr);
        } else {
            System.out.println("Innings not started!!");
        }

        System.out.println("**********************************************************************************************************************************************");
    }

    public static void statsPerInnings(Innings innings, Team teamA, Team teamB) {
        ArrayList<Player> teamAPlayersArr = teamA.getPlayersArr(); // getting the Player arr of Team A
        ArrayList<Player> teamBPlayersArr = teamB.getPlayersArr(); // getting the Player arr of Team B
        System.out.println("**********************************************************************************************************************************************");
        System.out.println("Printing Stats Innings wise");
        System.out.println("**********************************************************************************************************************************************");

        if (innings.getBattingTeamId() == teamA.getTeamId()) {
            printingScoreCard(teamA.getTeamName(), teamAPlayersArr, innings, teamBPlayersArr, false);
        } else {
            printingScoreCard(teamB.getTeamName(), teamBPlayersArr, innings, teamAPlayersArr, false);
        }

    }

    public static void completeScoreCard(Innings firstInnings, Innings secondInnings, Team teamA, Team teamB) {
        ArrayList<Player> teamAPlayersArr = teamA.getPlayersArr(); // getting the Player arr of Team A
        ArrayList<Player> teamBPlayersArr = teamB.getPlayersArr(); // getting the Player arr of Team B
        System.out.println("**********************************************************************************************************************************************");
        System.out.println("**********************************************************************************************************************************************");
        System.out.println("Printing Both Innings Stats");
        System.out.println("**********************************************************************************************************************************************");
        if (firstInnings.getBattingTeamId() == teamA.getTeamId()) {
            printingScoreCard(teamA.getTeamName(), teamAPlayersArr, firstInnings, teamBPlayersArr, false);
            printingScoreCard(teamB.getTeamName(), teamBPlayersArr, secondInnings, teamAPlayersArr, true);
        } else {
            printingScoreCard(teamB.getTeamName(), teamBPlayersArr, firstInnings, teamAPlayersArr, false);
            printingScoreCard(teamA.getTeamName(), teamAPlayersArr, secondInnings, teamBPlayersArr, true);
        }
    }
}
