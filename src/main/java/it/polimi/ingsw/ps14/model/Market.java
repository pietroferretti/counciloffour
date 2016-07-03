package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import it.polimi.ingsw.ps14.model.modelview.MarketView;

public class Market extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6466607003340777967L;
	private List<ItemForSale> objectsForSale;
	private int idCounter = 0;

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

	/**
	 * It only notifies to the {@link MarketView} that some assistants have been
	 * sold.
	 * 
	 * @param item
	 *            - object for sale({@link ItemForSale})
	 * @param howMany
	 *            - number of assistants sold (int)
	 */
	public void someAssistantsSold(ItemForSale item, int howMany) {
		setChanged();
		notifyObservers("Player" + Integer.toString(item.getOwnerID()) + " sold " + howMany + " assistants!");
	}

	/**
	 * It finds an {@link ItemForSale} object in the {@link Market}.
	 * 
	 * @param item
	 *            - searched {@link ItemForSale} object
	 * @return The searched item or null.
	 */
	public ItemForSale findObject(ItemForSale item) {
		for (ItemForSale object : objectsForSale)
			if (object.equals(item))
				return object;
		return null;
	}

	/**
	 * It finds an {@link ItemForSale} object in the {@link Market} given its
	 * ID.
	 * 
	 * @param item
	 *            - searched {@link ItemForSale} object
	 * @return The searched item or null.
	 */
	public ItemForSale id2itemForSale(int itemID) {
		for (ItemForSale object : objectsForSale)
			if (itemID == object.getBarCode())
				return object;
		return null;
	}

	/**
	 * It adds an item to market and notifies the {@link MarketView }.
	 * 
	 * @param item
	 *            - item for sale {@link ItemForSale}
	 */
	public void addItem(ItemForSale item) {
		item.setBarCode(idCounter);
		idCounter++;
		System.out.println("aggiungo "+item.toString());
		objectsForSale.add(item);
		setChanged();
		notifyObservers();
	}

	/**
	 * It removes an item from the market and notifies the {@link MarketView }.
	 * 
	 * @param item
	 *            - item for sale {@link ItemForSale}
	 */
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
