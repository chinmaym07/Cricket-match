package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
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

    public List<WicketsHistory> getWicketsHistory(int matchId, int inningsId) {
        String sql = "select * from `WicketsHistory` where matchId = ? and inningsId = ?";
        return jdbcTemplate.query(sql, wicketsHistoryRowMapper, matchId, inningsId);
    }

    public Player getPlayerInfo(int playerId) {
        String sql = "Select * from `Player` p where playerId = ? ";
        return jdbcTemplate.queryForObject(sql, playerRowMapper, playerId);
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
            System.out.println("Match Status updated !!");
        else
            System.out.println("No Update occurred !");
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
        Innings firstInnings =  getFirstInnings(matchId);
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
        insertCurrentPlay(matchId,secondInnings.getInningsId(), currentPlayInningsSecond);
        return secondInnings;
    }


    public String playOver(MatchesDTO matchesDTO, Innings innings, PlayerStatsDTO batsmanStats, PlayerStatsDTO bowlerStats, CurrentPlay currentPlay) {

        double currentOver = currentPlay.getCurrentOver();
        int currentBall = currentPlay.getCurrentBall();
        int runsInCurrentOver = 0, wideBall = 0, noBall = 0;
        for (;currentBall < 6 + wideBall + noBall; currentBall++) {
            BallSummary ballSummary = new BallSummary();

            if(innings.getFallOfWickets() < 11)
            {
                int ballOutcome = (int) (Math.random() * 10);
                batsmanStats.setBallsFaced(batsmanStats.getBallsFaced() + 1);
                if(ballOutcome == 7) {
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
                    batsmanStats.setAverageStrikeRate(((double) batsmanStats.getRunsScored() * 100.0) / batsmanStats.getBallsFaced()); // Calculating the current player's strikerate after each ball
                    ballSummary.setOutcomeOnBall("W");
                    if (innings.getFallOfWickets() < 11) {
                        updateInningsStats(innings.getMatchId(),innings);
                        updateCurrentPlay(innings.getMatchId(), innings.getInningsId(), currentPlay);
                        return "It's a Wickets. Please send in new Batsman.";
                    } else {
                        return "Innings Over! All wickets fell";
                    }
                } else if(ballOutcome == 8)
                {
                    wideBall++;
                    innings.setTotalScore(innings.getTotalScore() + 1);
                    innings.setExtraRuns(innings.getExtraRuns() + 1);
                    innings.setNoOfWideBalls(innings.getNoOfWideBalls() + 1);
                    bowlerStats.setRunsGiven(bowlerStats.getRunsGiven() + 1);
                    bowlerStats.setNoOfWideBalls(bowlerStats.getNoOfWideBalls() + 1);
                    runsInCurrentOver++;
                    ballSummary.setOutcomeOnBall("WB");
                } else if(ballOutcome == 9) {
                    noBall++;
                    innings.setTotalScore(innings.getTotalScore() + 1);
                    innings.setExtraRuns(innings.getExtraRuns() + 1);
                    innings.setNoOfNoBalls(innings.getNoOfNoBalls() + 1);
                    bowlerStats.setRunsGiven(bowlerStats.getRunsGiven() + 1);
                    bowlerStats.setNoOfNoBalls(bowlerStats.getNoOfNoBalls() + 1);
                    runsInCurrentOver++;
                    ballSummary.setOutcomeOnBall("NB");
                } else {
                    ballSummary.setOutcomeOnBall(Integer.toString(ballOutcome));
                }
            }
            currentPlay.setCurrentBall(currentBall);
        }
        currentPlay.setCurrentOver(currentPlay.getCurrentOver()+1);
        return "";
    }

    public void insertWicketsHistory(int matchId, int inningsId, WicketsHistory currentWicket) {
        String sql = "Insert into `Wicketshistory` (inningsId, matchId, batsmanId, bowlerId, wicketsFallenInOver, runScored, wicketsDown) values (?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, inningsId, matchId, currentWicket.getBatsmanId(), currentWicket.getBowlerId(), currentWicket.getWicketsFallenInOver(), currentWicket.getRunScored(), currentWicket.getWicketsDown());
        if (status != 0)
            System.out.println("Wickets History Updated");
        else
            System.out.println("No update Occured !!");

    }

    public void insertBallSummary(int matchId, Innings innings, int ballId, String ballOutcome) {
        String sql = "Insert into `Ballsummary` (inningsId, matchId, ballId, outcomeOnBall) values (?,?,?,?)";
        int status = jdbcTemplate.update(sql, innings.getInningsId(),matchId, ballId, ballOutcome);
        if (status != 0)
            System.out.println("Innings Stats Updated");
        else
            System.out.println("No update Occurred !!");
    }

    public void insertInningsStats(int matchId, Innings innings) {
        String sql = "Insert into `Innings` (inningsId, matchId, battingTeamId, bowlingTeamId, totalScore, wicketsFallen, extraRuns, noOfNoBalls, noOfWideBalls, oversBatted,inningsStatus) values (?,?,?,?,?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, innings.getInningsId(), matchId, innings.getBattingTeamId(), innings.getBowlingTeamId(), innings.getTotalScore(), innings.getFallOfWickets(), innings.getExtraRuns(), innings.getNoOfNoBalls(), innings.getNoOfWideBalls(), innings.getOversBatted(), innings.getInningsStatus());
        if (status != 0)
            System.out.println("Innings Stats Updated");
        else
            System.out.println("No update Occurred !!");
    }

    public void updateInningsStats(int matchId, Innings innings) {
        String sql = "Update `Innings` set totalScore = ? , wicketsFallen = ? , extraRuns = ? , noOfNoBalls = ? , noOfWideBalls = ? , oversBatted = ? ,inningsStatus = ? where inningsId = ? and matchId = ?";
        int status = jdbcTemplate.update(sql, innings.getTotalScore(), innings.getFallOfWickets(), innings.getExtraRuns(), innings.getNoOfNoBalls(), innings.getNoOfWideBalls(), innings.getOversBatted(), innings.getInningsStatus(),innings.getInningsId(), matchId);
        if (status != 0)
            System.out.println("Innings Stats Updated");
        else
            System.out.println("No update Occurred !!");
    }

    public void insertCurrentPlay(int matchId, int inningsId, CurrentPlay currentPlay) {
        String sql = "Insert into `Currentplay` (inningsId, matchId, currentOver, currentBall, currentBowlerId, currentBatsmanId, previousBowlerId) values (?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, inningsId, matchId, currentPlay.getCurrentOver(),currentPlay.getCurrentBall(),currentPlay.getCurrentBowlerId(),currentPlay.getCurrentBatsmanId(),currentPlay.getPreviousBowlerId());
        if (status != 0)
            System.out.println("Current Play inserted");
        else
            System.out.println("No insert Occurred !!");
    }

    public void updateCurrentPlay(int matchId, int inningsId, CurrentPlay currentPlay) {
        String sql = "Update `Currentplay` set  currentOver = ? , currentBall = ? , currentBowlerId = ? , currentBatsmanId = ? , previousBowlerId = ? where inningsId = ? and matchId = ?";
        int status = jdbcTemplate.update(sql, currentPlay.getCurrentOver(),currentPlay.getCurrentBall(),currentPlay.getCurrentBowlerId(),currentPlay.getCurrentBatsmanId(),currentPlay.getPreviousBowlerId(),inningsId, matchId);
        if (status != 0)
            System.out.println("Current Play Updated");
        else
            System.out.println("No update Occurred !!");
    }

    public CurrentPlay getCurrentPlayForInnings(int matchId, int inningsId) {
        String sql = "Select * from `Currentplay` where matchId = ? and inningsId = ?";
        return jdbcTemplate.queryForObject(sql,CurrentPlay.class,matchId,inningsId);
    }

    public boolean checkCurrentPlayPresent(int matchId, int inningsId) {
        String sql = "Select * from `Currentplay` where matchId = ? and inningsId = ?";
        Integer status = jdbcTemplate.queryForObject(sql,Integer.class,matchId,inningsId);
        return status != null & status > 0;
    }

    public String startBowling() {

        return "";
    }
}
