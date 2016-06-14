package it.polimi.ingsw.ps14.model.actions.market;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;

public class BuyAction {

	private Market market;
	private ItemForSale obj;
	private Player buyer;
	private Integer assistantToBuy;

	public BuyAction(Market market, ItemForSale obj, Player buyer) {
		this.market = market;
		this.obj = obj;
		this.buyer = buyer;
		assistantToBuy = null;
	}

	public BuyAction(Market market, ItemForSale obj, Player buyer,
			Integer assistantToBuy) {
		this.market = market;
		this.obj = obj;
		this.buyer = buyer;
		this.assistantToBuy = assistantToBuy;
	}

	/**
	 * if assistant2buy is null, I'm buying a item. if item is null, I'm buying
	 * one or more assistant
	 * 
	 * @return
	 */
	public boolean isValid() {
		if (!market.getObjectsForSale().contains(obj))
			return false;

		if (assistantToBuy == null) {
			// im buying whole item

			if (obj.getItem() != null) {
				// im buying a card

				if (buyer.getCoins() < obj.getPrice())
					return false;

			} else {
				// im buying all assistants of the item

				if (obj.getItem() != null)
					return false;

				if (buyer.getCoins() < obj.getPrice() * obj.getAssistants())
					return false;
			}
		} else if (assistantToBuy != null) {
			// im buying SOME asistant of the item

			if (obj.getItem() != null)
				return false;

			if (buyer.getCoins() < obj.getPrice() * assistantToBuy)
				return false;

		}
		return true;
	}

	/**
	 * BUY only some of selling assistants
	 * 
	 * @param buyer
	 * @param assistantToBuy
	 *            how many assistants do you want to buy
	 */
	public void buy() {
		ItemForSale item = market.getObject(obj);
		if (item != null) {

			if (assistantToBuy == null) {
				// im buying whole item

				if (item.getItem() != null) {
					// im buying a card

					buyer.useCoins(item.getPrice());
					item.getOwner().addCoins(item.getPrice());

					if (item.getItem() instanceof BusinessPermit) {
						item.getOwner().getBusinessHand()
								.sellPermits((BusinessPermit) item.getItem());
						buyer.getBusinessHand().acquireBusinessPermit(
								(BusinessPermit) item.getItem());
					}

					if (item.getItem() instanceof PoliticCard) {
						item.getOwner().getHand()
								.remove((PoliticCard) item.getItem());
						buyer.getHand().add((PoliticCard) item.getItem());
					}

				} else {
					// im buying all assistants of the item

					buyer.useCoins(item.getPrice() * item.getAssistants());
					buyer.addAssistants(item.getAssistants());
					item.getOwner().addCoins(
							item.getPrice() * item.getAssistants());
					item.getOwner().useAssistants(item.getAssistants());
				}

				market.removeItem(item);

			} else if (assistantToBuy != null) {
				// im buying SOME asistant of the item

				buyer.useCoins(item.getPrice() * assistantToBuy);
				buyer.addAssistants(assistantToBuy);
				item.getOwner().addCoins(item.getPrice() * assistantToBuy);
				item.getOwner().useAssistants(assistantToBuy);

				item.setAssistants(item.getAssistants() - assistantToBuy);
				if (item.getAssistants() == 0)
					market.removeItem(item);

			}
		}
	}
}
