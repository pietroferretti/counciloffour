package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class PoliticDeck implements Deck {

	public ArrayList<PoliticCard> deck;

	/*
	 * TODO: riscrivere il costruttore in modo che prenda i parametri salvati su
	 * file e costruisca carta per carta?
	 */
	public PoliticDeck(ArrayList<PoliticCard> deck) {
		this.deck = deck;
	}

	public void shuffle() {
		//TODO
	}

	public void shuffleAll() {
		//TODO
	}

	public PoliticCard drawCard() {
		return new PoliticCard();
	}

	public ArrayList<PoliticCard> drawMultipleCards(int number) {
		ArrayList<PoliticCard> result = new ArrayList<PoliticCard>();
		for (int i = 0; i < number; i++) {
		    result.add(drawCard());
		}
		return result;
	}

}