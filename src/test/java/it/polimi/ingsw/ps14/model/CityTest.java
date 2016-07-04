package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class CityTest {

	private static Model model;
	private static City city;
	private static Player player, player1, player2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = new Model();
		player = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		player1 = new Player("sdds", Color.cyan, 20, 12, model.getGameBoard().getPoliticDeck(), 4);
		player2 = new Player("dfgh", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		city = model.getGameBoard().getCities().get(2);
		System.out.println(city);
		city.getEmporiums().add(player);
	}

	@Test
	public void testCityStringColorCityRegionListOfCityBonus() {

	}

	@Test
	public void testCityCity() {
		City c = new City(city);
		assertNotSame(city, c);
		assertEquals(city.getName(), c.getName());
		assertEquals(city.getColor(), c.getColor());
		assertEquals(city.getRegion().getType(), c.getRegionType());
		assertEquals(city.getEmporiums().toString(), c.getEmporiums().toString());
		assertEquals(city.getToken().toString(), c.getToken().toString());
		List<String> neighborsName = new ArrayList<>(city.getNeighbors().size());
		for (City cty : city.getNeighbors()) {
			neighborsName.add(cty.getName());
		}
		System.out.println(neighborsName.toString());
		assertEquals(neighborsName.toString(), c.getNeighborsName().toString());
		assertEquals(city.toString(), c.toString());
	}

	@Test
	public void testNumEmporiumsBuilt() {
		assertTrue(city.numEmporiumsBuilt() > 0);
		assertFalse(city.numEmporiumsBuilt() < 0);
		assertFalse(city.numEmporiumsBuilt() > 2);
	}

	@Test
	public void testIsEmporiumBuilt() {
		assertTrue(city.isEmporiumBuilt(player));
		assertFalse(city.isEmporiumBuilt(player2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildEmporium() {

		city.buildEmporium(player1);
		assertTrue(city.isEmporiumBuilt(player1));

		city.buildEmporium(player);
	}
}
