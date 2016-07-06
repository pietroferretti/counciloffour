package it.polimi.ingsw.ps14.model.actions.market;

import java.io.Serializable;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;

public class BuyAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8768443626805335203L;
	private Integer objBarCode;
	private Integer buyerID;
	private Integer assistantToBuy;

	public BuyAction(Integer buyerID, Integer barCode, Integer assistantToBuy) {
		this.buyerID = buyerID;
		this.objBarCode = barCode;
		this.assistantToBuy = assistantToBuy;
	}

	/**
	 * if assistant2buy is null, I'm buying the whole item. Otherwise Im buying
	 * only some of the assistants
	 * 
	 * 
	 * @return
	 */
	public boolean isValid(Model model) {
		Market market = model.getMarket();
		Player buyer = model.id2player(buyerID);
		ItemForSale obj = market.id2itemForSale(objBarCode);
		if (obj == null)
			return false;

		if (assistantToBuy == null) {
			// im buying whole item

			if (obj.getType().equals(ItemForSale.ItemForSaleType.BUSINESS)) {
				if (obj.getIdORquantity() == null)
					return false;
				if (buyer.getCoins() < obj.getPrice())
					return false;
			}

			if (obj.getType().equals(ItemForSale.ItemForSaleType.ASSISTANT)) {
				if (obj.getIdORquantity() == null)
					return false;
				if (buyer.getCoins() < obj.getPrice() * obj.getIdORquantity())
					return false;
			}

			if (obj.getType().equals(ItemForSale.ItemForSaleType.POLITIC)) {
				if (obj.getColor() == null)
					return false;
				if (buyer.getCoins() < obj.getPrice())
					return false;
			}

		} else {
			// im buying SOME assistants of the item

			if (!obj.getType().equals(ItemForSale.ItemForSaleType.ASSISTANT))
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
	 * BUY action
	 * 
	 * @param buyer
	 * @param assistantToBuy
	 *            how many assistants do you want to buy
	 */
	public void execute(Model model) {
		Market market = model.getMarket();
		ItemForSale item = market.id2itemForSale(objBarCode);
		Player owner = model.id2player(item.getOwnerID());
		Player buyer = model.id2player(buyerID);

		if (owner != null && buyer != null && item != null) {

			if (assistantToBuy == null) {
				// im buying whole item

				buyer.useCoins(item.getPrice());
				owner.addCoins(item.getPrice());

				if (item.getType().equals(ItemForSale.ItemForSaleType.BUSINESS)) {
					BusinessPermit busPer = model.id2permit(
							item.getIdORquantity(), owner);
					owner.sellPermits(busPer);
					buyer.acquireBusinessPermit(busPer);
				}
				if (item.getType().equals(ItemForSale.ItemForSaleType.ASSISTANT)) {
					buyer.addAssistants(item.getIdORquantity());
					owner.useAssistants(item.getIdORquantity());
				}

				if (item.getType().equals(ItemForSale.ItemForSaleType.POLITIC)) {
					owner.removeColor(item.getColor());
					buyer.addPolitic(new PoliticCard(item.getColor()));
				}

				market.removeItem(item);
			}
		 else {
			// im buying SOME assistants of the item
			if (item.getType().equals(ItemForSale.ItemForSaleType.ASSISTANT)) {
				buyer.useCoins(item.getPrice() * assistantToBuy);
				buyer.addAssistants(assistantToBuy);
				
				owner.addCoins(item.getPrice() * assistantToBuy);
				owner.useAssistants(assistantToBuy);
				
				item.removeAssistant(assistantToBuy);
				market.someAssistantsSold(item, assistantToBuy);
				
				if (item.getIdORquantity() == 0)
					market.removeItem(item);
			}
		 }

		}

	}

}
