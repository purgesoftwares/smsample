package com.allowify.response;

import java.util.HashSet;
import java.util.Set;

import com.allowify.model.Card;

/**
 * 
 * @author tonyacunar
 *
 */
public class CardResponse {

	private Set<Card> cards = new HashSet<Card>();
	
	
	public CardResponse(Set<Card> cards) {
		this.cards = cards;
	}


	public Set<Card> getCards() {
		return cards;
	}


	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
	
}
