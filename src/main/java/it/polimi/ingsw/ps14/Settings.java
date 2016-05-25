package it.polimi.ingsw.ps14;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Settings {

	//TODO: Caricare i mazzi
	
	public final int councillorsEachBalcony;
	
	public final int availableCouncillorsEachColor;

	public final int availableAssistants;
	
	public final Map<String, Integer> bonuses;
	
	public final Map<String, Map<String, Object>> map;
	
	public final String kingStartingCityString;
	
	public final List<Map<String, Integer>> tokens;

	public Settings(String filename) throws IOException {
		 
		// Open the settings file
		BufferedReader settingsFile;
		try {
			settingsFile = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Couldn't open settings file on path '" + filename + "'");
		}

		// Create some useful JSON parsers
		JSONTokener jsonFile = new JSONTokener(settingsFile);
		JSONObject jsonSettings = (JSONObject) jsonFile.nextValue();
		
		// Extract the first variables
		councillorsEachBalcony = jsonSettings.getInt("balconyCouncillors");
		availableCouncillorsEachColor = jsonSettings.getInt("availableCouncillors");
		availableAssistants = jsonSettings.getInt("assistants");
		
		// Load the bonuses
		JSONObject jsonBonuses = jsonSettings.getJSONObject("bonuses");
		bonuses = loadBonuses(jsonBonuses);
		
		// Load the game map in a Map
		JSONObject jsonMap = jsonSettings.getJSONObject("map");
		map = loadMap(jsonMap);
		
		// Find the starting king city, raise exception if not found
		kingStartingCityString = findStartingKingCity(map);
		
		// Load tokens
		JSONArray jsonTokens = jsonSettings.getJSONArray("tokens");
		tokens = loadTokens(jsonTokens);
		
		// Close the settings file
		try {
			settingsFile.close();
		} catch (IOException e) {
			throw new IOException("Couldn't close settings file");
		}
	}
	
	Map<String, Integer> loadBonuses(JSONObject jsonBonuses){
		Map <String, Integer> bonuses = new HashMap<>();

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
			
			Map<String, Object> cityInfo = new HashMap<String, Object>();
			
			cityInfo.put("region", jsonCity.getString("region"));	// Get the city's region from the settings file
			cityInfo.put("color", jsonCity.get("color"));			// Get the city's color from the settings file
			
			List<String> tempNeighbors = new ArrayList<>();
			JSONArray jsonNeighbors = jsonCity.getJSONArray("neighbors");
			for (int i=0; i<jsonNeighbors.length(); i++) {			// Get the city's neighbors from the settings file
				tempNeighbors.add(jsonNeighbors.getString(i));		
			}
			cityInfo.put("neighbors", tempNeighbors);
			
			map.put(cityName, cityInfo);		// Add city to the map HashMap 
		}
		return map;
	}

	String findStartingKingCity(Map<String, Map<String, Object>> map) {
		String kingCityString = null;
		
		for (Map.Entry<String, Map<String, Object>> cityEntry : map.entrySet())
		{
			if (cityEntry.getValue().get("color").equals("purple")) {
				if (kingCityString == null) {
					kingCityString = cityEntry.getKey();
				} else {
					throw new RuntimeException("More than one purple city in the settings file!");
				}
			}
		}

		if (kingCityString != null) {
			return kingCityString;
		} else {
			throw new RuntimeException("No purple cities found in the settings file!");
		}
	}
	
	List<Map<String, Integer>> loadTokens(JSONArray jsonTokens) {
		List<Map<String, Integer>> tokens = new ArrayList<>();
		
		Map<String, Integer> tmpToken;
		JSONObject tmpJsonToken;
		
		for (int i=0; i<jsonTokens.length(); i++) {	
			tmpToken = new HashMap<>();
			tmpJsonToken = jsonTokens.getJSONObject(i);
			
			Iterator<?> tokenKeys = tmpJsonToken.keys();
			while (tokenKeys.hasNext()) {
				String bonusName = (String) tokenKeys.next();
				tmpToken.put(bonusName, new Integer(tmpJsonToken.getInt(bonusName)));
			}
			
			tokens.add(tmpToken);		
		}
		
		return tokens;
	}
}