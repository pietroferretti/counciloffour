package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PoliticDeck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8163955914204877210L;
	private List<PoliticCard> deck;
	private List<PoliticCard> discardedCards;

	public PoliticDeck(int numColoredCards, int numJolly) {
		deck = new ArrayList<>();
		discardedCards = new ArrayList<>();

		for (int i = 0; i < numColoredCards; i++) {
			for (ColorPolitic color : ColorPolitic.values()) {
				if (color != ColorPolitic.JOLLY) {
					deck.add(new PoliticCard(color));
				}
			}

		}

		for (int i = 0; i < numJolly; i++) {
			deck.add(new PoliticCard(ColorPolitic.JOLLY));
		}

		shuffle();

	}

	public PoliticDeck(PoliticDeck pd) {
		deck = new ArrayList<>(pd.deck.size());
		for (PoliticCard politicCard : pd.deck) {
			this.deck.add(new PoliticCard(politicCard));
		}
		discardedCards = new ArrayList<>(pd.discardedCards.size());
		for (PoliticCard politicCard : pd.discardedCards) {
			this.discardedCards.add(new PoliticCard(politicCard));
		}
	}

	public void addPoliticCard(PoliticCard card) {
		deck.add(card);
	}

	public List<PoliticCard> getDeck() {
		return deck;
	}

	public List<PoliticCard> getDiscardedCards() {
		return discardedCards;
	}

	public PoliticCard drawCard() {
		if (deck.isEmpty()) {
			if (discardedCards.isEmpty()) {
				return null;
			} else {
				shuffleAll();
			}
		}
		return deck.remove(0);
	}

	public List<PoliticCard> drawMultipleCards(int number) {
		List<PoliticCard> result = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			result.add(drawCard());
		}
		return result;
	}

	public void discardCard(PoliticCard card) {
		discardedCards.add(card);
	}

	public void discardCards(List<PoliticCard> cards) {
		discardedCards.addAll(cards);
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	private void shuffleAll() {
		deck.addAll(discardedCards);
		discardedCards.clear();
		shuffle();
	}

	@Override
	public String toString() {
		return "PoliticDeck [deck=" + deck + ", discardedCards=" + discardedCards + "]";
	}

}