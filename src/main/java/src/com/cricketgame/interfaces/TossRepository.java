package src.com.cricketgame.interfaces;

import src.com.cricketgame.DTO.RequestDTOs.StartTossDTO;
import src.com.cricketgame.DTO.ResponseDTOs.MatchesDTO;
import src.com.cricketgame.DTO.ResponseDTOs.TossDTO;
import src.com.cricketgame.models.Match;
import src.com.cricketgame.models.Team;
import src.com.cricketgame.models.Toss;

public interface TossRepository {
    String startToss(Team teamA, Team teamB, MatchesDTO matchesDTO, StartTossDTO startTossDTO);
    int getInningsIdCount();
    Toss getTossDetails(int matchId);
}
