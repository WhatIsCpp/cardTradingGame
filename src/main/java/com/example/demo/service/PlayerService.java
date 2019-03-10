package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Card;
import com.example.demo.entity.Player;

public interface PlayerService {

	Player findByName(String playerName);
	
	Player createPlayerByName(String playerName);
	
	String startGame(String player1, String player2);

	List<Card> firstHand(String playerName);

	List<Card> drawCard(String playerName);

	String checkPlayerHealth(String playerName);

	String dealDamage(int damage, String playerName);

	void initializePlayers(Player player1, Player player2);

	List<Card> playCardFromHand(String cardName, String playerName);

	void checkPlayerManaAndCardsInHand(String playerName);

	boolean checkDraw(String playerName);

	Player checkPlayer(String playerName);

	void setAndResetMana(String playerName);

	String endTurn(String playerName);
	
	List<Card> getHand(String playerName);
}
