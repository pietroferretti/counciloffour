package it.polimi.ingsw.ps14.model.bonus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.WaitingFor;

public class BonusTakeBusinessPermits implements SpecialNobilityBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6778943163874682668L;

	private final int quantity;

	public BonusTakeBusinessPermits(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	/**
	 * The player can get a free permit from the board.
	 * 
	 * @param player
	 *            The player that got the bonus
	 * @param model
	 *            The current gameboard, to get the business permits
	 */
	@Override
	public void useBonus(Player player, Model model) {
		
		Map<String, String> availableChoices = new HashMap<>();
		
		List<BusinessPermit> tempList;
		for (Region region : model.getGameBoard().getRegions()) {
			
			tempList = Arrays.asList(region.getAvailablePermits());
			for (BusinessPermit permit: tempList) {
				availableChoices.put(permit.getId().toString(), permit.toString());
			}
			
		}

		model.setAvailableChoices(availableChoices);
		model.setWaitingForHowMany(quantity);
		model.setWaitingFor(WaitingFor.TAKEPERMIT);
	}

	@Override
	public BonusTakeBusinessPermits makeCopy() {
		return new BonusTakeBusinessPermits(quantity);
	}

	@Override
	public String toString() {
		return "\n+" + Integer.toString(quantity) + " business permits!";
	}
}
