package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

public class RegionTest {

	private Model model;
	private Queue<ColorCouncillor> councillors;
	private Region r3;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		councillors = new LinkedList<>();
		for (int i = 0; i < 5; i++) {
			councillors.add(ColorCouncillor.getRandomCouncillor());
		}
		r3 = model.getGameBoard().getRegion(RegionType.COAST);
		System.out.println(r3);
	}

	@Test
	public void testRegionQueueOfColorCouncillorRegionType() {
		Region r = new Region(councillors, RegionType.COAST);
		assertEquals(councillors, r.getBalcony().readBalcony());
		assertEquals(RegionType.COAST, r.getType());
		assertEquals(0, r.getBonusRegion());
		assertTrue(r.getBusinessPermits() instanceof BusinessCardsRegion);
		assertTrue(r.getCities() instanceof ArrayList<?>);

		Region r1 = new Region(councillors, RegionType.HILLS);
		assertEquals(councillors, r1.getBalcony().readBalcony());
		assertEquals(RegionType.HILLS, r1.getType());
		assertEquals(0, r1.getBonusRegion());
		assertTrue(r1.getBusinessPermits() instanceof BusinessCardsRegion);
		assertTrue(r1.getCities() instanceof ArrayList<?>);

		Region r2 = new Region(councillors, RegionType.MOUNTAINS);
		assertEquals(councillors, r2.getBalcony().readBalcony());
		assertEquals(RegionType.MOUNTAINS, r2.getType());
		assertEquals(0, r2.getBonusRegion());
		assertTrue(r2.getBusinessPermits() instanceof BusinessCardsRegion);
		assertTrue(r2.getCities() instanceof ArrayList<?>);
	}

	@Test
	public void testRegionRegion() {
		Region r1 = new Region(r3);
		assertEquals(r3.getBalcony().readBalcony(), r1.getBalcony().readBalcony());
		assertNotSame(r3.getBalcony().readBalcony(), r1.getBalcony().readBalcony());
		assertEquals(r3.getType(), r1.getType());
		assertTrue(r1.getCities().size() == r3.getCities().size());
		int index = 0;
		for (City city : r3.getCities()) {
			assertEquals(city.getName(), r1.getCities().get(index).getName());
			assertEquals(city.toString(), r1.getCities().get(index).toString());

			index++;
		}
		// FIXME assertEquals(r3.toString(), r1.toString());
		// assertEquals(r3.getBusinessPermits().toString(),
		// r1.getBusinessPermits().toString());
		// System.out.println(r3.getBusinessPermits());
		// System.out.println(r1.getBusinessPermits());
		assertEquals(r3.getBonusRegion(), r1.getBonusRegion());
	}

	// Esti Dorful Arkon Burgen Castrum
	@Test
	public void testFindCity() {
		assertTrue(r3.findCity("Esti").getName().equals("Esti"));

		for (City c : r3.getCities()) {
			assertEquals(r3.findCity(c.getName()), c);
		}
	}

}
