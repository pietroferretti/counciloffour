package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusinessDeck {

	private List<BusinessPermit> deck;
	private List<BusinessPermit> drawnCards;

	public BusinessDeck(List<BusinessPermit> deck) {
		this.deck = deck;
		drawnCards = new ArrayList<>();
		shuffle();
	}

	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public BusinessPermit drawCard() {
		BusinessPermit card = (BusinessPermit) deck.remove(0);
		drawnCards.add(card);
		return card;
	}

	public void addToBottom(BusinessPermit card) {
		deck.add(card);
	}

	@Override
	public String toString() {
		return "BusinessDeck [deck=" + deck + ", drawnCards=" + drawnCards
				+ "]";
	}

}