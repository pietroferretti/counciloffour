package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.List;

public class Market {

	private List<ItemForSale> objectsForSale;
	
	public Market(){
		objectsForSale=new ArrayList<>();
	}
	
	/**
	 * check if player has enough coins to buy the item or assistants
	 * 
	 * @return
	 */
	public boolean canBuyBy(Player buyer,ItemForSale object) {
		ItemForSale item=findObject(object);
		if (item.getItem() != null)
			if (buyer.getCoins() < item.getPrice())
				return false;
		if (item.getAssistants() != null)
			if (buyer.getCoins() < item.getPrice() * item.getAssistants())
				return false;
		return true;
	}

	/**
	 * check if player has enough coins to buy some of assistants
	 * 
	 */
	public boolean canBuyBy(Player buyer,ItemForSale object, Integer assistantToBuy) {
		ItemForSale item=findObject(object);
		if (item.getItem() != null)
			if (buyer.getCoins() < item.getPrice() * assistantToBuy)
				return false;
		return true;
	}

	/**
	 * exec the BUY action, if item is ASSISTANT this method buy all the
	 * assistants
	 * 
	 * @param buyer
	 */
	public void buyBy(Player buyer,ItemForSale object) {
		ItemForSale item=findObject(object);

		if (item.getItem() != null) {

			if (item.getItem() instanceof BusinessPermit) {
				item.getOwner().getBusinessHand().sellPermits((BusinessPermit) item.getItem());
				buyer.getBusinessHand().acquireBusinessPermit(
						(BusinessPermit) item.getItem());

			}

			if (item.getItem() instanceof PoliticCard) {
				item.getOwner().getHand().remove((PoliticCard) item.getItem());
				buyer.getHand().add((PoliticCard) item.getItem());

			}
			item.setItem(null) ;
		}
		if (item.getAssistants() != null) {
			item.getOwner().useAssistants(item.getAssistants());
			buyer.useAssistants(item.getAssistants());
			item.setAssistants(null);
		}

	}

	/**
	 * BUY only some of selling assistants
	 * 
	 * @param buyer
	 * @param assistantToBuy
	 *            how many assistants do you want to buy
	 */
	public void BuyBy(Player buyer,ItemForSale object, Integer assistantToBuy) {
		ItemForSale item=findObject(object);

		if (item.getAssistants() != null && assistantToBuy <= item.getAssistants()) {
			item.getOwner().useAssistants(assistantToBuy);
			item.setAssistants(item.getAssistants() - assistantToBuy);
			buyer.useAssistants(assistantToBuy);
			if (item.getAssistants() ==0)
				item.setAssistants(null);
		}
	}
	
	private ItemForSale findObject(ItemForSale item){
		for(ItemForSale object : objectsForSale)
			if(object.equals(item)) return object;
		return null;
	}
	
	public void addItem(ItemForSale item) {
		objectsForSale.add(item);
	}

	@Override
	public String toString() {
		return "Market [objectsForSale=" + objectsForSale + "]";
	}

}