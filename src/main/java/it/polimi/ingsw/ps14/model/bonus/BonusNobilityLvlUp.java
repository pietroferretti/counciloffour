package it.polimi.ingsw.ps14.model.bonus;

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
		Integer levelUpsToDo = new Integer(quantity);
		NobilityTrack nobilityTrack = model.getGameBoard().getNobilityTrack();
		
		Integer currentLevel = player.getLevel();
		
		while (levelUpsToDo > 0) {
			
			if (!nobilityTrack.isBonusSpecial(currentLevel + 1)) {
				
				currentLevel = player.levelUp();
				nobilityTrack.useBonus(player, model, currentLevel);
				levelUpsToDo--;
				
			} else {
				
				currentLevel = player.levelUp();
				nobilityTrack.useBonus(player, model, currentLevel);  // sets the WaitingFor and related states appropriately
				levelUpsToDo--;
				model.setLevelUpsToDo(levelUpsToDo);	// saves the level ups still left to do
				break;		// goes back to waiting for a message from the client
				
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
