package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class PoliticDeck extends Deck {

	private ArrayList<PoliticCard> deck;
	private ArrayList<PoliticCard> discardedCards;

	public PoliticDeck(int numColoredCard, int numJolly) {
		for (int i = 0; i < numColoredCard; i++) {
			deck.add(new PoliticCard(ColorPolitic.BLACK));
			deck.add(new PoliticCard(ColorPolitic.BLUE));
			deck.add(new PoliticCard(ColorPolitic.ORANGE));
			deck.add(new PoliticCard(ColorPolitic.PINK));
			deck.add(new PoliticCard(ColorPolitic.PURPLE));
			deck.add(new PoliticCard(ColorPolitic.WHITE));
		}
		for (int i = 0; i < numJolly; i++) {
			deck.add(new PoliticCard(ColorPolitic.JOLLY));
		}
		shuffle();

	}

	private void shuffleAll() {
		deck.addAll(discardedCards);
		discardedCards.clear();
		shuffle();
	}

	public ArrayList<PoliticCard> getDeck() {
		return deck;
	}

	public ArrayList<PoliticCard> getDiscardedCards() {
		return discardedCards;
	}

	public PoliticCard drawCard() {
		if (deck.size() == 0)
			shuffleAll();
		return deck.remove(0);
	}

	public ArrayList<PoliticCard> drawMultipleCards(int number) {
		ArrayList<PoliticCard> result = new ArrayList<PoliticCard>();
		for (int i = 0; i < number; i++) {
			result.add(drawCard());
		}
		return result;
	}

}