package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusVictoryPoint extends Bonus {

	private boolean used;

	public BonusVictoryPoint(int quantity) {
		super(quantity);
		used = false;
	}

	
	@Override
	public void useBonus(Player player, Model model) {

		player.addPoints(super.getQuantity());
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed() {
		used = true;
	}

}
