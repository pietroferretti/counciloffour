package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class King {

	private City city;
	
	private Balcony balcony;

	public King(PriorityQueue<ColorCouncillor> initialCouncillors, City startCityKing) {
		// TODO Auto-generated constructor stub
		this.balcony=new Balcony(initialCouncillors);
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