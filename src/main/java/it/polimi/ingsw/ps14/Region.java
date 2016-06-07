package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;

import it.polimi.ingsw.ps14.model.bonus.BonusVictoryPoint;

public class Region extends Observable implements Cloneable {

	private final RegionType type;

	private List<City> cities;

	private Balcony balcony;

	private BusinessCardsRegion businessPermits; // unused + used

	private BonusVictoryPoint bonusRegion;

	public Region(Queue<ColorCouncillor> initialCouncillors, RegionType type) {
		this.balcony = new Balcony(initialCouncillors);
		this.type = type;
		this.cities = new ArrayList<>();
		businessPermits = new BusinessCardsRegion();
		bonusRegion = null;
	}

	public RegionType getType() {
		return type;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public void addCity(City city) {
		this.cities.add(city);
	}

	public List<City> getCities() {
		return cities;
	}

	public City findCity(String cityName) {
		for (City city : cities)
			if (city.getName().equals(cityName))
				return city;
		return null;
	}

	public void setBalcony(Balcony balcony) {
		this.balcony = balcony;
	}

	public Balcony getBalcony() {
		return balcony;
	}

	public void setBusinessPermits(BusinessCardsRegion decks) {
		businessPermits = decks;
	}

	public BusinessCardsRegion getBusinessPermits() {
		return businessPermits;
	}

	public void setBonusRegion(BonusVictoryPoint bonusRegion) {
		this.bonusRegion = bonusRegion;
	}

	public BonusVictoryPoint getBonusRegion() {
		return bonusRegion;
	}

	@Override
	public Region clone() throws CloneNotSupportedException {
		Region r=new Region(balcony.readBalcony(), type);
		r.cities = this.cities;
		r.businessPermits = this.businessPermits;
		r.bonusRegion = this.bonusRegion;
		return r;
	}



}