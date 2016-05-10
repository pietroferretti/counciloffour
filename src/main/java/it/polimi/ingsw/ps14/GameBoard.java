package it.polimi.ingsw.ps14;

import java.util.Random;

public class GameBoard {

	private Region[] region;
	
	private King king;

	private int availableAssistants;
	
	//table to store how many councillors for that color there are
	private int[] availableCouncillor = new int[ColorCouncillor.size]; 
	
	//start parameters
	private final int numberRegion=3;
	private final int councillorsEachBalcony=4;

	
	

	public int getAvailableAssistants() {
		return availableAssistants;
	}

	public GameBoard(Settings settings) {
		// TODO: build game object

		// Fill the councillors table
		for (int i = 0; i < ColorCouncillor.size; i++)
			availableCouncillor[i] = settings.availableCouncillorsEachColor;
		
		//set how many assistants there are
		this.availableAssistants = settings.availableAssistants;

		//Build #numberRegion region and send parameter: RandomBalcony and RegionType
		//TODO: do it better!
		region=new Region[numberRegion];
		for(int i=0;i<numberRegion;i++){
			region[i]=new Region(generateRandomBalcony(councillorsEachBalcony),RegionType.values()[i]);
		}
		
		king = new King(generateRandomBalcony(councillorsEachBalcony), settings.startCityKing);
		
		
		
	}

	/*
	 * ------------------------ COUNCILLOR ------------------------
	 */

	// check if is the chosen color available
	public boolean councillorIsAvailable(int pick) {
		if (availableCouncillor[pick] > 0)
			return true;
		else
			return false;
	}

	private ColorCouncillor[] generateRandomBalcony(int councillorsEachBalcony){
		ColorCouncillor[] tempBalcony= new ColorCouncillor[councillorsEachBalcony];
		for(int j=0;j<councillorsEachBalcony;j++)
			tempBalcony[j]=getRandomAvailableCouncillor();
		return tempBalcony;
	
	}

	public boolean getCouncillor(ColorCouncillor councillor) {
		if (councillorIsAvailable(councillor.ordinal())) {
			availableCouncillor[councillor.ordinal()]--;
			return true;
		} else
			return false;

	}

	// For first population of balconies
	public ColorCouncillor getRandomAvailableCouncillor() {
		int pick = new Random().nextInt(ColorCouncillor.size);
		while (!getCouncillor(ColorCouncillor.values()[pick]))
			pick++;
		return ColorCouncillor.values()[pick];
	}
	/*
	 * ----------------------- ASSISTANTS --------------------------
	 */

	public void setAssistantsAvailable(int AssistantsAvailable) {
	}

}