package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Card;
import com.example.demo.repository.CardRepository;
import com.example.demo.service.CardService;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardRepository cardRepository;
	
	@Override
	public Card findCardByName(String cardName) {
		return cardRepository.findByCardName(cardName);
	}

	@Override
	public List<Card> findAll() {
		return cardRepository.findAll();
	}

}
