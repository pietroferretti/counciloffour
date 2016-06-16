package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class BusinessCardsRegion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1821273532546033109L;
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

	public BusinessCardsRegion(BusinessCardsRegion bcr) {
		this.deck = new ArrayList<>();
		for (BusinessPermit bp : bcr.deck) {
			this.deck.add(new BusinessPermit(bp));
		}
		this.availablePermits = new BusinessPermit[2];
		for (int i = 0; i < availablePermits.length; i++) {
			this.availablePermits[i] = new BusinessPermit(bcr.availablePermits[i]);
		}
	}

	private void shuffle() {
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
		for (int i = 0; i < availablePermits.length; i++) {
			if (availablePermits[i] == toSubstitute) {
				availablePermits[i] = drawCardFromDeck();
				return true;
			}
		}
		return false;
	}

	public boolean cardIsFaceUp(BusinessPermit card) {

		for (BusinessPermit busPer : availablePermits) {
			if (busPer != null && busPer.getId() == card.getId()) {
				return true;
			}

		}
		return false;
	}

	public BusinessPermit[] getAvailablePermits() {
		return availablePermits;
	}

	public int cardsLeftInDeck() {
		return deck.size();
	}

	public void addCardToDeckBottom(BusinessPermit card) {
		deck.add(card);
	}

	public int findIndexBusinessPermit(BusinessPermit card) {
		if (cardIsFaceUp(card)) {
			return Arrays.asList(availablePermits).indexOf(card);
		} else {
			throw new NoSuchElementException();
		}
	}

	public void removeBusinessPermit(BusinessPermit card) {
		availablePermits[findIndexBusinessPermit(card)] = null;
	}

	public boolean fillEmptySpots() {
		for (int i = 0; i < availablePermits.length; i++) {
			if (availablePermits[i] == null) {
				if (deck.isEmpty()) {
					return false;
				} else {
					availablePermits[i] = drawCardFromDeck();
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String s = null;
		int i = 0;
		for (BusinessPermit businessPermit : availablePermits) {
			s = s + Integer.toString(i) + "° PERMIT:%n" + businessPermit.toString();
		}
		return s;
	}

}
