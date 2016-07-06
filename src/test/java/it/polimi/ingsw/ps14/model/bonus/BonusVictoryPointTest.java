package it.polimi.ingsw.ps14.model.bonus;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusVictoryPointTest {

	@Test
	public void testUseBonus() throws IOException {
		Model model = new Model();

		List<Player> pls = new ArrayList<>();

		Player player = new Player(0, 20, 12, model.getGameBoard().getPoliticDeck(), 6, "ubaldo", Color.DARK_GRAY);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));
		
		BonusVictoryPoint bonus = new BonusVictoryPoint(10);
		int oldPoints = player.getPoints();
		bonus.useBonus(player, model);
		assertEquals(oldPoints + 10, player.getPoints());
	}

}
