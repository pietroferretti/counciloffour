package it.polimi.ingsw.ps14.model.bonus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps14.message.fromserver.InfoPrivateMsg;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.WaitingFor;

public class BonusFromTokens implements SpecialNobilityBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1702851638011675183L;

	private final int quantity;

	public BonusFromTokens(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	/**
	 * The player obtains the bonus from one (or more) of the tokens from the
	 * cities where he has already built an emporium. The player cannot choose
	 * one that would advance him in the nobility track.
	 * 
	 * @param player
	 *            The player that got the bonus
	 * @param model
	 *            The current gameboard, to get the cities
	 */
	@Override
	public void useBonus(Player player, Model model) {

		Map<String, String> availableChoices = new HashMap<>();
		
		Bonus token;
		for (City c : model.getGameBoard().getCities()) {
			
			if (c.isEmporiumBuilt(player)) {
				
				token = c.getToken();				
				if (!containsLvlUp(token)) {
					availableChoices.put(c.getName(), c.getToken().toString());
				}
			}
			
		}
		
		if (availableChoices.isEmpty()) {
			model.setMessage(new InfoPrivateMsg(player.getId(),
					"You could have got a bonus from the cities you built in, but there are no tokens available..."));
		} else {
			model.setAvailableChoices(availableChoices);
			model.setWaitingForHowMany(quantity);
			model.setWaitingFor(WaitingFor.FROMTOKENS);
		}
	}
	
	private boolean containsLvlUp(Bonus token) {
		if (token instanceof BonusNobilityLvlUp) {
			return true;
		}
		
		if (!(token instanceof BonusList)){
			return false;
		}
		
		BonusList tokenBL = (BonusList) token;
		List<Bonus> bonuses = tokenBL.getListOfBonuses();
	
		for (Bonus bonus : bonuses) {
			if (containsLvlUp(bonus)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public BonusFromTokens makeCopy() {
		return new BonusFromTokens(quantity);
	}
	
	@Override
	public String toString() {
		if (quantity == 1) {
			return "\nChoose a bonus from the cities in which you own an emporium!"; 
		} else {
			return String.format("\nChoose %d bonuses from the cities in which you own an emporium!", quantity);
		}
	}
}
