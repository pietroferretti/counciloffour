package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Market extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6466607003340777967L;
	private List<ItemForSale> objectsForSale;

	public Market() {
		objectsForSale = new ArrayList<>();
	}

	public Market(Market m) {
		objectsForSale = new ArrayList<>();
		for (ItemForSale itemForSale : m.objectsForSale) {
			objectsForSale.add(new ItemForSale(itemForSale));
		}
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
	public ItemForSale findObject(ItemForSale item) {
		for (ItemForSale object : objectsForSale)
			if (object.equals(item))
				return object;
		return null;
	}

	public ItemForSale id2itemForSale(Integer itemID) {
		for (ItemForSale object : objectsForSale)
			if (itemID == object.getBarCode())
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
	public void addItem(ItemForSale item) {
		objectsForSale.add(item);
	}

	public void removeItem(ItemForSale item) {
		ItemForSale itemToRemove = findObject(item);
		if (itemToRemove != null)
			objectsForSale.remove(itemToRemove);
	}
	
	public void clear() {
		objectsForSale = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Market [objectsForSale=" + objectsForSale + "]";
	}

}