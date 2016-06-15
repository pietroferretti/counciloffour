package it.polimi.ingsw.ps14.model;

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

	public Region(Region r) {
		this.balcony = new Balcony(r.balcony);
		this.type = r.type;
		this.cities = new ArrayList<>(r.cities.size());
		for (City city : r.cities) {
			this.cities.add(new City(city));
		}
		this.businessPermits = new BusinessCardsRegion(r.businessPermits);
		this.bonusRegion = new BonusVictoryPoint(r.bonusRegion.getQuantity());
	}

	public RegionType getType() {
		return type;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	protected void addCity(City city) {
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

	public Balcony getBalcony() {
		return balcony;
	}

	protected void setBusinessPermits(BusinessCardsRegion decks) {
		businessPermits = decks;
		setChanged();
		notifyObservers();
	}

	public BusinessCardsRegion getBusinessPermits() {
		return businessPermits;
	}

	protected void setBonusRegion(BonusVictoryPoint bonusRegion) {
		this.bonusRegion = bonusRegion;
		setChanged();
		notifyObservers();
	}

	public BonusVictoryPoint getBonusRegion() {
		return bonusRegion;
	}

}