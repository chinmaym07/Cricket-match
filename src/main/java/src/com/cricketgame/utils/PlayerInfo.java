package src.com.cricketgame.utils;

import src.com.cricketgame.models.Player;

import java.util.ArrayList;

public class PlayerInfo {
    public static void displayPlayerInfo(ArrayList<Player> players) {
        System.out.println("PlayerId \t\t PlayerName \t\t\t\t Player Role");
        for(Player currentPlayer: players) {
            System.out.println(currentPlayer.getPlayerId()+" \t\t\t\t "+ currentPlayer.getName() + " \t\t\t\t"+ currentPlayer.getRole());
        }

    }
}
