package it.polimi.ingsw.ps14.model.bonus;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.State;

public class BonusMainActionTest {

	@Test
	public void testUseBonus() throws IOException {
		Model model = new Model();
		List<Player> pls = new ArrayList<>();
		Player player = new Player(0, 20, 12, model.getGameBoard().getPoliticDeck(), 6, "ubaldo", Color.DARK_GRAY);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));
		
		State oldState = new State(model.getState());
		
		BonusMainAction bonus = new BonusMainAction(2);
		bonus.useBonus(player, model);
		
		assertEquals(oldState.getCurrentPlayer().getId(), model.getCurrentPlayer().getId());
		assertEquals(oldState.getGamePhase(), model.getGamePhase());
		assertEquals(oldState.getCurrentTurnState().getClass(), model.getCurrentTurnState().getClass());
		assertEquals(oldState.getCurrentMarketState(), model.getCurrentMarketState());
		assertEquals(oldState.getWaitingFor(), model.getWaitingFor());
		assertEquals(oldState.getWaitingForHowMany(), model.getWaitingForHowMany());
		assertEquals(oldState.getAdditionalMainsToDo() + 2, model.getAdditionalMainsToDo());
	}

}
