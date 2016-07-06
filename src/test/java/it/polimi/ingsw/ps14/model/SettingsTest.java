package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.Settings;

public class SettingsTest {

	@Test
	public void testSettings() throws IOException {
		Settings settings = new Settings(); 
		
		assertTrue(settings.councillorsEachBalcony > 0);
		assertTrue(settings.availableCouncillorsEachColor > 0);
		assertTrue(settings.availableAssistants > 0);
		assertTrue(settings.numColoredCards > 0);
		assertNotNull(settings.buildingBonuses.get("bonusCoast"));
		assertNotNull(settings.buildingBonuses.get("bonusHills"));
		assertNotNull(settings.buildingBonuses.get("bonusMountains"));
		assertNotNull(settings.buildingBonuses.get("bonusKing1"));
		assertNotNull(settings.buildingBonuses.get("bonusKing2"));
		assertNotNull(settings.buildingBonuses.get("bonusKing3"));
		assertNotNull(settings.buildingBonuses.get("bonusKing4"));
		assertNotNull(settings.buildingBonuses.get("bonusKing5"));
		assertNotNull(settings.buildingBonuses.get("bonusGold"));
		assertNotNull(settings.buildingBonuses.get("bonusSilver"));
		assertNotNull(settings.buildingBonuses.get("bonusBronze"));
		assertNotNull(settings.buildingBonuses.get("bonusBlue"));
		assertFalse(settings.tokens.isEmpty());
		assertFalse(settings.nobilityTrack.isEmpty());
		assertFalse(settings.map.isEmpty());
		assertFalse(settings.permitDeckCoast.isEmpty());
		assertFalse(settings.permitDeckHills.isEmpty());
		assertFalse(settings.permitDeckMountains.isEmpty());
		assertFalse(settings.mapName.isEmpty());
	}

}
