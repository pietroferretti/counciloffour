package it.polimi.ingsw.ps14.model;

import java.util.Collections;
import java.util.List;

public abstract class Deck {
	
	protected List<Card> deck;
	protected List<Card> drawnCards;

	public abstract Card drawCard();

	public void shuffle(){
		Collections.shuffle(deck);
	}

	@Override
	public String toString() {
		return "Deck [deck=" + deck + ", drawnCards=" + drawnCards + "]";
	}
	
}