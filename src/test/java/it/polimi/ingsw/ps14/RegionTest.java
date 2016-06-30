package it.polimi.ingsw.ps14;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.Settings;

public class RegionTest {

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
		for(Region reg : gameboard.getRegions()){
			System.out.println( "\n"+reg.getType().toString()+":");
			for(City city : reg.getCities())
				System.out.print(" - "+city.getName());
			
		}
		
		assertEquals(gameboard.getRegion(RegionType.COAST).findCity("Esti").getName(),"Esti");
	}

}
