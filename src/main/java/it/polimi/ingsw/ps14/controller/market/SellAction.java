package it.polimi.ingsw.ps14.controller.market;

import it.polimi.ingsw.ps14.Market;
import it.polimi.ingsw.ps14.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SellAction {

	/**
	 * !!!!!!!!!Listener!!!!!!!!!!! qunado ricevo un oggetto da viewPlayer
	 * controllo se il player può venderla
	 */

	private Market market;
	private List<Player> players;

	public SellAction(List<Player> activePlayers) {
		players = new ArrayList<>(activePlayers);
		market = new Market();
	}

	public void selling() {
		for (Player pl : players) {
			/* LISTENER DEGLI OGGETTI CHE METTO IN VENDITA */

		}
	}

	public void buying() {
		Collections.shuffle(players);
		for (Player pl : players) {
			/* LISTENER DEGLI OGGETTI CHE VOGLIO COMPRARE */

		}
	}

}
