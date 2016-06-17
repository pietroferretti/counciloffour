package it.polimi.ingsw.ps14.model;

import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	private static int counter=0;

	public enum Type {
		BUSINESS, POLITIC, ASSISTANT;
	}

	/**
	 * CARD (business or politic) for sale
	 * 
	 * @param item
	 * @param price
	 * @param owner
	 */
	public ItemForSale(Type type, int idORquantity, int price, Integer ownerID) {
		this.type = type;
		this.idORquantity = idORquantity;
		this.price = price;
		this.ownerID = ownerID;
		this.color = null;
		counter++;
		barCode=counter;
		
		
	}

	public ItemForSale(ColorPolitic color, int price, Integer ownerID) {
		this.type = Type.POLITIC;
		this.idORquantity = null;
		this.price = price;
		this.ownerID = ownerID;
		this.color = color;
		counter++;
		barCode=counter;
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

		Player player = id2player(ownerID, model);

		if (type.name().matches("BUSINESS")) {
			BusinessPermit busPer = id2permit(idORquantity, player);
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
	
	public boolean equals(ItemForSale obj){
		if(barCode==obj.barCode) return true;
		return false;
	}

	public void removeAssistant(int howMany){
		if(idORquantity!=null && howMany<=idORquantity){
			idORquantity=idORquantity-howMany;
		}
	}
	
	protected Player id2player(Integer id, Model model) {
		for (Player p : model.getPlayers())
			if (p.getId() == id)
				return p;
		return null;
	}

	protected BusinessPermit id2permit(Integer permitID, Player player) {
		for (BusinessPermit bp : player.getBusinessHand().getValidCards())
			if (bp.getId() == permitID)
				return bp;
		return null;
	}


}