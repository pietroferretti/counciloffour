package it.polimi.ingsw.ps14.model.bonus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.message.fromserver.InfoPrivateMsg;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.State;
import it.polimi.ingsw.ps14.model.WaitingFor;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.server.ServerView;
import it.polimi.ingsw.ps14.server.SocketServerView;

public class BonusTakeBusinessPermitsTest {

	
	@Test
	public void testUseBonus() throws IOException{

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
		
		List<BusinessPermit> permitList = new ArrayList<>();
		for (Region region : model.getGameBoard().getRegions()) {
			permitList.addAll(Arrays.asList(region.getAvailablePermits()));
		}
		
		BonusTakeBusinessPermits bonus = new BonusTakeBusinessPermits(2);
		bonus.useBonus(model.id2player(mockView1.getPlayerID()), model);
		
		assertEquals(mockView1.getPlayerID(),model.getCurrentPlayer().getId());
		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(WaitingFor.TAKEPERMIT, model.getWaitingFor());
		assertEquals(new Integer(2), model.getWaitingForHowMany());
		
		assertEquals(6, model.getAvailableChoices().size());
		for (BusinessPermit permit : permitList) {
			assertTrue(model.getAvailableChoices().containsKey(permit.getId().toString()));
		}
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
		
		// removes all business permits
		for (Region region : model.getGameBoard().getRegions()) {
			BusinessPermit[] availablePermits = region.getAvailablePermits();
			while (!(availablePermits[0] == null && availablePermits[1] == null)) {
				if (region.getBusinessPermits().cardIsFaceUp(availablePermits[0])) {
					region.getBusinessPermits().removeBusinessPermit(availablePermits[0]);
				}
				if (region.getBusinessPermits().cardIsFaceUp(availablePermits[1])) {
					region.getBusinessPermits().removeBusinessPermit(availablePermits[1]);
				}
				
				region.getBusinessPermits().fillEmptySpots();
				availablePermits = region.getAvailablePermits();
			}
		}
		
		BonusTakeBusinessPermits bonus = new BonusTakeBusinessPermits(2);
		bonus.useBonus(model.id2player(mockView1.getPlayerID()), model);
		
		assertEquals(state, model.getState());
		assertEquals(InfoPrivateMsg.class, model.getMessage().getClass());
		assertEquals("You could have got a free permit, but there aren't any permits left to get...", ((InfoPrivateMsg) model.getMessage()).getInfo());
	}

}
