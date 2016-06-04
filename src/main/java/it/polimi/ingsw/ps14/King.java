package it.polimi.ingsw.ps14;

import java.util.List;
import java.util.Observable;
import java.util.Queue;

public class King extends Observable implements Cloneable{

	private City city;
	
	private Balcony balcony;

	public King(Queue<ColorCouncillor> initialCouncillors, City startCityKing) {
		// TODO Auto-generated constructor stub
		this.balcony=new Balcony(initialCouncillors);
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

	// versione in cui l'utente inserisce tutte le città da attraversare,
	// forse possiamo fare di meglio :)
	public boolean checkMove(List<City> cities) {
		//
		// int cost = cities.size();
		// we should check it before calling this method
		//
		City currentCity = city;
		for (City city : cities) {

			if (currentCity.getNeighbors().contains(city)) {
				currentCity = city;
			} else
				return false;

		}
		return true;
	}

}