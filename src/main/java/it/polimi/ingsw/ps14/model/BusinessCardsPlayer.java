package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Business permits own by a player.
 *
 * 
 */
public class BusinessCardsPlayer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7627943885672603400L;
	private List<BusinessPermit> validCards;
	private List<BusinessPermit> usedCards;

	public BusinessCardsPlayer() {
		validCards = new ArrayList<>();
		usedCards = new ArrayList<>();
	}

	public BusinessCardsPlayer(BusinessCardsPlayer bcp) {
		validCards = new ArrayList<>();
		usedCards = new ArrayList<>();
		for (BusinessPermit businessPermit : bcp.usedCards) {
			this.usedCards.add(new BusinessPermit(businessPermit));
		}
		for (BusinessPermit businessPermit : bcp.validCards) {
			this.validCards.add(new BusinessPermit(businessPermit));
		}
	}

	/**
	 * add business permit to valid card
	 * 
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

	public int getNumberOfPermits() {
		return validCards.size() + usedCards.size();
	}

	/**
	 * It removes from valid cards a permits that has been sold.
	 * 
	 * @param item
	 *            - {@link BusinessPermit}
	 */
	public void sellPermits(BusinessPermit item) {
		validCards.remove(item);
	}

	public List<BusinessPermit> getValidCards() {
		return validCards;
	}

	public List<BusinessPermit> getUsedCards() {
		if (usedCards != null)
			return usedCards;
		else
			return new ArrayList<>();
	}

	/**
	 * It finds a permit object {@link BusinessPermit} given its ID.
	 * 
	 * @param id
	 *            - {@link Integer}
	 * 
	 * @return Specific {@link BusinessPermit} or null if couldn't findit.
	 */
	public BusinessPermit id2permit(Integer id) {

		for (BusinessPermit permit : validCards) {
			if (permit.getId().equals(id)) {
				return permit;
			}
		}

		for (BusinessPermit permit : usedCards) {
			if (permit.getId().equals(id)) {
				return permit;
			}
		}

		return null;
	}

	@Override
	public String toString() {
		String s = "BUSINESS CARDS\nValid Cards: ";
		if (validCards.isEmpty())
			s = s + "\nNo valid cards yet.\n";
		else {
			for (BusinessPermit businessPermit : validCards) {
				s = s + businessPermit.toString() + "\n";
			}
		}
		s = s + "Used Cards: ";
		if (usedCards.isEmpty())
			s = s + "\nNo used cards yet.";
		else {
			for (BusinessPermit businessPermit : usedCards) {
				s = s + businessPermit.toString();
			}
		}

		return s;
	}

}
