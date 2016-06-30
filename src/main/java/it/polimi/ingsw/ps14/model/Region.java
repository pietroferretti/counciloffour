package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;

import it.polimi.ingsw.ps14.model.actions.quickactions.ChangeBusinessPermitTilesAction;

public class Region extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5272252892577316568L;

	private final RegionType type;

	private List<City> cities;

	private Balcony balcony;

	private BusinessCardsRegion businessPermits; // unused + used

	private int bonusRegion;

	public Region(Queue<ColorCouncillor> initialCouncillors, RegionType type) {
		this.balcony = new Balcony(initialCouncillors);
		this.type = type;
		this.cities = new ArrayList<>();
		businessPermits = new BusinessCardsRegion();
	}

	public Region(Region r) {
		this.balcony = new Balcony(r.balcony);
		this.type = r.type;
		this.cities = new ArrayList<>(r.cities.size());
		for (City city : r.cities) {
			this.cities.add(new City(city));
		}
		this.businessPermits = new BusinessCardsRegion(r.businessPermits);
		this.bonusRegion = r.bonusRegion;
	}

	public RegionType getType() {
		return type;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	/**
	 * It just notifies to each observer that any city has changed.
	 */
	public void setCities() {
		setChanged();
		notifyObservers();
	}

	protected void addCity(City city) {
		this.cities.add(city);
	}

	public List<City> getCities() {
		return cities;
	}
/**
 * It finds a specific city in the region given its name.
 * @param cityName
 * @return City - the required city.
 */
	public City findCity(String cityName) {
		for (City city : cities)
			if (city.getName().equals(cityName))
				return city;
		return null;
	}

	public Balcony getBalcony() {
		return balcony;
	}

	public void setBalcony() {
		setChanged();
		notifyObservers();
	}

	public void setBusinessPermits(BusinessCardsRegion decks) {
		businessPermits = decks;
		setChanged();
		notifyObservers();
	}

	/**
	 * It just notifies to each observer that the business permits deck has
	 * changed. (Called by {@link ChangeBusinessPermitTilesAction})
	 */
	public void setBusinessPermits() {
		setChanged();
		notifyObservers();
	}

	public BusinessCardsRegion getBusinessPermits() {
		return businessPermits;
	}

	protected void setBonusRegion(int bonusRegion) {
		this.bonusRegion = bonusRegion;
		setChanged();
		notifyObservers();
	}

	public int getBonusRegion() {
		return bonusRegion;
	}

	public void consumeBonusRegion() {
		bonusRegion = 0;
		setChanged();
		notifyObservers();
	}
	
	public BusinessPermit[] getAvailablePermits() {
		return businessPermits.getAvailablePermits();
	}

	@Override
	public String toString() {

		String stype = "\nREGION: " + type.toString() + "\n";

		String sbonus = "\nREGION BONUS: +".concat(String.valueOf(bonusRegion)) + " victory points\n";

		String scouncil = "\nCOUNCIL: " + balcony.toString() + "\n";

		String spermits = "\nBUSINESS PERMITS:" + businessPermits.toString() + "\n";

		String scitiesName = "\nThis region contains " + Integer.toString(cities.size()) + " cities.\n\nCITIES:\n";
		for (City city : cities) {
			scitiesName = scitiesName + city.getName() + " ";
		}
		String scities = "\n";
		for (City city : cities) {
			scities = scities + city.toString() + " ";
		}
		return stype + sbonus + scouncil + spermits + scitiesName + scities;

	}

	public String toStringCityName() {
		String s = " ";
		for (City city : cities) {
			s = s + city.getName() + " ";
		}
		return s;
	}

}