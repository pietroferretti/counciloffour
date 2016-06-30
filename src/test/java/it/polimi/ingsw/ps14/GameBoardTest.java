package it.polimi.ingsw.ps14;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Settings;

public class GameBoardTest {

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
		System.out.println("Balcony: " + gameboard.getKing().getBalcony().toString());
	}

}
