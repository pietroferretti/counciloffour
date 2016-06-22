package it.polimi.ingsw.ps14.model.bonus;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;

public class BonusNobilityLvlUp implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4670449670079163294L;

	private final int quantity;

	public BonusNobilityLvlUp(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}
	
	// nobility is player.level
	@Override
	public void useBonus(Player player, Model model) {
		NobilityTrack nobilityTrack = model.getGameBoard().getNobilityTrack();
		
		Integer currentLevel = player.getLevel();
		
		List<Bonus> bonusesToApply = new ArrayList<>();
		for (int level = currentLevel; level < currentLevel+quantity; level++) {
			if (nobilityTrack.bonusExistsAtLevel(level)) {
				bonusesToApply.add(nobilityTrack.getBonus(level));
			}
		}
		
		Bonus bonus;
		while (!bonusesToApply.isEmpty()) {
			
			bonus = bonusesToApply.remove(0);
			if (!(bonus instanceof SpecialNobilityBonus)) {
				
				bonus.useBonus(player, model);
				
			} else {
				
				bonus.useBonus(player, model);			// sets the WaitingFor and related states appropriately
				model.setBonusesToDo(bonusesToApply);	// saves the bonuses still left to do
				bonusesToApply.clear();					// goes back to waiting for a message from the client
			}
			
		}
		
	}

	@Override
	public BonusNobilityLvlUp makeCopy() {
		return new BonusNobilityLvlUp(quantity);
	}
	
	@Override
	public String toString() {
		return "\n+" + Integer.toString(quantity) + " nobility points";
	}
}
