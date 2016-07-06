package it.polimi.ingsw.ps14.model.bonus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.message.fromserver.InfoPrivateMsg;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.State;
import it.polimi.ingsw.ps14.model.WaitingFor;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.server.ServerView;
import it.polimi.ingsw.ps14.server.SocketServerView;

public class BonusFromTokensTest {

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
		
		List<City> cities = model.getGameBoard().getCities();
		
		City cityCoin = null;
		for (City city : cities) {
			if (city.getToken() instanceof BonusCoin) {
				cityCoin = city;
				cityCoin.buildEmporium(model.id2player(mockView1.getPlayerID()));
				break;
			}
		}
		assertNotNull(cityCoin);
		
		City cityPoints = null;
		for (City city : cities) {
			if (city.getToken() instanceof BonusVictoryPoint) {
				cityPoints = city;
				cityPoints.buildEmporium(model.id2player(mockView1.getPlayerID()));
				break;
			}
		}
		assertNotNull(cityPoints);
		
		City cityNobility = null;
		for (City city : cities) {
			if (city.getToken() instanceof BonusNobilityLvlUp) {
				cityNobility = city;
				cityNobility.buildEmporium(model.id2player(mockView1.getPlayerID()));
				break;
			}
		}
		assertNotNull(cityNobility);
		
		BonusFromTokens bonus = new BonusFromTokens(2);
		bonus.useBonus(model.id2player(mockView1.getPlayerID()), model);
		
		assertEquals(mockView1.getPlayerID(),model.getCurrentPlayer().getId());
		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(WaitingFor.FROMTOKENS, model.getWaitingFor());
		assertEquals(new Integer(2), model.getWaitingForHowMany());
		
		assertEquals(2, model.getAvailableChoices().size());
		assertTrue(model.getAvailableChoices().containsKey(cityCoin.getName()));
		assertTrue(model.getAvailableChoices().containsKey(cityPoints.getName()));
		assertFalse(model.getAvailableChoices().containsKey(cityNobility.getName()));
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
		
		BonusFromTokens bonus = new BonusFromTokens(2);
		bonus.useBonus(model.id2player(mockView1.getPlayerID()), model);
		
		assertEquals(state, model.getState());
		assertEquals(InfoPrivateMsg.class, model.getMessage().getClass());
		assertEquals("You could have got a bonus from the cities you built in, but there are no tokens available...", ((InfoPrivateMsg) model.getMessage()).getInfo());
	}

}
