package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;

public class StateTest {

	private Player player, player1, player2;
	private State state;
	private Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		player = new Player();
		player1 = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		player2 = new Player("sdds", Color.cyan, 20, 12, model.getGameBoard().getPoliticDeck(), 4);
		state = new State();
		state.setCurrentPlayer(player);
		Deque<Player> playerOrder = new ArrayDeque<>();
		playerOrder.add(player);
		playerOrder.add(player1);
		playerOrder.add(player2);
		state.setPlayerOrder(playerOrder);
		state.setGamePhase(GamePhase.TURNS);
		state.setCurrentTurnState(new CardDrawnState());
		state.setCurrentMarketState(MarketState.SELLING);
		state.setCurrentPlayer(player2);
	}

	@Test
	public void testState() {
		State s = new State();

		assertNull(s.getGamePhase());
		assertNull(s.getCurrentMarketState());
		assertNull(s.getCurrentTurnState());
		assertNull(s.getPlayerOrder());
		assertNull(s.getCurrentPlayer());
		assertEquals(WaitingFor.NOTHING, s.getWaitingFor());
		assertTrue(0 == s.getWaitingForHowMany());
		assertTrue(s.getAvailableChoices() instanceof HashMap<?, ?>);
	}

	@Test
	public void testStateState() {
		State s1 = new State(state);
		assertEquals(GamePhase.TURNS, s1.getGamePhase());
		assertEquals(state.getCurrentTurnState().toString(), s1.getCurrentTurnState().toString());
		assertNotSame(state.getCurrentTurnState(), s1.getCurrentTurnState());
		assertEquals(state.getAdditionalMainsToDo(), s1.getAdditionalMainsToDo());
		assertEquals(state.getCurrentMarketState(), s1.getCurrentMarketState());
		assertArrayEquals(state.getPlayerOrder().toArray(), s1.getPlayerOrder().toArray());
		assertNotSame(state.getPlayerOrder(), s1.getPlayerOrder());
		assertEquals(state.getCurrentPlayer().toString(), s1.getCurrentPlayer().toString());
		assertNotSame(state.getCurrentPlayer(), s1.getCurrentPlayer());
		assertEquals(state.getWaitingFor(), s1.getWaitingFor());
		assertEquals(state.getWaitingForHowMany(), s1.getWaitingForHowMany());
		assertEquals(state.getAvailableChoices().toString(), s1.getAvailableChoices().toString());
		// TODO
		// assertNotSame(state.getAvailableChoices().toString(),
		// s1.getAvailableChoices().toString());

	}

}
