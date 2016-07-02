package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.ItemForSale.Type;

public class MarketViewTest {

	private static Model model;
	private static Player player1, player2;
	private static BusinessPermit b;
	private static Market market;
	private static ItemForSale assistants, business, p1;
	private static MarketView mv;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = new Model();
		player1 = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		player2 = new Player("sdds", Color.cyan, 20, 12, model.getGameBoard().getPoliticDeck(), 4);
		b = new BusinessPermit(new ArrayList<>(), null);
		market = model.getMarket();
		assistants = new ItemForSale(Type.ASSISTANT, 2, 4, player1.getId());
		business = new ItemForSale(Type.BUSINESS, b.getId(), 6, player2.getId());
		market.getObjectsForSale().add(assistants);
		market.getObjectsForSale().add(business);
		mv = new MarketView(market);
		market.addObserver(mv);
	}

	@Test
	public void testMarketView() {
		Market copy = new Market(market);
		assertNotSame(market, copy);
		assertEquals(market.toString(), copy.toString());
	}

	@Test
	public void testUpdatesomeAssistantsSold() {
		System.out.println("\n---------testUpdatesomeAssistantsSold---------\n");
		market.someAssistantsSold(assistants, 1);
		System.out.println(mv.getMarketCopy().toString());
		System.out.println(market.toString());
		assertNotSame(market, mv.getMarketCopy());
		assertEquals(market.toString(), mv.getMarketCopy().toString());
	}

	@Test
	public void testUpdateaddItem() {
		System.out.println("\n---------testUpdateaddItem---------\n");
		market.addItem(new ItemForSale(ColorPolitic.BLACK, 4, 1));
		assertNotSame(market, mv.getMarketCopy());
		assertEquals(market.toString(), mv.getMarketCopy().toString());
	}

	@Test
	public void testUpdateremoveItem() {
		System.out.println("\n---------testUpdateremoveItem---------\n");
		market.removeItem(business);
		assertNotSame(market, mv.getMarketCopy());
		assertEquals(market.toString(), mv.getMarketCopy().toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateException() {
		player1.addObserver(mv);
		player1.addCoins(4);
	}

}
