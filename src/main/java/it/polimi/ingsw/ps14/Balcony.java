package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Balcony {

	private PriorityQueue<ColorCouncillor> councillors;

	public Balcony(PriorityQueue<ColorCouncillor> initialCouncillors) {
		councillors = initialCouncillors;
	}

	public ColorCouncillor electCouncillor(ColorCouncillor color) {
		ColorCouncillor discarded = councillors.poll();
		councillors.add(color);
		return discarded;
	}

	public PriorityQueue<ColorCouncillor> readBalcony() {
		return councillors;
	}

	private ColorCouncillor colorPoliticToCouncillor(PoliticCard card) {
		return ColorCouncillor.valueOf(card.getColor().name());
	}

	// check if Politic Cards from player have matching counsellors and return
	// number of bought counsellors

	public int cardsInBalcony(ArrayList<PoliticCard> cards) {
		int boughtCounsellor = 0;
		PriorityQueue<ColorCouncillor> newCouncillors = councillors;
		for (PoliticCard card : cards) {
			if (card.isJolly())
				boughtCounsellor++;
			else if (newCouncillors.contains(colorPoliticToCouncillor(card))) {
				newCouncillors.remove(colorPoliticToCouncillor(card));
				boughtCounsellor++;
			} else
				return -1;

		}
		return boughtCounsellor;
	}

	// redone previous method
	public boolean thereIsColorsInBalcony(ArrayList<PoliticCard> cards) {
		PriorityQueue<ColorCouncillor> newCouncillors = councillors;
		for (PoliticCard card : cards) {
			if (newCouncillors.contains(colorPoliticToCouncillor(card))) {
				newCouncillors.remove(colorPoliticToCouncillor(card));
			} else
				return false;
		}
		return true;
	}

	
	//TODO: is this the best way?
	public int councillorCost(ArrayList<PoliticCard> cards) {
		int cost=0;
		switch (cardsInBalcony(cards)) {
		case 1:
			cost = 10;
			break;
		case 2:
			cost = 7;
			break;
		case 3:
			cost = 4;
			break;
		case 4:
			cost = 0;
			break;
		}
			if (cards.contains(ColorPolitic.JOLLY))
				cost++;
			return cost;
		
	}

}
