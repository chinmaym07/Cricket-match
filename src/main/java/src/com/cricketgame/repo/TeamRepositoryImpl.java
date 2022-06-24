package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.models.Team;

import java.util.List;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    public final RowMapper<Team> rowMapper = new BeanPropertyRowMapper<>(Team.class);

    public List<Team> getAllTeams() {
        String sql = "Select * from `Team`";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Team getSpecificTeamById(int teamId) {
        String sql = "Select * from `Team` t where t.teamId = ? ";
        return jdbcTemplate.queryForObject(sql, rowMapper, teamId);
    }

    public Team getSpecificTeamByName(String name) {
        String sql = "Select * from `Team` t where t.teamName = ? ";
        return jdbcTemplate.queryForObject(sql, rowMapper, name);
    }

    public int getSpecificTeamIdByName(String name) {
        String sql = "Select teamId from `Team` t where t.teamName = ? ";

        return jdbcTemplate.queryForObject(sql, Integer.class, name);
    }

}
