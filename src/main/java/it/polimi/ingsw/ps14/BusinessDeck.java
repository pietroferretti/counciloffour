package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class BusinessDeck extends Deck {

	private ArrayList<BusinessPermit> deck;
	private ArrayList<BusinessPermit> drawnCards;

	public BusinessDeck(ArrayList<BusinessPermit> deck) {
		this.deck = deck;
		drawnCards = new ArrayList<BusinessPermit>();
		shuffle();
	}

	public BusinessPermit drawCard() {
		BusinessPermit card = deck.remove(0);
		drawnCards.add(card);
		return card;
	}

	public void addToBottom(BusinessPermit card) {
		deck.add(card);
	}

}