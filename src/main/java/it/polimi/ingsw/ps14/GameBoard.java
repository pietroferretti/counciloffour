package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.bonus.BonusAssistant;
import it.polimi.ingsw.ps14.model.bonus.BonusCoin;
import it.polimi.ingsw.ps14.model.bonus.BonusList;
import it.polimi.ingsw.ps14.model.bonus.BonusMainAction;
import it.polimi.ingsw.ps14.model.bonus.BonusNobility;
import it.polimi.ingsw.ps14.model.bonus.BonusPoliticCard;
import it.polimi.ingsw.ps14.model.bonus.BonusVictoryPoint;

public class GameBoard {

	Random random = new Random();

	private List<Region> regions;
	private King king;
	private List<City> cities;
	private int availableAssistants;
	private NobilityTrack nobilityTrack;
	// HashMap to store how many councillors there are for each color
	private EnumMap<ColorCouncillor, Integer> availableCouncillors = new EnumMap<>(ColorCouncillor.class);
	private Integer bonusGold;
	private Integer bonusSilver;
	private Integer bonusBronze;
	private Integer bonusBlue;
	private List<Integer> bonusesKing;
	private PoliticDeck politicDeck;

	public GameBoard(Settings settings) {
		
		// Fill the councillors hash map
		for (ColorCouncillor councillor : ColorCouncillor.values())
			availableCouncillors.put(councillor, settings.availableCouncillorsEachColor);

		// Set the number of assistants available in the game
		this.availableAssistants = settings.availableAssistants;
		
		// Build the politic card deck
		politicDeck = new PoliticDeck(settings.numColoredCards, settings.numJollyCards);

		// Create Regions and Cities reading from the settings file
		buildRegionsAndCities(settings);
		
		// Generate a King object
		City kingStartingCity = getCityByName(settings.kingStartingCityString);
		king = new King(generateRandomBalcony(settings.councillorsEachBalcony), kingStartingCity);

		// Get the victory point bonuses from settings
		bonusGold = settings.bonuses.get("bonusGold");
		bonusSilver = settings.bonuses.get("bonusSilver");
		bonusBronze = settings.bonuses.get("bonusBronze");
		bonusBlue = settings.bonuses.get("bonusBlue");
		bonusesKing = new ArrayList<>();
		bonusesKing.add(settings.bonuses.get("bonusKing1")); 
		bonusesKing.add(settings.bonuses.get("bonusKing2"));
		bonusesKing.add(settings.bonuses.get("bonusKing3"));
		bonusesKing.add(settings.bonuses.get("bonusKing4"));
		bonusesKing.add(settings.bonuses.get("bonusKing5"));
		
		// Get the tokens
		List<BonusList> tokens;
		tokens = getTokensFromSettings(settings);
		
		// Randomly assign a token to each city
		distributeTokens(tokens);
		
		// Get permit decks
		BusinessCardsRegion permitDeckCoast = new BusinessCardsRegion(getPermitDeckFromSettings(settings, RegionType.COAST));
		BusinessCardsRegion permitDeckHills = new BusinessCardsRegion(getPermitDeckFromSettings(settings, RegionType.HILLS));
		BusinessCardsRegion permitDeckMountains = new BusinessCardsRegion(getPermitDeckFromSettings(settings, RegionType.MOUNTAINS));
		
		// Give permit decks to each region
		getRegion(RegionType.COAST).setBusinessPermits(permitDeckCoast);
		getRegion(RegionType.HILLS).setBusinessPermits(permitDeckHills);
		getRegion(RegionType.MOUNTAINS).setBusinessPermits(permitDeckMountains);
		
		// Get bonuses for each region
		BonusVictoryPoint bonusCoast = new BonusVictoryPoint(settings.bonuses.get("bonusCoast").intValue());
		BonusVictoryPoint bonusHills = new BonusVictoryPoint(settings.bonuses.get("bonusHills").intValue());
		BonusVictoryPoint bonusMountains = new BonusVictoryPoint(settings.bonuses.get("bonusMountains").intValue());
		getRegion(RegionType.COAST).setBonusRegion(bonusCoast);
		getRegion(RegionType.HILLS).setBonusRegion(bonusHills);
		getRegion(RegionType.MOUNTAINS).setBonusRegion(bonusMountains);
		
		// Create a NobilityTrack object
		nobilityTrack = new NobilityTrack(this);

	}

	/*
	 * ------------------------- CONSTRUCTOR METHOD ---------------------------
	 */
	/*
	 */
	
	void buildRegionsAndCities(Settings settings) {
		// Build a region for each regionType, with random balconies
		regions = new ArrayList<>();
		for (RegionType regT : RegionType.values()) {
			regions.add(new Region(generateRandomBalcony(settings.councillorsEachBalcony),regT));
		}
		
		// Populate the "cities" array and the Regions
		cities = new ArrayList<>();
		for (String cityName : settings.map.keySet()) {

			String colorString = (String) settings.map.get(cityName).get("color");
			ColorCity cityColor = ColorCity.valueOf(colorString.toUpperCase());

			String regionString = (String) settings.map.get(cityName).get("region");
			RegionType type = RegionType.valueOf(regionString.toUpperCase());
			
			Region cityRegion = getRegion(type);
			
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
	}
	
	List<BonusList> getTokensFromSettings(Settings settings) {
		List<BonusList> tokens = new ArrayList<>();
		List<Map<String, Integer>> settingsTokens = settings.tokens;
		
		for (Map<String, Integer> tokenAsMap : settingsTokens) {
			BonusList token;
			List<Bonus> bonuses = new ArrayList<>();
			
			// TODO: rifare come una funzione estraiBonus(), codice riutilizzato in getPermitDeck
			for (Map.Entry<String, Integer> bonusEntry: tokenAsMap.entrySet()) {
				String bonusType = bonusEntry.getKey();
				int quantity = bonusEntry.getValue().intValue();
				Bonus bonus = newBonusFromString(bonusType, quantity);
				bonuses.add(bonus);
			}
			token = new BonusList(bonuses);
			tokens.add(token);
		}
		return tokens;
	}
	
	// TODO: non sono proprio sicuro che sia una best practice, specialmente se aggiungessimo nuovi bonus (ma anche no)
	Bonus newBonusFromString(String bonusType, int quantity) {
		Bonus bonus;
		
		switch(bonusType.toLowerCase()) {
		case "assistants":
			bonus = new BonusAssistant(quantity);
			break;
		case "coins":
			bonus = new BonusCoin(quantity);
			break;
		case "mainaction":
			bonus = new BonusMainAction(quantity);
			break;
		case "nobility":
			bonus = new BonusNobility(quantity);
			break;
		case "cards":
			bonus = new BonusPoliticCard(quantity);
			break;
		case "points":
			bonus = new BonusVictoryPoint(quantity);
			break;
		default:
			throw new RuntimeException("Bonus not recognized! Check your settings file.");
		}
		
		return bonus;
	}
	
	void distributeTokens(List<BonusList> tokens) {
		List<BonusList> tokensCopy = new ArrayList<>(tokens);
		Random generator = new Random();
		
		for (City city : cities) {
			System.out.println(city.getName() + " : " + city.getColor().toString());
			if (city.getColor() != ColorCity.PURPLE) {
				try {
					BonusList token = tokensCopy.get(generator.nextInt(tokensCopy.size()));
					city.setToken(token);
					tokensCopy.remove(token);
				} catch (IllegalArgumentException  e) {
					System.out.println("Not enough tokens! Check your settings file.");
					throw e;
				}
			}
		}
	}
	
	List<BusinessPermit> getPermitDeckFromSettings(Settings settings, RegionType regionType) {
		List<Map<String, Object>> settingsDeck;
		switch(regionType) {
		case COAST:
			settingsDeck = settings.permitDeckCoast;
			break;
		case HILLS:
			settingsDeck = settings.permitDeckHills;
			break;
		case MOUNTAINS:
			settingsDeck = settings.permitDeckMountains;
			break;
		default:
			throw new RuntimeException("Region type not recognised while loading permit decks.");
		}
		
		List<BusinessPermit> permitList = new ArrayList<>();
		for (Map<String, Object> settingsPermit : settingsDeck) {
			BusinessPermit permit;
			
			List<String> settingsCityList = (List<String>) settingsPermit.get("cities");
			List<City> cityList = new ArrayList<>();
			for (String cityName : settingsCityList) {
				cityList.add(getCityByName(cityName));
			}
			
			Map<String, Integer> bonusMap = (Map<String, Integer>) settingsPermit.get("bonus");
			List<Bonus> bonuses = new ArrayList<>();
			for (Map.Entry<String, Integer> bonusEntry: bonusMap.entrySet()) {
				String bonusType = bonusEntry.getKey();
				int quantity = bonusEntry.getValue().intValue();
				Bonus bonus = newBonusFromString(bonusType, quantity);
				bonuses.add(bonus);
			}
			BonusList bonusList = new BonusList(bonuses);
			
			permit = new BusinessPermit(cityList, bonusList);
			permitList.add(permit);
		}
		
		return permitList;
	}
	
	/*
	 * -------------------------- POLITIC CARD DECK ---------------------------
	 */
	
	public PoliticDeck getPoliticDeck() {
		return politicDeck;
	}

	public void setPoliticDeck(PoliticDeck politicDeck) {
		this.politicDeck = politicDeck;
	}

	/*
	 * ------------------------ AVAILABLE COUNCILLORS ------------------------
	 */

	public Integer getAvailableCouncillors(ColorCouncillor color) {
		return availableCouncillors.get(color);
	}
		
	// check if the chosen color is available
	public boolean councillorIsAvailable(ColorCouncillor color) {
		return getAvailableCouncillors(color) > 0;
	}

	public boolean useCouncillor(ColorCouncillor councillor) {
		if (councillorIsAvailable(councillor)) {
			availableCouncillors.put(councillor, availableCouncillors.get(councillor) - 1);
			return true;
		} else
			return false;
	}

	public void addDiscardedCouncillor(ColorCouncillor color){
		availableCouncillors.put(color, availableCouncillors.get(color)+1);
	}
	
	public ColorCouncillor getRandomAvailableCouncillor() {
		ColorCouncillor color;
		
		do {
			color = ColorCouncillor.getRandomCouncillor();
		} while (!councillorIsAvailable(color));
		
		return color;
	}
	
	private Queue<ColorCouncillor> generateRandomBalcony(int councillorsEachBalcony) {
		Queue<ColorCouncillor> tempBalcony = new PriorityQueue<>();
		
		for (int j = 0; j < councillorsEachBalcony; j++)
			tempBalcony.add(getRandomAvailableCouncillor());
		
		return tempBalcony;
	}

	/*
	 * ----------------------- AVAILABLE ASSISTANTS --------------------------
	 */

	public void setAvailableAssistants(int availableAssistants) {
		this.availableAssistants = availableAssistants;
	}

	public int getAvailableAssistants() {
		return availableAssistants;
	}

	public boolean useAssistants(int quantity) { // TODO: ma se li considerassimo
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

	public void addAssistants(int quantity) {
		availableAssistants += quantity;
	}
	
	/*
	 * -------------------------- REGIONS ---------------------------
	 */

	public Region getRegion(RegionType type) {

		for(Region regionInList: regions) {
			if(regionInList.getType() == type) {
				return regionInList;
			}
		}
		return null;
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

	public City getCityByName(String cityName) {
		for (City city : cities) {
			if (city.getName().equals(cityName)) {
				return city;
			}
		}
		throw new RuntimeException("City not found! Check your settings file?");
	}
	

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}

	@Override
	public String toString() {
		return "GameBoard [random=" + random + ", regions=" + regions
				+ ", king=" + king + ", cities=" + cities
				+ ", availableAssistants=" + availableAssistants
				+ ", nobilityTrack=" + nobilityTrack
				+ ", availableCouncillors=" + availableCouncillors
				+ ", bonusGold=" + bonusGold + ", bonusSilver=" + bonusSilver
				+ ", bonusBronze=" + bonusBronze + ", bonusBlue=" + bonusBlue
				+ ", bonusesKing=" + bonusesKing + ", politicDeck="
				+ politicDeck + "]";
	}
	
}
