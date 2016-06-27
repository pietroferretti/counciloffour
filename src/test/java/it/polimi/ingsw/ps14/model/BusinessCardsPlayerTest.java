package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;

import org.junit.Test;

public class BusinessCardsPlayerTest {

	BusinessPermit busCard;
	Player player;

	public void create() throws IOException {
		Model model = new Model();

		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}

		GameBoard gameboard = new GameBoard(settingsInstance);

		busCard = model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0];
		player = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		busCard = model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0];
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
