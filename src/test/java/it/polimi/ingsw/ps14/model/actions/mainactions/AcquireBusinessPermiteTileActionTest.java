package it.polimi.ingsw.ps14.model.actions.mainactions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.Settings;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AcquireBusinessPermiteTileActionTest {

	Player player;
	Model model;
	List<PoliticCard> cards;
	
	
	@Before
	public void create() throws IOException {

		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 model = new Model();

		List<Player> pls = new ArrayList<>();
		cards = new ArrayList<>();

		 player = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model
				.getGameBoard().getPoliticDeck(), 4);
		pls.add(player);
		model.setPlayers(new ArrayList<Player>(pls));

		model.getGameBoard().getRegion(RegionType.COAST).getBalcony()
				.electCouncillor(ColorCouncillor.BLACK);
		model.getGameBoard().getRegion(RegionType.COAST).getBalcony()
				.electCouncillor(ColorCouncillor.PINK);
		model.getGameBoard().getRegion(RegionType.COAST).getBalcony()
				.electCouncillor(ColorCouncillor.ORANGE);


	
	}

	@Test
	public void isValidTest1() throws IOException {
		

		player.addPolitic(new PoliticCard(ColorPolitic.BLACK));
		player.addPolitic(new PoliticCard(ColorPolitic.PINK));
		player.addPolitic(new PoliticCard(ColorPolitic.ORANGE));

		cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.PINK));
		cards.add(new PoliticCard(ColorPolitic.ORANGE));
		AcquireBusinessPermitTileAction action = new AcquireBusinessPermitTileAction(
				player.getId(), RegionType.COAST, model.getGameBoard()
						.getRegion(RegionType.COAST).getBusinessPermits()
						.getAvailablePermits()[0].getId(), cards);

		System.out.println(player.getHand().toString());

		assertTrue(action.isValid(model));

		System.out.println(model.getGameBoard().getRegion(RegionType.COAST)
				.getBusinessPermits().getAvailablePermits()[0].getBonusList()
				.toString());

		

		System.out.println(player.getHand().toString());

		System.out.println(player.getHand().toString());

	}
	
	
	@Test
	public void isValidTest2() throws IOException {

		
		player.getHand().clear();
		

		player.addPolitic(new PoliticCard(ColorPolitic.BLACK));
		player.addPolitic(new PoliticCard(ColorPolitic.PINK));
		player.addPolitic(new PoliticCard(ColorPolitic.ORANGE));
		
		cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
		cards.add(new PoliticCard(ColorPolitic.ORANGE));

		AcquireBusinessPermitTileAction action = new AcquireBusinessPermitTileAction(
				player.getId(), RegionType.COAST, model.getGameBoard()
						.getRegion(RegionType.COAST).getBusinessPermits()
						.getAvailablePermits()[0].getId(), cards);

		System.out.println(player.getHand().toString());

		assertEquals(action.isValid(model),false);

	}
	
	
	
	@Test 
	public void executeTest(){
		

		player.addPolitic(new PoliticCard(ColorPolitic.BLACK));
		player.addPolitic(new PoliticCard(ColorPolitic.PINK));
		player.addPolitic(new PoliticCard(ColorPolitic.ORANGE));

		cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.PINK));
		cards.add(new PoliticCard(ColorPolitic.ORANGE));
		Integer bp=new Integer( model.getGameBoard()
				.getRegion(RegionType.COAST).getBusinessPermits()
				.getAvailablePermits()[0].getId());

		AcquireBusinessPermitTileAction action = new AcquireBusinessPermitTileAction(
				player.getId(), RegionType.COAST,bp, cards);
		
		action.execute(null,model);
		
		assertTrue(bp!=model.getGameBoard()
				.getRegion(RegionType.COAST).getBusinessPermits()
				.getAvailablePermits()[0].getId());
		
		boolean ok=false;
		
		for(BusinessPermit b : player.getBusinessHand().getValidCards())
			if(b.getId().equals(bp))
				ok=true;
		
		assertTrue(ok);
		
		
	}

}
