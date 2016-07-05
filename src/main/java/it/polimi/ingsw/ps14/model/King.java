package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.Observable;
import java.util.Queue;

public class King extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5853003754386331566L;

	private City city;

	private Balcony balcony;

	public King(Queue<ColorCouncillor> initialCouncillors, City startCityKing) {
		this.balcony = new Balcony(initialCouncillors);
		this.city = startCityKing;
	}

	public King(King k) {
		this.city = new City(k.city);
		this.balcony = new Balcony(k.balcony);
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
		setChanged();
		notifyObservers();
	}

	public Balcony getBalcony() {
		return balcony;
	}
	
	public void setBalcony() {
		setChanged();
		notifyObservers();
	}

	@Override
	public String toString() {
		return "The KING is in " + city.getName().toUpperCase() + ".\n\nKING'S COUNCIL:" + balcony.toString();
	}

}