package it.polimi.ingsw.ps14.model.actions.quickAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.quickactions.QuickAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.SendAssistantToElectCouncillorAction;

public class SendAssistantToElectCouncillorActionTest {

	@Test
	public void test() throws IOException {
		Model model = new Model();
		List<Player> pls=new ArrayList<>();
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,model.getGameBoard().getPoliticDeck(),6);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));

		model.getGameBoard().addDiscardedCouncillor(ColorCouncillor.PURPLE);
		QuickAction action = new SendAssistantToElectCouncillorAction(player.getId(), null, ColorCouncillor.PURPLE);
		assertEquals(action.isValid(model),true);
		action.execute(null, model);
	}
	
	@Test
	public void test2() throws IOException {
		Model model = new Model();
		List<Player> pls=new ArrayList<>();
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,model.getGameBoard().getPoliticDeck(),6);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));

		model.getGameBoard().addDiscardedCouncillor(ColorCouncillor.PURPLE);
		QuickAction action = new SendAssistantToElectCouncillorAction(player.getId(), RegionType.COAST, ColorCouncillor.PURPLE);
		assertEquals(action.isValid(model),true);
		action.execute(null, model);
	}
	
	@Test
	public void testExecute() throws IOException {
		Model model = new Model();
		List<Player> pls=new ArrayList<>();
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,model.getGameBoard().getPoliticDeck(),6);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));

		model.getGameBoard().addDiscardedCouncillor(ColorCouncillor.PURPLE);
		QuickAction action = new SendAssistantToElectCouncillorAction(player.getId(), null, ColorCouncillor.PURPLE);
		
		Balcony b=new Balcony(model.getGameBoard().getKing().getBalcony());
		
		System.out.println(model.getGameBoard().getKing().getBalcony().toString());
		action.execute(null, model);
		assertTrue(!model.getGameBoard().getKing().getBalcony().readBalcony().equals(b.readBalcony()));

		System.out.println(model.getGameBoard().getKing().getBalcony().toString());

		
	}

}
