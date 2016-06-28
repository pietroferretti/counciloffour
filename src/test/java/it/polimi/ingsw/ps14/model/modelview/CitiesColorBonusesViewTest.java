package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.ColorCity;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class CitiesColorBonusesViewTest {

	private Model model;
	private Player player;
	private CitiesColorBonusesView rbv;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		player = new Player();
		rbv = new CitiesColorBonusesView(model.getGameBoard().getColorBonuses());
		model.getGameBoard().addObserver(rbv);
	}

	@Test
	public void testRegionBonusesView() {
		CitiesColorBonusesView rbv1 = new CitiesColorBonusesView(model.getGameBoard().getColorBonuses());

		assertEquals(model.getGameBoard().getColorBonuses(), rbv1.getColorBonusesCopy());
	}

	@Test
	public void testUpdateGold() {
		System.out.println("\n---------testUpdateGold---------\n");
		System.out.println(Integer.toString(model.getGameBoard().getColorBonus(ColorCity.GOLD)));
		model.getGameBoard().useColorBonus(ColorCity.GOLD);

		assertEquals(model.getGameBoard().getColorBonuses(), rbv.getColorBonusesCopy());

		System.out.println(rbv.toString());

	}

	@Test
	public void testUpdateBlue() {
		System.out.println("\n---------testUpdateBlue---------\n");
		System.out.println(Integer.toString(model.getGameBoard().getColorBonus(ColorCity.BLUE)));
		model.getGameBoard().useColorBonus(ColorCity.BLUE);

		assertEquals(model.getGameBoard().getColorBonuses(), rbv.getColorBonusesCopy());

		System.out.println(rbv.toString());
	}

	@Test
	public void testUpdateSilver() {
		System.out.println("\n---------testUpdateSilver---------\n");
		System.out.println(Integer.toString(model.getGameBoard().getColorBonus(ColorCity.SILVER)));
		model.getGameBoard().useColorBonus(ColorCity.SILVER);

		assertEquals(model.getGameBoard().getColorBonuses(), rbv.getColorBonusesCopy());

		System.out.println(rbv.toString());

	}

	@Test
	public void testUpdateBronze() {
		System.out.println("\n---------testUpdateBronze---------\n");
		System.out.println(Integer.toString(model.getGameBoard().getColorBonus(ColorCity.BRONZE)));
		model.getGameBoard().useColorBonus(ColorCity.BRONZE);

		assertEquals(model.getGameBoard().getColorBonuses(), rbv.getColorBonusesCopy());

		System.out.println(rbv.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateException() {
		player.addObserver(rbv);
		player.addCoins(4);
	}

}
