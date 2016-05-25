package it.polimi.ingsw.ps14;

import java.util.ArrayList;

import it.polimi.ingsw.ps14.model.bonus.BonusList;

public class BusinessPermit implements Card {

	private final ArrayList<City> cities;

	private final BonusList bonus;

	public BusinessPermit(ArrayList<City> cities, BonusList bonus) {
		this.cities = cities;
		this.bonus = bonus;
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public BonusList getBonus() {
		return bonus;
	}
}