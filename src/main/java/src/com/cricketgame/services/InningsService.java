package src.com.cricketgame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.CurrentPlay;
import src.com.cricketgame.models.Innings;
import src.com.cricketgame.models.PlayerStats;
import src.com.cricketgame.repo.InningsRepositoryImpl;
import src.com.cricketgame.repo.MatchRepositoryImpl;
import src.com.cricketgame.repo.PlayerRepositoryImpl;

@Service
public class InningsService {

    @Autowired
    private InningsRepositoryImpl inningsRepository;

    @Autowired
    private MatchRepositoryImpl matchRepository;

    @Autowired
    private PlayerRepositoryImpl playerRepository;


    public Innings getFirstInnings(int matchId) {
        return inningsRepository.getFirstInnings(matchId);
    }

    public Innings getSecondInnings(int matchId) {
        return inningsRepository.getSecondInnings(matchId);
    }

    public Innings startFirstInnings(int matchId) {
        return inningsRepository.startFirstInnings(matchId);
    }

    public Innings startSecondInnings(int matchId) {
        return inningsRepository.startSecondInnings(matchId);
    }

    public String playOver(int matchId, String inningsType, int currentBowlerId, int currentBatsmanId) {
        Innings innings;
        if (inningsType.equals("first"))
            innings = getFirstInnings(matchId);
        else
            innings = getSecondInnings(matchId);
        CurrentPlay currentPlay = inningsRepository.getCurrentPlayForInnings(innings.getMatchId(),innings.getInningsId());

        MatchesDTO matchesDTO = matchRepository.getMatchDetails(matchId);
        PlayerStatsDTO batsmanPlayerStatsDTO = playerRepository.getPlayerStats(currentBatsmanId,matchId);
        if(batsmanPlayerStatsDTO.getPlayingStatus().equals("OUT"))
            return "This Batsman is already out. Please enter a valid Batsman Id";

        if(currentBowlerId == currentPlay.getPreviousBowlerId())
            return "One Bowler Cannot Bowl two overs Simultaneously, Please provide a Different bowler ID";

        PlayerStatsDTO bowlerPlayerStatsDTO = playerRepository.getPlayerStats(currentBowlerId,matchId);

        inningsRepository.playOver(matchesDTO,innings, batsmanPlayerStatsDTO, bowlerPlayerStatsDTO, currentPlay);
        return "";
    }

}
