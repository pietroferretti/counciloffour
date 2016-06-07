package it.polimi.ingsw.ps14;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.ColorPolitic;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.PoliticCard;
import it.polimi.ingsw.ps14.RegionType;
import it.polimi.ingsw.ps14.Settings;
import it.polimi.ingsw.ps14.controller.actions.mainactions.AcquireBusinessPermiteTileAction;
import it.polimi.ingsw.ps14.controller.actions.mainactions.MainAction;

public class AcquireBusinessPermiteTileActionTest {

	@Test
	public void test() {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GameBoard gameboard = new GameBoard(settingsInstance);
		
		List<PoliticCard> cards=new ArrayList<>();
//		cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
//		cards.add(new PoliticCard(ColorPolitic.PINK));
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,gameboard.getPoliticDeck(),6);

		System.out.println(player.toString());
		
		MainAction action = new AcquireBusinessPermiteTileAction(player, gameboard, gameboard.getRegion(RegionType.COAST), gameboard.getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0], cards);
		assertEquals(action.isValid(),true);
		
		System.out.println(player.toString());

	}

}