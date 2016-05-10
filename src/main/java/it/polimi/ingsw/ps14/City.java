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

	/*
	 * Da tenere a mente che il metodo che cerca tutti i bonus tra città vicine
	 * deve ricordare quali città sono state già controllate
	 */
	public ArrayList<City> findLinkedEmporiums(Player player) {
		ArrayList<City> cities = new ArrayList<City>();
		for (City neighbor : neighbors) {
			if (neighbor.isEmporiumBuilt(player)){
				cities.add(neighbor);
			}
		}
		return cities;
	}

	public void buildEmporium(Player player) {
		if (!isEmporiumBuilt(player)) {
			emporiums.add(player);
		}
		else {
			throw new IllegalArgumentException("Error: the player has already built an emporium in this city");
		}
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