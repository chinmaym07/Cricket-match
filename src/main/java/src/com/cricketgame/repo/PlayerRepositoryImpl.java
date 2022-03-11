package src.com.cricketgame.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.interfaces.PlayerRepository;
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
    private final RowMapper<PlayerStatsDTO> playerStatsDTORowMapper = new BeanPropertyRowMapper<PlayerStatsDTO>() {
        public PlayerStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PlayerStatsDTO playerStatsDTO = new PlayerStatsDTO();
            playerStatsDTO.setPlayerId(rs.getInt("playerId"));
            playerStatsDTO.setMatchId(rs.getInt("matchId"));
            playerStatsDTO.setName(rs.getString("name"));
            playerStatsDTO.setRole(rs.getString("role"));
            playerStatsDTO.setAverageStrikeRate(rs.getDouble("averageStrikeRate"));
            playerStatsDTO.setEconomy(rs.getDouble("economy"));
            playerStatsDTO.setRunsGiven(rs.getInt("runsGiven"));
            playerStatsDTO.setRunsScored(rs.getInt("runsScored"));
            playerStatsDTO.setTeamName(rs.getString("teamName"));
            playerStatsDTO.setBallsBowled(rs.getInt("ballsBowled"));
            playerStatsDTO.setBallsFaced(rs.getInt("ballsFaced"));
            playerStatsDTO.setMaidenOvers(rs.getInt("averageStrikeRate"));
            playerStatsDTO.setNoOfNoBalls(rs.getInt("noOfNoBalls"));
            playerStatsDTO.setNoOfWideBalls(rs.getInt("noOfWideBalls"));
            playerStatsDTO.setOversBowled(rs.getDouble("oversBowled"));
            playerStatsDTO.setPlayingStatus(rs.getString("playingStatus"));
            return playerStatsDTO;
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

    public PlayerStatsDTO getPlayerStats(int playerId, int matchId) {
        PlayerStatsDTO playerStatsDTO = null;
        try {
            String sql = "Select * from `Player` p inner join `Playerstats` pst on p.playerId = pst.playerId inner join `Teamplayer` tmp on tmp.playerId = p.playerId inner join `Team` t on t.teamId = tmp.teamId where p.playerId = ? and pst.matchId= ? ";
            playerStatsDTO = jdbcTemplate.queryForObject(sql, playerStatsDTORowMapper, playerId, matchId);
        } catch (EmptyResultDataAccessException e) {
        }
        return playerStatsDTO;
    }

    public void updatePlayerStats(int matchId, PlayerStatsDTO playerStatsDTO) {
        String sql = "Update `Playerstats` set runsScored = ? , runsGiven = ? , wicketsTaken = ? , ballsFaced = ? , noOfNoBalls = ?, noOfWideBalls = ?, maidenOvers = ?, ballsBowled = ?, oversBowled = ? , averageStrikeRate = ?, economy = ?, playingStatus = ? where matchId = ? and playerId = ?";
        int status = jdbcTemplate.update(sql, playerStatsDTO.getRunsScored(), playerStatsDTO.getRunsGiven(), playerStatsDTO.getWicketsTaken(), playerStatsDTO.getBallsFaced(), playerStatsDTO.getNoOfNoBalls(), playerStatsDTO.getNoOfWideBalls(), playerStatsDTO.getMaidenOvers(), playerStatsDTO.getBallsBowled(), playerStatsDTO.getOversBowled(), playerStatsDTO.getAverageStrikeRate(), playerStatsDTO.getEconomy(), playerStatsDTO.getPlayingStatus(), matchId, playerStatsDTO.getPlayerId());
        if (status != 0) System.out.println("Updated Player Stats!");
        else System.out.println("No Player Stats update occured!");
    }

    public void insertPlayerStats(int matchId, PlayerStatsDTO playerStats) {
        String sql = "Insert into `Playerstats` (matchId, playerId, runsScored, runsGiven, wicketsTaken, ballsFaced, noOfNoBalls, noOfWideBalls, maidenOvers, ballsBowled, oversBowled, averageStrikeRate, economy, playingStatus) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sql, matchId, playerStats.getPlayerId(), playerStats.getRunsScored(), playerStats.getRunsGiven(), playerStats.getWicketsTaken(), playerStats.getBallsFaced(), playerStats.getNoOfNoBalls(), playerStats.getNoOfWideBalls(), playerStats.getMaidenOvers(), playerStats.getBallsBowled(), playerStats.getOversBowled(), playerStats.getAverageStrikeRate(), playerStats.getEconomy(), playerStats.getPlayingStatus());
        if (status != 0) System.out.println("Player Stats inserted!");
        else System.out.println("No Player stats insert occured!");
    }
}
