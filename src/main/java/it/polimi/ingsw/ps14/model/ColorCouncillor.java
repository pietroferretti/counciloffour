package it.polimi.ingsw.ps14.model;

import java.util.Random;

/**
 * Colors that a councillor can be. Used in {@link Balcony}.
 */
public enum ColorCouncillor {

	PURPLE, PINK, WHITE, ORANGE, BLACK, BLUE;

	private static final Random random = new Random();

	/**
	 * Returns a random councillor color.
	 */
	public static ColorCouncillor getRandomCouncillor() {
		return values()[random.nextInt(values().length)];
	}
}