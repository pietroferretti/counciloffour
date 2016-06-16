package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class BusinessDeck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8258733937074712103L;
	private Deque<BusinessPermit> deck;
	private Deque<BusinessPermit> drawnCards;

	public BusinessDeck(List<BusinessPermit> deck) {
		this.deck = new ArrayDeque<>(deck);
		drawnCards = new ArrayDeque<>();
		shuffle();
	}

	public BusinessDeck(BusinessDeck bd) {
		this.deck = new ArrayDeque<>(bd.deck.size());
		for (BusinessPermit businessPermit : bd.deck) {
			this.deck.add(new BusinessPermit(businessPermit));
		}
		drawnCards = new ArrayDeque<>(bd.drawnCards.size());
		for (BusinessPermit businessPermit : bd.drawnCards) {
			this.drawnCards.add(new BusinessPermit(businessPermit));
		}
	}

	public void shuffle() {
		List<BusinessPermit> tempList = new ArrayList<>(deck);
		Collections.shuffle(tempList);
		deck = new ArrayDeque<>(tempList);
	}

	public BusinessPermit drawCard() {
		BusinessPermit card = (BusinessPermit) deck.removeFirst();
		drawnCards.addFirst(card);
		return card;
	}

	public void addToBottom(BusinessPermit card) {
		deck.addLast(card);
	}

	@Override
	public String toString() {
		return "BusinessDeck [deck=" + deck + ", drawnCards=" + drawnCards + "]";
	}

}