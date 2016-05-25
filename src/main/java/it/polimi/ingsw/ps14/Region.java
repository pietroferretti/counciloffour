package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import it.polimi.ingsw.ps14.model.bonus.Bonus;

public class Region {

	private final RegionType type;

	private List<City> cities;

	private Balcony balcony;

	private BusinessPermit permitCard1;

	private BusinessPermit permitCard2;

	private BusinessDeck permitsDeck;

	private Bonus bonusRegion;

	public Region(Queue<ColorCouncillor> initialCouncillors, RegionType type) {
		this.balcony = new Balcony(initialCouncillors);
		this.type = type;
		this.cities = new ArrayList<>();
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

	public void setPermitCard1(BusinessPermit permitCard1) {
		this.permitCard1 = permitCard1;
	}

	public void setPermitCard2(BusinessPermit permitCard2) {
		this.permitCard2 = permitCard2;
	}

	public void setPermitsDeck(BusinessDeck permitsDeck) {
		this.permitsDeck = permitsDeck;
	}

	public void setBonusRegion(Bonus bonusRegion) {
		this.bonusRegion = bonusRegion;
	}

	public BusinessPermit getPermitCard1() {
		return permitCard1;
	}

	public BusinessPermit getPermitCard2() {
		return permitCard2;
	}

	public Bonus getBonusRegion() {
		return bonusRegion;
	}

}