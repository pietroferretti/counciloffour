/**
 * 
 */
package it.polimi.ingsw.ps14.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellMsg;
import it.polimi.ingsw.ps14.message.fromclient.TurnActionMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameEndedMsg;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.State;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.EngageAssistantAction;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.server.SocketServerView;
import it.polimi.ingsw.ps14.view.View;

/**
 * A set of tests for the {@link it.polimi.ingsw.ps14.controller.Controller
 * Controller} class.
 */
public class ControllerTest {

	private static Model model;
	private static Controller controller;
	private static View mockView1;
	private static View mockView2;
	private static View mockView3;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		mockView1 = mock(SocketServerView.class);
		when(mockView1.getPlayerID()).thenReturn(1);
		mockView2 = mock(SocketServerView.class);
		when(mockView2.getPlayerID()).thenReturn(2);
		mockView3 = mock(SocketServerView.class);
		when(mockView3.getPlayerID()).thenReturn(3);

		model = new Model();

		List<Player> playerList = new ArrayList<>();
		playerList.add(new Player(mockView1.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView2.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView3.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6));
		model.setPlayers(playerList);

		controller = new Controller(model);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//
	}

	/**
	 * Test method for the Controller constructor. The constructor sets the
	 * player order in the model, nothing else can be tested because the
	 * controller hides all its fields.
	 */
	@Test
	public void testControllerConstructor() throws IOException {

		Model model2 = new Model();

		List<Player> playerList = new ArrayList<>();
		playerList.add(new Player(123, 10, 2, model2.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(124, 10, 2, model2.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(125, 10, 2, model2.getGameBoard().getPoliticDeck(), 6));
		model2.setPlayers(playerList);

		new Controller(model2);

		Integer currentPlayerID = model2.getCurrentPlayer().getId();
		assertTrue(currentPlayerID.equals(123) || currentPlayerID.equals(124) || currentPlayerID.equals(125));
		assertEquals(model2.getPlayerOrder().size(), 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdatePlayerNameMsg() {

		when(mockView1.getPlayerName()).thenReturn("ubaldo");
		controller.update(mockView1, new PlayerNameMsg(mockView1.getPlayerName()));

		assertEquals("ubaldo", model.id2player(mockView1.getPlayerID()).getName());
	}

	/**
	 * Tests the transition InitialTurnState -> CardDrawntate
	 */
	@Test
	public void testUpdateTurnActionMsgDrawCard() {
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new InitialTurnState());
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));

		model.setState(testState);

		controller.update(mockView1, new TurnActionMsg(new DrawCardAction(mockView1.getPlayerID())));

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(CardDrawnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
	}

	/**
	 * Tests the transition CardDrawnState -> MainActionDoneTurnState
	 */
	@Test
	public void testUpdateTurnActionMsgMainAction1() {
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new CardDrawnState());
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));

		model.setState(testState);

		controller.update(mockView1,
				new TurnActionMsg(new ElectCouncillorAction(mockView1.getPlayerID(), ColorCouncillor.ORANGE, "COAST")));

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
	}

	/**
	 * Tests the transition CardDrawnState -> MainActionDoneTurnState with
	 * additional main actions
	 */
	@Test
	public void testUpdateTurnActionMsgMainAction2() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Tests the transition Card
	 */
	@Test
	public void testUpdateTurnActionMsgMainAction3() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Tests the transition CardDrawnState -> QuickActionDoneTurnState
	 */
	@Test
	public void testUpdateTurnActionMsgQuickAction1() {
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new CardDrawnState());
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));

		model.setState(testState);

		controller.update(mockView1, new TurnActionMsg(new EngageAssistantAction(mockView1.getPlayerID())));

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(QuickActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
	}

	/**
	 * Tests the transition MainActionDoneTurnState ->
	 * MainAndQuickActionDoneTurnState
	 */
	@Test
	public void testUpdateTurnActionMsgQuickAction2() {
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new MainActionDoneTurnState(0));
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));

		model.setState(testState);

		controller.update(mockView1, new TurnActionMsg(new EngageAssistantAction(mockView1.getPlayerID())));

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainAndQuickActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
	}

	/**
	 * Tests transition MainActionDoneTurnState -> end turn -> next turn
	 */
	@Test
	public void testUpdateTurnActionMsgPassTurnToNextPlayer() {
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new MainActionDoneTurnState(0));
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model.id2player(mockView2.getPlayerID()))));

		model.setState(testState);

		controller.update(mockView1, new TurnActionMsg(new EndTurnAction(mockView1.getPlayerID())));

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(InitialTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView2.getPlayerID(), model.getCurrentPlayer().getId());
		assertTrue(model.getPlayerOrder().isEmpty());
	}

	/**
	 * Tests transition MainAndQuickActionDoneTurnState -> end turn -> market
	 * phase
	 */
	@Test
	public void testUpdateTurnActionMsgPassTurnToMarket() {
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new MainAndQuickActionDoneTurnState(0));
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>());

		model.setState(testState);

		controller.update(mockView1, new TurnActionMsg(new EndTurnAction(mockView1.getPlayerID())));

		assertEquals(GamePhase.MARKET, model.getGamePhase());
		assertEquals(MarketState.SELLING, model.getCurrentMarketState());
		Integer currentPlayerID = model.getCurrentPlayer().getId();
		assertTrue(currentPlayerID == mockView1.getPlayerID() || currentPlayerID == mockView2.getPlayerID()
				|| currentPlayerID == mockView3.getPlayerID());
		assertTrue(!model.getPlayerOrder().isEmpty());
	}

	/**
	 * Tests transition SellMsg -> next player
	 */
	@Test
	public void testUpdateSellMsgToNextPlayer() {
		State testState = new State();
		testState.setGamePhase(GamePhase.MARKET);
		testState.setCurrentMarketState(MarketState.SELLING);
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model.id2player(mockView2.getPlayerID()))));

		model.setState(testState);

		controller.update(mockView1, new SellMsg(new SellAction(new ArrayList<>())));

		assertEquals(GamePhase.MARKET, model.getGamePhase());
		assertEquals(MarketState.SELLING, model.getCurrentMarketState());
		assertEquals(mockView2.getPlayerID(), model.getCurrentPlayer().getId());
		assertTrue(model.getPlayerOrder().isEmpty());
	}
	
	/**
	 * Tests transition SellMsg -> buying phase
	 */
	@Test
	public void testUpdateSellMsgToBuying() {
		// TODO
		// ho bisogno di fare una transizione da turns a market per settare marketorder, e passare da selling a buying
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateSellNoneMsg() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateBuyMsg() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateDoneBuyingMsg() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateNobilityRequestAnswer1() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateNobilityRequestAnswer2() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateFinalTurnsNextPlayer() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateFinalTurnsEndGame() {		
		State testState = new State();
		testState.setGamePhase(GamePhase.FINALTURNS);
		testState.setCurrentTurnState(new MainAndQuickActionDoneTurnState(0));
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>());

		model.setState(testState);

		model.id2player(mockView1.getPlayerID()).addPoints(100);
		
		controller.update(mockView1, new TurnActionMsg(new EndTurnAction(mockView1.getPlayerID())));

		assertEquals(GamePhase.END, model.getGamePhase());
		assertEquals(EndTurnState.class, model.getCurrentTurnState().getClass());
		assertTrue(model.getPlayerOrder().isEmpty());
		
		assertEquals(GameEndedMsg.class, model.getMessage().getClass());
		// aggiungere check su cosa c'é dentro gameendedmsg
	}
	// Tests to do


	// main action (quickactiondone -> mainandquickactiondone)
	// main action (mainactiondone && additionalmainstodo -> mainactiondone)
	// main action (quickactiondone && additionalmainstodo -> quickactiondone)
	// main action (mainandquickactiondone && additionalmainstodo ->
	// mainandquickactiondone)

	// additionalmainaction (mainactiondone -> mainandquickactiondone &&
	// additionalmainstodo)


	// sell (-> nextplayer)
	// sell (-> buying)

	// buy (-> nextplayer)
	// donebuying (-> nextplayer)
	// donebuying (-> turns)

	// main action (-> finalturns)

	// final turns, pass (-> nextplayer)


}
