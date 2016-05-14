package it.polimi.ingsw.ps14;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class GameBoard {

	Random random = new Random();
	
	private Region[] region;
	
	private King king;

	private int availableAssistants;
	
	//hashmap to store how many councillors for that color there are
	private Map<ColorCouncillor, Integer> availableCouncillors = new HashMap<>();
	
	//start parameters
	private final int councillorsEachBalcony=4;

	
	

	public int getAvailableAssistants() {
		return availableAssistants;
	}

	public GameBoard(Settings settings) {
		// TODO: build game object

		// Fill the councillors hash map
		for (ColorCouncillor councillor : ColorCouncillor.values())
			availableCouncillors.put(councillor,settings.availableCouncillorsEachColor);
		
		//set how many assistants there are
		this.availableAssistants = settings.availableAssistants;

		//Build a region for each regionType and send parameter: RandomBalcony and RegionType
		//TODO: do it better! "region[regT.ordinal()] not so good"
		region = new Region[RegionType.values().length];
		for(RegionType regT : RegionType.values()){
			region[regT.ordinal()]=new Region(generateRandomBalcony(councillorsEachBalcony),regT);
		}
		
		king = new King(generateRandomBalcony(councillorsEachBalcony), settings.startCityKing);
		
		
		
	}

	/*
	 * ------------------------ COUNCILLOR ------------------------
	 */

	// check if is the chosen color available
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

	public void setAssistantsAvailable(int AssistantsAvailable) {
	}

}