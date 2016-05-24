package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Balcony {

	private PriorityQueue<ColorCouncillor> councillors;

	public Balcony(PriorityQueue<ColorCouncillor> initialCouncillors) {
		councillors = initialCouncillors;
	}

	public ColorCouncillor electCouncillor(ColorCouncillor color) {
		ColorCouncillor discarded=councillors.poll();
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
}
