package it.polimi.ingsw.ps14.controller.actions.quickAction;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.Settings;
import it.polimi.ingsw.ps14.model.actions.quickactions.ChangeBusinessPermitTilesAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.QuickAction;

public class ChangeBusinessPermitTilesActionTest {

	@Test
	public void test() throws IOException {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Model model = new Model();
		List<Player> pls=new ArrayList<>();
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,model.getGameBoard().getPoliticDeck(),6);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));
		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0].toString());
		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[1].toString());

		
		QuickAction action = new ChangeBusinessPermitTilesAction(player.getId(), RegionType.COAST);
		assertEquals(action.isValid(model),true);
		action.execute(null, model);


	}

}
