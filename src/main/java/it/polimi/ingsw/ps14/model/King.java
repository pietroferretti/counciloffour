package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.List;
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

	/**
	 * Versione in cui l'utente inserisce tutte le città da attraversare, forse
	 * possiamo fare di meglio :)
	 * 
	 * @param cities
	 *            - List of cities to check.
	 * @return True if the king can follow this cities path.
	 */
	public boolean checkMove(List<City> cities) {
		//
		// int cost = cities.size();
		// we should check it before calling this method
		//
		City currentCity = city;
		for (City cty : cities) {

			if (currentCity.getNeighbors().contains(cty)) {
				currentCity = cty;
			} else
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "The king is in " + city.getName() + "%nKING'S COUNCIL:" + balcony.toString();
	}

}