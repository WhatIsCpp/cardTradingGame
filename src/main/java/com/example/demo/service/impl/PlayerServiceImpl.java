package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.demo.entity.Card;
import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.service.CardService;
import com.example.demo.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

	private static final String STRING_DOESNT_EXIST = " doesn't exist";

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private CardService cardService;

	private Random random = new Random();

	@Override
	public Player findByName(String playerName) {
		return playerRepository.findByName(playerName);
	}

	@Override
	public Player createPlayerByName(String playerName) {
		Assert.isNull(findByName(playerName), playerName + " is taken");
		Player newPlayer = new Player();
		newPlayer.setName(playerName);
		return playerRepository.save(newPlayer);
	}

	@Override
	public String startGame(String player1, String player2) {
		Player playerPlayer1 = checkPlayer(player1);
		Assert.isTrue(!playerPlayer1.isInGame(), player1 + " is already in a game");
		Player playerPlayer2 = checkPlayer(player2);
		Assert.isTrue(!playerPlayer1.isInGame(), player2 + " is already in a game");

		int starterIndex = random.nextInt(2);
		String starterPlayer;
		if (1 == starterIndex) {
			initializePlayers(playerPlayer1, playerPlayer2);
			starterPlayer = playerPlayer1.getName();
		} else {
			initializePlayers(playerPlayer2, playerPlayer1);
			starterPlayer = playerPlayer2.getName();
		}
		String result = player1 + " and " + player2 + " are ready to play \n it is " + starterPlayer + "'s turn";
		return result;
	}

	@Override
	public List<Card> drawCard(String playerName) {
		Player player = checkPlayer(playerName);
		Assert.isTrue(player.isTurn(), playerName + " please wait your turn");
		Assert.isTrue(!checkDraw(playerName), playerName + " has already draw");
		Assert.notNull(player.getEnemyPlayer(), "Enemy" + STRING_DOESNT_EXIST);
		Assert.isTrue(player.isInGame(), playerName + " is not in a game");

		if (null != player.getDeck() && player.getDeck().size() > 0) {
			int cardIndex = random.nextInt(player.getDeck().size());
			if (player.getHand().size() == 5) {
				player.getDeck().remove(cardIndex);
			} else {
				player.getHand().add(player.getDeck().remove(cardIndex));
			}
		} else {
			dealDamage(1, playerName);
		}
		setAndResetMana(playerName);
		checkPlayerManaAndCardsInHand(playerName);
		player.setDraw(true);
		playerRepository.save(player);

		return player.getHand();
	}

	@Override
	public List<Card> playCardFromHand(String cardName, String playerName) {
		Player player = checkPlayer(playerName);
		Assert.isTrue(player.isTurn(), playerName + " please wait your turn");
		Assert.notNull(cardService.findCardByName(cardName), cardName + STRING_DOESNT_EXIST);
		Assert.isTrue(player.isDraw(), playerName + " please draw a card first");
		Card card = cardService.findCardByName(cardName);
		Assert.isTrue(player.getMana() >= card.getManaCost(), "you dont have enough mana to play that card");

		for (Card cardInHand : player.getHand()) {
			if (card.getCardName() == cardInHand.getCardName()) {
				dealDamage(card.getManaCost(), player.getEnemyPlayer().getName());
				player.setMana(player.getMana() - card.getManaCost());
				player.getHand().remove(player.getHand().indexOf(cardInHand));
				break;
			}
		}
		checkPlayerManaAndCardsInHand(playerName);
		playerRepository.save(player);
		return player.getHand();
	}

	@Override
	public String endTurn(String playerName) {
		Player player = checkPlayer(playerName);
		Assert.isTrue(player.isTurn(), playerName + " it is not your turn!");
		player.setTurn(false);
		player.setDraw(false);
		playerRepository.save(player);
		Player player2 = checkPlayer(player.getEnemyPlayer().getName());
		player2.setTurn(true);
		player2.setDraw(false);
		playerRepository.save(player2);
		return " It is now " + player.getEnemyPlayer().getName() + "'s turn";
	}

	@Override
	public List<Card> firstHand(String playerName) {
		Player player = checkPlayer(playerName);
		Assert.isTrue(player.isInGame(), playerName + " is not in a game");
		Assert.isTrue(player.getHand() != null && player.getHand().size() == 0,
				playerName + " already has cards in hand");

		List<Card> listHand = new ArrayList<>();
		player.setHand(listHand);

		for (int handSize = 0; handSize < 3; handSize++) {
			int cardIndex = random.nextInt(player.getDeck().size());
			player.getHand().add(player.getDeck().get(cardIndex));
			player.getDeck().remove(player.getDeck().get(cardIndex));
		}
		playerRepository.save(player);
		return player.getHand();
	}

	@Override
	public void initializePlayers(Player player1, Player player2) {
		List<Card> deck = cardService.findAll();
		player1.setDeck(deck);
		player1.setHealth(30);
		player1.setInGame(true);
		firstHand(player1.getName());
		player1.setManaSlot(0);
		player1.setMana(0);
		player1.setTurn(true);
		player1.setDraw(false);
		player1.setEnemyPlayer(player2);
		playerRepository.save(player1);

		List<Card> deck2 = cardService.findAll();
		player2.setDeck(deck2);
		player2.setHealth(30);
		player2.setInGame(true);
		firstHand(player2.getName());
		player2.setManaSlot(0);
		player2.setMana(0);
		player2.setTurn(false);
		player1.setDraw(false);
		player2.setEnemyPlayer(player1);
		playerRepository.save(player2);
	}

	@Override
	public List<Card> getHand(String playerName) {
		Player player = checkPlayer(playerName);
		return player.getHand();
	}

	@Override
	public String checkPlayerHealth(String playerName) {
		Player player = checkPlayer(playerName);
		Assert.isTrue(player.isInGame(), playerName + " is not in a game");

		if (0 >= player.getHealth()) {
			player.setInGame(false);
			player.getEnemyPlayer().setInGame(false);
			player.setTurn(false);
			return playerName + " lost the game";
		}
		playerRepository.save(player);
		return Integer.toString(player.getHealth());
	}

	@Override
	public String dealDamage(int damage, String playerName) {
		Player player = checkPlayer(playerName);
		Assert.isTrue(damage <= 8, "Damage cant be greater than 8, you tried to deal:" + Integer.toString(damage));
		player.setHealth(player.getHealth() - damage);
		playerRepository.save(player);
		return checkPlayerHealth(playerName);
	}

	@Override
	public void checkPlayerManaAndCardsInHand(String playerName) {
		Player player = checkPlayer(playerName);
		player.setTurn(false);
		for (Card card : player.getHand()) {
			if (card.getManaCost() <= player.getMana()) {
				player.setTurn(true);
			}
		}
		if (!player.isTurn()) {
			player.setDraw(true);
			Player player2 = checkPlayer(player.getEnemyPlayer().getName());
			player2.setTurn(true);
			player2.setDraw(false);
			playerRepository.save(player2);
		}
		playerRepository.save(player);
	}

	@Override
	public boolean checkDraw(String playerName) {
		Player player = checkPlayer(playerName);
		return player.isDraw();
	}

	@Override
	public Player checkPlayer(String playerName) {
		Assert.notNull(findByName(playerName), playerName + STRING_DOESNT_EXIST);
		return findByName(playerName);
	}

	@Override
	public void setAndResetMana(String playerName) {
		Player player = checkPlayer(playerName);
		if (10 > player.getManaSlot()) {
			player.setMana(player.getManaSlot() + 1);
			player.setManaSlot(player.getManaSlot() + 1);
		} else {
			player.setMana(player.getManaSlot());
		}
		playerRepository.save(player);
	}

}
