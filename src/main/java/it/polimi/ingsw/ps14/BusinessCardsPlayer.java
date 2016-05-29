package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.List;

public class BusinessCardsPlayer {
	private List<BusinessPermit> validCards;
	private List<BusinessPermit> usedCards;

	public BusinessCardsPlayer() {
		validCards = new ArrayList<>();
		usedCards = new ArrayList<>();
	}

	public boolean checkBusinessPermit(BusinessPermit card) {
		if (validCards.contains(card))
			return true;
		else
			return false;
	}

	public void acquireBusinessPermit(BusinessPermit card) {
		validCards.add(card);
	}

	public void usePermit(BusinessPermit card) {
		if (validCards.contains(card)) {
			validCards.remove(card);
			usedCards.add(card);
		}
	}

	public int getNumberOfPermits() {
		return validCards.size() + usedCards.size();
	}

	public void sellPermits(BusinessPermit item) {
		validCards.remove(item);

	}

}
