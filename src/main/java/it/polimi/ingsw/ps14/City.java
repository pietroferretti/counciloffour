package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.Vector;

public class City {

	private ArrayList<String> emporiums;

	private ArrayList<City> neighbors;

	private ColorCity color;

	private String name;

	private Region region;

	private Bonus token;

	public void checkExsistingEmporium() {
	}

	public void buildEmporium() {
	}

	public City(ColorCity color, ArrayList<City> neighbours, Region region, Bonus token, String name) {
	}



	public ArrayList<String> getEmporiums() {
		return emporiums;
	}

	public ArrayList<City> getNeighbors() {
		return neighbors;
	}

	public ColorCity getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public Region getRegion() {
		return region;
	}

	public Bonus getToken() {
		return token;
	}

	public void findLinkedEmporium() {
	}

}