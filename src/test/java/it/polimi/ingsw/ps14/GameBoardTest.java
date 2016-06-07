package it.polimi.ingsw.ps14;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Settings;

public class GameBoardTest {

	@Test
	public void test() {

		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GameBoard gameboard = new GameBoard(settingsInstance);
		System.out.println("Nessun errore!");
	}

}
