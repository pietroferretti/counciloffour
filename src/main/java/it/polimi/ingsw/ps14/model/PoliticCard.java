package it.polimi.ingsw.ps14.model;

public class PoliticCard implements Card {

	private final ColorPolitic color;
	private final Integer id;

	public PoliticCard(ColorPolitic color,Integer id) {
		this.color = color;
		this.id=id;
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

	
	
	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "PoliticCard [color=" + color + "]";
	}

}
