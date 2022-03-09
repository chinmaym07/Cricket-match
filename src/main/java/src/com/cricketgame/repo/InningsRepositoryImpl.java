package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.DTO.ResponseDTOs.InningsDTO;
import src.com.cricketgame.interfaces.InningsRepository;
import src.com.cricketgame.models.Innings;

import java.util.List;
@Repository
public class InningsRepositoryImpl implements InningsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private final RowMapper<Innings> rowMapper = new BeanPropertyRowMapper(Innings.class);

    public List<Innings> getMatchInnings(int matchId) {
        String sql = "Select * from `Innings` where matchId = ? ";

        return jdbcTemplate.query(sql,rowMapper,matchId);
    }
}
