package it.polimi.ingsw.ps14.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.model.bonus.BonusList;

public class BusinessPermit implements Card {

	private final List<City> cities;

	private final BonusList bonus;

	public BusinessPermit(List<City> cities, BonusList bonus) {
		this.cities = cities;
		this.bonus = bonus;
	}

	public BusinessPermit(BusinessPermit bp) {
		this.cities = new ArrayList<>();
		for (City city : bp.cities) {
			this.cities.add(new City(city));
		}
		this.bonus = new BonusList(bp.bonus);
	}

	public List<City> getCities() {
		return cities;
	}

	public BonusList getBonus() {
		return bonus;
	}

	public void useBonuses(Player player, GameBoard gameboard) {
		bonus.useBonus(player, gameboard);
	}

	/**
	 * Check if city parameter is contained in the cities declared in the
	 * businessPermit
	 *
	 * @param city
	 *            city to check
	 * @return true is it is contained, false if it is NOT contained
	 */
	public boolean contains(City city) {
		for (City cty : cities)
			if (cty.equals(city))
				return true;
		return false;
	}

}