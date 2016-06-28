package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class AvailableCouncillorsViewTest {

	private static Model model;
	private static AvailableCouncillorsView acv2;
	private static Player player;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = new Model();
		System.out.println(model.getGameBoard().getAvailableCouncillors().toString());
		acv2 = new AvailableCouncillorsView(model.getGameBoard().getAvailableCouncillors());
		model.getGameBoard().addObserver(acv2);
		player = new Player();
		player.addObserver(acv2);
	}

	@Test
	public void testAvailableCouncillorsView() {
		AvailableCouncillorsView acv = new AvailableCouncillorsView(model.getGameBoard().getAvailableCouncillors());
		assertNotSame(model.getGameBoard().getAvailableCouncillors(), acv.getAvailableCouncillorsCopy());
		assertEquals(model.getGameBoard().getAvailableCouncillors(), acv.getAvailableCouncillorsCopy());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdate() {

		assertTrue(model.getGameBoard().useCouncillor(ColorCouncillor.PINK));
		assertEquals(model.getGameBoard().getAvailableCouncillors(), acv2.getAvailableCouncillorsCopy());
		System.out.println(acv2.getAvailableCouncillorsCopy().toString());

		player.addCoins(4);
	}

}
