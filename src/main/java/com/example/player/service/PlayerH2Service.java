/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */
package com.example.player.service;
import java.util.ArrayList;
import java.util.List;

import com.example.player.model.Player;
import com.example.player.model.PlayerRowMapper;
import com.example.player.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlayerH2Service implements PlayerRepository{
	@Autowired
	private JdbcTemplate db;


	@Override
	public ArrayList<Player> getPlayers() {
		List<Player> playerList = db.query("SELECT * FROM team", new PlayerRowMapper());
		ArrayList<Player> players = new ArrayList<>(playerList);
		return players;
	}

	@Override
	public Player postPlayer(Player player) {
		db.update("INSERT INTO team(playerName,jerseyNumber,role) values(?,?,?)", player.getPlayerName(),player.getJerseyNumber(),player.getRole());
		return db.queryForObject("SELECT * FROM team WHERE playerName = ? AND jerseyNumber = ? AND role = ?", new PlayerRowMapper(), player.getPlayerName(),player.getJerseyNumber(),player.getRole());
	}

	@Override
	public Player getPlayerById(int playerId) {
		try{
			return db.queryForObject("SELECT * FROM team WHERE playerId = ?", new PlayerRowMapper(), playerId); 
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		}	
	}

	@Override
	public Player putPlayer(int playerId, Player player) {
		try{
			if (player.getPlayerName() != null) {
            	db.update("update team set playerName = ? where playerId =?", player.getPlayerName(), playerId);
			}
			if (player.getJerseyNumber() != 0) {
				db.update("update team set jerseyNumber = ? where playerId =?", player.getJerseyNumber(), playerId);
			}
			if (player.getRole() != null) {
				db.update("update team set role = ? where playerId =?", player.getRole(), playerId);
			}
        	return getPlayerById(playerId);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}

	@Override
	public void deletePlayer(int playerId) {
		db.update("delete from team where playerId = ?", playerId);	
	}

}

