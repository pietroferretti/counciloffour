package it.polimi.ingsw.ps14.model.actions.mainactions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;

public class BuildEmporiumUsingPermitTileActionTest {

	@Test
	public void isValidTest() throws IOException {
		Model model = new Model();

		List<Player> pls = new ArrayList<>();
		
		List<PoliticCard> cards = new ArrayList<>();
		// cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
		// cards.add(new PoliticCard(ColorPolitic.PINK));
		
		Player player = new Player(1, 20, 12,
				model.getGameBoard().getPoliticDeck(), 6);
		pls.add(player);
		model.setPlayers(pls);
	
		BusinessPermit busCard = model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0];

		System.out.println(busCard);
		
		player.acquireBusinessPermit(busCard);

		MainAction action = new BuildEmporiumUsingPermitTileAction(
				player.getId(), busCard.getId(), busCard.getCities().get(0).getName());		
		
		assertEquals(action.isValid(model), true);


	}
	
	
	@Test
	public void isValidTest2() throws IOException {

		Model model = new Model();

		List<Player> pls = new ArrayList<>();
		
		List<PoliticCard> cards = new ArrayList<>();
		// cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
		// cards.add(new PoliticCard(ColorPolitic.PINK));
		
		Player player = new Player(1, 20, 12,
				model.getGameBoard().getPoliticDeck(), 6);
		pls.add(player);
		model.setPlayers(pls);
		
		BusinessPermit busCard = model.getGameBoard().getRegion(RegionType.HILLS).getAvailablePermits()[0];


		MainAction action = new BuildEmporiumUsingPermitTileAction(
				player.getId(), busCard.getId(), busCard.getCities().get(0).getName());		
		
		assertEquals(action.isValid(model), false);

	}
	
	
	

	@Test
	public void executeTest() throws IOException{

		Model model = new Model();

		List<Player> pls = new ArrayList<>();
		
		List<PoliticCard> cards = new ArrayList<>();
		// cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
		// cards.add(new PoliticCard(ColorPolitic.PINK));
		
		Player player = new Player(1, 20, 12,
				model.getGameBoard().getPoliticDeck(), 6);
		pls.add(player);
		model.setPlayers(pls);
		BusinessPermit busCard = model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0];

		player.acquireBusinessPermit(busCard);
		
		String cityName= busCard.getCities().get(0).getName();

		MainAction action = new BuildEmporiumUsingPermitTileAction(
				player.getId(), busCard.getId(),cityName);		

		assertTrue(action.isValid(model));
		
		action.execute(null, model);

		boolean ok=false;
		for(Player p:model.getGameBoard().getCityByName(cityName).getEmporiums())
			if(p.getId()==player.getId())
				ok=true;
		
		assertEquals(ok,true);
		assertTrue(player.getBusinessHand().getUsedCards() != null);
		assertEquals(player.getBusinessHand().getUsedCards().get(0).equals(busCard),true);
		
		
		
	}
}
