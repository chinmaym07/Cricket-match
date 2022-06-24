package src.com.cricketgame.services;

import src.com.cricketgame.DTO.RequestDTOs.StartTossDTO;
import src.com.cricketgame.DTO.ResponseDTOs.TossDTO;

public interface TossService {
    String startToss(int matchId, StartTossDTO startTossDTO);

    TossDTO getTossDetails(int matchId);

    String makeChoice(int matchId, String winnerChoice);
}
