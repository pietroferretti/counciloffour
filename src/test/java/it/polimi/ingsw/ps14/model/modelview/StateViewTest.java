package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.State;
import it.polimi.ingsw.ps14.model.WaitingFor;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;

public class StateViewTest {
	private Model model;
	private Player player, player1, player2;
	private StateView sv;
	private Deque<Player> playerOrder1;

	@Before
	public void setUp() throws Exception {
		model = new Model();

		player = new Player(0);
		player1 = new Player(1, 20, 12, model.getGameBoard().getPoliticDeck(), 6, "ubaldo", Color.DARK_GRAY);
		player2 = new Player(2, 20, 12, model.getGameBoard().getPoliticDeck(), 4, "sdds", Color.cyan);
		State state = new State();
		state.setCurrentPlayer(player);
		Deque<Player> playerOrder = new ArrayDeque<>();
		playerOrder.add(player);
		playerOrder.add(player1);
		playerOrder.add(player2);
		state.setPlayerOrder(playerOrder);
		playerOrder1 = new ArrayDeque<>();
		playerOrder.add(player1);
		playerOrder.add(player2);
		playerOrder.add(player);
		state.setGamePhase(GamePhase.TURNS);
		state.setCurrentTurnState(new CardDrawnState());
		sv = new StateView(state);
		model.setState(state);
		model.getState().addObserver(sv);
		System.out.println(model.getState().toString());
	}

	@Test
	public void testStateView() {
		System.out.println("\n---------testStateView---------\n");

		StateView sv1 = new StateView(model.getState());
		System.out.println(sv1.getStateCopy().toString());

		assertNotSame(model.getState(), sv1.getStateCopy());
		assertEquals(model.getState().toString(), sv1.getStateCopy().toString());
	}

	@Test
	public void testUpdatesetAdditionalMainsToDo() {
		System.out.println("\n---------testUpdatesetAdditionalMainsToDo---------\n");
		model.getState().setAdditionalMainsToDo(1);

		assertNotSame(model.getState(), sv.getStateCopy());
		assertEquals(model.getState().toString(), sv.getStateCopy().toString());
	}

	@Test
	public void testUpdatesetCurrentMarketState() {
		System.out.println("\n---------testUpdatesetCurrentMarketState---------\n");
		model.getState().setCurrentMarketState(MarketState.SELLING);

		assertNotSame(model.getState(), sv.getStateCopy());
		assertEquals(model.getState().toString(), sv.getStateCopy().toString());
	}

	@Test
	public void testUpdatesetCurrentPlayer() {
		System.out.println("\n---------testUpdatesetCurrentPlayer---------\n");
		model.getState().setCurrentPlayer(player2);

		assertNotSame(model.getState(), sv.getStateCopy());
		assertEquals(model.getState().toString(), sv.getStateCopy().toString());
	}

	@Test
	public void testUpdatesetCurrentTurnState() {
		System.out.println("\n---------testUpdatesetCurrentTurnState---------\n");
		model.getState().setCurrentTurnState(new MainActionDoneTurnState(0));

		assertNotSame(model.getState(), sv.getStateCopy());
		assertEquals(model.getState().toString(), sv.getStateCopy().toString());
	}

	@Test
	public void testUpdatesetGamePhase() {
		System.out.println("\n---------testUpdatesetGamePhase---------\n");
		model.getState().setGamePhase(GamePhase.MARKET);

		assertNotSame(model.getState(), sv.getStateCopy());
		assertEquals(model.getState().toString(), sv.getStateCopy().toString());
	}

	@Test
	public void testUpdatesetPlayerOrder() {
		System.out.println("\n---------testUpdatesetPlayerOrder---------\n");
		model.getState().setPlayerOrder(playerOrder1);

		assertNotSame(model.getState(), sv.getStateCopy());
		assertEquals(model.getState().toString(), sv.getStateCopy().toString());
	}

	@Test
	public void testUpdatesetWaitingFor() {
		System.out.println("\n---------testUpdatesetWaitingFor---------\n");
		model.getState().setWaitingFor(WaitingFor.FROMTOKENS);

		assertNotSame(model.getState(), sv.getStateCopy());
		assertEquals(model.getState().toString(), sv.getStateCopy().toString());
	}

	@Test
	public void testUpdateloadNextPlayer() {
		System.out.println("\n---------testUpdateloadNextPlayer---------\n");
		model.getState().loadNextPlayer();

		assertNotSame(model.getState(), sv.getStateCopy());
		assertEquals(model.getState().toString(), sv.getStateCopy().toString());
	}

	@Test
	public void testUpdatequeueAndLoadNextPlayer() {
		System.out.println("\n---------testUpdatequeueAndLoadNextPlayer---------\n");
		model.getState().queueAndLoadNextPlayer();

		assertNotSame(model.getState(), sv.getStateCopy());
		assertEquals(model.getState().toString(), sv.getStateCopy().toString());
		;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateException() {
		player.addObserver(sv);
		player.addCoins(4);

	}
}
