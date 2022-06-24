package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.utils.BallSummary;

import java.util.List;

@Repository
public class BallSummaryRepositoryImpl implements BallSummaryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void insertBallSummary(int matchId, int inningsId, BallSummary ballSummary) {
        String sql = "Insert into `Ballsummary` (inningsId, matchId, outcomeOnBall, currentOver) values ( ?, ?, ?, ? )";
        int status = jdbcTemplate.update(sql, inningsId, matchId, ballSummary.getOutcomeOnBall(), ballSummary.getCurrentOver());
        if (status != 0)
            System.out.println("Ball Summary Updated");
        else
            System.out.println("No Ball Summary update Occurred !!" + ballSummary.getBallId());
    }

    public List<String> getBallSummaryForInnings(int matchId, int inningsId, int currentOver) {
        String sql = "Select outcomeOnBall from `Ballsummary` where matchId = ? and inningsId = ? and currentOver = ?";
        return  jdbcTemplate.queryForList(sql, String.class, matchId, inningsId, currentOver);
    }
}
