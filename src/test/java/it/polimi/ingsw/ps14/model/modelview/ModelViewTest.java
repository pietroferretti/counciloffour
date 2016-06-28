package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.controller.Controller;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.ErrorMsg;
import it.polimi.ingsw.ps14.model.ColorCity;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;

public class ModelViewTest {

	private Model model;
	private Player player, player2, player3;
	private ModelView mv;


	@Before
	public void setUp() throws Exception {
		model = new Model();

		player = new Player("efrt", Color.red, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		player3 = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		player2 = new Player("sdds", Color.cyan, 20, 12, model.getGameBoard().getPoliticDeck(), 4);
		List<Player> players = new ArrayList<>(3);
		players.add(player);// id 1
		players.add(player2);// id 3
		players.add(player3);// id 2

		model.setPlayers(players);
		
		new Controller(model);
		// model.setPlayerOrder(players);
		// model.loadNextPlayer();
		mv = new ModelView(model);

	}

	@Test
	public void testModelView() {
		ModelView mv1 = new ModelView(model);

		assertEquals(model.getGameBoard().getAvailableAssistants(),
				mv1.getAvailableAssistantsView().getAvailableAssistantsCopy());

		assertEquals(model.getGameBoard().getAvailableCouncillors(),
				mv1.getAvailableCouncillorsView().getAvailableCouncillorsCopy());

		assertNotSame(model.getGameBoard().getAvailableCouncillors(),
				mv1.getAvailableCouncillorsView().getAvailableCouncillorsCopy());

		assertEquals(model.getGameBoard().getColorBonuses(), mv1.getCitiesColorBonusesView().getColorBonusesCopy());

		assertNotSame(model.getGameBoard().getNobilityTrack(), mv1.getNobilityTrackView().getNobilityTrackCopy());
		assertEquals(model.getGameBoard().getNobilityTrack().toString(),
				mv1.getNobilityTrackView().getNobilityTrackCopy().toString());

		assertNotSame(model.getPlayers().get(0), mv1.getPlayersView().get(0).getPlayerCopy());
		assertEquals(model.getPlayers().get(0).toString(), mv1.getPlayersView().get(0).getPlayerCopy().toString());
		assertNotSame(model.getPlayers().get(1), mv1.getPlayersView().get(1).getPlayerCopy());
		assertEquals(model.getPlayers().get(1).toString(), mv1.getPlayersView().get(1).getPlayerCopy().toString());
		assertNotSame(model.getPlayers().get(2), mv1.getPlayersView().get(2).getPlayerCopy());
		assertEquals(model.getPlayers().get(2).toString(), mv1.getPlayersView().get(2).getPlayerCopy().toString());
		assertNotSame(model.getMessageObservable(), mv1.getMessageView().getMessageCopy());
		assertEquals(model.getMessageObservable().getMessage(), mv1.getMessageView().getMessageCopy());

		assertNotSame(model.getGameBoard().getKing(), mv1.getKingView().getKingCopy());
		assertEquals(model.getGameBoard().getKing().toString(), mv1.getKingView().getKingCopy().toString());

		assertNotSame(model.getState(), mv1.getStateView().getStateCopy());
		assertEquals(model.getState().toString(), mv1.getStateView().getStateCopy().toString());

		assertNotSame(model.getMessageObservable(), mv1.getMessageView().getMessageCopy());
		assertEquals(model.getMessageObservable().getMessage(), mv1.getMessageView().getMessageCopy());

		assertEquals(model.getGameBoard().getKingBonuses().peek().intValue(),
				mv1.getKingBonusesView().getShowableKingBonus());
	}

	@Test
	public void testUpdateStateView() {

		model.getState().setCurrentMarketState(MarketState.SELLING);

		assertFalse(mv.hasChanged());
	}

	@Test
	public void testUpdateRegionView() {
		model.getGameBoard().getRegion(RegionType.COAST).getCities().get(0).buildEmporium(player);

		assertFalse(mv.hasChanged());
	}

	@Test
	public void testUpdateKingView() {
		model.getGameBoard().getKing().setBalcony();
		assertFalse(mv.hasChanged());
	}

	@Test
	public void testUpdateCitiesColorBonusesView() {
		model.getGameBoard().useColorBonus(ColorCity.BLUE);
		assertFalse(mv.hasChanged());
	}

	@Test
	public void testUpdateMessageView() {
		Message m = new ErrorMsg(player.getId(), "prova");
		model.setMessage(m);
		assertFalse(mv.hasChanged());
	}

	@Test
	public void testUpdateAvailableAssistantsView() {
		model.getGameBoard().setAvailableAssistants(70);
		assertFalse(mv.hasChanged());
	}

	@Test
	public void testUpdateAvailableCouncillorsView() {
		model.getGameBoard().addDiscardedCouncillor(ColorCouncillor.BLACK);
		assertFalse(mv.hasChanged());
	}

	@Test
	public void testUpdateKingBonusesView() {
		model.getGameBoard().useKingBonus();
		assertFalse(mv.hasChanged());
	}

	@Test
	public void testUpdatePlayerView() {
		player3.addCoins(10);
		assertFalse(mv.hasChanged());
	}

	// TODO testare eccezione
	@Test
	public void testGetPlayerByID() {
		assertEquals(mv.getPlayerByID(player.getId()).toString(), player.toString());
		assertEquals(mv.getPlayerByID(player2.getId()).toString(), player2.toString());
		assertEquals(mv.getPlayerByID(player3.getId()).toString(), player3.toString());
	}

}
