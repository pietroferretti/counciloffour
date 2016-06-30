package it.polimi.ingsw.ps14.model.actions.quickAction;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.quickactions.PerformAdditionalMainActionAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.QuickAction;

public class PerformAdditionalMainActionActionTest {

	@Test
	public void test() throws IOException {
		Model model = new Model();
		List<Player> pls=new ArrayList<>();
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,model.getGameBoard().getPoliticDeck(),6);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));
		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0].toString());
		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[1].toString());

		QuickAction action = new PerformAdditionalMainActionAction(player.getId());
		assertEquals(action.isValid(model),true);
	}
	
	@Test
	public void testExecute() throws IOException {
		Model model = new Model();
		List<Player> pls=new ArrayList<>();
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,model.getGameBoard().getPoliticDeck(),6);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));
//		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0].toString());
//		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[1].toString());

		QuickAction action = new PerformAdditionalMainActionAction(player.getId());
		int old = player.getAssistants();
		int old2 = model.getGameBoard().getAvailableAssistants();
		action.execute(null, model);
		assertEquals(player.getAssistants(), old - 3);
		assertEquals(old2 + 3, model.getGameBoard().getAvailableAssistants());	
	}

}
