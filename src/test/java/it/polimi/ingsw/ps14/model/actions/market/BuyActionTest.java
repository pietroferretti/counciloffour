package it.polimi.ingsw.ps14.model.actions.market;

import static org.junit.Assert.*;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.ItemForSale.Type;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class BuyActionTest {
	
	static Player player,player2;
	static Model model;
	static List<ItemForSale> lista;
	
	@BeforeClass
	public static void create() throws IOException {
		model = new Model();
		lista = new ArrayList<>();
		List<Player> pls = new ArrayList<>();
		player2 = new Player("gigi", Color.DARK_GRAY, 20, 12, model
				.getGameBoard().getPoliticDeck(), 4);
		pls.add(player2);
		player = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model
				.getGameBoard().getPoliticDeck(), 4);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));
		player.addPolitic(new PoliticCard(ColorPolitic.BLACK));
		player.addPolitic(new PoliticCard(ColorPolitic.PINK));
		player.addPolitic(new PoliticCard(ColorPolitic.ORANGE));
		player.acquireBusinessPermit(model.getGameBoard()
				.getRegion(RegionType.COAST).getAvailablePermits()[0]);
		player.addAssistants(10);
	
		lista.add(new ItemForSale(ColorPolitic.BLACK, 20, player.getId()));
		lista.add(new ItemForSale(Type.ASSISTANT, 10, 2, player.getId()));
		lista.add(new ItemForSale(Type.BUSINESS, model.getGameBoard()
				.getRegion(RegionType.COAST).getAvailablePermits()[0].getId(),
				20, player.getId()));
		Market market = new Market();
		for(ItemForSale item:lista)
			market.addItem(item);
		model.setMarket(market);
	}

	@Test
	public void testIsValid1() {
		player2.addCoins(10);
		BuyAction action = new BuyAction(player2.getId(),3,null);
		assertTrue(!action.isValid(model));
	}
	
	@Test
	public void testIsValid2() {
		player2.useCoins(player2.getCoins());
		BuyAction action = new BuyAction(player2.getId(),2,null);
		assertTrue(!action.isValid(model));
	}
	
	@Test
	public void testIsValid3() {
		player2.addCoins(100);
		BuyAction action = new BuyAction(player2.getId(),1,2);
		assertTrue(action.isValid(model));
	}

	@Test
	public void testExecute() {
		int p2ass=player2.getAssistants();
		int p1ass=player.getAssistants();
		BuyAction action = new BuyAction(player2.getId(),1,2);
		action.execute(model);
		assertEquals(player2.getAssistants()-2,p2ass);
		assertEquals(player.getAssistants()+2,p1ass);
	}

	@Test
	public void testExecute2() {
		BusinessPermit bp=model.id2permit(model.getMarket().getObjectsForSale().get(2).getIdORquantity(), player);
		BuyAction action = new BuyAction(player2.getId(),2,null);
		action.execute(model);
		assertTrue(player2.getBusinessHand().getValidCards().contains(bp));
		assertTrue(!player.getBusinessHand().getValidCards().contains(bp));
	}
	
	@Test
	public void testExecute3() {
		int oldSize2=player2.getHand().size();
		int oldSize1=player.getHand().size();
		BuyAction action = new BuyAction(player2.getId(),0,null);
		action.execute(model);
		assertEquals(player2.getHand().size()-1,oldSize2);
		assertEquals(player.getHand().size()+1,oldSize1);
	}
}
