/**
 * 
 */
package it.polimi.ingsw.ps14.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
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
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateTurnActionMsgDrawCard() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateTurnActionMsgMainAction1() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateTurnActionMsgMainAction2() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateTurnActionMsgMainAction3() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateTurnActionMsgQuickAction1() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateTurnActionMsgQuickAction2() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateTurnActionMsgPassTurn() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps14.controller.Controller#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdateSellMsg() {
		fail("Not yet implemented"); // TODO
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
		fail("Not yet implemented"); // TODO
	}
	// Tests to do


	// draw card (initial -> carddrawn)

	// main action (carddrawn -> mainactiondone)
	// main action (quickactiondone -> mainandquickactiondone)
	// main action (mainactiondone && additionalmainstodo -> mainactiondone)
	// main action (quickactiondone && additionalmainstodo -> quickactiondone)
	// main action (mainandquickactiondone && additionalmainstodo ->
	// mainandquickactiondone)

	// quick action (carddrawn -> quickactiondone)
	// quick action (mainactiondone -> mainandquickactiondone)
	// additionalmainaction (mainactiondone -> mainandquickactiondone &&
	// additionalmainstodo)

	// pass (-> nextplayer)
	// pass (-> market)

	// sell (-> nextplayer)
	// sell (-> buying)

	// buy (-> nextplayer)
	// donebuying (-> nextplayer)
	// donebuying (-> turns)

	// main action (-> finalturns)

	// final turns, pass (-> nextplayer)
	// final turns, pass (-> end game)
	// ---> winner

}
