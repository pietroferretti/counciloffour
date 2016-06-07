package it.polimi.ingsw.ps14;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.Settings;

public class SettingsTest {

	@Test
	public void testSettings() {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO: assert veri e propri
	}

}
