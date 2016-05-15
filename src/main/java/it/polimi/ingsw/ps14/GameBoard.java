package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class GameBoard {

	Random random = new Random();
	
	private Region[] region;
	
	private King king;
	
	private List<City> cities;

	private int availableAssistants;
	
	// HashMap to store how many councillors there are for each color
	private Map<ColorCouncillor, Integer> availableCouncillors = new HashMap<>();
	

	public int getAvailableAssistants() {
		return availableAssistants;
	}

	public GameBoard(Settings settings) {
		// TODO: build game object

		// Fill the councillors hash map
		for (ColorCouncillor councillor : ColorCouncillor.values())
			availableCouncillors.put(councillor,settings.availableCouncillorsEachColor);
		
		// Set the number of assistants available in the game
		this.availableAssistants = settings.availableAssistants;

		//Build a region for each regionType and send parameter: RandomBalcony and RegionType
		//TODO: do it better! "region[regT.ordinal()] not so good"
		region = new Region[RegionType.values().length];
		for (RegionType regT : RegionType.values()){
			region[regT.ordinal()]=new Region(generateRandomBalcony(settings.councillorsEachBalcony),regT);
		}
		
		// Populate the "cities" array and the Regions
		cities = new ArrayList<>();
		for (String cityName : settings.map.keySet()) {		
			
			String colorString = (String) settings.map.get(cityName).get("color");
			ColorCity cityColor = ColorCity.valueOf(colorString);
			
			String regionString = (String) settings.map.get(cityName).get("region");	
			Region cityRegion = getRegion(RegionType.valueOf(regionString));
			
			City newCity = new City(cityName, cityColor, cityRegion);
			cities.add(newCity);
			cityRegion.addCity(newCity);
		}
		
		// Create connections between cities
		for (String cityName: settings.map.keySet()) {
			
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
		City startCityKing = getCityByName(settings.startCityKing);
		king = new King(generateRandomBalcony(settings.councillorsEachBalcony), startCityKing);
		
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

	private PriorityQueue<ColorCouncillor> generateRandomBalcony(int councillorsEachBalcony){
		PriorityQueue<ColorCouncillor> tempBalcony= new PriorityQueue<ColorCouncillor>();
		for(int j=0;j<councillorsEachBalcony;j++)
			tempBalcony.add(getRandomAvailableCouncillor());
		return tempBalcony;
	
	}

	public boolean useCouncillor(ColorCouncillor councillor) {
		if (councillorIsAvailable(councillor)) {
			availableCouncillors.put(councillor, availableCouncillors.get(councillor)-1);
			return true;
		} else
			return false;
	}
		
	public Integer getCouncillor(ColorCouncillor color){
		return availableCouncillors.get(color);
	}


	// For first population of balconies
	public ColorCouncillor getRandomAvailableCouncillor() {
		int pick = new Random().nextInt(ColorCouncillor.values().length);
		while (councillorIsAvailable(ColorCouncillor.values()[pick]))
			pick++;
		return ColorCouncillor.values()[pick];
	}
	/*
	 * ----------------------- ASSISTANTS --------------------------
	 */

	public void setAssistantsAvailable(int assistantsAvailable) {
		this.availableAssistants=assistantsAvailable;
	}
	
	public int getAssistantsAvailable(){
		return availableAssistants;
	}

	public boolean useAssistants(int quantity){
		if(availableAssistants>=quantity){
			availableAssistants=availableAssistants-quantity;
			return true;
		}
		else return false;
		}
		
	public boolean useAssistant(){
		return useAssistants(1);
	}
	
	/*
	 * -------------------------- REGION ---------------------------
	 */
	
	public Region getRegion(RegionType type){
		int i=0;
		while(region[i].getType()!=type)
			i++;
		if(i != region.length) {
			return region[i];
		} else {
			throw new RuntimeException("Region not found!");
		}
	}
	
	public Region[] getRegions(){
		return region;
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
		for(City city : cities) {
			if(city.getName() == cityName) {
				return city;
			}
		}
		throw new RuntimeException("City not found! Check your settings file?");
	}
}
