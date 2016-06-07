package it.polimi.ingsw.ps14.model;

public class ItemForSale {

	/**
	 * This class represents one item for sale. It can be a card (business or
	 * politic) or a set of assistants. If it is a card, assistants is null. If
	 * it is a set of assistants, item is null.
	 * 
	 * IF ITEM AND ASSISTANTS ARE null, THE ITEM HAS BEEN SOLD!
	 * 
	 * 
	 */

	private Card item;
	private Integer assistants;
	private final int price;
	private final Player owner;

	/**
	 * CARD (business or politic) for sale
	 * 
	 * @param item
	 * @param price
	 * @param owner
	 */
	public ItemForSale(Card item, int price, Player owner) {
		this.item = item;
		this.price = price;
		this.owner = owner;
		this.assistants = null;
	}

	/**
	 * ASSISTANTS for sale
	 * 
	 * @param assistant
	 *            how many assistant to sell
	 * @param priceForEachAssistant
	 *            price for each assistants
	 * @param player
	 */
	public ItemForSale(Integer assistant, int priceForEachAssistant,
			Player owner) {
		this.assistants = assistant;
		this.price = priceForEachAssistant;
		this.owner = owner;
		this.item = null;
	}

	public int getPrice() {
		return price;
	}

	/**
	 * check if owner has that objects according to its type
	 * 
	 * @return
	 */
	public boolean isValid() {

		if (item != null) {
			if (item instanceof BusinessPermit)
				if (!owner.getBusinessHand().checkBusinessPermit(
						(BusinessPermit) item))
					return false;

			if (item instanceof PoliticCard)
				if (!owner.getHand().contains((PoliticCard) item))
					return false;
		}
		if (assistants != null)
			if (assistants > owner.getAssistants())
				return false;

		return true;
	}

	

	public Object getItem() {
		return item;
	}
	


	public Player getOwner() {
		return owner;
	}

	public Integer getAssistants() {
		return assistants;
	}

	public void setAssistants(Integer assistants) {
		this.assistants = assistants;
	}

	public void setItem(Card item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "ItemForSale [item=" + item + ", price=" + price + ", owner="
				+ owner + "]";
	}

}