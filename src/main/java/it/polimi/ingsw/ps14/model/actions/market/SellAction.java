package it.polimi.ingsw.ps14.model.actions.market;

import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.Model;

import java.io.Serializable;
import java.util.List;

public class SellAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6644216312168944647L;
	private List<ItemForSale> items;

	public SellAction( List<ItemForSale> items) {
		this.items = items;
	}

	public boolean isValid(Model model) {
		for (ItemForSale item : items)
			if (!item.isValid(model))
				return false;
		return true;
	}

	public void execute(Market market) {
		for (ItemForSale item : items){
			market.addItem(item);
			System.out.println("aggiungo "+item.toString());
		}	
	}

}
