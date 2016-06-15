package it.polimi.ingsw.ps14.controller.actions.mainactions;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.Settings;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.bonus.BonusAssistant;
import it.polimi.ingsw.ps14.model.bonus.BonusCoin;
import it.polimi.ingsw.ps14.model.bonus.BonusList;
import it.polimi.ingsw.ps14.model.bonus.BonusNobility;
import it.polimi.ingsw.ps14.model.bonus.BonusPoliticCard;
import it.polimi.ingsw.ps14.model.bonus.BonusVictoryPoint;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BuildEmporiumUsingPermitTileActionTest {

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
		
		List<PoliticCard> cards = new ArrayList<>();
		// cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
		// cards.add(new PoliticCard(ColorPolitic.PINK));
		
		Player player = new Player("ubaldo", Color.DARK_GRAY, 20, 12,
				model.getGameBoard().getPoliticDeck(), 6);
		pls.add(player);
		model.setPlayers(pls);
		
		List cities = new ArrayList<City>();
		cities.add(model.getGameBoard().getCityByName("Arkon"));
		cities.add(model.getGameBoard().getCityByName("Esti"));
		
		BusinessPermit busCard = model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0];

		player.acquireBusinessPermit(busCard);

		MainAction action = new BuildEmporiumUsingPermitTileAction(
				player.getId(), busCard.getId(), busCard.getCities().get(0).getName());		
		
		assertEquals(action.isValid(model), true);
		action.execute(null,model);
		System.out.println(player.toString());

	}

}
