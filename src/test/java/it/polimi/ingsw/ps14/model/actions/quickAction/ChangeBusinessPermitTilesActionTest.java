package it.polimi.ingsw.ps14.model.actions.quickAction;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.quickactions.ChangeBusinessPermitTilesAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.QuickAction;

public class ChangeBusinessPermitTilesActionTest {

	Model model;
	Player player;

	@Before
	public void create() throws IOException {
		model = new Model();
		List<Player> pls = new ArrayList<>();
		player = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model
				.getGameBoard().getPoliticDeck(), 6);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));
	}

	@Test
	public void test() throws IOException {

		QuickAction action = new ChangeBusinessPermitTilesAction(
				player.getId(), RegionType.COAST);
		assertEquals(action.isValid(model), true);

	}

	@Test
	public void testExecute() {
		QuickAction action = new ChangeBusinessPermitTilesAction(
				player.getId(), RegionType.COAST);
		BusinessPermit bp1 = new BusinessPermit(model.getGameBoard()
				.getRegion(RegionType.COAST).getBusinessPermits()
				.getAvailablePermits()[0]);
		BusinessPermit bp2 = new BusinessPermit(model.getGameBoard()
				.getRegion(RegionType.COAST).getBusinessPermits()
				.getAvailablePermits()[1]);

		action.execute(null, model);
		assertEquals(model.getGameBoard().getRegion(RegionType.COAST)
				.getBusinessPermits().getAvailablePermits()[0] != bp1, true);
		assertEquals(model.getGameBoard().getRegion(RegionType.COAST)
				.getBusinessPermits().getAvailablePermits()[1] != bp2, true);
	}

}
