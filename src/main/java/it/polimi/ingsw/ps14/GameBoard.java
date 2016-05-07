package it.polimi.ingsw.ps14;

import java.util.Random;

public class GameBoard {

	private Region[] region;

	private int assistantsAvailable;

	private int[] availableCouncillor = new int[ColorCouncillor.size];

	public int getAssistantsAvailable() {
		return 0;
	}

	public GameBoard(int availableCouncillorsEachColor, int councillorsEachBalcony,int assistantAvailable,int numberRegion) {
		// TODO: build game object

		// Example of availableCouncillor
		for (int i = 0; i < ColorCouncillor.size; i++)
			availableCouncillor[i] = availableCouncillorsEachColor;
		
		this.assistantsAvailable=assistantAvailable;

//		//constructor region and 
//		region=new Region[numberRegion];
//		for(int i=0;i<numberRegion;i++){
//			region[i]=new Region(generateRandomBalcony(councillorsEachBalcony));
//		}
//		
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