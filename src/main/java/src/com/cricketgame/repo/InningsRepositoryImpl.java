package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.models.Innings;

import java.util.List;

@Repository
public class InningsRepositoryImpl implements InningsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private final RowMapper<Innings> inningsRowMapper = new BeanPropertyRowMapper(Innings.class);


    public int getInningsIdCount() {
        String sql = "Select count(inningsId) from innings";
        return jdbcTemplate.queryForObject(sql, Integer.class);
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
        return firstInnings;
    }

    public Innings getSecondInnings(int matchId) {
        Innings secondInnings = getMatchInnings(matchId).get(1);
        return secondInnings;
    }


    public Innings startFirstInnings(int matchId) {
        updateInningsStatus(matchId, "first");
        Innings firstInnings = getFirstInnings(matchId);
        return firstInnings;
    }

    public Innings startSecondInnings(int matchId) {
        updateInningsStatus(matchId, "second");
        Innings secondInnings = getSecondInnings(matchId);
        return secondInnings;
    }


    public void insertInningsStats(int matchId, Innings innings) {
        String sql = "Insert into `Innings` (inningsId, matchId, battingTeamId, bowlingTeamId, totalScore, wicketsFallen, extraRuns, noOfNoBalls, noOfWideBalls, oversBatted,inningsStatus) values (?,?,?,?,?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, innings.getInningsId(), matchId, innings.getBattingTeamId(), innings.getBowlingTeamId(), innings.getTotalScore(), innings.getWicketsFallen(), innings.getExtraRuns(), innings.getNoOfNoBalls(), innings.getNoOfWideBalls(), innings.getOversBatted(), innings.getInningsStatus());
        if (status != 0)
            System.out.println("Innings Stats inserted");
        else
            System.out.println("No Innings insert Occurred !!");
    }

    public void updateInningsStats(int matchId, Innings innings) {
        String sql = "Update `Innings` set totalScore = ? , wicketsFallen = ? , extraRuns = ? , noOfNoBalls = ? , noOfWideBalls = ? , oversBatted = ? ,inningsStatus = ? where inningsId = ? and matchId = ?";
        int status = jdbcTemplate.update(sql, innings.getTotalScore(), innings.getWicketsFallen(), innings.getExtraRuns(), innings.getNoOfNoBalls(), innings.getNoOfWideBalls(), innings.getOversBatted(), innings.getInningsStatus(), innings.getInningsId(), matchId);
        if (status != 0)
            System.out.println("Innings Stats Updated");
        else
            System.out.println("No Innings update Occurred !!");
    }

    public void updatePartialInningsDetails(Innings innings) {
        String sql = "Insert into innings (inningsId, matchId, battingTeamId, bowlingTeamId) values (?,?,?,?)";

        int status = jdbcTemplate.update(sql, innings.getInningsId(), innings.getMatchId(), innings.getBattingTeamId(), innings.getBowlingTeamId());
        if (status != 0)
            System.out.println("Updated Partial Innings details");
        else
            System.out.println("No Partial Innings Update occurred");
    }


}
