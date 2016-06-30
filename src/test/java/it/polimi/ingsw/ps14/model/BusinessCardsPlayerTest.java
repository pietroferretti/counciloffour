package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;

import org.junit.Test;

public class BusinessCardsPlayerTest {

	BusinessPermit busCard;
	Player player;

	public void create() throws IOException {
		Model model = new Model();

		busCard = model.getGameBoard().getRegion(RegionType.COAST)
				.getBusinessPermits().getAvailablePermits()[0];
		player = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model
				.getGameBoard().getPoliticDeck(), 6);
		busCard = model.getGameBoard().getRegion(RegionType.COAST)
				.getBusinessPermits().getAvailablePermits()[0];
	}

	@Test
	public void testUsePermit() throws IOException {
		create();
		// FIXME
		// System.out.println(busCard);

		player.acquireBusinessPermit(busCard);

		busCard = player.getBusinessHand().getValidCards().get(0);
		player.getBusinessHand().usePermit(busCard);
		assertEquals(player.getBusinessHand().checkBusinessPermit(busCard), false);

	}

}
