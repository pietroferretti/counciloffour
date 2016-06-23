package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.ItemForSale.Type;

public class ItemForSaleTest {
	private static Model model;
	private static Player player1, player2;
	private static BusinessPermit b;
	private static ItemForSale assistants, business, politic, p1, p1copy;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = new Model();
		player1 = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		player2 = new Player("sdds", Color.cyan, 20, 12, model.getGameBoard().getPoliticDeck(), 4);
		b = new BusinessPermit(null, null);
		player1.addPolitic(new PoliticCard(ColorPolitic.BLUE));
		player2.getBusinessHand().getValidCards().add(b);
		List<Player> players = new ArrayList<>(2);
		players.add(player1);
		players.add(player2);
		model.setPlayers(players);

		assistants = new ItemForSale(Type.ASSISTANT, 2, 4, 1);
		business = new ItemForSale(Type.BUSINESS, b.getId(), 6, 2);
		politic = new ItemForSale(Type.POLITIC, 5, 4, 1);
		p1 = new ItemForSale(ColorPolitic.BLUE, 5, 1);
		p1copy = new ItemForSale(p1);
	}

	@Test
	public void testItemForSaleTypeIntIntInteger() {
		assertTrue(assistants.getBarCode() == 1);
		assertTrue(business.getBarCode() == 2);
		assertTrue(politic.getBarCode() == 3);
	}

	@Test
	public void testItemForSaleColorPoliticIntInteger() {
		assertTrue(p1.getBarCode() == 4);
	}

	@Test
	public void testItemForSaleItemForSale() {
		assertEquals(p1.getBarCode(), p1copy.getBarCode());
		assertEquals(p1.getColor(), p1copy.getColor());
		assertEquals(p1.getIdORquantity(), p1copy.getIdORquantity());
		assertEquals(p1.getOwnerID(), p1copy.getOwnerID());
		assertEquals(p1.getPrice(), p1copy.getPrice());
		assertEquals(p1.getType(), p1copy.getType());
	}

	@Test
	public void testIsValid() {
		ItemForSale assistants1 = new ItemForSale(Type.ASSISTANT, 80, 8, 1);
		ItemForSale assistants2 = new ItemForSale(Type.ASSISTANT, 1, 6, 89);
		assertTrue(assistants.isValid(model));
		assertFalse(assistants1.isValid(model));
		assertFalse(assistants2.isValid(model));

		assertTrue(business.isValid(model));
		ItemForSale business1 = new ItemForSale(Type.BUSINESS, 6000, 7, 1);
		assertFalse(business1.isValid(model));
		ItemForSale business2 = new ItemForSale(Type.BUSINESS, b.getId(), 5, 89);
		assertFalse(business2.isValid(model));

		Player player = new Player();
		player.getHand().add(new PoliticCard(ColorPolitic.BLACK));
		assertTrue(p1.isValid(model));
		ItemForSale p11 = new ItemForSale(ColorPolitic.BLACK, 6, player.getId() + 5);
		assertFalse(p11.isValid(model));
		ItemForSale p12 = new ItemForSale(ColorPolitic.ORANGE, 3, player.getId());
		assertFalse(p12.isValid(model));

	}

	@Test
	public void testRemoveAssistant() {
		assertTrue(assistants.removeAssistant(1));
		assertTrue(assistants.getIdORquantity().equals(1));
		assertFalse(assistants.removeAssistant(10));
		assertFalse(business.removeAssistant(1));
	}

}