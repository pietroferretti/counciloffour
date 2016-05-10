package it.polimi.ingsw.ps14;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Settings {

	final Map completeSettings;
	
	final ArrayList<City> cities;

	final int availableCouncillorsEachColor;

	final int availableAssistants;

	final City startCityKing;

	public Settings(String filename) throws IOException {
		BufferedReader settingsFile;
		try {
			settingsFile = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Couldn't open settings file on path '" + filename + "'");
		}

		JSONTokener jsonFile = new JSONTokener(settingsFile);
		JSONObject jsonMap = (JSONObject) jsonFile.nextValue();
		availableAssistants = jsonMap.getInt("assistants");
		availableCouncillorsEachColor = jsonMap.getInt("councillors");
		
		cities = null;
		startCityKing = null;
		completeSettings = null;
	
		try {
			settingsFile.close();
		} catch (IOException e) {
			throw new IOException("Couldn't close settings file");
		}
	}

}