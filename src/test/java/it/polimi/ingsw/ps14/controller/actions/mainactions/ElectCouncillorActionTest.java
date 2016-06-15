package it.polimi.ingsw.ps14.controller.actions.mainactions;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.Settings;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;

public class ElectCouncillorActionTest {

	@Test
	public void test() throws IOException {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Model model = new Model();

		List<Player> pls = new ArrayList<>();
		Player player = new Player("ubaldo", Color.DARK_GRAY, 20, 12,
				model.getGameBoard().getPoliticDeck(), 6);
		pls.add(player);
		model.setPlayers(pls);

		for (Region reg : model.getGameBoard().getRegions())
			System.out.println(reg.toString());
		System.out.println(player.toString());

		MainAction action = new ElectCouncillorAction(player.getId(),
				ColorCouncillor.ORANGE, RegionType.COAST);
		assertEquals(action.isValid(model), true);
		
		action.execute(null,model);
		
		for (Region reg : model.getGameBoard().getRegions())
			System.out.println(reg.toString());
		System.out.println(player.toString());
		

	}

}
