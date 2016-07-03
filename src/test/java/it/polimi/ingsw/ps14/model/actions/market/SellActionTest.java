package it.polimi.ingsw.ps14.model.actions.market;

import static org.junit.Assert.*;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.ItemForSale.Type;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SellActionTest {

	Player player;
	Model model;
	List<ItemForSale> lista;

	@Before
	public void create() throws IOException {
		model = new Model();
		lista = new ArrayList<>();
		List<Player> pls = new ArrayList<>();
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
	}

	@Test
	public void testIsValid1() {
		lista.add(new ItemForSale(ColorPolitic.BLACK, 20, player.getId()));
		lista.add(new ItemForSale(Type.ASSISTANT, 10, 2, player.getId()));
		lista.add(new ItemForSale(Type.BUSINESS, model.getGameBoard()
				.getRegion(RegionType.COAST).getAvailablePermits()[0].getId(),
				20, player.getId()));
		lista.add(new ItemForSale(Type.POLITIC, 11, 2, player.getId()));
		SellAction action = new SellAction(lista);
		assertTrue(!action.isValid(model));

	}

	@Test
	public void testIsValid2() {
		lista.add(new ItemForSale(ColorPolitic.BLACK, 20, player.getId()));
		lista.add(new ItemForSale(Type.ASSISTANT, 10, 2, player.getId()));
		lista.add(new ItemForSale(Type.BUSINESS, model.getGameBoard()
				.getRegion(RegionType.COAST).getAvailablePermits()[0].getId(),
				20, player.getId()));
		SellAction action = new SellAction(lista);
		assertTrue(action.isValid(model));
	}

	@Test
	public void testIsValid3() {
		lista.add(new ItemForSale(Type.ASSISTANT, 40, 2, player.getId()));
		lista.add(new ItemForSale(Type.BUSINESS, model.getGameBoard()
				.getRegion(RegionType.COAST).getAvailablePermits()[0].getId(),
				20, player.getId()));
		SellAction action = new SellAction(lista);
		assertTrue(!action.isValid(model));

	}

	@Test
	public void testExecute1() {
		lista.add(new ItemForSale(ColorPolitic.BLACK, 20, player.getId()));
		lista.add(new ItemForSale(Type.ASSISTANT, 10, 2, player.getId()));
		lista.add(new ItemForSale(Type.BUSINESS, model.getGameBoard()
				.getRegion(RegionType.COAST).getAvailablePermits()[0].getId(),
				20, player.getId()));
		SellAction action = new SellAction(lista);
		Market market = new Market();
		action.execute(market);
		assertTrue(market.getObjectsForSale().containsAll(lista));
	}

}
