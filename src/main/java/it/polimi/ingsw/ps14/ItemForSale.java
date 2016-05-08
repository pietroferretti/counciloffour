package it.polimi.ingsw.ps14;


public class ItemForSale {

	private final Object item;
	private final int price;
	private final Player owner;

	public ItemForSale(Object item, int price, Player owner) {
		this.item = item;
		this.price = price;
		this.owner = owner;
	}
	
	public int getPrice() {
		return price;
	}

	public Object getItem() {
		return item;
	}

	public Player getOwner() {
		return owner;
	}

}