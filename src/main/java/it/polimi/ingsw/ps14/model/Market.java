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
	private static int idCounter=0;

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
	
	public void someAssistantsSold(ItemForSale item,int howMany){
		setChanged();
		notifyObservers("Player" + Integer.toString(item.getOwnerID()) + " sold "+howMany+" assistants!");
	}

	/**
	 * is it an object on sale?
	 * 
	 * @param item
	 * @return object on sale
	 */
	public ItemForSale findObject(ItemForSale item) {
		for (ItemForSale object : objectsForSale)
			if (object.equals(item))
				return object;
		return null;
	}

	public ItemForSale id2itemForSale(int itemID) {
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
		item.setBarCode(idCounter);
		idCounter++;
		objectsForSale.add(item);
		setChanged();
		notifyObservers();
	}

	public void removeItem(ItemForSale item) {
		ItemForSale itemToRemove = findObject(item);
		if (itemToRemove != null)
			objectsForSale.remove(itemToRemove);
		setChanged();
		notifyObservers("The item " + item.getType() + "with ID: " + Integer.toString(item.getBarCode()) + " of player "
				+ Integer.toString(item.getOwnerID()) + " has been sold!");
	}
	
	public boolean isEmpty() {
		return objectsForSale.isEmpty();
	}

	public void clear() {
		objectsForSale = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Market:\n" + objectsForSale + "\n";
	}

}