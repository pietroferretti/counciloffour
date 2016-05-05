package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class King extends Balcony {

	private City city;
	private ArrayList<ColorCouncillor> colors;
	
	public King(City city, ArrayList<ColorCouncillor> colors){
		this.city = city;
		this.colors = new ArrayList<ColorCouncillor>(colors);
	}

	public City getCity() {
		return city;
	}

	public void move() {
	}

}