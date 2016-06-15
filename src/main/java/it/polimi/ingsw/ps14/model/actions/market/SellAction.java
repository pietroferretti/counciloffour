package it.polimi.ingsw.ps14.model.actions.market;

import java.util.List;

import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.Market;

public class SellAction {


	private Market market;
	private List<ItemForSale> items;

	public SellAction(Market market, List<ItemForSale> items) {
		this.market = market;
		this.items=items;
	}

	public boolean isValid() {
		for(ItemForSale item : items)
			if(!item.isValid()) return false;
		return true;
	}
	
	public void sell(){
		for(ItemForSale item : items)
			market.addItem(item);
	}


}
