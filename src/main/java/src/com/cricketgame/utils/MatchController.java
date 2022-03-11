package src.com.cricketgame.utils;

import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;
import src.com.cricketgame.enums.MatchWinnerEnums;
import src.com.cricketgame.models.*;
import src.com.cricketgame.services.Scoreboard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchController {
    private Match match;

    private void increaseRunFreq(EachRunfreqDTO eachRunfreqDTO, int ballOutcome) {
        switch (ballOutcome) {
            case 1:
                eachRunfreqDTO.setOnes(eachRunfreqDTO.getOnes()+1);
                break;
            case 2:
                eachRunfreqDTO.setTwos(eachRunfreqDTO.getTwos()+1);
                break;
            case 3:
                eachRunfreqDTO.setThrees(eachRunfreqDTO.getThrees()+1);
                break;
            case 4:
                eachRunfreqDTO.setFours(eachRunfreqDTO.getFours()+1);
                break;
            case 5:
                eachRunfreqDTO.setFives(eachRunfreqDTO.getFives()+1);
                break;
            case 6:
                eachRunfreqDTO.setSixes(eachRunfreqDTO.getSixes()+1);
                break;
        }
    }
    private Player startBowling(List<Player> battingTeamArr, List<Player> bowlingTeamArr, boolean isSecondInnings, Innings innings) {
        // This function will return the best player who scored most runs.
        Team teamA; // get Team A
        Team teamB; // get Team B
        teamB = match.getTeamB(); // get Team B
        teamA = match.getTeamA(); // get Team A
        List<List<String>> ballSummary = new ArrayList<List<String>>();

        int currentOver = 0, currentBall = 0, indOfPlayerOnStrike = 0;
        boolean isTeamWhoBatted2ndScoreGreaterThanTeamWhoBatted1st = false;
        int wideBall = 0, noBall = 0, prevBowler = -1;
        Player teamPlayerWhoScoredMostRuns = null;
        Player currentBatsman = battingTeamArr.get(indOfPlayerOnStrike);
        PlayerStats currentBatsmanStats = currentBatsman.getPlayerStats();
        while (currentOver <= match.getMatchOvers() - 1 && innings.getFallOfWickets() < 11) {
            ArrayList<String> currentOverBallSummary = new ArrayList<String>();
            wideBall = 0;
            noBall = 0;
            int indOfBowler = (int) (Math.random() * 5) + 6;
            // I have assumed that the lower order players from number 6 will have Bowling as primary skill, so we will have bowlers from index 6 to 10
            // therefore, we generate a random number less than 5, we will also take care that the prevBowler is not the same as new Bowler
            // as 1 bowler cannot bowl two overs contiguously
            while (prevBowler == indOfBowler) indOfBowler = (int) (Math.random() * 5) + 6;

            Player currentBowler = bowlingTeamArr.get(indOfBowler);
            PlayerStats currentBowlerStats = currentBowler.getPlayerStats();
            int runsInCurrentover = 0;
            for (currentBall = 0; currentBall < 6 + wideBall + noBall; currentBall++) {

                // we check for the best player in a team i.e, who scored more runs
                if (teamPlayerWhoScoredMostRuns != null && currentBatsmanStats.getRunsScored() > teamPlayerWhoScoredMostRuns.getPlayerStats().getRunsScored())
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
                    currentBatsmanStats.setBallsFaced(currentBatsmanStats.getBallsFaced() + 1); // increasing the ball count
                    // if outcome is 7 it means the batsman is out then we call the next player to bat
                    // else we will add the run scored on that ball to the player's scorecard as well as to the team's scorecard
                    if (ballOutcome == 7) {
                        innings.setFallOfWickets(innings.getFallOfWickets() + 1);
                        WicketsHistory wc = new WicketsHistory();
                        wc.setBatsmanId(currentBatsman.getPlayerId());
                        wc.setBatsmanName(currentBatsman.getName());
                        wc.setBowlerId(currentBowler.getPlayerId());
                        wc.setBowlerName(currentBowler.getName()); // set the bowler name who took the wicket
                        currentBowlerStats.setEconomy(((double) currentBowlerStats.getRunsGiven()) / currentBowlerStats.getOversBowled());
                        currentBowlerStats.setWicketsTaken(currentBowlerStats.getWicketsTaken() + 1);
                        currentBowlerStats.setBallsBowled(currentBowlerStats.getBallsBowled() + 1);
                        currentBowlerStats.setOversBowled((double) (currentBowlerStats.getBallsBowled() / 6) + ((currentBowlerStats.getBallsBowled()) % 6) * 0.1);
                        double cov = 0.0;// get current over
                        if (currentBall - wideBall - noBall < 5) {
                            cov = currentOver + ((currentBall + 1 - wideBall - noBall) * 0.1);
                        } else cov = currentOver + 1;
                        wc.setWicketsFallenInOver(cov); // over in which the wicket fall
                        wc.setWicketsDown(innings.getFallOfWickets()); // number of wickets down at that instant
                        wc.setRunScored(innings.getTotalScore()); // Total score at that instant when wicket fall
                        List<WicketsHistory> wicketsArr = innings.getWicketsFallenHistory();
                        wicketsArr.add(wc);
                        innings.setWicketsFallenHistory(wicketsArr);
                        innings.setOversBatted(cov);
                        currentBatsmanStats.setAverageStrikeRate(((double) currentBatsmanStats.getRunsScored() * 100.0) / currentBatsmanStats.getBallsFaced()); // Calculating the current player's strikerate after each ball
                        indOfPlayerOnStrike++;
                        currentOverBallSummary.add("W");
                        if (innings.getFallOfWickets() < 11) {
                            currentBatsman = battingTeamArr.get(indOfPlayerOnStrike);
                            currentBatsmanStats = currentBatsman.getPlayerStats();
                        }
                    } else if (ballOutcome == 8) // for wide ball
                    {
                        wideBall++;
                        innings.setTotalScore(innings.getTotalScore() + 1);
                        innings.setExtraRuns(innings.getExtraRuns() + 1);
                        innings.setNoOfWideBalls(innings.getNoOfWideBalls() + 1);
                        currentBowlerStats.setRunsGiven(currentBowlerStats.getRunsGiven() + 1);
                        currentBowlerStats.setNoOfWideBalls(currentBowlerStats.getNoOfWideBalls() + 1);
                        runsInCurrentover++;
                        currentOverBallSummary.add("WB");
                    } else if (ballOutcome == 9) // for No ball
                    {
                        noBall++;
                        innings.setTotalScore(innings.getTotalScore() + 1);
                        innings.setExtraRuns(innings.getExtraRuns() + 1);
                        innings.setNoOfNoBalls(innings.getNoOfNoBalls() + 1);
                        currentBowlerStats.setRunsGiven(currentBowlerStats.getRunsGiven() + 1);
                        currentBowlerStats.setNoOfNoBalls(currentBowlerStats.getNoOfNoBalls() + 1);
                        runsInCurrentover++;
                        currentOverBallSummary.add("NB");
                    } else { // for all other outcomes like 1 to 6 runs.
                        double cov = 0.0;// get current over
                        innings.setTotalScore(innings.getTotalScore() + ballOutcome);
                        currentBatsmanStats.setRunsScored(currentBatsmanStats.getRunsScored() + ballOutcome);
                        currentBowlerStats.setBallsBowled(currentBowlerStats.getBallsBowled() + 1);
                        currentBowlerStats.setOversBowled((double) (currentBowlerStats.getBallsBowled() / 6) + ((currentBowlerStats.getBallsBowled()) % 6) * 0.1);
                        EachRunfreqDTO currentBatsmanEachRunFreq = currentBatsmanStats.getEachRunFreq();

                        increaseRunFreq(currentBatsmanEachRunFreq,ballOutcome);


                        currentBatsmanStats.setEachRunFreq(currentBatsmanEachRunFreq);
                        currentBowlerStats.setRunsGiven(currentBowlerStats.getRunsGiven() + ballOutcome);
                        if (currentBall - wideBall - noBall < 5) {
                            cov = currentOver + ((currentBall + 1 - wideBall - noBall) * 0.1);
                        } else cov = currentOver + 1;
                        innings.setOversBatted(cov);
                        runsInCurrentover += ballOutcome;
                        currentOverBallSummary.add(Integer.toString(ballOutcome));
                    }
                    currentBowlerStats.setEconomy(((double) currentBowlerStats.getRunsGiven()) / currentBowlerStats.getOversBowled());
                    currentBatsmanStats.setAverageStrikeRate(((double) currentBatsmanStats.getRunsScored() * 100.0) / currentBatsmanStats.getBallsFaced()); // Calculating the current player's strikerate after each ball
                } else break;
            }
            if (currentOver == 3) {
                if (isSecondInnings) {
                    System.out.println("Printing Stats of Second Innings after 4 overs");
                    Scoreboard.statsPerInnings(innings, teamA, teamB);
                } else {
                    System.out.println("Printing Stats of First Innings after 4 overs");
                    Scoreboard.statsPerInnings(innings, teamA, teamB);
                }
            }
            ballSummary.add(currentOverBallSummary);
            innings.setBallSummary(ballSummary);
            if (runsInCurrentover == 0)
                currentBowlerStats.setMaidenOvers(currentBowlerStats.getMaidenOvers() + 1);
            prevBowler = indOfBowler;
            if (isSecondInnings && isTeamWhoBatted2ndScoreGreaterThanTeamWhoBatted1st) break;
            currentOver++;
        }
        // If team who batted second score greater than team who batted 1st then team 2nd batter Team is winner
        if (isSecondInnings) {
            Innings innings1 = match.getFirstInnings();
            if (isTeamWhoBatted2ndScoreGreaterThanTeamWhoBatted1st)
                if (innings.getBattingTeamId() == teamA.getTeamId())
                    match.setMatchWinner(MatchWinnerEnums.TEAMA);
                else
                    match.setMatchWinner(MatchWinnerEnums.TEAMB);
            else {
                if (innings1.getBattingTeamId() == teamA.getTeamId())
                    // 1st Batter Team is winner.
                    match.setMatchWinner(MatchWinnerEnums.TEAMA);
                else
                    match.setMatchWinner(MatchWinnerEnums.TEAMB);
            }
        }
        double overs;
        if (currentBall - wideBall - noBall < 6) {
            overs = currentOver + ((currentBall - wideBall - noBall) * 0.1);
        } else overs = currentOver;
        innings.setOversBatted(overs);
        innings.setBallSummary(ballSummary);
        return teamPlayerWhoScoredMostRuns;
    }

    // There are seven outcomes on each ball {"0", "1", "2", "3", "4", "5", "6", "W","Wide Ball", "No Ball"}
    // therefore I am considering that if the Random function generates a number 7 then I consider as a wicket fall on that ball
    // else all the other ball constitutes runs which will be added to the final score.
    // For Wide ball we will add 1 run to the extras & total score
    // For No Ball we will add 1 extra ball & 1 run.

    public void checkWinner(Innings firstInnings, Innings secondInnings, Player teamAplayer, Player teamBplayer) {
        Team teamA; // get Team A
        Team teamB; // get Team B
        teamB = match.getTeamB(); // get Team B
        teamA = match.getTeamA(); // get Team A

        System.out.println("**********************************************************************************************************************************************");
        if (match.getMatchWinner() == MatchWinnerEnums.TEAMB) { // Checking the Match Winner
            if (firstInnings.getBattingTeamId() == teamB.getTeamId()) {
                System.out.println(teamB.getTeamName() + " won the match by " + (firstInnings.getTotalScore() - secondInnings.getTotalScore()) + " runs.");
                System.out.printf(teamA.getTeamName() + " player's were not able to achieve the target of " + (firstInnings.getTotalScore() + 1) + " runs & were able to score only " + secondInnings.getTotalScore() + " at the cost of " + secondInnings.getFallOfWickets() + " wickets in %.1f overs.\n", secondInnings.getOversBatted());
            } else {
                System.out.printf(teamB.getTeamName() + " player's were able to achieve the target of " + (firstInnings.getTotalScore() + 1) + " & scored " + secondInnings.getTotalScore() + " runs at the cost of " + secondInnings.getFallOfWickets() + " wickets in %.1f\n overs.", secondInnings.getOversBatted());
            }
            System.out.println(teamBplayer.getName() + " is choosen to be the Man of the Match as he scored " + teamBplayer.getPlayerStats().getRunsScored() + " in " + teamBplayer.getPlayerStats().getBallsFaced() + " balls.");
        } else {
            if (firstInnings.getBattingTeamId() == teamA.getTeamId()) {
                System.out.println(teamA.getTeamName() + " won the match by " + (firstInnings.getTotalScore() - secondInnings.getTotalScore()) + " runs.");
                System.out.printf(teamB.getTeamName() + " player's were not able to achieve the target of " + (firstInnings.getTotalScore() + 1) + " runs & were able to score only " + secondInnings.getTotalScore() + " at the cost of " + secondInnings.getFallOfWickets() + " wickets in %.1f overs.\n", secondInnings.getOversBatted());
            } else {
                System.out.printf(teamA.getTeamName() + " player's were able to achieve the target of " + (firstInnings.getTotalScore() + 1) + " & scored " + secondInnings.getTotalScore() + " runs at the cost of " + secondInnings.getFallOfWickets() + " wickets in %.1f\n overs.", secondInnings.getOversBatted());
            }
            System.out.println(teamAplayer.getName() + " is choosen to be the Man of the Match as he scored " + teamAplayer.getPlayerStats().getRunsScored() + " in " + teamAplayer.getPlayerStats().getBallsFaced() + " balls.");
        }
    }

    public void playMatch() throws SQLException {

        Team teamB = match.getTeamB(); // get Team B
        Team teamA = match.getTeamA(); // get Team A

        Player teamAplayer = null; // Reference variable to Team A best player
        Player teamBplayer = null; // Reference variable to Team B best player

        List<Player> teamAPlayersArr = teamA.getPlayersArr(); // getting the Player arr of Team A
        List<Player> teamBPlayersArr = teamB.getPlayersArr(); // getting the Player arr of Team B
        System.out.println("**********************************************************************************************************************************************");
        Innings firstInnings = match.getFirstInnings();
        Innings secondInnings = match.getSecondInnings();
        System.out.println("Game has Started & " + match.getTeamNameWhoWonToss() + " have won the toss & choose to " + match.getToss().getWinnerChoseTo() + " first !");
        System.out.println("**********************************************************************************************************************************************");
        if (match.getTeamNameWhoWonToss().equals(teamA.getTeamName())) {
            if (match.getToss().getWinnerChoseTo().equals("Bat")) {
                teamAplayer = startBowling(teamAPlayersArr, teamBPlayersArr, false, firstInnings); // Team A will be batting first
                match.setTeamABestPlayer(teamAplayer);
            } else {
                teamBplayer = startBowling(teamBPlayersArr, teamAPlayersArr, false, firstInnings); // Team B will be bowl first
                match.setTeamBBestPlayer(teamBplayer);
            }
        } else {
            if (match.getToss().getWinnerChoseTo().equals("Bat")) {
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
        checkWinner(firstInnings, secondInnings, teamAplayer, teamBplayer);
        /*DB.updateMatchData(match); // Updating match Data in Sql
        DB.updatePlayerStats(teamAPlayersArr, match.getMatchId()); // Updating stats of team A Players
        DB.updatePlayerStats(teamBPlayersArr, match.getMatchId()); // Updating stats of team B Players
        DB.updateInningsStats(match.getMatchId(), firstInnings);
        DB.updateInningsStats(match.getMatchId(), secondInnings);
        DB.updateWicketsHistory(match.getMatchId(),firstInnings.getInningsId(),firstInnings.getWicketsFallenHistory()); // Updating stats of team A Players Wickets
        DB.updateWicketsHistory(match.getMatchId(),secondInnings.getInningsId(),secondInnings.getWicketsFallenHistory()); // Updating stats of team B Players wickets
        */
        Scoreboard.completeScoreCard(firstInnings, secondInnings, teamA, teamB);
        System.out.println("Printing Ball Summary of first Innings");
        BallSummary.printBallSummary(firstInnings.getBallSummary());
        System.out.println("Printing Ball Summary of Second Innings");
        BallSummary.printBallSummary(secondInnings.getBallSummary());
    }
}
