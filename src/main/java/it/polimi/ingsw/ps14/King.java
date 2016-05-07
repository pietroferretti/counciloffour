package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class King extends Balcony {

	private City city;
	private ArrayList<ColorCouncillor> colors;

	public King(City city) {
		this.city = city;
		
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	// versione in cui l'utente inserisce tutte le città da attraversare,
	// forse possiamo fare di meglio :)
	public boolean checkMove(ArrayList<City> cities) {
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