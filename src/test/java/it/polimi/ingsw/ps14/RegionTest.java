package it.polimi.ingsw.ps14;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class RegionTest {

	@Test
	public void test() {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
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
