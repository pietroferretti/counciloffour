package it.polimi.ingsw.ps14;

import java.io.IOException;

import org.junit.Test;

public class BusinessCardsRegionTest {

	@Test
	public void test() {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
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
