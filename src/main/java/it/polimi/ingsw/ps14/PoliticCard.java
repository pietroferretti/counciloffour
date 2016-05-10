package it.polimi.ingsw.ps14;

public class PoliticCard implements Card {

	private final ColorPolitic color;

	public PoliticCard(ColorPolitic color) {
		this.color = color;
	}

	public ColorPolitic getColor() {
		return color;
	}

	public boolean isJolly(PoliticCard card) {
		if (card.color.equals(ColorPolitic.JOLLY))
			return true;
		return false;
	}

}