package it.polimi.ingsw.ps14.model;

import java.io.Serializable;

public class ItemForSale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6925492154183921807L;
	/**
	 * This class represents one item for sale. It can be a card (business or
	 * politic) or a set of assistants. If it is a card, assistants is null. If
	 * it is a set of assistants, item is null.
	 * 
	 * IF ITEM AND ASSISTANTS ARE null, THE ITEM HAS BEEN SOLD!
	 * 
	 * 
	 */

	private Integer idORquantity;
	private ColorPolitic color;
	private int price;
	private Integer ownerID;
	private Type type;
	private int barCode;

	public enum Type {
		BUSINESS, POLITIC, ASSISTANT;
	}

	public ItemForSale(Type type, int idORquantity, int price, Integer ownerID) {
		this.type = type;
		this.idORquantity = idORquantity;
		this.price = price;
		this.ownerID = ownerID;
		this.color = null;
	}

	public ItemForSale(ColorPolitic color, int price, Integer ownerID) {
		this.type = Type.POLITIC;
		this.idORquantity = null;
		this.price = price;
		this.ownerID = ownerID;
		this.color = color;
	}

	public ItemForSale(ItemForSale i) {
		this.idORquantity = i.idORquantity;
		this.color = i.color;
		this.price = i.price;
		this.ownerID = i.ownerID;
		this.type = i.type;
		this.barCode = i.barCode;
	}

	protected void setBarCode(int code) {
		this.barCode = code;
	}

	public int getPrice() {
		return price;
	}

	public Type getType() {
		return type;
	}

	/**
	 * check if owner has that objects according to its type
	 * 
	 * @return
	 */
	public boolean isValid(Model model) {

		Player player = model.id2player(ownerID);

		if (type.name().matches("BUSINESS")) {
			BusinessPermit busPer = model.id2permit(idORquantity, player);
			if (player == null || busPer == null)
				return false;
		}
		if (type.name().matches("ASSISTANT")) {
			if (player == null)
				return false;
			if (player.getAssistants() < idORquantity)
				return false;
		}
		if (type.name().matches("POLITIC")) {
			if (player == null)
				return false;
			if (!player.hasCardInHand(color))
				return false;
		}
		return true;
	}

	public Integer getIdORquantity() {
		return idORquantity;
	}

	public ColorPolitic getColor() {
		return color;
	}

	public Integer getOwnerID() {
		return ownerID;
	}

	public Integer getBarCode() {
		return barCode;
	}

	/**
	 * It remove a specific number of assistants from an ASSISTANTS type item.
	 * 
	 * @param howMany
	 *            - number of assistants we want to remove
	 * @return It returns true if it was possible to remove the choosen number
	 *         if assistants, false otherwise.
	 */
	public boolean removeAssistant(int howMany) {
		if (type == Type.ASSISTANT && idORquantity != null && howMany <= idORquantity) {
			idORquantity = idORquantity - howMany;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String sbarcode = "\nBAR_CODE= ";
		String sprice = ", price= ";
		String sowner = ", ownerID= ";
		if (type.name().matches("BUSINESS") || type.name().matches("ASSISTANT")) {
			return sbarcode + barCode + ": " + type + ", idORquantity= " + idORquantity + sprice + price + sowner
					+ ownerID;
		}
		return sbarcode + barCode + ": " + type + ", color= " + color + sprice + price + sowner + ownerID;

	}
}