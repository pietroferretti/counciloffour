package it.polimi.ingsw.ps14.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import it.polimi.ingsw.ps14.exceptions.InvalidSettingsException;

public class Settings {
	private static final Logger LOGGER = Logger.getLogger(Settings.class.getName());
	
	private static final String SETTINGS_DIRECTORY = "src/main/resources/";
	private static final String SETTINGS_FILENAME = "settings.json";
	private static final String MAPS_DIRECTORY = "src/main/resources/maps/";

	public final int councillorsEachBalcony;
	public final int availableCouncillorsEachColor;
	public final int availableAssistants;
	public final int numColoredCards;
	public final int numJollyCards;
	public final Map<String, Integer> buildingBonuses;
	public final List<Map<String, Integer>> tokens;
	public final Map<Integer, Map<String, Integer>> nobilityTrack;
	public final Map<String, Map<String, Object>> map;
	public final String kingStartingCityString;
	public final List<Map<String, Object>> permitDeckCoast;
	public final List<Map<String, Object>> permitDeckHills;
	public final List<Map<String, Object>> permitDeckMountains;
	
	public final String mapName;
	public final int turnCountdown;

	public Settings() throws IOException {

		// Open the settings file
		try (BufferedReader settingsFile = new BufferedReader(new FileReader(SETTINGS_DIRECTORY + SETTINGS_FILENAME))){
			
			// Create some useful JSON parsers
			JSONTokener jsonFile = new JSONTokener(settingsFile);
			JSONObject jsonSettings = (JSONObject) jsonFile.nextValue();
	
			// Extract the first variables
			turnCountdown = jsonSettings.getInt("turntimer");
			councillorsEachBalcony = jsonSettings.getInt("balconyCouncillors");
			availableCouncillorsEachColor = jsonSettings.getInt("availableCouncillors");
			availableAssistants = jsonSettings.getInt("assistants");
			numColoredCards = jsonSettings.getInt("numColoredCards");
			numJollyCards = jsonSettings.getInt("numJollyCards");
	
			// Load the bonuses
			JSONObject jsonBonuses = jsonSettings.getJSONObject("bonuses");
			buildingBonuses = loadBonuses(jsonBonuses);
			
			// Load tokens
			JSONArray jsonTokens = jsonSettings.getJSONArray("tokens");
			tokens = loadTokens(jsonTokens);
	
			// Load nobility track
			JSONObject jsonNobilityTrack = jsonSettings.getJSONObject("nobilitytrack");
			nobilityTrack = loadNobilityTrack(jsonNobilityTrack);
	
			// Load the game map in a Map
			mapName = jsonSettings.getString("mapname");
			String mapFileName = MAPS_DIRECTORY + mapName + ".json";
			
			try (BufferedReader mapFile = new BufferedReader(new FileReader(mapFileName))){
				
				JSONTokener jsonMapFile = new JSONTokener(mapFile);
				JSONObject jsonMapFileObject = (JSONObject) jsonMapFile.nextValue();
				
				JSONObject jsonMap = jsonMapFileObject.getJSONObject("map");
				map = loadMap(jsonMap);
		
				// Find the starting king city, raise exception if not found
				kingStartingCityString = findStartingKingCity(map);
		
				// Load business permits decks
				JSONArray jsonPermitDeckCoast = jsonMapFileObject.getJSONArray("permitDeckCoast");
				permitDeckCoast = loadPermitDeck(jsonPermitDeckCoast);
		
				JSONArray jsonPermitDeckHills = jsonMapFileObject.getJSONArray("permitDeckHills");
				permitDeckHills = loadPermitDeck(jsonPermitDeckHills);
		
				JSONArray jsonPermitDeckMountains = jsonMapFileObject.getJSONArray("permitDeckMountains");
				permitDeckMountains = loadPermitDeck(jsonPermitDeckMountains);
				
			}
		
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Couldn'open settings file!", e);
			throw e;
		}
	}

	Map<String, Integer> loadBonuses(JSONObject jsonBonuses) {
		Map<String, Integer> bonuses = new HashMap<>();

		Iterator<?> bonusesKeys = jsonBonuses.keys();
		while (bonusesKeys.hasNext()) {
			String bonusName = (String) bonusesKeys.next();
			bonuses.put(bonusName, new Integer(jsonBonuses.getInt(bonusName)));
		}

		return bonuses;
	}

	Map<String, Map<String, Object>> loadMap(JSONObject jsonMap) {
		Map<String, Map<String, Object>> map = new HashMap<>();

		Iterator<?> mapKeys = jsonMap.keys();
		while (mapKeys.hasNext()) {
			String cityName = (String) mapKeys.next();
			JSONObject jsonCity = jsonMap.getJSONObject(cityName);

			Map<String, Object> cityInfo = new HashMap<>();

			cityInfo.put("region", jsonCity.getString("region")); // Get the
																	// city's
																	// region
																	// from the
																	// settings
																	// file
			cityInfo.put("color", jsonCity.get("color")); // Get the city's
															// color from the
															// settings file

			List<String> tempNeighbors = new ArrayList<>();
			JSONArray jsonNeighbors = jsonCity.getJSONArray("neighbors");
			for (int i = 0; i < jsonNeighbors.length(); i++) { // Get the city's
																// neighbors
																// from the
																// settings file
				tempNeighbors.add(jsonNeighbors.getString(i));
			}
			cityInfo.put("neighbors", tempNeighbors);

			map.put(cityName, cityInfo); // Add city to the map HashMap
		}
		return map;
	}

	String findStartingKingCity(Map<String, Map<String, Object>> map) {
		String kingCityString = null;

		for (Map.Entry<String, Map<String, Object>> cityEntry : map.entrySet()) {
			if (cityEntry.getValue().get("color").equals("purple")) {
				if (kingCityString == null) {
					kingCityString = cityEntry.getKey();
				} else {
					throw new InvalidSettingsException("More than one purple city in the settings file!");
				}
			}
		}

		if (kingCityString != null) {
			return kingCityString;
		} else {
			throw new InvalidSettingsException("No purple cities found in the settings file!");
		}
	}

	List<Map<String, Integer>> loadTokens(JSONArray jsonTokens) {
		List<Map<String, Integer>> tokens = new ArrayList<>();
		Map<String, Integer> token;
		JSONObject jsonToken;

		for (int i = 0; i < jsonTokens.length(); i++) {
			token = new HashMap<>();
			jsonToken = jsonTokens.getJSONObject(i);

			Iterator<?> tokenKeys = jsonToken.keys();
			while (tokenKeys.hasNext()) {
				String bonusType = (String) tokenKeys.next();
				token.put(bonusType, new Integer(jsonToken.getInt(bonusType)));
			}

			tokens.add(token);
		}

		return tokens;
	}

	Map<Integer, Map<String, Integer>> loadNobilityTrack(JSONObject jsonNobilityTrack) {
		Map<Integer, Map<String, Integer>> nobilityTrackMap = new HashMap<>();
		Map<String, Integer> bonusMap;
		JSONObject jsonBonus;

		Iterator<?> jsonNobilityTrackKeys = jsonNobilityTrack.keys();
		while (jsonNobilityTrackKeys.hasNext()) {
			bonusMap = new HashMap<>();
			String levelString = (String) jsonNobilityTrackKeys.next();
			jsonBonus = jsonNobilityTrack.getJSONObject(levelString);

			Iterator<?> jsonBonusKeys = jsonBonus.keys();
			while (jsonBonusKeys.hasNext()) {
				String bonusType = (String) jsonBonusKeys.next();
				bonusMap.put(bonusType, new Integer(jsonBonus.getInt(bonusType)));
			}

			try {
				nobilityTrackMap.put(new Integer(Integer.parseInt(levelString)), bonusMap);
			} catch (NumberFormatException e) {
				LOGGER.info("Error while parsing the Nobility Track in the settings file: '" + levelString
						+ "' isn't a number.");
				throw e;
			}
		}

		return nobilityTrackMap;
	}

	List<Map<String, Object>> loadPermitDeck(JSONArray jsonPermitDeck) {
		List<Map<String, Object>> permitDeck = new ArrayList<>();
		Map<String, Object> permitCard;
		JSONObject jsonPermitCard;

		for (int i = 0; i < jsonPermitDeck.length(); i++) {
			permitCard = new HashMap<>();
			jsonPermitCard = jsonPermitDeck.getJSONObject(i);

			permitCard.put("cities", loadCitiesFromPermit(jsonPermitCard));
			permitCard.put("bonus", loadBonusFromPermit(jsonPermitCard));
			permitDeck.add(permitCard);
		}

		return permitDeck;
	}

	List<String> loadCitiesFromPermit(JSONObject jsonPermitCard) {
		List<String> permitCities = new ArrayList<>();
		JSONArray jsonPermitCities = jsonPermitCard.getJSONArray("cities");

		for (int i = 0; i < jsonPermitCities.length(); i++) {
			permitCities.add(jsonPermitCities.getString(i));
		}

		return permitCities;
	}

	Map<String, Integer> loadBonusFromPermit(JSONObject jsonPermitCard) {
		Map<String, Integer> permitBonus = new HashMap<>();
		JSONObject jsonPermitBonus = jsonPermitCard.getJSONObject("bonus");

		Iterator<?> bonusKeys = jsonPermitBonus.keys();
		while (bonusKeys.hasNext()) {
			String bonusType = (String) bonusKeys.next();
			permitBonus.put(bonusType, new Integer(jsonPermitBonus.getInt(bonusType)));
		}

		return permitBonus;
	}
}