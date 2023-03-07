package com.example.player.repository;
import java.util.*;
import com.example.player.model.Player;

public interface PlayerRepository{
    ArrayList<Player> getPlayers();
    Player postPlayer(Player player);
    Player getPlayerById(int playerId);
    Player putPlayer(int playerId, Player player);
    void deletePlayer(int playerId);
}