package it.polimi.ingsw.ps14;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Settings {

	public final int councillorsEachBalcony;
	
	public final int availableCouncillorsEachColor;

	public final int availableAssistants;
	
	public final Map<String, Map<String, Object>> map;
	
	public final String startCityKing;

	public Settings(String filename) throws IOException {
		BufferedReader settingsFile;
		try {
			settingsFile = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Couldn't open settings file on path '" + filename + "'");
		}

		JSONTokener jsonFile = new JSONTokener(settingsFile);
		JSONObject jsonSettings = (JSONObject) jsonFile.nextValue();
		
		councillorsEachBalcony = jsonSettings.getInt("councillorsEachBalcony");
		availableCouncillorsEachColor = jsonSettings.getInt("councillors");
		availableAssistants = jsonSettings.getInt("assistants");
		
		map = new HashMap<>();
		JSONObject jsonMap = jsonSettings.getJSONObject("map");
		
		String tempCityKing = null;
		while (jsonMap.keys().hasNext()) {
			String cityName = jsonMap.keys().next();
			
			Map<String, Object> cityInfo = new HashMap<String, Object>();
			
			cityInfo.put("region", jsonMap.getString("region"));	// Get the city's region from the settings file
			cityInfo.put("color", jsonMap.get("color"));			// Get the city's color from the settings file
			if (cityInfo.get("color") == "purple") {
				if (tempCityKing == null) {
					tempCityKing = cityName;
				} else {
					throw new RuntimeException("More than one purple city in the settings file!");
				}
			}
			
			List<String> tempNeighbors = new ArrayList<>();
			JSONArray jsonNeighbors = jsonMap.getJSONArray("neighbors");
			for (int i=0; i<jsonNeighbors.length(); i++) {			// Get the city's neighbors from the settings file
				tempNeighbors.add(jsonNeighbors.getString(i));		// as a list
			}
			cityInfo.put("neighbors", tempNeighbors);
			
			map.put(cityName, cityInfo);		// Add city to the map HashMap ('key' is the name of the city)	
		}
		// map now contains a structure like the json file (-> {"cityname1" : *cityInfo1*, ...})
		
		if (tempCityKing != null) {
			startCityKing = tempCityKing;
		} else {
			throw new RuntimeException("No purple cities in the settings file!");
		}

		try {
			settingsFile.close();
		} catch (IOException e) {
			throw new IOException("Couldn't close settings file");
		}
	}

}