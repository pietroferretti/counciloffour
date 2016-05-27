package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import it.polimi.ingsw.ps14.model.bonus.Bonus;

public class Region {

	private final RegionType type;

	private List<City> cities;

	private Balcony balcony;

	private BusinessCardsRegion businessPermits; // unused + used

	private Integer bonusRegion;

	public Region(Queue<ColorCouncillor> initialCouncillors, RegionType type) {
		this.balcony = new Balcony(initialCouncillors);
		this.type = type;
		this.cities = new ArrayList<>();
		businessPermits = new BusinessCardsRegion();
	}

	public City findCity(String cityName) {
		for (City city : cities)
			if (city.getName() == cityName)
				return city;
		return null;
	}

	// TODO: mettere in ordine i metodi pls

	public RegionType getType() {
		return type;
	}

	public Balcony getBalcony() {
		return balcony;
	}

	public List<City> getCities() {
		return cities;
	}

	public BusinessCardsRegion getBusinessPermits() {
		return businessPermits;
	}

	public void setBusinessPermits(BusinessCardsRegion decks) {
		businessPermits = decks;
	}

	public void addCity(City city) {
		this.cities.add(city);
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public void setBalcony(Balcony balcony) {
		this.balcony = balcony;
	}

	public void setBonusRegion(Integer bonusRegion) {
		this.bonusRegion = bonusRegion;
	}

	public Integer getBonusRegion() {
		return bonusRegion;
	}

}