package it.polimi.ingsw.ps14.model.bonus;

import static org.junit.Assert.*;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BonusAssistantTest {

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
	public void testUseBonus() {
		BonusAssistant b = new BonusAssistant(2);
		int old=player.getAssistants();
		b.useBonus(player, model);
		assertEquals(player.getAssistants(),2+old);
	}
	
}
