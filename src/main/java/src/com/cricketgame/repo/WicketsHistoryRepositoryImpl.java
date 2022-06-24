package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.models.WicketsHistory;

import java.util.List;

@Repository
public class WicketsHistoryRepositoryImpl {
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final RowMapper<WicketsHistory> wicketsHistoryRowMapper = new BeanPropertyRowMapper(WicketsHistory.class);


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

    public List<WicketsHistory> getWicketsHistory(int matchId, int inningsId) {
        String sql = "select * from `WicketsHistory` where matchId = ? and inningsId = ?";
        return jdbcTemplate.query(sql, wicketsHistoryRowMapper, matchId, inningsId);
    }
}
