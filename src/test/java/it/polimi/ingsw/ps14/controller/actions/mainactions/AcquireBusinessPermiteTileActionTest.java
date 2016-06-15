package it.polimi.ingsw.ps14.controller.actions.mainactions;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.Settings;
import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermiteTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AcquireBusinessPermiteTileActionTest {

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
		List<PoliticCard> cards=new ArrayList<>();
//		cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
//		cards.add(new PoliticCard(ColorPolitic.PINK));
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,model.getGameBoard().getPoliticDeck(),6);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));

		System.out.println(player.toString());
		
		MainAction action = new AcquireBusinessPermiteTileAction(player.getId(), RegionType.COAST, model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0].getId(), cards);
		assertEquals(action.isValid(model),true);
		action.execute(null, model);
		
		System.out.println(player.toString());

	}

}
