package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Region {

	private final RegionType type;

	private List<City> cities;

	private Balcony balcony;

	private BusinessPermit permitCard1;

	private BusinessPermit permitCard2;

	private BusinessDeck permitsDeck;

	private Integer bonusRegion;

	public Region(Queue<ColorCouncillor> initialCouncillors, RegionType type) {
		this.balcony = new Balcony(initialCouncillors);
		this.type = type;
		this.cities = new ArrayList<>();
	}

	// TODO: mettere in ordine i metodi pls
	
	// TODO: pescare i 2 permit a faccia in su
	
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

	public BusinessPermit getPermitCard1() {
		return permitCard1;
	}

	public BusinessPermit getPermitCard2() {
		return permitCard2;
	}

	public Integer getBonusRegion() {
		return bonusRegion;
	}

}