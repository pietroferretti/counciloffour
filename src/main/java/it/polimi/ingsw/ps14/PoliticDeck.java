package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class PoliticDeck implements Deck {

	public ArrayList<PoliticCard> deck;
	
	public void shuffle() {
	}

	public PoliticCard drawCard() {
		return new PoliticCard();
	}

}