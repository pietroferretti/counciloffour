package it.polimi.ingsw.ps14.model;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.PoliticDeck;
import it.polimi.ingsw.ps14.model.RegionType;
import static org.junit.Assert.*;
import it.polimi.ingsw.ps14.model.bonus.BonusAssistant;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PoliticDeckTest {

	Player player;
	Model model;
	List<PoliticCard> cards;

	@Before
	public void create() throws IOException {

		model = new Model();

		List<Player> pls = new ArrayList<>();
		cards = new ArrayList<>();

		player = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model
				.getGameBoard().getPoliticDeck(), 4);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));

		model.getGameBoard().getRegion(RegionType.COAST).getBalcony()
				.electCouncillor(ColorCouncillor.BLACK);
		model.getGameBoard().getRegion(RegionType.COAST).getBalcony()
				.electCouncillor(ColorCouncillor.PINK);
		model.getGameBoard().getRegion(RegionType.COAST).getBalcony()
				.electCouncillor(ColorCouncillor.ORANGE);
	}

	@Test
	public void testDrawCard() {
		model.getGameBoard()
				.getPoliticDeck()
				.discardCards(
						model.getGameBoard()
								.getPoliticDeck()
								.drawMultipleCards(
										model.getGameBoard().getPoliticDeck()
												.getDeck().size()));
		System.out.println(model.getGameBoard().getPoliticDeck().getDeck()
				.size());
		assertTrue(model.getGameBoard().getPoliticDeck().getDeck().size() == 0);

		model.getGameBoard().getPoliticDeck().drawCard();
		// System.out.println(model.getGameBoard().getPoliticDeck().getDeck().size());
		assertTrue(model.getGameBoard().getPoliticDeck().getDeck().size() > 0);
	}

	@Test
	public void testPoliticDeck() {
		PoliticDeck pd = new PoliticDeck(model.getGameBoard().getPoliticDeck());
		boolean i = false;
		assertNotEquals(pd, model.getGameBoard().getPoliticDeck());
		for (PoliticCard politicCard : pd.getDeck()) {
			i = false;
			for (PoliticCard p : model.getGameBoard().getPoliticDeck()
					.getDeck())
				if (p.getColor() == politicCard.getColor())
					i = true;
			assertTrue(i);

		}
		assertEquals(model.getGameBoard().getPoliticDeck().getDeck().size(), pd
				.getDeck().size());

		boolean y = false;
		for (PoliticCard politicCard : pd.getDiscardedCards()) {
			y = false;
			for (PoliticCard p : model.getGameBoard().getPoliticDeck()
					.getDiscardedCards())
				if (p.getColor() == politicCard.getColor())
					y = true;
			assertTrue(y);

		}
		assertEquals(model.getGameBoard().getPoliticDeck().getDiscardedCards().size(), pd
				.getDiscardedCards().size());
	}

}
