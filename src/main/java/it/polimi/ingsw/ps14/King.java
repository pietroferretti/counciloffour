package it.polimi.ingsw.ps14;

import java.util.List;
import java.util.Observable;
import java.util.Queue;

public class King extends Observable implements Cloneable {

	private City city;

	private Balcony balcony;

	public King(Queue<ColorCouncillor> initialCouncillors, City startCityKing) {
		this.balcony = new Balcony(initialCouncillors);
		this.city = startCityKing;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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
	public King clone() throws CloneNotSupportedException {
		return new King(balcony.readBalcony(), city);
	}

	@Override
	public String toString() {
		return "King [city=" + city + ", balcony=" + balcony + "]";
	}

}