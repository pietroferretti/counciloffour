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

	/**
	 * draw a card from the deck
	 * @return new drawn card
	 */
	private BusinessPermit drawCardFromDeck() {
		if (!deck.isEmpty()) {
			return deck.remove(0);
		} else {
			return null;
		}
	}

	/**
	 * substitute the card toSubstitute with a new one, drawn from deck
	 * @param toSubstitute
	 * @return true if this method is successfully done
	 */
	public boolean substituteCard(BusinessPermit toSubstitute) {
		for (int i = 0; i < availablePermits.length; i++) {
			if (availablePermits[i] == toSubstitute) {
				availablePermits[i] = drawCardFromDeck();
				return true;
			}
		}
		return false;
	}

	/**
	 * check if card is one of the available card from this region
	 * @param card to check
	 * @return true or false
	 */
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

	/**
	 * find the parameter index of the array of available cards 
	 * @param card that you want to know index
	 * @return index of the parameter card
	 */
	public int findIndexBusinessPermit(BusinessPermit card) {
		if (cardIsFaceUp(card)) {
			return Arrays.asList(availablePermits).indexOf(card);
		} else {
			throw new NoSuchElementException();
		}
	}

	/**
	 * remove businessPermit from available card and insert null
	 * @param card
	 */
	public void removeBusinessPermit(BusinessPermit card) {
		availablePermits[findIndexBusinessPermit(card)] = null;
	}

	
	/**
	 * if deck is not empty, this method fill the empty spot of the available cards
	 * @return false is the deck is empty,  otherwise true
	 */
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
