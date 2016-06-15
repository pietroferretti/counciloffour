package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.assertTrue;

import java.util.EnumMap;
import java.util.Map;

import org.junit.Test;

public class ColorCouncillorTest {

	/**
	 * Tests if {@link ColorCouncillor#getRandomCouncillor()
	 * getRandomCouncillor} is able to return at least one of each available
	 * color.
	 */
	@Test
	public void testGetRandomCouncillor() {
		Map<ColorCouncillor, Integer> colorOccurrences = new EnumMap<>(ColorCouncillor.class);

		for (ColorCouncillor color : ColorCouncillor.values()) {
			colorOccurrences.put(color, 0);
		}

		for (int i = 0; i < 100; i++) {
			ColorCouncillor randomColor = ColorCouncillor.getRandomCouncillor();
			colorOccurrences.put(randomColor, colorOccurrences.get(randomColor) + 1);
		}

		for (Map.Entry<ColorCouncillor, Integer> entry : colorOccurrences.entrySet()) {
			assertTrue(entry.getValue() >= 1);
		}
	}

}
