package it.polimi.ingsw.ps14.model.bonus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.message.fromserver.InfoPrivateMsg;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.State;
import it.polimi.ingsw.ps14.model.WaitingFor;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.server.ServerView;
import it.polimi.ingsw.ps14.server.SocketServerView;

public class BonusFromBusinessPermitsTest {

	@Test
	public void testUseBonus() throws IOException {
		ServerView mockView1 = mock(SocketServerView.class);
		when(mockView1.getPlayerID()).thenReturn(1);
		ServerView mockView2 = mock(SocketServerView.class);
		when(mockView2.getPlayerID()).thenReturn(2);
		ServerView mockView3 = mock(SocketServerView.class);
		when(mockView3.getPlayerID()).thenReturn(3);

		Model model = new Model();

		List<Player> playerList = new ArrayList<>();
		playerList.add(new Player(mockView1.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView2.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView3.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6));
		model.setPlayers(playerList);

		State state = new State();
		state.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		state.setGamePhase(GamePhase.TURNS);
		state.setCurrentTurnState(new MainActionDoneTurnState(0));
		
		model.setState(state);
		
		Player player1 = model.id2player(mockView1.getPlayerID());
		
		BusinessPermit permit1 = new BusinessPermit(new ArrayList<>(), new BonusCoin(3));
		BusinessPermit permit2 = new BusinessPermit(new ArrayList<>(), new BonusVictoryPoint(5));
		BusinessPermit permit3 = new BusinessPermit(new ArrayList<>(), new BonusList(new BonusNobilityLvlUp(1), new BonusAssistant(1)));
		player1.acquireBusinessPermit(permit1);
		player1.acquireBusinessPermit(permit2);
		player1.acquireBusinessPermit(permit3);
		
		BonusFromBusinessPermits bonus = new BonusFromBusinessPermits(2);
		bonus.useBonus(model.id2player(mockView1.getPlayerID()), model);
		
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(WaitingFor.FROMPERMITS, model.getWaitingFor());
		assertEquals(new Integer(2), model.getWaitingForHowMany());
		
		assertEquals(3, model.getAvailableChoices().size());
		assertTrue(model.getAvailableChoices().containsKey(permit1.getId().toString()));
		assertTrue(model.getAvailableChoices().containsKey(permit2.getId().toString()));
		assertTrue(model.getAvailableChoices().containsKey(permit3.getId().toString()));
	}
	
	@Test
	public void testUseBonusFailed() throws IOException {
		ServerView mockView1 = mock(SocketServerView.class);
		when(mockView1.getPlayerID()).thenReturn(1);
		ServerView mockView2 = mock(SocketServerView.class);
		when(mockView2.getPlayerID()).thenReturn(2);
		ServerView mockView3 = mock(SocketServerView.class);
		when(mockView3.getPlayerID()).thenReturn(3);

		Model model = new Model();

		List<Player> playerList = new ArrayList<>();
		playerList.add(new Player(mockView1.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView2.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView3.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6));
		model.setPlayers(playerList);

		State state = new State();
		state.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		state.setGamePhase(GamePhase.TURNS);
		state.setCurrentTurnState(new MainActionDoneTurnState(0));
		
		model.setState(state);
	
		BonusFromBusinessPermits bonus = new BonusFromBusinessPermits(2);
		bonus.useBonus(model.id2player(mockView1.getPlayerID()), model);
		
		assertEquals(state, model.getState());
		assertEquals(InfoPrivateMsg.class, model.getMessage().getClass());
		assertEquals("You could have got a bonus from the permits you own, but you don't have any permits...", ((InfoPrivateMsg) model.getMessage()).getInfo());
	}

}
