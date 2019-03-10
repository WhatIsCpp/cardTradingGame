package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "card")
public class Card {

	@Id
	@NotNull
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(unique = true, name = "card_name")
	private String cardName;

	@NotNull
	@Column(name = "mana_cost")
	private int manaCost;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "deck")
	@JsonIgnore
	private List<Player> playerDeck;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "hand")
	@JsonIgnore
	private List<Player> playerHand;

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public List<Player> getPlayerDeck() {
		return playerDeck;
	}

	public void setPlayerDeck(List<Player> playerDeck) {
		this.playerDeck = playerDeck;
	}

	public List<Player> getPlayerHand() {
		return playerHand;
	}

	public void setPlayerHand(List<Player> playerHand) {
		this.playerHand = playerHand;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}
}
