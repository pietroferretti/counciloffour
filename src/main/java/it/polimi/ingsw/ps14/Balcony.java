package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.junit.internal.runners.model.EachTestNotifier;

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
