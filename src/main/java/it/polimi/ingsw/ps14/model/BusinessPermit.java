package it.polimi.ingsw.ps14.model;

import java.util.List;

import it.polimi.ingsw.ps14.model.bonus.BonusList;

public class BusinessPermit implements Card {

	private final List<City> cities;
private final Integer id;


	private final BonusList bonus;

	public BusinessPermit(List<City> cities, BonusList bonus, Integer id) {
		this.cities = cities;
		this.bonus = bonus;
		this.id=id;
	}

	public List<City> getCities() {
		return cities;
	}

	public BonusList getBonus() {
		return bonus;
	}
	
	public void useBonuses(Player player, Model model){
		bonus.useBonus(player, model);
	}

	public Integer getId() {
	return id;
}

	
	/**
	 * Check if city parameter is contained in the cities declared in the businessPermit
	 *
	 * @param  city city to check
	 * @return      true is it is contained, false if it is NOT contained
	 */
	public boolean contains(City city) {
		for (City cty : cities)
			if (cty.equals(city))
				return true;
		return false;
	}

	
}