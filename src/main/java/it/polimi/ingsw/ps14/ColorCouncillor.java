package it.polimi.ingsw.ps14;

import java.util.Random;

enum ColorCouncillor {

	PURPLE,

	PINK,

	WHITE,

	ORANGE,

	BLACK,

	BLUE;
	
	//not used:
	public static ColorCouncillor getRandomConcillor(Random random){
		ColorCouncillor[] values=ColorCouncillor.values();
		return values[random.nextInt(values.length)];
	}
	
	public final static int size=ColorCouncillor.values().length;

}