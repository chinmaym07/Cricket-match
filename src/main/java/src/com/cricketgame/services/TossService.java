package src.com.cricketgame.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.com.cricketgame.DTO.RequestDTOs.StartTossDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.TossDTO;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.models.Toss;
import src.com.cricketgame.repo.MatchRepositoryImpl;
import src.com.cricketgame.repo.TeamRepositoryImpl;
import src.com.cricketgame.repo.TossRepositoryImpl;

@Service
public class TossService {
    @Autowired
    private TossRepositoryImpl tossRepository;

    @Autowired
    private MatchRepositoryImpl matchRepository;

    @Autowired
    private TeamRepositoryImpl teamRepository;

    public String startToss(int matchId, StartTossDTO startTossDTO) {

        MatchesDTO matchesDTO = matchRepository.getMatchDetails(matchId);
        Team teamA = teamRepository.getSpecificTeamById(matchesDTO.getTeamAId());
        Team teamB = teamRepository.getSpecificTeamById(matchesDTO.getTeamBId());
        matchesDTO.setTeamAName(teamA.getTeamName());
        matchesDTO.setTeamBName(teamB.getTeamName());

        return tossRepository.startToss(teamA, teamB, matchesDTO, startTossDTO);
    }

    public TossDTO getTossDetails(int matchId) {
        TossDTO tossDTO = new TossDTO();
        Toss toss = tossRepository.getTossDetails(matchId);
        MatchesDTO matchesDTO = matchRepository.getMatchDetails(matchId);
        Team teamA = teamRepository.getSpecificTeamById(matchesDTO.getTeamAId());
        Team teamB = teamRepository.getSpecificTeamById(matchesDTO.getTeamBId());
        matchesDTO.setTeamAName(teamA.getTeamName());
        matchesDTO.setTeamBName(teamB.getTeamName());

        tossDTO.convertTossToTossDTO(toss, matchesDTO.getTeamAId(), matchesDTO.getTeamBId(), matchesDTO.getTeamAName(), matchesDTO.getTeamBName());
        return tossDTO;
    }

    public String makeChoice(int matchId, String winnerChoice) {
        MatchesDTO matchesDTO = matchRepository.getMatchDetails(matchId);
        Team teamA = teamRepository.getSpecificTeamById(matchesDTO.getTeamAId());
        Team teamB = teamRepository.getSpecificTeamById(matchesDTO.getTeamBId());
        matchesDTO.setTeamAName(teamA.getTeamName());
        matchesDTO.setTeamBName(teamB.getTeamName());

        return tossRepository.makeChoice(matchId, winnerChoice, matchesDTO);
    }
}
