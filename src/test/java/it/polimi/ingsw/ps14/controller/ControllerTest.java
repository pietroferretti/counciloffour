/**
 * 
 */
package it.polimi.ingsw.ps14.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps14.message.DisconnectionMsg;
import it.polimi.ingsw.ps14.message.fromclient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromclient.DoneBuyingMsg;
import it.polimi.ingsw.ps14.message.fromclient.NobilityRequestAnswerMsg;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellNoneMsg;
import it.polimi.ingsw.ps14.message.fromclient.TurnActionMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameEndedMsg;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.ItemForSale.Type;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.State;
import it.polimi.ingsw.ps14.model.WaitingFor;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.market.BuyAction;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.EngageAssistantAction;
import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.bonus.BonusCoin;
import it.polimi.ingsw.ps14.model.bonus.BonusPoliticCard;
import it.polimi.ingsw.ps14.model.bonus.BonusVictoryPoint;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.server.ServerView;
import it.polimi.ingsw.ps14.server.SocketServerView;

/**
 * A set of tests for the {@link it.polimi.ingsw.ps14.controller.Controller
 * Controller} class.
 */
public class ControllerTest {

	private static Model model;
	private static Controller controller;
	private static ServerView mockView1;
	private static ServerView mockView2;
	private static ServerView mockView3;

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
	 * Test method for the Controller constructor. The constructor sets the
	 * player order in the model.
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
	 * Tests if setting the name of a player works.
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
		
		model.getGameBoard().addDiscardedCouncillor(ColorCouncillor.ORANGE);

		controller.update(mockView1,
				new TurnActionMsg(new ElectCouncillorAction(mockView1.getPlayerID(), ColorCouncillor.ORANGE, "COAST")));

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
	}


	/**
	 * Tests the transition QuickActionDoneTurnState -> MainAndQuickActionDoneTurnState
	 */
	@Test
	public void testUpdateTurnActionMsgMainAction2() {
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new QuickActionDoneTurnState(0));
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));

		model.setState(testState);

		model.getGameBoard().addDiscardedCouncillor(ColorCouncillor.ORANGE);
		
		controller.update(mockView1,
				new TurnActionMsg(new ElectCouncillorAction(mockView1.getPlayerID(), ColorCouncillor.ORANGE, "COAST")));

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainAndQuickActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
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
		State testState = new State();
		testState.setGamePhase(GamePhase.MARKET);
		testState.setCurrentMarketState(MarketState.SELLING);
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>());

		model.setState(testState);
		
		model.id2player(mockView1.getPlayerID()).addAssistants(1);
		
		ItemForSale item = new ItemForSale(Type.ASSISTANT, 1, 2, mockView1.getPlayerID());
		List<ItemForSale> itemList = new ArrayList<>();
		itemList.add(item);
		controller.update(mockView1, new SellMsg(new SellAction(itemList)));

		assertEquals(GamePhase.MARKET, model.getGamePhase());
		assertEquals(MarketState.BUYING, model.getCurrentMarketState());
		assertTrue(!model.getPlayerOrder().isEmpty());
	}
	
	/**
	 * Tests transition SellMsg -> empty market -> turns phase
	 */
	@Test
	public void testUpdateSellMsgToTurns() {
		State testState = new State();
		testState.setGamePhase(GamePhase.MARKET);
		testState.setCurrentMarketState(MarketState.SELLING);
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>());

		model.setState(testState);
		
		controller.update(mockView1, new SellMsg(new SellAction(new ArrayList<>())));

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(InitialTurnState.class, model.getCurrentTurnState().getClass());
		assertTrue(!model.getPlayerOrder().isEmpty());
	}
	/**
	 * Tests transition SellNoneMsg -> next player
	 */
	@Test
	public void testUpdateSellNoneMsgToNextPlayer() {
		State testState = new State();
		testState.setGamePhase(GamePhase.MARKET);
		testState.setCurrentMarketState(MarketState.SELLING);
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model.id2player(mockView2.getPlayerID()))));

		model.setState(testState);

		controller.update(mockView1, new SellNoneMsg());

		assertEquals(GamePhase.MARKET, model.getGamePhase());
		assertEquals(MarketState.SELLING, model.getCurrentMarketState());
		assertEquals(mockView2.getPlayerID(), model.getCurrentPlayer().getId());
		assertTrue(model.getPlayerOrder().isEmpty());
	}

	/**
	 * Tests transition SellNoneMsg -> buying phase
	 */
	@Test
	public void testUpdateSellNoneMsgToBuying() {
		State testState = new State();
		testState.setGamePhase(GamePhase.MARKET);
		testState.setCurrentMarketState(MarketState.SELLING);
		testState.setCurrentPlayer(model.id2player(mockView2.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>());
		
		model.setState(testState);

		Market market = new Market();
		market.addItem(new ItemForSale(Type.ASSISTANT, 1, 2, mockView1.getPlayerID()));
		model.setMarket(market);
		
		controller.update(mockView2, new SellNoneMsg());

		assertEquals(GamePhase.MARKET, model.getGamePhase());
		assertEquals(MarketState.BUYING, model.getCurrentMarketState());
		assertTrue(!model.getPlayerOrder().isEmpty());
	}
	
	/**
	 * Tests transition SellNoneMsg -> empty market -> turns phase
	 */
	@Test
	public void testUpdateSellNoneMsgToTurns() {
		State testState = new State();
		testState.setGamePhase(GamePhase.MARKET);
		testState.setCurrentMarketState(MarketState.SELLING);
		testState.setCurrentPlayer(model.id2player(mockView2.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>());
		
		model.setState(testState);

		Market market = new Market();
		model.setMarket(market);
		
		controller.update(mockView2, new SellNoneMsg());

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(InitialTurnState.class, model.getCurrentTurnState().getClass());
		assertTrue(!model.getPlayerOrder().isEmpty());
	}

	/**
	 * BuyMsg -> next player
	 */
	@Test
	public void testUpdateBuyMsg() {
		State testState = new State();
		testState.setGamePhase(GamePhase.MARKET);
		testState.setCurrentMarketState(MarketState.BUYING);
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model.id2player(mockView2.getPlayerID()))));

		model.setState(testState);
		
		model.id2player(mockView2.getPlayerID()).addAssistants(1);
		model.id2player(mockView1.getPlayerID()).addCoins(2);
		
		Market market = new Market();
		market.addItem(new ItemForSale(Type.ASSISTANT, 1, 2, mockView2.getPlayerID()));
		model.setMarket(market);
		
		controller.update(mockView1, new BuyMsg(new BuyAction(mockView1.getPlayerID(), 0, 1)));

		assertEquals(GamePhase.MARKET, model.getGamePhase());
		assertEquals(MarketState.BUYING, model.getCurrentMarketState());
		assertEquals(mockView2.getPlayerID(), model.getCurrentPlayer().getId());
		assertEquals(mockView1.getPlayerID(), model.getPlayerOrder().peek().getId());
	}
	
	/**
	 * DoneBuyingMsg -> next player
	 */
	@Test
	public void testUpdateDoneBuyingMsg() {
		State testState = new State();
		testState.setGamePhase(GamePhase.MARKET);
		testState.setCurrentMarketState(MarketState.BUYING);
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model.id2player(mockView2.getPlayerID()))));

		model.setState(testState);

		controller.update(mockView1, new DoneBuyingMsg());

		assertEquals(GamePhase.MARKET, model.getGamePhase());
		assertEquals(MarketState.BUYING, model.getCurrentMarketState());
		assertEquals(mockView2.getPlayerID(), model.getCurrentPlayer().getId());
		assertTrue(model.getPlayerOrder().isEmpty());
	}

	/**
	 * DoneBuyingMsg -> turns phase
	 */
	@Test
	public void testUpdateDoneBuyingMsgToTurns() {
		State testState = new State();
		testState.setGamePhase(GamePhase.MARKET);
		testState.setCurrentMarketState(MarketState.BUYING);
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>());

		model.setState(testState);

		controller.update(mockView1, new DoneBuyingMsg());

		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(InitialTurnState.class, model.getCurrentTurnState().getClass());
		assertTrue(!model.getPlayerOrder().isEmpty());
	}

	/**
	 * Tests the "take a free permit" nobility request and answer
	 */
	@Test
	public void testHandleNobilityAnswerTakePermit() {
		
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new MainActionDoneTurnState(0));
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model.id2player(mockView2.getPlayerID()))));
		testState.setWaitingFor(WaitingFor.TAKEPERMIT);
		testState.setWaitingForHowMany(1);
		Map<String, String> availableChoices = new HashMap<>();
		
		String permitID1 = String.valueOf(model.getGameBoard().getRegion(RegionType.COAST).getAvailablePermits()[1].getId());
		String permitID2 = String.valueOf(model.getGameBoard().getRegion(RegionType.HILLS).getAvailablePermits()[0].getId());		
		availableChoices.put(permitID1, "Permit 1");
		availableChoices.put(permitID2, "Permit 2");
		testState.setAvailableChoices(availableChoices);
		
		model.setState(testState);
	
		controller.update(mockView1, new NobilityRequestAnswerMsg(new ArrayList<>(Arrays.asList(permitID2))));
		
		List<BusinessPermit> playerPermits = model.id2player(mockView1.getPlayerID()).getAllPermits();
		
		assertTrue(!playerPermits.isEmpty());
		
		boolean playerHasPermit = false;
		for (BusinessPermit permit : playerPermits) {
			if(permit.getId().equals(Integer.valueOf(permitID2))) {
				playerHasPermit = true;
			}
		}
		assertTrue(playerHasPermit);
		
		assertEquals(WaitingFor.NOTHING, model.getWaitingFor());
		assertEquals(new Integer(0), model.getWaitingForHowMany());
		assertTrue(model.getAvailableChoices().isEmpty());
		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
		assertEquals(mockView2.getPlayerID(), model.getPlayerOrder().peek().getId());
		
	}

	/**
	 * Tests the "bonus from permits" nobility request and answer
	 */
	@Test
	public void testHandleNobilityAnswerFromPermits() {
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new MainActionDoneTurnState(0));
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model.id2player(mockView2.getPlayerID()))));
		testState.setWaitingFor(WaitingFor.FROMPERMITS);
		testState.setWaitingForHowMany(1);
		
		BusinessPermit permit = new BusinessPermit(model.getGameBoard().getCities(), new BonusCoin(7));
		model.id2player(mockView1.getPlayerID()).acquireBusinessPermit(permit);
		
		Map<String, String> availableChoices = new HashMap<>();
		availableChoices.put(String.valueOf(permit.getId()), "Bonus: 7 coins");
		testState.setAvailableChoices(availableChoices);
		
		model.setState(testState);
	
		Integer oldCoins = model.id2player(mockView1.getPlayerID()).getCoins();
		
		controller.update(mockView1, new NobilityRequestAnswerMsg(new ArrayList<>(Arrays.asList(String.valueOf(permit.getId())))));
		
		List<BusinessPermit> playerPermits = model.id2player(mockView1.getPlayerID()).getAllPermits();
		assertTrue(!playerPermits.isEmpty());
		
		boolean playerHasPermit = false;
		for (BusinessPermit permit2 : playerPermits) {
			if(permit2.getId().equals(permit.getId())) {
				playerHasPermit = true;
			}
		}
		assertTrue(playerHasPermit);
		
		assertEquals(oldCoins + 7, model.id2player(mockView1.getPlayerID()).getCoins());	
		assertEquals(WaitingFor.NOTHING, model.getWaitingFor());
		assertEquals(new Integer(0), model.getWaitingForHowMany());
		assertTrue(model.getAvailableChoices().isEmpty());
		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
		assertEquals(mockView2.getPlayerID(), model.getPlayerOrder().peek().getId());
	}

	/**
	 * Tests the "bonus from tokens" nobility request and answer
	 */
	@Test
	public void testHandleNobilityAnswerFromTokens() {
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new MainActionDoneTurnState(0));
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model.id2player(mockView2.getPlayerID()))));
		testState.setWaitingFor(WaitingFor.FROMTOKENS);
		testState.setWaitingForHowMany(2);
		
		model.getGameBoard().getCities().get(0).setToken(new BonusPoliticCard(3));
		model.getGameBoard().getCities().get(1).setToken(new BonusCoin(5));
		model.getGameBoard().getCities().get(2).setToken(new BonusVictoryPoint(2));
		
		String cityName1 = model.getGameBoard().getCities().get(0).getName();
		Bonus bonus1 = model.getGameBoard().getCities().get(0).getToken();
		String cityName2 = model.getGameBoard().getCities().get(1).getName();
		Bonus bonus2 = model.getGameBoard().getCities().get(1).getToken();
		String cityName3 = model.getGameBoard().getCities().get(2).getName();
		Bonus bonus3 = model.getGameBoard().getCities().get(2).getToken();

		Map<String, String> availableChoices = new HashMap<>();
		availableChoices.put(cityName1, bonus1.toString());
		availableChoices.put(cityName2, bonus2.toString());
		availableChoices.put(cityName3, bonus3.toString());

		testState.setAvailableChoices(availableChoices);
		
		model.setState(testState);
		
		Integer oldCoins = model.id2player(mockView1.getPlayerID()).getCoins();
		Integer oldPoints = model.id2player(mockView1.getPlayerID()).getPoints();
	
		controller.update(mockView1, new NobilityRequestAnswerMsg(new ArrayList<>(Arrays.asList(cityName3, cityName2))));

		assertEquals(oldCoins + 5, model.id2player(mockView1.getPlayerID()).getCoins());
		assertEquals(oldPoints + 2, model.id2player(mockView1.getPlayerID()).getPoints());
		
		assertEquals(WaitingFor.NOTHING, model.getWaitingFor());
		assertEquals(new Integer(0), model.getWaitingForHowMany());
		assertTrue(model.getAvailableChoices().isEmpty());
		assertEquals(GamePhase.TURNS, model.getGamePhase());
		assertEquals(MainActionDoneTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView1.getPlayerID(), model.getCurrentPlayer().getId());
		assertEquals(mockView2.getPlayerID(), model.getPlayerOrder().peek().getId());
				
	}

	
	/**
	 * Tests switching to the next player while in the final turns
	 */
	@Test
	public void testUpdateFinalTurnsNextPlayer() {
		State testState = new State();
		testState.setGamePhase(GamePhase.FINALTURNS);
		testState.setCurrentTurnState(new MainActionDoneTurnState(0));
		testState.setCurrentPlayer(model.id2player(mockView1.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model.id2player(mockView2.getPlayerID()))));

		model.setState(testState);

		controller.update(mockView1, new TurnActionMsg(new EndTurnAction(mockView1.getPlayerID())));

		assertEquals(GamePhase.FINALTURNS, model.getGamePhase());
		assertEquals(InitialTurnState.class, model.getCurrentTurnState().getClass());
		assertEquals(mockView2.getPlayerID(), model.getCurrentPlayer().getId());
		assertTrue(model.getPlayerOrder().isEmpty());
	}
	
	/**
	 * Tests the transition from turns to finalturns
	 */
	@Test
	public void testUpdateTenEmporiums() throws IOException {
		ServerView mockView4 = mock(SocketServerView.class);
		when(mockView4.getPlayerID()).thenReturn(4);
		ServerView mockView5 = mock(SocketServerView.class);
		when(mockView5.getPlayerID()).thenReturn(5);
		ServerView mockView6 = mock(SocketServerView.class);
		when(mockView6.getPlayerID()).thenReturn(6);

		Model model2 = new Model();

		List<Player> playerList = new ArrayList<>();
		playerList.add(new Player(mockView4.getPlayerID(), 10, 2, model2.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView5.getPlayerID(), 10, 2, model2.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView6.getPlayerID(), 10, 2, model2.getGameBoard().getPoliticDeck(), 6));
		model2.setPlayers(playerList);

		Controller controller2 = new Controller(model2);
		
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new CardDrawnState());
		testState.setCurrentPlayer(model2.id2player(mockView5.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model2.id2player(mockView6.getPlayerID()))));

		model2.setState(testState);
		
		int numEmporiumsBuilt = model2.id2player(mockView4.getPlayerID()).getNumEmporiums();
		for (int i=0; i< (9-numEmporiumsBuilt); i++) {
			model2.id2player(mockView5.getPlayerID()).incrementNumEmporiums();
		}
		
		BusinessPermit permit = new BusinessPermit(model2.getGameBoard().getCities(), null);
		model2.id2player(mockView5.getPlayerID()).acquireBusinessPermit(permit);
		
		controller2.update(mockView5, new TurnActionMsg(new BuildEmporiumUsingPermitTileAction(mockView5.getPlayerID(), permit.getId(), "Hellar")));

		assertEquals(GamePhase.FINALTURNS, model2.getGamePhase());
		assertEquals(InitialTurnState.class, model2.getCurrentTurnState().getClass());
		assertEquals(mockView6.getPlayerID(), model2.getCurrentPlayer().getId());
		assertTrue(model2.getPlayerOrder().size() == 1);
		assertEquals(mockView4.getPlayerID(), model2.getPlayerOrder().peek().getId());
	}
	
	/**
	 * Tests the end game
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
		assertEquals(String.valueOf(mockView1.getPlayerID()), ((GameEndedMsg)model.getMessage()).getEndResults().get(0).get(0));
		assertEquals("ubaldo", ((GameEndedMsg) model.getMessage()).getEndResults().get(0).get(1));
		
		// TODO settare punti, nobiltà e permessi dei player
		// in modo da testare se il calcolo finale è corretto
	}

	/**
	 * Tests the removal of a player after they disconnect
	 */
	@Test
	public void testDisconnection() throws IOException {
		
		ServerView mockView4 = mock(SocketServerView.class);
		when(mockView4.getPlayerID()).thenReturn(4);
		ServerView mockView5 = mock(SocketServerView.class);
		when(mockView5.getPlayerID()).thenReturn(5);
		ServerView mockView6 = mock(SocketServerView.class);
		when(mockView6.getPlayerID()).thenReturn(6);

		Model model2 = new Model();

		List<Player> playerList = new ArrayList<>();
		playerList.add(new Player(mockView4.getPlayerID(), 10, 2, model2.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView5.getPlayerID(), 10, 2, model2.getGameBoard().getPoliticDeck(), 6));
		playerList.add(new Player(mockView6.getPlayerID(), 10, 2, model2.getGameBoard().getPoliticDeck(), 6));
		model2.setPlayers(playerList);

		Controller controller2 = new Controller(model2);
		
		State testState = new State();
		testState.setGamePhase(GamePhase.TURNS);
		testState.setCurrentTurnState(new MainActionDoneTurnState(0));
		testState.setCurrentPlayer(model2.id2player(mockView5.getPlayerID()));
		testState.setPlayerOrder(new ArrayDeque<>(Arrays.asList(model2.id2player(mockView6.getPlayerID()))));

		model2.setState(testState);

		controller2.update(mockView5, new DisconnectionMsg(mockView5.getPlayerID()));

		assertEquals(GamePhase.TURNS, model2.getGamePhase());
		assertEquals(InitialTurnState.class, model2.getCurrentTurnState().getClass());
		assertEquals(mockView6.getPlayerID(), model2.getCurrentPlayer().getId());
		assertTrue(model2.getPlayerOrder().isEmpty());	
	}
}
