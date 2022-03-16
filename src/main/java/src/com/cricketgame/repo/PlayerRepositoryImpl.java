package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.models.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private final RowMapper<Player> playerRowMapper = new BeanPropertyRowMapper<Player>() {
        public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
            Player player = new Player();
            player.setPlayerId(rs.getInt("playerId"));
            player.setName(rs.getString("name"));
            player.setRole(rs.getString("role"));
            return player;
        }
    };

    public List<Player> getTeamPlayers(int teamId) {
        String sql = "select * from `Player` p inner join `Teamplayer` t on t.playerId=p.playerId where teamId= ? ";
        return jdbcTemplate.query(sql, playerRowMapper, teamId);
    }

    public Player getPlayer(int playerId) {
        String sql = "select * from `Player` p where p.playerId= ? ";
        return jdbcTemplate.queryForObject(sql, playerRowMapper, playerId);
    }

}
