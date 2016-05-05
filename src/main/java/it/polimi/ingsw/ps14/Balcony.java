package it.polimi.ingsw.ps14;

import java.util.PriorityQueue;

public class Balcony {

	public static int availableCouncillors[];	// Should this go in GameBoard??

	private PriorityQueue<ColorCouncillor> councillors;

	public Balcony() {
		// TODO: get 4 random colors chosen from ColorCouncillor
	}

	public void electCouncillor(ColorCouncillor color) {
		councillors.poll();
		councillors.add(color);
	}

	public PriorityQueue<ColorCouncillor> readBalcony() {
		return councillors;
	}

}