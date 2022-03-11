package src.com.cricketgame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.com.cricketgame.DTO.ResponseDTOs.EachRunfreqDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.PlayerStatsDTO;
import src.com.cricketgame.models.CurrentPlay;
import src.com.cricketgame.models.Innings;
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

    public void findWinner(int matchId, MatchesDTO matchesDTO) {
        Innings firstInnings = getFirstInnings(matchId);
        Innings secondInnings = getSecondInnings(matchId);
        if (matchesDTO.getMatchStatus().equals("COMPLETED")) {
            if (secondInnings.getTotalScore() >= firstInnings.getTotalScore()) {
                matchesDTO.setTeamIdWhoWonTheMatch(secondInnings.getBattingTeamId());
            } else {
                matchesDTO.setTeamIdWhoWonTheMatch(firstInnings.getBattingTeamId());
            }
        }

    }

    public String playOver(int matchId, String inningsType, int currentBowlerId, int currentBatsmanId) {

        Innings innings;
        if (inningsType.equals("first"))
            innings = getFirstInnings(matchId);
        else
            innings = getSecondInnings(matchId);
        CurrentPlay currentPlay = inningsRepository.getCurrentPlayForInnings(innings.getMatchId(), innings.getInningsId());

        MatchesDTO matchesDTO = matchRepository.getMatchDetails(matchId);
        if (currentPlay.getCurrentOver() >= matchesDTO.getMatchOvers()) {
            findWinner(matchId, matchesDTO);
            matchRepository.updateMatchDetails(matchesDTO);

            return "Match Completed!!";
        }
        int batsmanStatsDoesNotExist = 0, bowlersStatsDoesNotExist = 0;

        PlayerStatsDTO batsmanPlayerStatsDTO;

        batsmanPlayerStatsDTO = playerRepository.getPlayerStats(currentBatsmanId, matchId);
        if (batsmanPlayerStatsDTO == null) {
            batsmanPlayerStatsDTO = new PlayerStatsDTO();
            batsmanPlayerStatsDTO.setPlayingStatus("NOT_OUT");
            batsmanPlayerStatsDTO.setPlayerId(currentBatsmanId);
            batsmanPlayerStatsDTO.setMatchId(matchId);
            batsmanStatsDoesNotExist = 1;
        }
        EachRunfreqDTO eachRunfreqDTO;
        if (inningsRepository.getPlayerStatsInEachRunFreq(matchId, currentBatsmanId) == null) {
            eachRunfreqDTO = new EachRunfreqDTO();
            eachRunfreqDTO.setMatchId(matchId);
            eachRunfreqDTO.setPlayerId(currentBatsmanId);
        } else {
            eachRunfreqDTO = inningsRepository.getPlayerStatsInEachRunFreq(matchId, currentBatsmanId);
        }
        batsmanPlayerStatsDTO.setEachRunFreq(eachRunfreqDTO);

        if (batsmanPlayerStatsDTO.getPlayingStatus().equals("OUT"))
            return "This Batsman is already out. Please enter a valid Batsman Id";

        if (currentBowlerId == currentPlay.getPreviousBowlerId())
            return "One Bowler Cannot Bowl two overs Simultaneously, Please provide a Different bowler ID";


        PlayerStatsDTO bowlerPlayerStatsDTO;

        bowlerPlayerStatsDTO = playerRepository.getPlayerStats(currentBowlerId, matchId);
        if (bowlerPlayerStatsDTO == null) {
            bowlerPlayerStatsDTO = new PlayerStatsDTO();
            bowlerPlayerStatsDTO.setPlayerId(currentBowlerId);
            bowlerPlayerStatsDTO.setMatchId(matchId);
            bowlersStatsDoesNotExist = 1;
        }


        String resp = inningsRepository.playOver(matchesDTO, innings, batsmanPlayerStatsDTO, bowlerPlayerStatsDTO, currentPlay);


        if (currentPlay.getCurrentOver() >= matchesDTO.getMatchOvers()) {
            findWinner(matchId, matchesDTO);
            matchRepository.updateMatchDetails(matchesDTO);
            innings.setInningsStatus("COMPLETED");
            resp = "Match Completed!!";
        }


        inningsRepository.updateCurrentPlay(innings.getMatchId(), innings.getInningsId(), currentPlay);

        inningsRepository.updateInningsStats(matchId, innings);
        if (batsmanStatsDoesNotExist == 1)
            playerRepository.insertPlayerStats(matchId, batsmanPlayerStatsDTO);
        else
            playerRepository.updatePlayerStats(matchId, batsmanPlayerStatsDTO);

        if (bowlersStatsDoesNotExist == 1)
            playerRepository.insertPlayerStats(matchId, bowlerPlayerStatsDTO);
        else
            playerRepository.updatePlayerStats(matchId, bowlerPlayerStatsDTO);


        return resp;
    }

}
