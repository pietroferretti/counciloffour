package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class KingBonusesViewTest {
	private Model model;
	private Player player;
	private KingBonusesView kbv;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		player = new Player(-1);
		kbv = new KingBonusesView(model.getGameBoard().getKingBonuses().peek());
		model.getGameBoard().addObserver(kbv);
	}

	@Test
	public void testKingBonusesView() {
		KingBonusesView kbv1 = new KingBonusesView(model.getGameBoard().getKingBonuses().peek());
		assertEquals(model.getGameBoard().getKingBonuses().peek().intValue(), kbv1.getShowableKingBonus());
	}

	@Test
	public void testUpdate() {
		System.out.println("\n---------testUpdate---------\n");
		System.out.println(model.getGameBoard().getKingBonuses().toString());
		System.out.println(Integer.toString(kbv.getShowableKingBonus()));

		System.out.println("Use bonus");
		model.getGameBoard().useKingBonus();

		assertEquals(model.getGameBoard().getKingBonuses().peek().intValue(), kbv.getShowableKingBonus());

		System.out.println(model.getGameBoard().getKingBonuses().toString());
		System.out.println(Integer.toString(kbv.getShowableKingBonus()));

		KingBonusesView kbv1 = new KingBonusesView(model.getGameBoard().getKingBonuses().peek());
		model.getGameBoard().addObserver(kbv1);	
		
		model.getGameBoard().getKingBonuses().clear();
		model.getGameBoard().useKingBonus();
		System.out.println(kbv1.getShowableKingBonus());
		
		assertTrue(kbv.getShowableKingBonus()==0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateException() {
		player.addObserver(kbv);
		player.addCoins(4);
	}

}
