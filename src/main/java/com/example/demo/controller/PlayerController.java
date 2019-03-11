package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Card;
import com.example.demo.entity.Player;
import com.example.demo.service.PlayerService;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	@GetMapping("/{playerName}")
	public Player getPlayerByName(@PathVariable String playerName) {
		return playerService.findByName(playerName);
	}

	@PostMapping("/create/{playerName}")
	public Player createNewPlayer(@PathVariable String playerName) {
		return playerService.createPlayerByName(playerName);
	}

	@GetMapping("/hand/{playerName}")
	public List<Card> getHand(@PathVariable String playerName) {
		return playerService.getHand(playerName);
	}

}
