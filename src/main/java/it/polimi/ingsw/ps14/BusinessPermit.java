package it.polimi.ingsw.ps14;

import java.util.List;

import it.polimi.ingsw.ps14.model.bonus.BonusList;

public class BusinessPermit implements Card {

	private final List<City> cities;

	private final BonusList bonus;

	public BusinessPermit(List<City> cities, BonusList bonus) {
		this.cities = cities;
		this.bonus = bonus;
	}

	public List<City> getCities() {
		return cities;
	}

	public BonusList getBonus() {
		return bonus;
	}
}