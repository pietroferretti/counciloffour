package it.polimi.ingsw.ps14.model;

import java.util.List;

import it.polimi.ingsw.ps14.model.bonus.BonusList;

public class BusinessPermitFactory {

	static int idCounter = 0;
	
	private BusinessPermitFactory(){};
	
	public static BusinessPermit createBusinessPermit(List<City> cities, BonusList bonus) {
		idCounter++;
		return new BusinessPermit(cities, bonus, new Integer(idCounter));
	}
	
}
