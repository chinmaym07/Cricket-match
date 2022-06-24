package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.models.CurrentPlay;

@Repository
public class CurrentPlayRepositoryImpl implements CurrentPlayRepository {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final RowMapper<CurrentPlay> currentPlayRowMapper = new BeanPropertyRowMapper<>(CurrentPlay.class);

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
}
