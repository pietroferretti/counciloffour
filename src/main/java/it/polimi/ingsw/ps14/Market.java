package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class Market {

	private ArrayList<ItemForSale> objectsForSale;

	public boolean sell(ItemForSale item, Player buyer) {
		// TODO dobbiamo implementare delle eccezioni per i vari problemi?
		// do per scontato che item appartenga a objectsForSale
		// altrimenti dobbiamo aggiungere controlli
		/*
		 * int index = objectsForSale.indexOf(item); if (index == -1) return
		 * false;
		 */
		// se non funziona con getClass proviamo instance of
		
		if (!buyer.useCoins(item.getPrice()))
			return false;
		if (item.getItem().getClass().equals(BusinessPermit.class)) {
			earnCoins(item);
			buyer.acquireBusinessPermit((BusinessPermit) item.getItem());
			item.getOwner().sellPermits((BusinessPermit) item.getItem());

		} else if (item.getItem().getClass().equals(PoliticCard.class)) {
			earnCoins(item);
			buyer.addPolitic((PoliticCard) item.getItem());
			item.getOwner().useCards((PoliticCard) item.getItem());

		} else {
			earnCoins(item);
			buyer.addAssistants(1);
			if (!item.getOwner().useAssistants(1))
				return false;
		}
		if (objectsForSale.remove(item))
			return true;
		return false;

	}

	private void earnCoins(ItemForSale item) {
		item.getOwner().addCoins(item.getPrice());
	}

	public void addItem(ItemForSale item) {
		objectsForSale.add(item);
	}

}