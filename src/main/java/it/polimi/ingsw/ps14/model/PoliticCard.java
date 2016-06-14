package it.polimi.ingsw.ps14.model;

public class PoliticCard implements Card {

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
		return "PoliticCard [color=" + color + "]";
	}

}