package it.polimi.ingsw.ps14.model;

import java.io.Serializable;

public class PoliticCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4425604938548305334L;
	private final ColorPolitic color;

	public PoliticCard(ColorPolitic color) {
		this.color = color;
	}
	
	public PoliticCard(PoliticCard pc){
		this.color=pc.color;
	}

	public ColorPolitic getColor() {
		return color;
	}

	public boolean isJolly() {
		if (color.equals(ColorPolitic.JOLLY))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "\nPoliticCard [color=" + color + "]";
	}

}
