package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class GameBoard {

	Random random = new Random();

	private List<Region> regions;

	private King king;

	private List<City> cities;

	private int availableAssistants;

	// HashMap to store how many councillors there are for each color
	private Map<ColorCouncillor, Integer> availableCouncillors = new HashMap<>();

	private Integer bonusGold;
	private Integer bonusSilver;
	private Integer bonusBronze;
	private Integer bonusBlue;
	private List<Integer> bonusesKing;

	private PoliticDeck politicDeck;

	public int getAvailableAssistants() {
		return availableAssistants;
	}

	public PoliticDeck getPoliticDeck() {
		return politicDeck;
	}

	public void setPoliticDeck(PoliticDeck politicDeck) {
		this.politicDeck = politicDeck;
	}

	public GameBoard(Settings settings) {
		// TODO: build game object

		// Fill the councillors hash map
		for (ColorCouncillor councillor : ColorCouncillor.values())
			availableCouncillors.put(councillor, settings.availableCouncillorsEachColor);

		// Set the number of assistants available in the game
		this.availableAssistants = settings.availableAssistants;

		// Build a region for each regionType and send parameter: RandomBalcony
		// and RegionType
		// TODO: do it better! "region[regT.ordinal()] not so good"
		regions = new ArrayList<>();
		for (RegionType regT : RegionType.values()) {
			regions.add(new Region(generateRandomBalcony(settings.councillorsEachBalcony), regT));
		}
		
		// Populate the "cities" array and the Regions
		cities = new ArrayList<>();
		for (String cityName : settings.map.keySet()) {

			String colorString = (String) settings.map.get(cityName).get("color");
			ColorCity cityColor = ColorCity.valueOf(colorString.toUpperCase());

			String regionString = (String) settings.map.get(cityName).get("region");
			RegionType type = RegionType.valueOf(regionString.toUpperCase());
			
			Region cityRegion = getRegion(RegionType.valueOf(regionString.toUpperCase()));
			
			City newCity = new City(cityName, cityColor, cityRegion);
			cities.add(newCity);
			cityRegion.addCity(newCity);
		}

		// Create connections between cities
		for (String cityName : settings.map.keySet()) {
			City city = getCityByName(cityName);
			List<String> neighborsStringList = (ArrayList<String>) settings.map.get(cityName).get("neighbors");
			List<City> neighborsList = new ArrayList<>();
			for (String neighborName : neighborsStringList) {
				City tempNeighbor = getCityByName(neighborName);
				neighborsList.add(tempNeighbor);
			}

			city.setNeighbors(neighborsList);
		}

		// Generate a King object
		City kingStartingCity = getCityByName(settings.kingStartingCityString);
		king = new King(generateRandomBalcony(settings.councillorsEachBalcony), kingStartingCity);

		// Get the bonuses from settings
		bonusGold = settings.bonuses.get("bonusGold");
		bonusSilver = settings.bonuses.get("bonusSilver");
		bonusBronze = settings.bonuses.get("bonusBronze");
		bonusBlue = settings.bonuses.get("bonusBlue");
		bonusesKing = new ArrayList<>();
		bonusesKing.add(settings.bonuses.get("bonusKing1")); // Maybe should
																// refactor as a
																// loop
		bonusesKing.add(settings.bonuses.get("bonusKing2"));
		bonusesKing.add(settings.bonuses.get("bonusKing3"));
		bonusesKing.add(settings.bonuses.get("bonusKing4"));
		bonusesKing.add(settings.bonuses.get("bonusKing5"));

	}

	/*
	 * ------------------------ COUNCILLOR ------------------------
	 */

	// check if the chosen color is available
	public boolean councillorIsAvailable(ColorCouncillor color) {
		if (availableCouncillors.get(color) > 0)
			return true;
		else
			return false;
	}

	private PriorityQueue<ColorCouncillor> generateRandomBalcony(int councillorsEachBalcony) {
		PriorityQueue<ColorCouncillor> tempBalcony = new PriorityQueue<ColorCouncillor>();
		for (int j = 0; j < councillorsEachBalcony; j++)
			tempBalcony.add(getRandomAvailableCouncillor());
		return tempBalcony;

	}

	public boolean useCouncillor(ColorCouncillor councillor) {
		if (councillorIsAvailable(councillor)) {
			availableCouncillors.put(councillor, availableCouncillors.get(councillor) - 1);
			return true;
		} else
			return false;
	}

	public Integer getCouncillor(ColorCouncillor color) {
		return availableCouncillors.get(color);
	}
	
	public void addDiscardedCouncillor(ColorCouncillor color){
		availableCouncillors.put(color, availableCouncillors.get(color)+1);
	}

	// For first population of balconies
	public ColorCouncillor getRandomAvailableCouncillor() {
		Random generator = new Random();
		int pick = generator.nextInt(ColorCouncillor.values().length - 1);
		while (!councillorIsAvailable(ColorCouncillor.values()[pick]))
			pick++;
		return ColorCouncillor.values()[pick];
	}
	/*
	 * ----------------------- ASSISTANTS --------------------------
	 */

	public void setAssistantsAvailable(int assistantsAvailable) {
		this.availableAssistants = assistantsAvailable;
	}

	public int getAssistantsAvailable() {
		return availableAssistants;
	}

	public boolean useAssistants(int quantity) { // ma se li considerassimo
													// infiniti??
		if (availableAssistants >= quantity) {
			availableAssistants = availableAssistants - quantity;
			return true;
		} else
			return false;
	}

	public boolean useAssistant() {
		return useAssistants(1);
	}

	/*
	 * -------------------------- REGION ---------------------------
	 */

	public Region getRegion(RegionType type) {
		Region regionFound = null;
		for(Region regionInList: regions) {
			if(regionInList.getType() == type) {
				regionFound = regionInList;
				break;
			}
		}
		return regionFound;
	}

	public List<Region> getRegions() {
		return regions;
	}
	
	/*
	 * --------------------------- KING -----------------------------
	 */

	public King getKing() {
		return king;
	}

	public void setKing(King king) {
		this.king = king;
	}

	/*
	 * --------------------------- CITIES -----------------------------
	 */

	private City getCityByName(String cityName) {
		for (City city : cities) {
			if (city.getName().equals(cityName)) {
				return city;
			}
		}
		throw new RuntimeException("City not found! Check your settings file?");
	}
}
