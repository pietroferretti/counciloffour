package it.polimi.ingsw.ps14.model.bonus;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps14.message.fromserver.InfoPrivateMsg;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.WaitingFor;

public class BonusFromBusinessPermits implements SpecialNobilityBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -254514472185231405L;

	private final int quantity;

	public BonusFromBusinessPermits(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	/**
	 * The player obtains the bonus of one (or more) of the business permits he
	 * previously bought.
	 * 
	 * @param player
	 *            the player that got the bonus
	 * @param model
	 *            the game model
	 */
	@Override
	public void useBonus(Player player, Model model) {
		
		Map<String, String> availableChoices = new HashMap<>();
		
		for (BusinessPermit permit : player.getAllPermits()) {
			
			availableChoices.put(permit.getId().toString(), permit.getBonusList().toString());
			
		}
		
		if (availableChoices.isEmpty()) {

			model.setMessage(new InfoPrivateMsg(player.getId(),
					"You could have got a bonus from the permits you own, but you don't have any permits..."));
		} else {
			model.setAvailableChoices(availableChoices);
			model.setWaitingForHowMany(quantity);
			model.setWaitingFor(WaitingFor.FROMPERMITS);
		}

	}

	@Override
	public BonusFromBusinessPermits makeCopy() {
		return new BonusFromBusinessPermits(quantity);
	}

	@Override
	public String toString() {
		if (quantity == 1) {
			return "\nChoose a bonus from the business permits that you own!"; 
		} else {
			return String.format("\nChoose %d bonuses from the business permits that you own!", quantity);
		}
	}

}
