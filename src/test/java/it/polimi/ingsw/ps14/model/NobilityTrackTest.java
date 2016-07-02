package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NobilityTrackTest {
	private static Model model;
	private static Player p;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = new Model();
		System.out.println(model.getGameBoard().getNobilityTrack().toString());
		p = mock(Player.class);
		when(p.getLevel()).thenReturn(2);
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNobilityTrack() {
		NobilityTrack nt = new NobilityTrack();
		assertTrue(nt.getBonusesByLevel().isEmpty());
	}

	// @Test
	// public void testNobilityTrackSortedMapOfIntegerBonus() {
	// fail("Not yet implemented");
	// }

	@Test
	public void testNobilityTrackNobilityTrack() {
		NobilityTrack nt = new NobilityTrack(model.getGameBoard().getNobilityTrack());
		assertNotSame(model.getGameBoard().getNobilityTrack(), nt);
		assertEquals(model.getGameBoard().getNobilityTrack().toString(), nt.toString());
	}

	@Test
	public void testBonusExistsAtLevel() {
		assertTrue(model.getGameBoard().getNobilityTrack().bonusExistsAtLevel(2));
		assertFalse(model.getGameBoard().getNobilityTrack().bonusExistsAtLevel(0));
	}

	@Test
	public void testGetBonus() {
		assertEquals(model.getGameBoard().getNobilityTrack().getBonusesByLevel().get(2),
				model.getGameBoard().getNobilityTrack().getBonus(2));
		assertNotEquals(model.getGameBoard().getNobilityTrack().getBonus(2), model.getGameBoard().getNobilityTrack().getBonusesByLevel().get(1));
	}

}
