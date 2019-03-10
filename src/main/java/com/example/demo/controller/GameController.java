package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Card;
import com.example.demo.service.PlayerService;

@RestController
@RequestMapping("/game")
public class GameController {

	@Autowired
	private PlayerService playerService;

	@PostMapping("/start/{player1},{player2}")
	public String startGame(@PathVariable String player1, @PathVariable String player2) {
		return playerService.startGame(player1, player2);
	}

	@PostMapping("/draw/{playerName}")
	public List<Card> drawCard(@PathVariable String playerName) {
		return playerService.drawCard(playerName);
	}

	@PostMapping("/play/{cardName}/{playerName}")
	public List<Card> playCardFromHand(@PathVariable String cardName, @PathVariable String playerName) {
		return playerService.playCardFromHand(cardName, playerName);
	}

	@PostMapping("/endturn/{playerName}")
	public String endTurn(@PathVariable String playerName) {
		return playerService.endTurn(playerName);
	}

}
