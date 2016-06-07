package it.polimi.ingsw.ps14.controller.actions.mainactions;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.ps14.ColorCouncillor;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.Region;
import it.polimi.ingsw.ps14.RegionType;
import it.polimi.ingsw.ps14.Settings;

import java.awt.Color;
import java.io.IOException;

import org.junit.Test;

public class ElectCouncillorActionTest {

	@Test
	public void test() {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}

		GameBoard gameboard = new GameBoard(settingsInstance);

		Player player = new Player("ubaldo", Color.DARK_GRAY, 20, 12,
				gameboard.getPoliticDeck(), 6);

		for (Region reg : gameboard.getRegions())
			System.out.println(reg.toString());
		System.out.println(player.toString());

		MainAction action = new ElectCouncillorAction(player, gameboard,
				ColorCouncillor.ORANGE, gameboard.getRegion(RegionType.COAST)
						.getBalcony());
		assertEquals(action.isValid(), true);
		
		action.execute(null);
		
		for (Region reg : gameboard.getRegions())
			System.out.println(reg.toString());
		System.out.println(player.toString());
		

	}

}
