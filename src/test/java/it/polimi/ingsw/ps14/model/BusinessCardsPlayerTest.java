package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class BusinessCardsPlayerTest {

	private static Model model;
	private static BusinessPermit busCard, busCard2, busCard3, busCard4;
	private static BusinessCardsPlayer bcp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = new Model();
		busCard = model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0];
		busCard2 = model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[1];
		busCard3 = model.getGameBoard().getRegion(RegionType.MOUNTAINS).getBusinessPermits().getAvailablePermits()[1];
		bcp = new BusinessCardsPlayer();
		bcp.getValidCards().add(busCard);
		bcp.getValidCards().add(busCard2);
		bcp.getUsedCards().add(busCard3);
		System.out.println(bcp);
	}

	@Test
	public void testBusinessCardsPlayerBusinessCardsPlayer() {

		BusinessCardsPlayer bcopy = new BusinessCardsPlayer(bcp);
		assertNotSame(bcp, bcopy);
		assertEquals(bcp.getNumberOfPermits(), bcopy.getNumberOfPermits());
		assertEquals(bcp.getUsedCards().toString(), bcopy.getUsedCards().toString());
	}

	@Test
	public void testAcquireBusinessPermit() {
		busCard4 = model.getGameBoard().getRegion(RegionType.MOUNTAINS).getBusinessPermits().getAvailablePermits()[0];
		bcp.acquireBusinessPermit(busCard4);
		assertEquals(busCard4, bcp.getValidCards().get(bcp.getValidCards().size() - 1));

	}

	@Test
	public void testUsePermit() {
		bcp.usePermit(busCard);
		assertTrue(bcp.getUsedCards().contains(busCard));
		assertFalse(bcp.getValidCards().contains(busCard));

	}

	@Test
	public void testGetNumberOfPermits() {
		assertEquals(bcp.getNumberOfPermits(), bcp.getUsedCards().size() + bcp.getValidCards().size());
	}

	@Test
	public void testSellPermits() {
		bcp.sellPermits(busCard2);
		assertFalse(bcp.getUsedCards().contains(busCard2));
		assertFalse(bcp.getValidCards().contains(busCard2));
	}

	@Test
	public void testId2permit() {
		assertEquals(busCard, bcp.id2permit(busCard.getId()));
		assertEquals(busCard3, bcp.id2permit(busCard3.getId()));
	}
}
