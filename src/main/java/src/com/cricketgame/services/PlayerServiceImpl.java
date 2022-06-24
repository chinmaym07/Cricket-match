package src.com.cricketgame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.Player;
import src.com.cricketgame.repo.EachRunFreqRepositoryImpl;
import src.com.cricketgame.repo.PlayerRepositoryImpl;
import src.com.cricketgame.repo.PlayerStatsRepositoryImpl;

import java.util.List;

@Service
public class PlayerServiceImpl {
    @Autowired
    private PlayerRepositoryImpl playerRepositoryImpl;

    @Autowired
    private PlayerStatsRepositoryImpl playerStatsRepository;

    @Autowired
    private EachRunFreqRepositoryImpl eachRunFreqRepository;


    public Player getPlayer(int teamId) {
        return playerRepositoryImpl.getPlayer(teamId);
    }

    public List<Player> getTeamPlayers(int teamId) {
        return playerRepositoryImpl.getTeamPlayers(teamId);
    }

    public PlayerStatsDTO getPlayerStats(int playerId, int matchId) {
        PlayerStatsDTO playerStatsDTO = playerStatsRepository.getPlayerStats(playerId, matchId);
        EachRunfreqDTO eachRunfreqDTO = eachRunFreqRepository.getPlayerStatsInEachRunFreq(matchId, playerId);
        playerStatsDTO.setEachRunFreq(eachRunfreqDTO);
        return playerStatsDTO;
    }

}
