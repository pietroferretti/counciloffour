package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.Collections;

public class BusinessDeck implements Deck {

	private ArrayList<BusinessPermit> deck;
	private ArrayList<BusinessPermit> drawnCards;
	
	public BusinessDeck(ArrayList<BusinessPermit> deck) {
		this.deck = deck;
		drawnCards = new ArrayList<BusinessPermit>();
		shuffleDeck();
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	public void shuffleAll() {
		deck.addAll(drawnCards);
		drawnCards.clear();
		Collections.shuffle(deck);
	}

	public BusinessPermit drawCard() {
		BusinessPermit card = deck.remove(deck.size() - 1);	// Maybe not the best way to remove an element from the list
		drawnCards.add(card);
		return card;		
	}

}