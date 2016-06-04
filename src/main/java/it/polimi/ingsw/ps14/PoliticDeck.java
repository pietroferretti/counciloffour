package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PoliticDeck extends Deck {

	private List<PoliticCard> deck;
	private List<PoliticCard> discardedCards;

	public PoliticDeck(int numColoredCard, int numJolly) {
		deck= new ArrayList<>();
		discardedCards= new ArrayList<>();
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
	
	public void addPoliticCard(PoliticCard card){
		deck.add(card);
	}

	public List<PoliticCard> getDeck() {
		return deck;
	}

	public List<PoliticCard> getDiscardedCards() {
		return discardedCards;
	}

	public PoliticCard drawCard() {
		if (deck.size() == 0)
			shuffleAll();
		return deck.remove(0);
	}

	public List<PoliticCard> drawMultipleCards(int number) {
		List<PoliticCard> result = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			result.add(drawCard());
		}
		return result;
	}
	
	public void discardCard(PoliticCard card){
		discardedCards.add(card);
	}
	
	public void discardCards(List<PoliticCard> cards){
		discardedCards.addAll(cards);
	}
	
	@Override
	public void shuffle(){
		Collections.shuffle(deck);
	}

}