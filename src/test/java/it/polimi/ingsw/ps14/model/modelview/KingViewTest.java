package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;

public class KingViewTest {

	private Model model;
	private KingView kingView;
	private Player player;
	private ElectCouncillorAction action;

	@Before
	public void inizialize() throws IOException {
		model = new Model();
		player = new Player();
		model.getPlayers().add(player);
		model.setCurrentPlayer(player);
		// System.out.println(model.getGameBoard().toString());
		 System.out.println(model.getGameBoard().getKing().toString());
		// System.out.println(model.getGameBoard().getKing().getBalcony().toString());
		kingView = new KingView(model.getGameBoard().getKing());
		action = new ElectCouncillorAction(player.getId(), ColorCouncillor.BLUE, "KING");
		model.getGameBoard().getKing().addObserver(kingView);
	}

	@Test
	public void testKingView() {
		KingView kingView2 = new KingView(model.getGameBoard().getKing());

		assertNotSame(model.getGameBoard().getKing(), kingView2.getKingCopy());
		assertEquals(model.getGameBoard().getKing().getCity().getName(), kingView2.getKingCopy().getCity().getName());
		while ((model.getGameBoard().getKing().getBalcony().readBalcony().size()) != 0
				|| (kingView2.getKingCopy().getBalcony().readBalcony().size()) != 0) {
			assertEquals(model.getGameBoard().getKing().getBalcony().readBalcony().poll(),
					kingView2.getKingCopy().getBalcony().readBalcony().poll());
		}

	}

	@Test
	public void testUpdate1() {

		model.getGameBoard().getKing().setBalcony();
		assertNotSame(model.getGameBoard().getKing(), kingView.getKingCopy());
		assertEquals(model.getGameBoard().getKing().getCity().getName(), kingView.getKingCopy().getCity().getName());
		while ((model.getGameBoard().getKing().getBalcony().readBalcony().size()) != 0
				|| (kingView.getKingCopy().getBalcony().readBalcony().size()) != 0) {
			assertEquals(model.getGameBoard().getKing().getBalcony().readBalcony().poll(),
					kingView.getKingCopy().getBalcony().readBalcony().poll());
		}

	}

	@Test
	public void testUpdate2() {
		System.out.println(model.getGameBoard().getKing().getBalcony().toString());
		System.out.println(kingView.getKingCopy().getBalcony().toString());

		System.out.println("Add BLUE councillor:");
		action.execute(new InitialTurnState(), model);
		System.out.println(model.getGameBoard().getKing().getBalcony().toString());
		System.out.println(kingView.getKingCopy().getBalcony().toString());

		assertNotSame(model.getGameBoard().getKing(), kingView.getKingCopy());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdate3() {
		player.addObserver(kingView);
		player.addCoins(4);
		
	}
}
