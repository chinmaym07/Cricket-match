package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.interfaces.InningsRepository;
import src.com.cricketgame.models.CurrentPlay;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.models.WicketsHistory;
import src.com.cricketgame.utils.BallSummary;

import java.util.List;

@Repository
public class InningsRepositoryImpl implements InningsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private final RowMapper<Innings> inningsRowMapper = new BeanPropertyRowMapper(Innings.class);
    private final RowMapper<WicketsHistory> wicketsHistoryRowMapper = new BeanPropertyRowMapper(WicketsHistory.class);
    private final RowMapper<Player> playerRowMapper = new BeanPropertyRowMapper<>(Player.class);
    private final RowMapper<CurrentPlay> currentPlayRowMapper = new BeanPropertyRowMapper<>(CurrentPlay.class);
    private final RowMapper<EachRunfreqDTO> eachRunfreqDTORowMapper = new BeanPropertyRowMapper<>(EachRunfreqDTO.class);

    public List<WicketsHistory> getWicketsHistory(int matchId, int inningsId) {
        String sql = "select * from `WicketsHistory` where matchId = ? and inningsId = ?";
        return jdbcTemplate.query(sql, wicketsHistoryRowMapper, matchId, inningsId);
    }

    public Player getPlayerInfo(int playerId) {
        String sql = "Select * from `Player` p where playerId = ? ";
        return jdbcTemplate.queryForObject(sql, playerRowMapper, playerId);
    }

    private void increaseRunFreq(EachRunfreqDTO eachRunfreqDTO, int ballOutcome) {
        switch (ballOutcome) {
            case 1:
                eachRunfreqDTO.setOnes(eachRunfreqDTO.getOnes() + 1);
                break;
            case 2:
                eachRunfreqDTO.setTwos(eachRunfreqDTO.getTwos() + 1);
                break;
            case 3:
                eachRunfreqDTO.setThrees(eachRunfreqDTO.getThrees() + 1);
                break;
            case 4:
                eachRunfreqDTO.setFours(eachRunfreqDTO.getFours() + 1);
                break;
            case 5:
                eachRunfreqDTO.setFives(eachRunfreqDTO.getFives() + 1);
                break;
            case 6:
                eachRunfreqDTO.setSixes(eachRunfreqDTO.getSixes() + 1);
                break;
        }
    }

    public void updateInningsStatus(int matchId, String inningsType) {
        Innings innings;
        if (inningsType == "first")
            innings = getFirstInnings(matchId);
        else
            innings = getSecondInnings(matchId);

        String sql = "Update `Innings` set inningsStatus = ? where matchId = ? and inningsId = ?";
        int status = jdbcTemplate.update(sql, "ONGOING", matchId, innings.getInningsId());
        if (status != 0)
            System.out.println("Innings Status updated !!");
        else
            System.out.println("No Innings Status Update occurred !");
    }

    public List<Innings> getMatchInnings(int matchId) {
        String sql = "Select * from `Innings` where matchId = ? ";
        return jdbcTemplate.query(sql, inningsRowMapper, matchId);
    }

    public Innings getFirstInnings(int matchId) {
        Innings firstInnings = getMatchInnings(matchId).get(0);
        List<WicketsHistory> wicketsHistories = getWicketsHistory(matchId, firstInnings.getInningsId());
        for (WicketsHistory wicketsHistory : wicketsHistories) {
            Player batsman = getPlayerInfo(wicketsHistory.getBatsmanId());
            Player bowler = getPlayerInfo(wicketsHistory.getBowlerId());
            wicketsHistory.setBatsmanName(batsman.getName());
            wicketsHistory.setBowlerName(bowler.getName());
        }
        firstInnings.setWicketsFallenHistory(wicketsHistories);
        return firstInnings;
    }

    public Innings getSecondInnings(int matchId) {
        Innings secondInnings = getMatchInnings(matchId).get(1);
        List<WicketsHistory> wicketsHistories = getWicketsHistory(matchId, secondInnings.getInningsId());
        for (WicketsHistory wicketsHistory : wicketsHistories) {
            Player batsman = getPlayerInfo(wicketsHistory.getBatsmanId());
            Player bowler = getPlayerInfo(wicketsHistory.getBowlerId());
            wicketsHistory.setBatsmanName(batsman.getName());
            wicketsHistory.setBowlerName(bowler.getName());
        }
        secondInnings.setWicketsFallenHistory(wicketsHistories);
        return secondInnings;
    }


    public Innings startFirstInnings(int matchId) {
        updateInningsStatus(matchId, "first");
        Innings firstInnings = getFirstInnings(matchId);
        CurrentPlay currentPlayInningsfirst = new CurrentPlay();
        currentPlayInningsfirst.setMatchId(firstInnings.getMatchId());
        currentPlayInningsfirst.setInningsId(firstInnings.getInningsId());
        insertCurrentPlay(matchId, firstInnings.getInningsId(), currentPlayInningsfirst);
        return firstInnings;
    }

    public Innings startSecondInnings(int matchId) {
        updateInningsStatus(matchId, "second");
        Innings secondInnings = getSecondInnings(matchId);

        CurrentPlay currentPlayInningsSecond = new CurrentPlay();
        currentPlayInningsSecond.setInningsId(secondInnings.getInningsId());
        currentPlayInningsSecond.setMatchId(secondInnings.getMatchId());
        insertCurrentPlay(matchId, secondInnings.getInningsId(), currentPlayInningsSecond);
        return secondInnings;
    }


    public String playOver(MatchesDTO matchesDTO, Innings innings, PlayerStatsDTO batsmanStats, PlayerStatsDTO bowlerStats, CurrentPlay currentPlay) {

        double currentOver = currentPlay.getCurrentOver();
        int currentBall = currentPlay.getCurrentBall();
        int runsInCurrentOver = 0, wideBall = 0, noBall = 0;
        for (; currentBall < 6 + wideBall + noBall; currentBall++) {

            BallSummary ballSummary = new BallSummary();
            ballSummary.setInningsId(innings.getInningsId());
            ballSummary.setMatchId(matchesDTO.getMatchId());

            if (innings.getFallOfWickets() < 11) {
                int ballOutcome = (int) (Math.random() * 10);
                batsmanStats.setBallsFaced(batsmanStats.getBallsFaced() + 1);
                if (ballOutcome == 7) {
                    innings.setFallOfWickets(innings.getFallOfWickets() + 1);
                    WicketsHistory wc = new WicketsHistory();
                    wc.setBatsmanId(batsmanStats.getPlayerId());
                    wc.setBatsmanName(batsmanStats.getName());
                    wc.setBowlerId(bowlerStats.getPlayerId());
                    wc.setBowlerName(bowlerStats.getName()); // set the bowler name who took the wicket
                    bowlerStats.setEconomy(((double) bowlerStats.getRunsGiven()) / bowlerStats.getOversBowled());
                    bowlerStats.setWicketsTaken(bowlerStats.getWicketsTaken() + 1);
                    bowlerStats.setBallsBowled(bowlerStats.getBallsBowled() + 1);
                    bowlerStats.setOversBowled((double) (bowlerStats.getBallsBowled() / 6) + ((bowlerStats.getBallsBowled()) % 6) * 0.1);
                    double cov = 0.0;// get current over
                    if (currentBall - wideBall - noBall < 5) {
                        cov = currentOver + ((currentBall + 1 - wideBall - noBall) * 0.1);
                    } else cov = currentOver + 1;
                    wc.setWicketsFallenInOver(cov); // over in which the wicket fall
                    wc.setWicketsDown(innings.getFallOfWickets()); // number of wickets down at that instant
                    wc.setRunScored(innings.getTotalScore()); // Total score at that instant when wicket fall
                    innings.setOversBatted(cov);
                    batsmanStats.setPlayingStatus("OUT");
                    batsmanStats.setAverageStrikeRate(((double) batsmanStats.getRunsScored() * 100.0) / batsmanStats.getBallsFaced()); // Calculating the current player's strikerate after each ball
                    ballSummary.setOutcomeOnBall("W");

                    // Updating Ball Summary
                    insertBallSummary(matchesDTO.getMatchId(), innings.getInningsId(), ballSummary);
                    // Updating Wicket history
                    insertWicketsHistory(matchesDTO.getMatchId(), innings.getInningsId(), wc);
                    if (innings.getFallOfWickets() < 11) {
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
                    ballSummary.setOutcomeOnBall("NB");
                } else {
                    double cov = 0.0;// get current over
                    innings.setTotalScore(innings.getTotalScore() + ballOutcome);
                    batsmanStats.setRunsScored(batsmanStats.getRunsScored() + ballOutcome);
                    bowlerStats.setBallsBowled(bowlerStats.getBallsBowled() + 1);
                    bowlerStats.setOversBowled((double) (bowlerStats.getBallsBowled() / 6) + ((bowlerStats.getBallsBowled()) % 6) * 0.1);
                    bowlerStats.setRunsGiven(bowlerStats.getRunsGiven() + ballOutcome);

                    EachRunfreqDTO currentBatsmanEachRunFreq = batsmanStats.getEachRunFreq();
                    increaseRunFreq(currentBatsmanEachRunFreq, ballOutcome);
                    batsmanStats.setEachRunFreq(currentBatsmanEachRunFreq);


                    EachRunfreqDTO currentBatsmanRunFreqInTable = getPlayerStatsInEachRunFreq(matchesDTO.getMatchId(),batsmanStats.getPlayerId());
                    if(currentBatsmanRunFreqInTable == null)
                        insertInEachRunFreq(matchesDTO.getMatchId(),batsmanStats.getPlayerId(),batsmanStats.getEachRunFreq());
                    else {
                        updateInEachRunFreq(matchesDTO.getMatchId(), batsmanStats.getPlayerId(), batsmanStats.getEachRunFreq());
                    }

                    if (currentBall - wideBall - noBall < 5) {
                        cov = currentOver + ((currentBall + 1 - wideBall - noBall) * 0.1);
                    } else cov = currentOver + 1;
                    innings.setOversBatted(cov);
                    runsInCurrentOver += ballOutcome;
                    ballSummary.setOutcomeOnBall(Integer.toString(ballOutcome));
                }
                insertBallSummary(matchesDTO.getMatchId(), innings.getInningsId(), ballSummary);
                bowlerStats.setEconomy(((double) bowlerStats.getRunsGiven()) / bowlerStats.getOversBowled());
                batsmanStats.setAverageStrikeRate(((double) batsmanStats.getRunsScored() * 100.0) / batsmanStats.getBallsFaced()); // Calculating the current player's strikerate after each ball
                currentPlay.setCurrentBall(currentBall);
            } else break;
        }
        double overs;
        if (currentBall - wideBall - noBall < 6) {
            overs = currentOver + ((currentBall - wideBall - noBall) * 0.1);
        } else overs = currentOver + 1;
        innings.setOversBatted(overs);
        if (runsInCurrentOver == 0)
            bowlerStats.setMaidenOvers(bowlerStats.getMaidenOvers() + 1);

        currentPlay.setCurrentOver(currentPlay.getCurrentOver() + 1);
        currentPlay.setPreviousBowlerId(currentPlay.getCurrentBowlerId());
        return "Over Completed!!. Please send in next Bowler";
    }

    public void insertWicketsHistory(int matchId, int inningsId, WicketsHistory currentWicket) {
        String sql1 = "SET foreign_key_checks = 0";
        String sql2 = "SET foreign_key_checks = 1";
        jdbcTemplate.execute(sql1);
        String sql = "Insert into `Wicketshistory` (inningsId, matchId, batsmanId, bowlerId, wicketsFallenInOver, runScored, wicketsDown) values (?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, inningsId, matchId, currentWicket.getBatsmanId(), currentWicket.getBowlerId(), currentWicket.getWicketsFallenInOver(), currentWicket.getRunScored(), currentWicket.getWicketsDown());
        if (status != 0)
            System.out.println("Wickets History insert");
        else
            System.out.println("No WicketsHistory insert Occurred !!");
        jdbcTemplate.execute(sql2);
    }

    public void insertBallSummary(int matchId, int inningsId, BallSummary ballSummary) {
        String sql = "Insert into `Ballsummary` (inningsId, matchId, outcomeOnBall) values (?,?,?)";
        int status = jdbcTemplate.update(sql, inningsId, matchId, ballSummary.getOutcomeOnBall());
        if (status != 0)
            System.out.println("Ball Summary Updated");
        else
            System.out.println("No Ball Summary update Occurred !!" + ballSummary.getBallId());
    }

    public void insertInningsStats(int matchId, Innings innings) {
        String sql = "Insert into `Innings` (inningsId, matchId, battingTeamId, bowlingTeamId, totalScore, wicketsFallen, extraRuns, noOfNoBalls, noOfWideBalls, oversBatted,inningsStatus) values (?,?,?,?,?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, innings.getInningsId(), matchId, innings.getBattingTeamId(), innings.getBowlingTeamId(), innings.getTotalScore(), innings.getFallOfWickets(), innings.getExtraRuns(), innings.getNoOfNoBalls(), innings.getNoOfWideBalls(), innings.getOversBatted(), innings.getInningsStatus());
        if (status != 0)
            System.out.println("Innings Stats inserted");
        else
            System.out.println("No Innings insert Occurred !!");
    }

    public void updateInningsStats(int matchId, Innings innings) {
        String sql = "Update `Innings` set totalScore = ? , wicketsFallen = ? , extraRuns = ? , noOfNoBalls = ? , noOfWideBalls = ? , oversBatted = ? ,inningsStatus = ? where inningsId = ? and matchId = ?";
        int status = jdbcTemplate.update(sql, innings.getTotalScore(), innings.getFallOfWickets(), innings.getExtraRuns(), innings.getNoOfNoBalls(), innings.getNoOfWideBalls(), innings.getOversBatted(), innings.getInningsStatus(), innings.getInningsId(), matchId);
        if (status != 0)
            System.out.println("Innings Stats Updated");
        else
            System.out.println("No Innings update Occurred !!");
    }

    public void insertCurrentPlay(int matchId, int inningsId, CurrentPlay currentPlay) {
        String sql1 = "SET foreign_key_checks = 0";
        String sql2 = "SET foreign_key_checks = 1";
        jdbcTemplate.execute(sql1);
        String sql = "Insert into `Currentplay` (inningsId, matchId, currentOver, currentBall, currentBowlerId, currentBatsmanId, previousBowlerId) values (?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, inningsId, matchId, currentPlay.getCurrentOver(), currentPlay.getCurrentBall(), currentPlay.getCurrentBowlerId(), currentPlay.getCurrentBatsmanId(), currentPlay.getPreviousBowlerId());
        if (status != 0)
            System.out.println("Current Play inserted");
        else
            System.out.println("No Current Play insert Occurred !!");
        jdbcTemplate.execute(sql2);
    }

    public void updateCurrentPlay(int matchId, int inningsId, CurrentPlay currentPlay) {
        String sql1 = "SET foreign_key_checks = 0";

        String sql2 = "SET foreign_key_checks = 1";
        jdbcTemplate.execute(sql1);
        String sql = "Update `Currentplay` set  currentOver = ? , currentBall = ? , currentBowlerId = ? , currentBatsmanId = ? , previousBowlerId = ? where inningsId = ? and matchId = ?";
        int status = jdbcTemplate.update(sql, currentPlay.getCurrentOver(), currentPlay.getCurrentBall(), currentPlay.getCurrentBowlerId(), currentPlay.getCurrentBatsmanId(), currentPlay.getPreviousBowlerId(), inningsId, matchId);
        if (status != 0)
            System.out.println("Current Play Updated");
        else
            System.out.println("No Current Play update Occurred !!");
        jdbcTemplate.execute(sql2);
    }

    public boolean checkForPlayerStats(int matchId, int playerId) {
        Integer status = null;
        try {
            String sql = "Select * from `Playerstats` where matchId = ? and playerId = ?";
            status = jdbcTemplate.queryForObject(sql, Integer.class, matchId, playerId);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return status != null && status > 0;
    }

    public CurrentPlay getCurrentPlayForInnings(int matchId, int inningsId) {
        String sql = "Select * from `Currentplay` where matchId = ? and inningsId = ?";
        return jdbcTemplate.queryForObject(sql, currentPlayRowMapper, matchId, inningsId);
    }

    public boolean checkCurrentPlayPresent(int matchId, int inningsId) {
        Integer status = null;
        try {
            String sql = "Select * from `Currentplay` where matchId = ? and inningsId = ?";
            status = jdbcTemplate.queryForObject(sql, Integer.class, matchId, inningsId);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return status != null && status > 0;
    }

    public EachRunfreqDTO getPlayerStatsInEachRunFreq(int matchId, int playerId) {
        EachRunfreqDTO eachRunfreqDTO = null;
        try {
            String sql = "Select * from `Eachrunfreq` where matchId= ? and playerId = ? ";
            eachRunfreqDTO = jdbcTemplate.queryForObject(sql, eachRunfreqDTORowMapper, matchId, playerId);
        } catch (EmptyResultDataAccessException e) {

        }
        return eachRunfreqDTO;
    }

    public boolean checkForPlayerStatsInEachRunFreq(int matchId, int playerId) {
        Integer result = null;
        try {
            String sql = "Select * from `Eachrunfreq` where matchId= ? and playerId = ? ";
            result = jdbcTemplate.queryForObject(sql, Integer.class, matchId, playerId);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return result != null && result > 0;
    }

    public void insertInEachRunFreq(int matchId, int playerId, EachRunfreqDTO runsFreq) {
        String sql = "Insert into `Eachrunfreq` (matchId,playerId,ones,twos,threes,fours,fives,sixes) values (?,?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, matchId, playerId, runsFreq.getOnes(), runsFreq.getTwos(), runsFreq.getThrees(), runsFreq.getFours(), runsFreq.getFives(), runsFreq.getSixes());
        if (status != 0)
            System.out.println("Runs freq inserted");
        else
            System.out.println("No Runs freq insert Occurred !!");
    }


    public void updateInEachRunFreq(int matchId, int playerId, EachRunfreqDTO runsfreq) {
        String sql = "Update `Eachrunfreq` set ones = ?, twos = ?, threes = ?, fours = ?, fives = ?, sixes = ? where matchId = ? and playerId = ? ";
        int status = jdbcTemplate.update(sql, runsfreq.getOnes(), runsfreq.getTwos(), runsfreq.getThrees(), runsfreq.getFours(), runsfreq.getFives(), runsfreq.getSixes(), matchId, playerId);
        if (status != 0)
            System.out.println("Runs freq updated");
        else
            System.out.println("No Runs Freq update Occurred !!");
    }

}
