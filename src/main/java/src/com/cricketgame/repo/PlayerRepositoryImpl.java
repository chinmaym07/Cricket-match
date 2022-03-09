package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.interfaces.PlayerRepository;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.models.PlayerStats;

import java.util.List;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private final RowMapper<Player> playerRowMapper = new BeanPropertyRowMapper<>(Player.class);
    private final RowMapper<PlayerStatsDTO> playerStatsDTORowMapper = new BeanPropertyRowMapper<>(PlayerStatsDTO.class);

    public List<Player> getTeamPlayers(int teamId) {
        String sql = "select * from `Player` p inner join `Teamplayer` t on t.playerId=p.playerId where teamId= ? ";
        return jdbcTemplate.query(sql, playerRowMapper, teamId);
    }

    public Player getPlayer(int playerId) {
        String sql = "select * from `Player` p where p.playerId= ? ";
        return jdbcTemplate.queryForObject(sql, playerRowMapper, playerId);
    }

    public PlayerStatsDTO getPlayerStats(int playerId, int matchId) {
        String sql = "Select * from `Player` p inner join `Playerstats` pst on p.playerId = pst.playerId inner join `Teamplayer` tmp on tmp.playerId = p.playerId inner join `Team` t on t.teamId = tmp.teamId where p.playerId = ? and pst.matchId= ? ";
        return jdbcTemplate.queryForObject(sql, playerStatsDTORowMapper, playerId ,matchId);
    }
}
