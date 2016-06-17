package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.model.ItemForSale;

public class SoldItemMsg {

	
	private ItemForSale item;
	
	public SoldItemMsg(ItemForSale item) {
		this.item=item;
	}
	
	public ItemForSale getItemSold(){
		return item;
	}

	
}
