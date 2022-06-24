package src.com.cricketgame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.CurrentPlay;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.models.WicketsHistory;
import src.com.cricketgame.repo.*;
import src.com.cricketgame.utils.BallSummary;
import src.com.cricketgame.utils.InningsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InningsServiceImpl implements InningsService {

    @Autowired
    private InningsRepositoryImpl inningsRepository;

    @Autowired
    private MatchRepositoryImpl matchRepository;

    @Autowired
    private PlayerRepositoryImpl playerRepository;

    @Autowired
    private PlayerStatsRepositoryImpl playerStatsRepository;

    @Autowired
    private EachRunFreqRepositoryImpl eachRunFreqRepository;

    @Autowired
    private CurrentPlayRepositoryImpl currentPlayRepository;

    @Autowired
    BallSummaryRepositoryImpl ballSummaryRepository;

    @Autowired
    private WicketsHistoryRepositoryImpl wicketsHistoryRepository;


    public Innings getFirstInnings(int matchId) {
        Innings firstInnings = inningsRepository.getFirstInnings(matchId);
        List<WicketsHistory> wicketsHistories = wicketsHistoryRepository.getWicketsHistory(matchId, firstInnings.getInningsId());
        for (WicketsHistory wicketsHistory : wicketsHistories) {
            Player batsman = playerRepository.getPlayer(wicketsHistory.getBatsmanId());
            Player bowler = playerRepository.getPlayer(wicketsHistory.getBowlerId());
            wicketsHistory.setBatsmanName(batsman.getName());
            wicketsHistory.setBowlerName(bowler.getName());
        }
        List<List<String>> ballSummary = new ArrayList<List<String>>();
        for (int currentOver = 0; currentOver < firstInnings.getOversBatted(); currentOver++) {
            List<String> summeryPerOver = ballSummaryRepository.getBallSummaryForInnings(matchId, firstInnings.getInningsId(), currentOver);
            ballSummary.add(summeryPerOver);
        }
        firstInnings.setBallSummary(ballSummary);
        firstInnings.setWicketsFallenHistory(wicketsHistories);
        return firstInnings;
    }

    public Innings getSecondInnings(int matchId) {
        Innings secondInnings = inningsRepository.getSecondInnings(matchId);
        List<WicketsHistory> wicketsHistories = wicketsHistoryRepository.getWicketsHistory(matchId, secondInnings.getInningsId());
        for (WicketsHistory wicketsHistory : wicketsHistories) {
            Player batsman = playerRepository.getPlayer(wicketsHistory.getBatsmanId());
            Player bowler = playerRepository.getPlayer(wicketsHistory.getBowlerId());
            wicketsHistory.setBatsmanName(batsman.getName());
            wicketsHistory.setBowlerName(bowler.getName());
        }

        List<List<String>> ballSummary = new ArrayList<List<String>>();
        for (int currentOver = 0; currentOver < secondInnings.getOversBatted(); currentOver++) {
            List<String> summeryPerOver = ballSummaryRepository.getBallSummaryForInnings(matchId, secondInnings.getInningsId(), currentOver);
            ballSummary.add(summeryPerOver);
        }
        secondInnings.setBallSummary(ballSummary);
        secondInnings.setWicketsFallenHistory(wicketsHistories);
        return secondInnings;
    }

    public Innings startFirstInnings(int matchId) {
        Innings firstInnings = inningsRepository.startFirstInnings(matchId);
        CurrentPlay currentPlayInningsfirst = new CurrentPlay();
        currentPlayInningsfirst.setMatchId(firstInnings.getMatchId());
        currentPlayInningsfirst.setInningsId(firstInnings.getInningsId());
        currentPlayRepository.insertCurrentPlay(matchId, firstInnings.getInningsId(), currentPlayInningsfirst);
        return firstInnings;
    }

    public Innings startSecondInnings(int matchId) {
        Innings secondInnings = inningsRepository.startSecondInnings(matchId);
        CurrentPlay currentPlayInningsSecond = new CurrentPlay();
        currentPlayInningsSecond.setInningsId(secondInnings.getInningsId());
        currentPlayInningsSecond.setMatchId(secondInnings.getMatchId());
        currentPlayRepository.insertCurrentPlay(matchId, secondInnings.getInningsId(), currentPlayInningsSecond);
        return secondInnings;
    }

    public void findWinner(int matchId, MatchesDTO matchesDTO) {
        Innings firstInnings = getFirstInnings(matchId);
        Innings secondInnings = getSecondInnings(matchId);
        System.out.println("First Innings Total Score: "+ firstInnings.getTotalScore());
        System.out.println("Second Innings Total Score: "+ secondInnings.getTotalScore());
        System.out.println("First Innings Status: "+ firstInnings.getInningsStatus());
        System.out.println("Second Innings Status: "+ secondInnings.getInningsStatus());
        if (firstInnings.getInningsStatus().equals("COMPLETED") && secondInnings.getInningsStatus().equals("COMPLETED")) {

            if (secondInnings.getTotalScore() > firstInnings.getTotalScore()) {
                matchesDTO.setTeamIdWhoWonTheMatch(secondInnings.getBattingTeamId());
            } else {
                matchesDTO.setTeamIdWhoWonTheMatch(firstInnings.getBattingTeamId());
            }
        }

    }

    public String startOver(int matchId, String inningsType, int currentBowlerId, int currentBatsmanId) {

        Innings innings;
        if (inningsType.equals("first"))
            innings = getFirstInnings(matchId);
        else
            innings = getSecondInnings(matchId);
        if (innings.getInningsStatus().equals("COMPLETED"))
            return "Innings already Completed !!";

        CurrentPlay currentPlay = currentPlayRepository.getCurrentPlayForInnings(innings.getMatchId(), innings.getInningsId());
        currentPlay.setCurrentBatsmanId(currentBatsmanId);
        currentPlay.setCurrentBowlerId(currentBowlerId);
        MatchesDTO matchesDTO = matchRepository.getMatchDetails(matchId);
        int batsmanStatsDoesNotExist = 0, bowlersStatsDoesNotExist = 0;

        PlayerStatsDTO batsmanPlayerStatsDTO;

        batsmanPlayerStatsDTO = playerStatsRepository.getPlayerStats(currentBatsmanId, matchId);
        if (batsmanPlayerStatsDTO == null) {
            batsmanPlayerStatsDTO = new PlayerStatsDTO();
            batsmanPlayerStatsDTO.setPlayingStatus("NOT_OUT");
            batsmanPlayerStatsDTO.setPlayerId(currentBatsmanId);
            batsmanPlayerStatsDTO.setMatchId(matchId);
            batsmanStatsDoesNotExist = 1;
        }
        EachRunfreqDTO eachRunfreqDTO;
        if (eachRunFreqRepository.getPlayerStatsInEachRunFreq(matchId, currentBatsmanId) == null) {
            eachRunfreqDTO = new EachRunfreqDTO();
            eachRunfreqDTO.setMatchId(matchId);
            eachRunfreqDTO.setPlayerId(currentBatsmanId);
        } else {
            eachRunfreqDTO = eachRunFreqRepository.getPlayerStatsInEachRunFreq(matchId, currentBatsmanId);
        }
        batsmanPlayerStatsDTO.setEachRunFreq(eachRunfreqDTO);

        if (batsmanPlayerStatsDTO.getPlayingStatus().equals("OUT"))
            return "This Batsman is already out. Please enter a valid Batsman Id";

        if (currentBowlerId == currentPlay.getPreviousBowlerId())
            return "One Bowler Cannot Bowl two overs Simultaneously, Please provide a Different bowler ID";


        PlayerStatsDTO bowlerPlayerStatsDTO;

        bowlerPlayerStatsDTO = playerStatsRepository.getPlayerStats(currentBowlerId, matchId);
        if (bowlerPlayerStatsDTO == null) {
            bowlerPlayerStatsDTO = new PlayerStatsDTO();
            bowlerPlayerStatsDTO.setPlayerId(currentBowlerId);
            bowlerPlayerStatsDTO.setMatchId(matchId);
            bowlersStatsDoesNotExist = 1;
        }


        String resp = playOver(matchesDTO, innings, batsmanPlayerStatsDTO, bowlerPlayerStatsDTO, currentPlay, inningsType.equals("second"));
        // InningsUtils.inningsStats(innings);
        int inningsRepoUpdated = 0;
        if (inningsType.equals("first")) {
            if (currentPlay.getCurrentOver() == matchesDTO.getMatchOvers()) {
                innings.setInningsStatus("COMPLETED");
                resp = "Innings Completed!!";
            }
        } else {
            if (innings.getInningsStatus().equals("COMPLETED") || currentPlay.getCurrentOver() == matchesDTO.getMatchOvers()) {
                matchesDTO.setMatchStatus("COMPLETED");
                resp = "Innings Completed!!";
                inningsRepoUpdated = 1;
                inningsRepository.updateInningsStats(matchId, innings);
                findWinner(matchId, matchesDTO);
                matchRepository.updateMatchDetails(matchesDTO);
                resp += "\nMatch Over!!";
            }

        }
        if(inningsRepoUpdated != 1)
            inningsRepository.updateInningsStats(matchId, innings);
        currentPlayRepository.updateCurrentPlay(innings.getMatchId(), innings.getInningsId(), currentPlay);
        if (batsmanStatsDoesNotExist == 1)
            playerStatsRepository.insertPlayerStats(matchId, batsmanPlayerStatsDTO);
        else
            playerStatsRepository.updatePlayerStats(matchId, batsmanPlayerStatsDTO);

        if (bowlersStatsDoesNotExist == 1)
            playerStatsRepository.insertPlayerStats(matchId, bowlerPlayerStatsDTO);
        else
            playerStatsRepository.updatePlayerStats(matchId, bowlerPlayerStatsDTO);


        return resp;
    }


    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public String playOver(MatchesDTO matchesDTO, Innings innings, PlayerStatsDTO batsmanStats, PlayerStatsDTO bowlerStats, CurrentPlay currentPlay, boolean secondInnings) {
        String resp = "";
        boolean inningsCompleted = false;
        double currentOver = currentPlay.getCurrentOver();
        int currentBall = currentPlay.getCurrentBall() + 1;
        int runsInCurrentOver = currentPlay.getRunsInCurrentOver(), wideBall = 0, noBall = 0;
        for (; currentBall <= 6 + wideBall + noBall; currentBall++) {

            BallSummary ballSummary = new BallSummary();
            ballSummary.setInningsId(innings.getInningsId());
            ballSummary.setMatchId(matchesDTO.getMatchId());
            ballSummary.setCurrentOver((int) currentPlay.getCurrentOver());

            if (innings.getWicketsFallen() < 11) {
                int ballOutcome = (int) (Math.random() * 10);
                batsmanStats.setBallsFaced(batsmanStats.getBallsFaced() + 1);
                if (ballOutcome == 7) {
                    innings.setWicketsFallen(innings.getWicketsFallen() + 1);
                    WicketsHistory wc = new WicketsHistory();
                    wc.setBatsmanId(batsmanStats.getPlayerId());
                    wc.setBatsmanName(batsmanStats.getName());
                    wc.setBowlerId(bowlerStats.getPlayerId());
                    wc.setBowlerName(bowlerStats.getName()); // set the bowler name who took the wicket
                    bowlerStats.setWicketsTaken(bowlerStats.getWicketsTaken() + 1);
                    bowlerStats.setBallsBowled(bowlerStats.getBallsBowled() + 1);
                    bowlerStats.setEconomy(round((double) bowlerStats.getRunsGiven() / bowlerStats.getOversBowled(),2));
                    bowlerStats.setOversBowled((double) (bowlerStats.getBallsBowled() / 6) + ((bowlerStats.getBallsBowled()) % 6) * 0.1);
                    double cov = 0.0;// get current over
                    if (currentBall - wideBall - noBall <= 5) {
                        cov = currentOver + ((currentBall - wideBall - noBall) * 0.1);
                    } else
                        cov = currentOver + 1;


                    wc.setWicketsFallenInOver(round(cov, 1)); // over in which the wicket fall
                    wc.setWicketsDown(innings.getWicketsFallen()); // number of wickets down at that instant
                    wc.setRunScored(innings.getTotalScore()); // Total score at that instant when wicket fall
                    innings.setOversBatted(cov);
                    batsmanStats.setPlayingStatus("OUT");
                    batsmanStats.setAverageStrikeRate(round((double) batsmanStats.getRunsScored() * 100.0 / batsmanStats.getBallsFaced(),2)); // Calculating the current player's strikerate after each ball
                    ballSummary.setOutcomeOnBall("W");

                    // Updating Ball Summary
                    ballSummaryRepository.insertBallSummary(matchesDTO.getMatchId(), innings.getInningsId(), ballSummary);
                    // Updating Wicket history
                    wicketsHistoryRepository.insertWicketsHistory(matchesDTO.getMatchId(), innings.getInningsId(), wc);
                    if (currentBall - wideBall - noBall == 6) {
                        currentPlay.setCurrentBall(0);
                        currentPlay.setCurrentOver(currentOver + 1);
                    } else
                        currentPlay.setCurrentBall(currentBall - wideBall - noBall);
                    if (innings.getWicketsFallen() < 11) {
                        if (currentBall - wideBall - noBall == 6)
                            return "It's a Wickets on the last ball of the over. Please send in new Batsman & a new Bowler.";
                        else
                            return "It's a Wickets. Please send in new Batsman.";
                    } else {
                        innings.setInningsStatus("COMPLETED");
                        return "Innings Over! All wickets fell";
                    }
                } else if (ballOutcome == 8) {
                    wideBall++;
                    innings.setTotalScore(innings.getTotalScore() + 1);
                    innings.setExtraRuns(innings.getExtraRuns() + 1);
                    innings.setNoOfWideBalls(innings.getNoOfWideBalls() + 1);
                    bowlerStats.setRunsGiven(bowlerStats.getRunsGiven() + 1);
                    bowlerStats.setNoOfWideBalls(bowlerStats.getNoOfWideBalls() + 1);
                    if (currentBall - wideBall - noBall == 6)
                        currentPlay.setCurrentBall(0);
                    else
                        currentPlay.setCurrentBall(currentBall - wideBall - noBall);
                    runsInCurrentOver++;
                    ballSummary.setOutcomeOnBall("WB");
                } else if (ballOutcome == 9) {
                    noBall++;
                    innings.setTotalScore(innings.getTotalScore() + 1);
                    innings.setExtraRuns(innings.getExtraRuns() + 1);
                    innings.setNoOfNoBalls(innings.getNoOfNoBalls() + 1);
                    bowlerStats.setRunsGiven(bowlerStats.getRunsGiven() + 1);
                    bowlerStats.setNoOfNoBalls(bowlerStats.getNoOfNoBalls() + 1);
                    runsInCurrentOver++;
                    if (currentBall - wideBall - noBall == 6)
                        currentPlay.setCurrentBall(0);
                    else
                        currentPlay.setCurrentBall(currentBall - wideBall - noBall);
                    ballSummary.setOutcomeOnBall("NB");
                } else {
                    double cov = 0.0;// get current over
                    innings.setTotalScore(innings.getTotalScore() + ballOutcome);
                    batsmanStats.setRunsScored(batsmanStats.getRunsScored() + ballOutcome);
                    bowlerStats.setBallsBowled(bowlerStats.getBallsBowled() + 1);
                    bowlerStats.setOversBowled((double) (bowlerStats.getBallsBowled() / 6) + ((bowlerStats.getBallsBowled()) % 6) * 0.1);
                    bowlerStats.setRunsGiven(bowlerStats.getRunsGiven() + ballOutcome);

                    EachRunfreqDTO currentBatsmanEachRunFreq = batsmanStats.getEachRunFreq();
                    HashMap<Integer, Integer> runsFreq = currentBatsmanEachRunFreq.getEachRunFreq();
                    if (runsFreq.containsKey(ballOutcome))
                        runsFreq.put(ballOutcome, runsFreq.get(ballOutcome) + 1);
                    else
                        runsFreq.put(ballOutcome, 1);
                    currentBatsmanEachRunFreq.setEachRunFreq(runsFreq);
                    batsmanStats.setEachRunFreq(currentBatsmanEachRunFreq);

                    EachRunfreqDTO currentBatsmanRunFreqInTable = eachRunFreqRepository.getPlayerStatsInEachRunFreq(matchesDTO.getMatchId(), batsmanStats.getPlayerId());
                    if (currentBatsmanRunFreqInTable == null)
                        eachRunFreqRepository.insertInEachRunFreq(matchesDTO.getMatchId(), batsmanStats.getPlayerId(), batsmanStats.getEachRunFreq());
                    else {
                        eachRunFreqRepository.updateInEachRunFreq(matchesDTO.getMatchId(), batsmanStats.getPlayerId(), batsmanStats.getEachRunFreq());
                    }

                    if (currentBall - wideBall - noBall <= 5) {
                        cov = currentOver + ((currentBall - wideBall - noBall) * 0.1);
                    } else cov = currentOver + 1;
                    innings.setOversBatted(cov);
                    runsInCurrentOver += ballOutcome;
                    ballSummary.setOutcomeOnBall(Integer.toString(ballOutcome));
                }
                ballSummaryRepository.insertBallSummary(matchesDTO.getMatchId(), innings.getInningsId(), ballSummary);
                bowlerStats.setEconomy(round((double) bowlerStats.getRunsGiven() / bowlerStats.getOversBowled(),2));
                batsmanStats.setAverageStrikeRate(round(((double) batsmanStats.getRunsScored() * 100.0) / batsmanStats.getBallsFaced(), 2)); // Calculating the current player's strikerate after each ball
                if (currentBall - wideBall - noBall == 6)
                    currentPlay.setCurrentBall(0);
                else
                    currentPlay.setCurrentBall(currentBall - wideBall - noBall);

            } else break;
            if (secondInnings) {
                Innings firstInnings = getFirstInnings(matchesDTO.getMatchId());
                if (innings.getTotalScore() > firstInnings.getTotalScore()) {
                    innings.setInningsStatus("COMPLETED");
                    inningsCompleted = true;
                    resp = "Innings Completed !!";
                    break;
                }
            }

        }
        if (runsInCurrentOver == 0)
            bowlerStats.setMaidenOvers(bowlerStats.getMaidenOvers() + 1);

        currentPlay.setCurrentOver(currentOver + 1);
        currentPlay.setCurrentBall(0);
        currentPlay.setPreviousBowlerId(currentPlay.getCurrentBowlerId());
        if (inningsCompleted)
            return resp;
        else
            return "Over Completed!!. Please send in next Bowler";
    }
}
