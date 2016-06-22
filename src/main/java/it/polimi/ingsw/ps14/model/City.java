package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.model.bonus.Bonus;

public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6004358049049353737L;

	private final String name;

	private final ColorCity color;

	private final Region region;

	// @SuppressWarnings("unused")
	// private RegionType regionType = null;

	private List<City> neighbors;
	// usato solo per la copia
	private List<String> neighborsName;

	private Bonus token;

	private ArrayList<Player> emporiums;

	public City(String name, ColorCity color, Region region) {
		this.name = name;
		this.color = color;
		this.region = region;
		this.neighbors = new ArrayList<>();
		this.token = null;
		emporiums = new ArrayList<>();
	}

	public City(String name, ColorCity color, Region region, List<City> neighbors, Bonus token) {
		this.name = name;
		this.color = color;
		this.neighbors = neighbors;
		this.region = region;
		this.token = token;
		emporiums = new ArrayList<>();

	}

	public City(City c) {

		this.name = c.name;
		this.color = c.color;
		region = null;

		if (c.neighbors != null) {
			this.neighborsName = new ArrayList<>(c.neighbors.size());

			for (City city : c.neighbors) {
				this.neighborsName.add(city.name);
			}
		}

		// if (c.getRegion() != null) {
		// regionType = c.getRegion().getType();
		// }

		if (c.emporiums != null) {
			this.emporiums = new ArrayList<>(c.emporiums.size());
			for (Player player : c.emporiums) {
				this.emporiums.add(new Player(player));
			}
		}

		if (c.token != null) {
			this.token = c.token.makeCopy();
		}
	}

	public int numEmporiumsBuilt() {
		return emporiums.size();
	}

	public boolean isEmporiumBuilt(Player player) {
		return emporiums.contains(player);
	}

	/**
	 * Return all the neighboring cities where the player has built an emporium
	 * 
	 * @param player
	 * @return
	 */
	public List<City> findLinkedEmporiums(Player player) {
		List<City> cities = new ArrayList<>();
		for (City neighbor : neighbors) {
			if (neighbor.isEmporiumBuilt(player)) {
				cities.add(neighbor);
			}
		}
		return cities;
	}

	public void buildEmporium(Player player) {
		if (!isEmporiumBuilt(player)) {
			emporiums.add(player);
			region.setCities();
			player.incrementNumEmporiums();
		} else {
			throw new IllegalArgumentException("Error: the player has already built an emporium in this city");
		}
	}

	public void setNeighbors(List<City> neighbors) {
		this.neighbors = neighbors;
		this.neighborsName = new ArrayList<>(neighbors.size());
		for (City city : neighbors) {
			this.neighborsName.add(city.name);
		}
	}

	public void setToken(Bonus token) {
		this.token = token;
	}

	public List<Player> getEmporiums() {
		return emporiums;
	}

	public List<City> getNeighbors() {
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

	@Override
	public String toString() {
		String s = "\n" + name.toUpperCase() + "\nColor: " + color + "\nNeighbors: " + toStringNeighborsName()
				+ "\nBonus: ";
		if (token == null) {
			s = s + "No bonus for this city";
		} else
			s = s + token.toString();
		return s + toStringEmporiums();

	}

	private String toStringEmporiums() {
		if (emporiums.isEmpty())
			return "\nNo emporiums in this city yet\n";
		String s = "\nThese players own an emporium in this city:\n";
		for (Player player : emporiums) {
			s = s + player.getName() + " ";
		}
		return s + "\n";
	}

	private String toStringNeighborsName() {
		String s = "\n";
		for (String neigh : neighborsName) {
			s = s + neigh + " ";
		}
		return s;

	}

}