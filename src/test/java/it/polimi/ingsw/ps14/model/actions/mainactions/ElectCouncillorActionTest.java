package it.polimi.ingsw.ps14.model.actions.mainactions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.RegionType;


public class ElectCouncillorActionTest {

	Model model;
	Player player;
	@Before
	public void create() throws IOException{
		 model = new Model();

		List<Player> pls = new ArrayList<>();
		 player = new Player(0, 20, 12, model.getGameBoard().getPoliticDeck(),
				6, "ubaldo", Color.DARK_GRAY);
		pls.add(player);
		model.setPlayers(pls);
	}
	
	
	@Test
	public void testIsValid() {
		

		for (Region reg : model.getGameBoard().getRegions())
			System.out.println(reg.toString());
		System.out.println(player.toString());

		model.getGameBoard().addDiscardedCouncillor(ColorCouncillor.ORANGE);
		ElectCouncillorAction action = new ElectCouncillorAction(player.getId(),
				ColorCouncillor.ORANGE, "COAST");
		assertEquals(action.isValid(model), true);
		

	}
	
	@Test
	public void testExecute() {
		ElectCouncillorAction action = new ElectCouncillorAction(player.getId(),
				ColorCouncillor.ORANGE, "coast");
		Balcony b=new Balcony(model.getGameBoard().getRegion(RegionType.COAST).getBalcony());
		action.execute(null, model);
		assertTrue(model.getGameBoard().getRegion(RegionType.COAST).getBalcony()!=b);
}
	
	@Test
	public void testExecute2() {
		model.getGameBoard().addDiscardedCouncillor(ColorCouncillor.ORANGE);
		ElectCouncillorAction action = new ElectCouncillorAction(player.getId(),
				ColorCouncillor.ORANGE, "king");
		Balcony b=new Balcony(model.getGameBoard().getKing().getBalcony());

		action.execute(null, model);
		assertTrue(model.getGameBoard().getKing().getBalcony()!=b);

}

}