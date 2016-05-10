package it.polimi.ingsw.ps14;

import java.util.PriorityQueue;

public class Balcony {

	private PriorityQueue<ColorCouncillor> councillors;

	public Balcony(ColorCouncillor[] initialCouncillors) {
		for (int i = 0; i < initialCouncillors.length; i++)
			councillors.add(initialCouncillors[i]);
	}

	public void electCouncillor(ColorCouncillor color) {
		councillors.poll();
		councillors.add(color);
	}

	public PriorityQueue<ColorCouncillor> readBalcony() {
		return councillors;
	}

	// check if Politic Card from player has a matching counsellor
	public boolean cardsInBalcony(PoliticCard card) {
		return councillors.contains(ColorCouncillor.valueOf(card.getColor().name()));
	}

	public boolean cardsInBalcony(PoliticCard card1, PoliticCard card2) {
		return cardsInBalcony(card1) && cardsInBalcony(card2);
	}

	public boolean cardsInBalcony(PoliticCard card1, PoliticCard card2, PoliticCard card3) {
		return cardsInBalcony(card1, card2) && cardsInBalcony(card3);
	}

	public boolean cardsInBalcony(PoliticCard card1, PoliticCard card2, PoliticCard card3, PoliticCard card4) {
		return cardsInBalcony(card1, card2, card3) && cardsInBalcony(card4);
	}

}