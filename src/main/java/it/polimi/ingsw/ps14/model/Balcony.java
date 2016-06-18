package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Balcony implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2587226995863764413L;
	private Queue<ColorCouncillor> councillors;

	public Balcony(Queue<ColorCouncillor> initialCouncillors) {
		councillors = initialCouncillors;
	}

	public Balcony(Balcony b) {
		this.councillors = new LinkedList<>();
		for (ColorCouncillor colorCouncillor : b.councillors) {
			this.councillors.add(colorCouncillor);
		}

	}

	/**
	 * elect new councillor and discard a councillor from the head of balcony
	 * @param color to elect
	 * @return color councillor discarded
	 */
	public ColorCouncillor electCouncillor(ColorCouncillor color) {
		ColorCouncillor discarded = councillors.poll();
		councillors.add(color);
		return discarded;
	}

	public Queue<ColorCouncillor> readBalcony() {
		return councillors;
	}

	private ColorCouncillor colorPoliticToCouncillor(PoliticCard card) {
		return ColorCouncillor.valueOf(card.getColor().name());
	}

	/**
	 * Returns true if the cards match the councillors in this council.
	 * 
	 * @param cards
	 * @return true if the cards match the councillors in this council
	 */
	public boolean cardsMatch(List<PoliticCard> cards) {
		Queue<ColorCouncillor> newCouncillors = new LinkedList<>(councillors);

		if (cards.size() > councillors.size()) {
			return false;
		} else {
			for (PoliticCard card : cards) {
				if (card.isJolly()) {
					continue;
				} else if (newCouncillors.contains(colorPoliticToCouncillor(card))) {
					newCouncillors.remove(colorPoliticToCouncillor(card));
				} else {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Returns the amount cards that match this council. All the cards must
	 * match.
	 * 
	 * @param cards
	 *            - cards to be checked
	 * @return The cost in coins the player has to pay to bribe this council
	 *         with these cards
	 * @throws IllegalArgumentException
	 *             if there are cards that don't match
	 */
	public int numOfMatchingCards(List<PoliticCard> cards) {
		// FIXME possiamo aggiungere jolly all'infinito, non funziona
		int bribedCouncillors = 0;
		Queue<ColorCouncillor> newCouncillors = new LinkedList<>(councillors);

		if (cards.size() > councillors.size()) {
			throw new IllegalArgumentException("Too many cards!");
		} else {
			for (PoliticCard card : cards) {
				if (card.isJolly())
					bribedCouncillors++;
				else if (newCouncillors.contains(colorPoliticToCouncillor(card))) {
					newCouncillors.remove(colorPoliticToCouncillor(card));
					bribedCouncillors++;
				} else
					throw new IllegalArgumentException(
							"Some cards didn't match! -- Hint: the code should check the cards before calling this method --");
			}

			return bribedCouncillors;
		}
	}

	/**
	 * Returns the amount of coins the player has to pay to bribe this council
	 * passed as parameter. All the cards must be used.
	 * 
	 * @param cards
	 *            - cards selected to bribe the council
	 * @return The cost in coins the player has to pay to bribe this council
	 *         with these cards
	 * @throws IllegalArgumentException
	 *             if there are unused cards
	 */
	public int councillorCost(List<PoliticCard> cards) {
		int cost;
		int numMatching = numOfMatchingCards(cards);

		if (numMatching == councillors.size()) {
			cost = 0;
		} else {
			cost = 1 + 3 * (councillors.size() - numMatching);
		}

		for (PoliticCard card : cards) {
			if (card.getColor() == ColorPolitic.JOLLY)
				cost++;
		}

		return cost;
	}

	
	
@Override
	public String toString() {
		String s = "%nhead%n";
		for (ColorCouncillor colorCouncillor : councillors) {
			s = s + colorCouncillor.toString() + " ";
		}
		return ( s + "%ntail%n");
	}

}
