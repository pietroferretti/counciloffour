package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.bonus.BonusCoin;
import it.polimi.ingsw.ps14.model.bonus.BonusList;

public class BusinessPermitTest {
	
	private static BusinessPermit businessPermit;
	private static City testCity1;
	private static City testCity2;
	private static BonusList testBonus;

	@BeforeClass
	public static void testBusinessPermitListOfCityBonusList() {
		
		testCity1 = new City("Arkham", ColorCity.PURPLE, null);
		testCity2 = new City("Bologna", ColorCity.BRONZE, null);
		List<City> cityList = new ArrayList<>();
		cityList.add(testCity1);
		cityList.add(testCity2);
		
		List<Bonus> listOfBonus = new ArrayList<>();
		listOfBonus.add(new BonusCoin(5));
		testBonus = new BonusList(listOfBonus);
		
		businessPermit = new BusinessPermit(cityList, testBonus);

	}

	@Test
	public void testBusinessPermitBusinessPermit() {
		
		BusinessPermit businessPermitCopy = new BusinessPermit(businessPermit);
		assertEquals(businessPermitCopy.getBonusList().getClass(), businessPermit.getBonusList().getClass());
		assertEquals(businessPermitCopy.getCityNames().get(0), businessPermit.getCities().get(0).getName());
		assertEquals(businessPermitCopy.getCityNames().get(1), businessPermit.getCities().get(1).getName());
		
	}

	@Test
	public void testGetCities() {
		
		assertEquals(businessPermit.getCities().get(0), testCity1);
		assertEquals(businessPermit.getCities().get(1), testCity2);
		
	}

	@Test
	public void testGetBonus() {
		
		assertEquals(businessPermit.getBonusList(), testBonus);
		
	}

	@Test
	public void testUseBonuses() {
		
		Player testPlayer = new Player();
		businessPermit.useBonuses(testPlayer, null);
		assertEquals(testPlayer.getCoins(), 5);
	}

	@Test
	public void testGetId() {
	
		assertEquals(businessPermit.getId().getClass(), Integer.class);
	
	}

	@Test
	public void testContains() {
		
		assertTrue(businessPermit.contains(testCity2));
		
	}

}
