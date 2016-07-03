package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.bonus.BonusList;

public class BusinessPermit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1807264304675939603L;

	static int idCounter = 0;
	private List<String> cityNames; // for modelView copy only
	private final Integer id;
	private List<City> cities;
	private final Bonus bonus;

	public BusinessPermit(List<City> cities, Bonus bonus) {
		this.id = idCounter;
		idCounter++;
		this.cities = cities;
		this.bonus = bonus;
		
		this.cityNames=new ArrayList<>();
		for (City city : cities) {
			this.cityNames.add(new String(city.getName()));
		}
	}

	public BusinessPermit(BusinessPermit bp) {
		this.cityNames=new ArrayList<>();
		for (City city : bp.cities) {
			this.cityNames.add(new String(city.getName()));
		}
		this.bonus = new BonusList(bp.bonus);
		this.id = new Integer(bp.getId());
	}

	public List<City> getCities() {
		return cities;
	}
	
	public List<String> getCityNames() {
		return cityNames;
	}

	public Bonus getBonusList() {
		return bonus;
	}

	public void useBonuses(Player player, Model model) {
		bonus.useBonus(player, model);
	}

	public Integer getId() {
		return id;
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

		for (City cty : cities) {
			if (cty.equals(city)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		String s = "\nPermit ID: " + Integer.toString(id)
				+ "\nPermit to build in: ";
		if (cityNames != null) {
			for (String city : cityNames) {
				s = s + city + " ";
			}
		} else {
			s = s + "no cities.";
		}
		
		if (bonus != null) {
			s = s + "\nBonus: " + bonus.toString();
		} else {
			s = s + "\nBonus: none.";
		}
		
		return s;
	}

}