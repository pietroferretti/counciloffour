package it.polimi.ingsw.ps14.controller.actions.mainactions;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.Settings;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumWithHelpOfKingAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;

public class BuildEmporiumWithHelpOfKingActionTest {

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
		
		
		List<PoliticCard> cards=new ArrayList<>();
//		cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
//		cards.add(new PoliticCard(ColorPolitic.PINK));
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,model.getGameBoard().getPoliticDeck(),6);
		pls.add(player);
		model.setPlayers(pls);
		
		
		MainAction action = new BuildEmporiumWithHelpOfKingAction(player.getId(), model.getGameBoard().getCities().get(2).getName(), cards);
		
		System.out.println("Balcony : "+ model.getGameBoard().getKing().getBalcony().toString());
		
		
		assertEquals(action.isValid(model),true);
		action.execute(null,model);
		
		
	}

}
