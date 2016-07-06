package it.polimi.ingsw.ps14.model.bonus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;

public class BonusPoliticCardTest {

	@Test
	public void testUseBonus() throws IOException {
		Model model = new Model();
		List<Player> pls = new ArrayList<>();
		Player player = new Player(0, 20, 12, model.getGameBoard().getPoliticDeck(), 6, "ubaldo", Color.DARK_GRAY);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));
		
		List<PoliticCard> oldCards = new ArrayList<>(player.getHand());
		
		BonusPoliticCard bonus = new BonusPoliticCard(3);
		bonus.useBonus(player, model);
		
		assertEquals(oldCards.size() + 3, player.getHand().size());
		for (PoliticCard card : oldCards) {
			assertTrue(player.getHand().contains(card));
		}
	}

}
