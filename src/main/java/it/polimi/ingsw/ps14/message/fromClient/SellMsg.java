package it.polimi.ingsw.ps14.message.fromClient;

import it.polimi.ingsw.ps14.model.ItemForSale;

import java.util.List;

public class SellMsg {

	List<ItemForSale> items;

	public List<ItemForSale> getItems() {
		return items;
	}

	public SellMsg(List<ItemForSale> items) {
		this.items = items;
	}


	
}
