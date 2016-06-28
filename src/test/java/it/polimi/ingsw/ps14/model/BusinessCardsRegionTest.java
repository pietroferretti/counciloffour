package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;

import org.junit.Test;

public class BusinessCardsRegionTest {
	
	BusinessPermit busCard;
	Player player;
	Model model;		

	public void create() throws IOException {
 model = new Model();
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}

		GameBoard gameboard = new GameBoard(settingsInstance);
		
		

		busCard = model.getGameBoard().getRegion(RegionType.COAST)
				.getBusinessPermits().getAvailablePermits()[0];
		player = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model
				.getGameBoard().getPoliticDeck(), 6);
		
	}


	@Test
	public void testCardIsFaceUp() throws IOException {
		create();
		int cardsleft = model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().cardsLeftInDeck();
		model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().substituteCard(busCard);
		BusinessPermit busCardAfter = model.getGameBoard().getRegion(RegionType.COAST)
				.getBusinessPermits().getAvailablePermits()[0];
		assertEquals(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().cardsLeftInDeck(),cardsleft-1);
		assertEquals(busCardAfter!=busCard,true);
		
		
		
	}


	@Test
	public void testFindIndexBusinessPermit() throws IOException {
		create();
		assertEquals(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().findIndexBusinessPermit(busCard),0);
		
	}

	@Test
	public void testFillEmptySpots() throws IOException {
		
		create();
		model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().removeBusinessPermit(busCard);
		assertEquals(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0]!=null,false);

		model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().fillEmptySpots();
		assertEquals(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0]!=null,true);
		
	}

}
