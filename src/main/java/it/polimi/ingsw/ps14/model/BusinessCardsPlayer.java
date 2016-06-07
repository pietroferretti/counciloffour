package it.polimi.ingsw.ps14.model;

import java.util.ArrayList;
import java.util.List;

public class BusinessCardsPlayer {
	private List<BusinessPermit> validCards;
	private List<BusinessPermit> usedCards;

	public BusinessCardsPlayer() {
		validCards = new ArrayList<>();
		usedCards = new ArrayList<>();
	}

	/**
	 * check if there is caard in unused businessPermitCard 
	 * @param card card to check
	 * @return
	 */
	public boolean checkBusinessPermit(BusinessPermit card) {
		if (validCards.contains(card))
			return true;
		else
			return false;
	}

	
	/**
	 * add business permit to valid card
	 * @param card
	 */
	public void acquireBusinessPermit(BusinessPermit card) {
		validCards.add(card);
	}

	/**
	 * remove business permit from validCard and add it to usedCard
	 * 
	 * @param card
	 *            card to use
	 */
	public void usePermit(BusinessPermit card) {
			validCards.remove(card);
			usedCards.add(card);
	}

	public boolean contains(BusinessPermit cardChoice) {
		for (BusinessPermit card : validCards) {
			if (card.equals(cardChoice))
				return true;
		}
		return false;
	}

	public int getNumberOfPermits() {
		return validCards.size() + usedCards.size();
	}

	/**
	 * remove from valid cards player
	 * @param item
	 */
	public void sellPermits(BusinessPermit item) {
		validCards.remove(item);

	}

	public List<BusinessPermit> getValidCards() {
		return validCards;
	}

	public void setValidCards(List<BusinessPermit> validCards) {
		this.validCards = validCards;
	}

	public List<BusinessPermit> getUsedCards() {
		return usedCards;
	}

	public void setUsedCards(List<BusinessPermit> usedCards) {
		this.usedCards = usedCards;
	}

	@Override
	public String toString() {
		return "BusinessCardsPlayer [validCards=" + validCards + ", usedCards=" + usedCards + "]";
	}

}