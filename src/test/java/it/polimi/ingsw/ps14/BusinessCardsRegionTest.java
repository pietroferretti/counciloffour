package it.polimi.ingsw.ps14;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.BusinessCardsRegion;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.Settings;

public class BusinessCardsRegionTest {	

	private static final String SETTINGS_FILENAME = "src/main/resources/settings.json";

	@Test
	public void test() {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings(SETTINGS_FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		}

		GameBoard gameboard = new GameBoard(settingsInstance);

		BusinessCardsRegion busDeck = gameboard.getRegion(RegionType.COAST)
				.getBusinessPermits();

		System.out.println(busDeck.cardsLeftInDeck());
		for (City city : busDeck.getAvailablePermits()[0].getCities())
			System.out.println("-"+city.getName());
		busDeck.substituteCard(busDeck.getAvailablePermits()[0]);
		for (City city : busDeck.getAvailablePermits()[0].getCities())
			System.out.println("+"+city.getName());
		System.out.println(busDeck.cardsLeftInDeck());
	}

}
