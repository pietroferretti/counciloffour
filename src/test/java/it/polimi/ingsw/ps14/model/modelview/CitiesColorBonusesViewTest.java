package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		rbv = new CitiesColorBonusesView(model.getGameBoard().getBonusGold(), model.getGameBoard().getBonusSilver(),
				model.getGameBoard().getBonusBronze(), model.getGameBoard().getBonusBlue());
		model.getGameBoard().addObserver(rbv);
	}

	@Test
	public void testRegionBonusesView() {
		CitiesColorBonusesView rbv1 = new CitiesColorBonusesView(model.getGameBoard().getBonusGold(),
				model.getGameBoard().getBonusSilver(), model.getGameBoard().getBonusBronze(),
				model.getGameBoard().getBonusBlue());

		assertEquals(model.getGameBoard().getBonusBlue(), rbv1.getBonusBlueCopy());
		assertEquals(model.getGameBoard().getBonusBronze(), rbv1.getBonusBronzeCopy());
		assertEquals(model.getGameBoard().getBonusGold(), rbv1.getBonusGoldCopy());
		assertEquals(model.getGameBoard().getBonusSilver(), rbv1.getBonusSilverCopy());
	}

	@Test
	public void testUpdateGold() {
		System.out.println("\n---------testUpdateGold---------\n");
		System.out.println(Integer.toString(model.getGameBoard().getBonusGold()));
		model.getGameBoard().useBonusGold();

		assertEquals(model.getGameBoard().getBonusBlue(), rbv.getBonusBlueCopy());
		assertEquals(model.getGameBoard().getBonusBronze(), rbv.getBonusBronzeCopy());
		assertEquals(model.getGameBoard().getBonusGold(), rbv.getBonusGoldCopy());
		assertEquals(model.getGameBoard().getBonusSilver(), rbv.getBonusSilverCopy());

		System.out.println(rbv.toString());

	}

	@Test
	public void testUpdateBlue() {
		System.out.println("\n---------testUpdateBlue---------\n");
		System.out.println(Integer.toString(model.getGameBoard().getBonusBlue()));
		model.getGameBoard().useBonusBlue();

		assertEquals(model.getGameBoard().getBonusBlue(), rbv.getBonusBlueCopy());
		assertEquals(model.getGameBoard().getBonusBronze(), rbv.getBonusBronzeCopy());
		assertEquals(model.getGameBoard().getBonusGold(), rbv.getBonusGoldCopy());
		assertEquals(model.getGameBoard().getBonusSilver(), rbv.getBonusSilverCopy());

		System.out.println(rbv.toString());
	}

	@Test
	public void testUpdateSilver() {
		System.out.println("\n---------testUpdateSilver---------\n");
		System.out.println(Integer.toString(model.getGameBoard().getBonusSilver()));
		model.getGameBoard().useBonusSilver();

		assertEquals(model.getGameBoard().getBonusBlue(), rbv.getBonusBlueCopy());
		assertEquals(model.getGameBoard().getBonusBronze(), rbv.getBonusBronzeCopy());
		assertEquals(model.getGameBoard().getBonusGold(), rbv.getBonusGoldCopy());
		assertEquals(model.getGameBoard().getBonusSilver(), rbv.getBonusSilverCopy());

		System.out.println(rbv.toString());

	}

	@Test
	public void testUpdateBronze() {
		System.out.println("\n---------testUpdateBronze---------\n");
		System.out.println(Integer.toString(model.getGameBoard().getBonusBronze()));
		model.getGameBoard().useBonusBronze();

		assertEquals(model.getGameBoard().getBonusBlue(), rbv.getBonusBlueCopy());
		assertEquals(model.getGameBoard().getBonusBronze(), rbv.getBonusBronzeCopy());
		assertEquals(model.getGameBoard().getBonusGold(), rbv.getBonusGoldCopy());
		assertEquals(model.getGameBoard().getBonusSilver(), rbv.getBonusSilverCopy());

		System.out.println(rbv.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateException() {
		player.addObserver(rbv);
		player.addCoins(4);
	}

}
