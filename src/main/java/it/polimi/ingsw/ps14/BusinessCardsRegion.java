package it.polimi.ingsw.ps14;

import java.util.Collections;
import java.util.List;

public class BusinessCardsRegion {

	private List<BusinessPermit> deck;
	private BusinessPermit[] availablePermits;

	public BusinessCardsRegion(List<BusinessPermit> deck) {
		this.deck = deck;
		shuffle();
		availablePermits = new BusinessPermit[2];
		availablePermits[0] = drawCardFromDeck();
		availablePermits[1] = drawCardFromDeck();
	}

	public BusinessCardsRegion() {
		deck = null;
		availablePermits = new BusinessPermit[2];
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	private BusinessPermit drawCardFromDeck() {
		if (!deck.isEmpty()) {
			return deck.remove(0);
		} else {
			return null;
		}
	}

	public boolean substituteCard(BusinessPermit toSubstitute) {
		for (int i=0; i < availablePermits.length; i++) {
			if (availablePermits[i] == toSubstitute) {
				availablePermits[i] = drawCardFromDeck();
				return true;
			}
		}
		return false;
	}

	public boolean cardIsFaceUp(BusinessPermit card) {
		for (BusinessPermit busPer : availablePermits)
			if (busPer.equals(card)) {
				return true;
			}
		return false;
	}

	public BusinessPermit[] getAvailablePermits() {
		return availablePermits;
	}
	
	public boolean BusinessPermitIsAvailable(BusinessPermit permit){
		for(BusinessPermit avBP : availablePermits)
			if(permit.equals(avBP)) return true;
		return false;
	}

}
