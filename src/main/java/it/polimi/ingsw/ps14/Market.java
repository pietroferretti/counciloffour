package it.polimi.ingsw.ps14;

import java.util.ArrayList;

/*
 */
public class Market {

	private ArrayList<ItemForSale> objectsForSale;

	public boolean sell(ItemForSale item) {
		return false;
	}

	public void addItem(ItemForSale item) {
		objectsForSale.add(item);
	}

//	public ItemForSale nextItem() {
//		return null; 
//	}

}