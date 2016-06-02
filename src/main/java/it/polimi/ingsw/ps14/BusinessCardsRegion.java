package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusinessCardsRegion {

	private List<BusinessPermit> deck;
	private BusinessPermit[] availablePermits;

	public BusinessCardsRegion(ArrayList<BusinessPermit> deck) {
		this.deck = deck;
		availablePermits = new BusinessPermit[2];
	}

	public BusinessCardsRegion() {
		availablePermits = new BusinessPermit[2];
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	private BusinessPermit drawCardFromDeck() {
		BusinessPermit card = (BusinessPermit) deck.remove(0);
		return card;
	}

	public void substituteCard(BusinessPermit toSubstitute) {
		for (BusinessPermit busPer : availablePermits)
			if (busPer == toSubstitute) {
				busPer = drawCardFromDeck();
			}
	}

	public boolean cardIsChoosable(BusinessPermit card) {
		for (BusinessPermit busPer : availablePermits)
			if (busPer.equals(card)) {
				return true;
			}
		return false;
	}

	public BusinessPermit[] getAvailablePermits() {
		return availablePermits;
	}

}
