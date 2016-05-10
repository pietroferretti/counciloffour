package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class BusinessPermit implements Card {

	private final ArrayList<City> cities;

	private final Bonus bonus;

	public BusinessPermit(ArrayList<City> cities, Bonus bonus) {
		this.cities = cities;
		this.bonus = bonus;
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public Bonus getBonus() {
		return bonus;
	}
}