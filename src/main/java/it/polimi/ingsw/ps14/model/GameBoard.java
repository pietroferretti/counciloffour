package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.exceptions.InvalidSettingsException;
import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.bonus.BonusAssistant;
import it.polimi.ingsw.ps14.model.bonus.BonusCoin;
import it.polimi.ingsw.ps14.model.bonus.BonusFromBusinessPermits;
import it.polimi.ingsw.ps14.model.bonus.BonusFromTokens;
import it.polimi.ingsw.ps14.model.bonus.BonusList;
import it.polimi.ingsw.ps14.model.bonus.BonusMainAction;
import it.polimi.ingsw.ps14.model.bonus.BonusNobility;
import it.polimi.ingsw.ps14.model.bonus.BonusPoliticCard;
import it.polimi.ingsw.ps14.model.bonus.BonusTakeBusinessPermits;
import it.polimi.ingsw.ps14.model.bonus.BonusVictoryPoint;

public class GameBoard extends Observable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8154193006137131849L;

	private static final Logger LOGGER = Logger.getLogger(Settings.class.getName());

	Random random = new Random();

	private List<Region> regions;
	private King king;
	private List<City> cities;
	private int availableAssistants;
	private NobilityTrack nobilityTrack;
	// HashMap to store how many councillors there are for each color
	private EnumMap<ColorCouncillor, Integer> availableCouncillors = new EnumMap<>(ColorCouncillor.class);
	private int bonusGold;
	private int bonusSilver;
	private int bonusBronze;
	private int bonusBlue;
	private Queue<Integer> kingBonuses;
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
		bonusGold = settings.buildingBonuses.get("bonusGold");
		bonusSilver = settings.buildingBonuses.get("bonusSilver");
		bonusBronze = settings.buildingBonuses.get("bonusBronze");
		bonusBlue = settings.buildingBonuses.get("bonusBlue");
		kingBonuses = new PriorityQueue<>();
		kingBonuses.add(settings.buildingBonuses.get("bonusKing1"));
		kingBonuses.add(settings.buildingBonuses.get("bonusKing2"));
		kingBonuses.add(settings.buildingBonuses.get("bonusKing3"));
		kingBonuses.add(settings.buildingBonuses.get("bonusKing4"));
		kingBonuses.add(settings.buildingBonuses.get("bonusKing5"));

		// Get the tokens
		List<BonusList> tokens;
		tokens = getTokensFromSettings(settings);

		// Randomly assign a token to each city
		distributeTokens(tokens);

		// Get permit decks
		BusinessCardsRegion permitDeckCoast = new BusinessCardsRegion(
				getPermitDeckFromSettings(settings, RegionType.COAST));
		BusinessCardsRegion permitDeckHills = new BusinessCardsRegion(
				getPermitDeckFromSettings(settings, RegionType.HILLS));
		BusinessCardsRegion permitDeckMountains = new BusinessCardsRegion(
				getPermitDeckFromSettings(settings, RegionType.MOUNTAINS));

		// Give permit decks to each region
		getRegion(RegionType.COAST).setBusinessPermits(permitDeckCoast);
		getRegion(RegionType.HILLS).setBusinessPermits(permitDeckHills);
		getRegion(RegionType.MOUNTAINS).setBusinessPermits(permitDeckMountains);

		// Get bonuses for each region
		BonusVictoryPoint bonusCoast = new BonusVictoryPoint(settings.buildingBonuses.get("bonusCoast").intValue());
		BonusVictoryPoint bonusHills = new BonusVictoryPoint(settings.buildingBonuses.get("bonusHills").intValue());
		BonusVictoryPoint bonusMountains = new BonusVictoryPoint(
				settings.buildingBonuses.get("bonusMountains").intValue());
		getRegion(RegionType.COAST).setBonusRegion(bonusCoast);
		getRegion(RegionType.HILLS).setBonusRegion(bonusHills);
		getRegion(RegionType.MOUNTAINS).setBonusRegion(bonusMountains);

		// Create a NobilityTrack object
		Map<Integer, BonusList> nobilityTrackMap = buildNobilityTrack(settings.nobilityTrack);
		nobilityTrack = new NobilityTrack(nobilityTrackMap);

	}

	/*
	 * ------------------------- CONSTRUCTOR METHOD ---------------------------
	 */

	void buildRegionsAndCities(Settings settings) {
		// Build a region for each regionType, with random balconies
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

	BonusList buildBonusFromMap(Map<String, Integer> bonusAsMap) {
		List<Bonus> bonuses = new ArrayList<>();

		for (Map.Entry<String, Integer> bonusEntry : bonusAsMap.entrySet()) {
			String bonusType = bonusEntry.getKey();
			int quantity = bonusEntry.getValue().intValue();
			Bonus tempBonus = newBonusFromString(bonusType, quantity);
			bonuses.add(tempBonus);
		}

		return new BonusList(bonuses);
	}

	Bonus newBonusFromString(String bonusType, int quantity) {
		Bonus bonus;

		switch (bonusType.toLowerCase()) {
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
		case "fromtokens":
			bonus = new BonusFromTokens(quantity);
			break;
		case "frompermits":
			bonus = new BonusFromBusinessPermits(quantity);
			break;
		case "takepermits":
			bonus = new BonusTakeBusinessPermits(quantity);
			break;
		default:
			throw new InvalidSettingsException("Bonus not recognized! Check your settings file.");
		}

		return bonus;
	}

	Map<Integer, BonusList> buildNobilityTrack(Map<Integer, Map<String, Integer>> nobilityTrackSettings) {
		Map<Integer, BonusList> nobilityTrackMap = new HashMap<>();

		for (Map.Entry<Integer, Map<String, Integer>> levelEntry : nobilityTrackSettings.entrySet()) {
			Integer level = levelEntry.getKey();
			Map<String, Integer> bonusAsMap = levelEntry.getValue();
			BonusList bonusThisLevel = buildBonusFromMap(bonusAsMap);
			nobilityTrackMap.put(level, bonusThisLevel);
		}

		return nobilityTrackMap;
	}

	List<BonusList> getTokensFromSettings(Settings settings) {
		List<BonusList> tokens = new ArrayList<>();
		List<Map<String, Integer>> settingsTokens = settings.tokens;

		for (Map<String, Integer> tokenAsMap : settingsTokens) {
			tokens.add(buildBonusFromMap(tokenAsMap));
		}

		return tokens;
	}

	void distributeTokens(List<BonusList> tokens) {
		List<BonusList> tokensCopy = new ArrayList<>(tokens);
		Random generator = new Random();

		for (City city : cities) {
			if (city.getColor() != ColorCity.PURPLE) {
				try {
					BonusList token = tokensCopy.get(generator.nextInt(tokensCopy.size()));
					city.setToken(token);
					tokensCopy.remove(token);
				} catch (IllegalArgumentException e) {
					LOGGER.info("Not enough tokens! Check your settings file.");
					throw e;
				}
			}
		}
	}

	List<BusinessPermit> getPermitDeckFromSettings(Settings settings, RegionType regionType) {
		List<Map<String, Object>> settingsDeck;
		switch (regionType) {
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
			throw new InvalidSettingsException("Region type not recognised while loading permit decks.");
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
			BonusList bonusList = buildBonusFromMap(bonusMap);
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

	public Map<ColorCouncillor, Integer> getAvailableCouncillors() {
		return availableCouncillors;
	}

	// check if the chosen color is available
	public boolean councillorIsAvailable(ColorCouncillor color) {
		return getAvailableCouncillors(color) > 0;
	}

	public boolean useCouncillor(ColorCouncillor councillor) {
		if (councillorIsAvailable(councillor)) {
			availableCouncillors.put(councillor, availableCouncillors.get(councillor) - 1);
			setChanged();
			notifyObservers();
			return true;
		} else
			return false;
	}

	public void addDiscardedCouncillor(ColorCouncillor color) {
		availableCouncillors.put(color, availableCouncillors.get(color) + 1);
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
	}

	public int getAvailableAssistants() {
		return availableAssistants;
	}

	public boolean useAssistants(int quantity) {
		if (availableAssistants >= quantity) {
			availableAssistants = availableAssistants - quantity;
			setChanged();
			notifyObservers();
			return true;
		} else
			return false;
	}

	public boolean useAssistant() {
		return useAssistants(1);
	}

	public void addAssistants(int quantity) {
		availableAssistants += quantity;
		setChanged();
		notifyObservers();
	}

	/*
	 * -------------------------- REGIONS ---------------------------
	 */

	public Region getRegion(RegionType type) {

		for (Region regionInList : regions) {
			if (regionInList.getType() == type) {
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

	/*
	 * --------------------------- CITIES -----------------------------
	 */

	public City getCityByName(String cityName) {
		for (City city : cities) {
			if (city.getName().equals(cityName)) {
				return city;
			}
		}
		throw new InvalidSettingsException("City not found! Check your settings file?");
	}

	public List<City> getCities() {
		return cities;
	}

	public Queue<Integer> getKingBonuses() {
		return kingBonuses;
	}

	public NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}

	/*
	 * --------------------------- REGION BONUS -----------------------------
	 */

	public int getBonusGold() {
		return bonusGold;
	}

	public int getBonusSilver() {
		return bonusSilver;
	}

	public int getBonusBronze() {
		return bonusBronze;
	}

	public int getBonusBlue() {
		return bonusBlue;
	}

	public void useBonusGold() {
		bonusGold = 0;
		setChanged();
		notifyObservers();
	}

	public void useBonusSilver() {
		bonusSilver = 0;
		setChanged();
		notifyObservers();
	}

	public void useBonusBronze() {
		bonusBronze = 0;
		setChanged();
		notifyObservers();
	}

	public void useBonusBlue() {
		bonusBlue = 0;
		setChanged();
		notifyObservers();
	}

	@Override
	public String toString() {
		return "GameBoard [random=" + random + ", regions=" + regions + ", king=" + king + ", cities=" + cities
				+ ", availableAssistants=" + availableAssistants + ", nobilityTrack=" + nobilityTrack
				+ ", availableCouncillors=" + availableCouncillors + ", bonusGold=" + bonusGold + ", bonusSilver="
				+ bonusSilver + ", bonusBronze=" + bonusBronze + ", bonusBlue=" + bonusBlue + ", bonusesKing="
				+ kingBonuses + ", politicDeck=" + politicDeck + "]";
	}

}
