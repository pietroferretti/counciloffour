package it.polimi.ingsw.ps14;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

public class SettingsTest {

	@Test
	public void testSettings() {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
			fail("error");
		}
		System.out.println(settingsInstance.startCityKing);
		System.out.println(settingsInstance.bonuses.toString());
		System.out.println(settingsInstance.map.toString());
	}

}
