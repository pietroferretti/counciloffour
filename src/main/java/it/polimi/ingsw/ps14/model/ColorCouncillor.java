package it.polimi.ingsw.ps14.model;

import java.util.Random;

public enum ColorCouncillor {

	PURPLE,

	PINK,

	WHITE,

	ORANGE,

	BLACK,

	BLUE;
	
	private static final Random random = new Random();
	
	public static ColorCouncillor getRandomCouncillor(){
		return values()[random.nextInt(values().length)];
	}
}