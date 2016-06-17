package it.polimi.ingsw.ps14.model.actions.market;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;

import java.io.Serializable;

public class BuyAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8768443626805335203L;
	private Integer objBarCode;
	private Integer buyerID;
	private Integer assistantToBuy;

	public BuyAction(Integer barCode, Integer buyerID) {
		this.objBarCode = barCode;
		this.buyerID = buyerID;
		assistantToBuy = null;
	}

	public BuyAction(Integer barCode, Integer buyerID, Integer assistantToBuy) {
		this.buyerID = buyerID;
		this.objBarCode = barCode;
		this.assistantToBuy = assistantToBuy;
	}

	/**
	 * if assistant2buy is null, I'm buying the whole item. Otherwise Im buying only some of the assistants
	 * 
	 * 
	 * @return
	 */
	public boolean isValid(Model model, Market market) {
		Player buyer = id2player(buyerID, model);
		ItemForSale obj = market.id2itemForSale(objBarCode);
		if (obj == null)
			return false;

		if (assistantToBuy == null) {
			// im buying whole item

			if (obj.getType().equals(ItemForSale.Type.BUSINESS)) {
				if (obj.getIdORquantity() == null)
					return false;
				if (buyer.getCoins() < obj.getPrice())
					return false;
			}

			if (obj.getType().equals(ItemForSale.Type.ASSISTANT)) {
				if (obj.getIdORquantity() == null)
					return false;
				if (buyer.getCoins() < obj.getPrice() * obj.getIdORquantity())
					return false;
			}

			if (obj.getType().equals(ItemForSale.Type.POLITIC)) {
				if (obj.getColor() == null)
					return false;
				if (buyer.getCoins() < obj.getPrice())
					return false;
			}

		} else {
			// im buying SOME assistants of the item

			if (!obj.getType().equals(ItemForSale.Type.ASSISTANT))
				return false;

			if (obj.getIdORquantity() == null)
				return false;
			if (obj.getIdORquantity() < assistantToBuy)
				return false;

			if (buyer.getCoins() < obj.getPrice() * assistantToBuy)
				return false;

		}
		return true;
	}

	/**
	 * BUY  action
	 * 
	 * @param buyer
	 * @param assistantToBuy
	 *            how many assistants do you want to buy
	 */
	public void execute(Model model, Market market) {
		ItemForSale item = market.id2itemForSale(objBarCode);
		Player owner = id2player(item.getOwnerID(), model);
		Player buyer = id2player(buyerID, model);

		if (owner != null && buyer != null && item != null) {

			if (assistantToBuy == null) {
				// im buying whole item

				buyer.useCoins(item.getPrice());
				owner.addCoins(item.getPrice());

				if (item.getType().equals(ItemForSale.Type.BUSINESS)) {
					BusinessPermit busPer = id2permit(item.getIdORquantity(),
							owner);
					owner.getBusinessHand().sellPermits(busPer);
					buyer.getBusinessHand().acquireBusinessPermit(busPer);
				}
				if (item.getType().equals(ItemForSale.Type.ASSISTANT)) {
					buyer.useCoins(item.getPrice() * item.getIdORquantity());
					buyer.addAssistants(item.getIdORquantity());
					owner.addCoins(item.getPrice() * item.getIdORquantity());
					owner.useAssistants(item.getIdORquantity());
				}

				if (item.getType().equals(ItemForSale.Type.POLITIC)) {
					owner.removeColor(item.getColor());
					buyer.addPolitic(new PoliticCard(item.getColor()));
				}

				market.removeItem(item);
			}
		} else {
			// im buying SOME assistants of the item
			if (item.getType().equals(ItemForSale.Type.ASSISTANT)) {
				buyer.useCoins(item.getPrice() * assistantToBuy);
				buyer.addAssistants(assistantToBuy);
				owner.addCoins(item.getPrice() * assistantToBuy);
				owner.useAssistants(assistantToBuy);
				item.removeAssistant(assistantToBuy);

				if (item.getIdORquantity() == 0)
					market.removeItem(item);
			}

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
