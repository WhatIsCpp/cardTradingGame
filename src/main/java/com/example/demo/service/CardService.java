package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Card;

public interface CardService {

	Card findCardByName(String cardName);
	
	List<Card> findAll();
}
