package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Market implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6466607003340777967L;
	private List<ItemForSale> objectsForSale;
	
	public Market() {
		objectsForSale = new ArrayList<>();
	}

	public List<ItemForSale> getObjectsForSale() {
		return objectsForSale;
	}

	public ItemForSale getObject(ItemForSale item) {
		for (ItemForSale prod : objectsForSale)
			if (item.equals(prod))
				return prod;
		return null;
	}

	/**
	 * is it a selling object?
	 * 
	 * @param item
	 * @return the selling object
	 */
	private ItemForSale findObject(ItemForSale item) {
		for (ItemForSale object : objectsForSale)
			if (object.equals(item))
				return object;
		return null;
	}

	/**
	 * add item to market
	 * 
	 * @param item
	 *            item to sell
	 * @return true if the action is valid, return false if not
	 */
	public boolean addItem(ItemForSale item) {
		if (item.isValid()) {
			objectsForSale.add(item);
			return true;
		} else
			return false;
	}

	public void removeItem(ItemForSale item) {
		if (objectsForSale.contains(item))
			objectsForSale.remove(item);
	}

	@Override
	public String toString() {
		return "Market [objectsForSale=" + objectsForSale + "]";
	}

}