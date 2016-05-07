package it.polimi.ingsw.ps14;

import java.util.Collection;
import java.util.PriorityQueue;

public class Balcony {

	private PriorityQueue<ColorCouncillor> councillors;
	
	public Balcony(ColorCouncillor[] initialCouncillors) {
		for(int i=0;i<initialCouncillors.length;i++)
			councillors.add(initialCouncillors[i]);
	}

	public void electCouncillor(ColorCouncillor color) {
		councillors.poll();
		councillors.add(color);
	}

	public PriorityQueue<ColorCouncillor> readBalcony() {
		return councillors;
	}

}