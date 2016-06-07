package it.polimi.ingsw.ps14;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.ps14.controller.actions.mainactions.BuildEmporiumWithHelpOfKingAction;
import it.polimi.ingsw.ps14.controller.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.Settings;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BuildEmporiumWithHelpOfKingActionTest {

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
		
		MainAction action = new BuildEmporiumWithHelpOfKingAction(player, gameboard, gameboard.getCityByName("Arkon"), cards);
		
		System.out.println("Balcony : "+ gameboard.getKing().getBalcony().toString());
		
		
		assertEquals(action.isValid(),true);
			
		
		
	}

}
