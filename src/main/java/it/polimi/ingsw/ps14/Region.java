package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import it.polimi.ingsw.ps14.model.bonus.Bonus;

public class Region {

	private final RegionType type;

	private List<City> cities;

	private Balcony balcony;

	private BusinessPermit[] availablePermit;

	private BusinessDeck permitsDeck;

	private Integer bonusRegion;

	public Region(Queue<ColorCouncillor> initialCouncillors, RegionType type,
			List<City> cities, BusinessDeck permitsDeck, Integer bonusRegion) {
		this.balcony = new Balcony(initialCouncillors);
		this.type = type;
		this.cities = cities;
		availablePermit = new BusinessPermit[2];
		fillAvailablePermit();
		this.permitsDeck = permitsDeck;
		this.bonusRegion=bonusRegion;
	}

	private void fillAvailablePermit() {
		for (BusinessPermit busPer : availablePermit)
			busPer = permitsDeck.drawCard();
	}

	
	public City findCity(String cityName){
		for (City city : cities)
			if (city.getName()==cityName) return city;
		return null;
	}
	
	public boolean cardIsChoosable(BusinessPermit permitTile){
		for (BusinessPermit busPer : availablePermit)
			if(permitTile==busPer) return true;
		return false;
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

	public BusinessDeck getPermitsDeck() {
		return permitsDeck;
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

	public void setPermitsDeck(BusinessDeck permitsDeck) {
		this.permitsDeck = permitsDeck;
	}

	public void setBonusRegion(Integer bonusRegion) {
		this.bonusRegion = bonusRegion;
	}

	public Integer getBonusRegion() {
		return bonusRegion;
	}

}