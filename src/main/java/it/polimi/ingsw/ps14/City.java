package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class City {

	private final String name;
	
	private final ColorCity color;

	private final ArrayList<City> neighbors;

	private final Region region;

	private final Bonus token;
	
	private ArrayList<Player> emporiums;
	
	public City(String name, ColorCity color, ArrayList<City> neighbors, Region region, Bonus token) {
		this.name = name;
		this.color = color;
		this.neighbors = neighbors;
		this.region = region;
		this.token = token;
		emporiums = new ArrayList<Player>();
	}
	
	
	// TODO: return number of emporiums not owned by player
	
	public boolean isEmporiumBuilt(Player player) {
		return emporiums.contains(player);
	}

	public void findLinkedEmporium() {
	}

	public void buildEmporium() {
	}

	public ArrayList<Player> getEmporiums() {
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


}