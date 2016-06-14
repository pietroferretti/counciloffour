package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

import java.util.ArrayList;
import java.util.List;

public class BonusList {

	private final List<Bonus> bonuses;
	
	public BonusList(List<Bonus> bonuses){
		this.bonuses = bonuses;
	}
	
	public BonusList(Bonus bonus1) {
		bonuses = new ArrayList<>();
		bonuses.add(bonus1);
	}
	
	public BonusList(Bonus bonus1, Bonus bonus2) {
		bonuses = new ArrayList<>();
		bonuses.add(bonus1);
		bonuses.add(bonus2);
	}
	
	public List<Bonus> getBonusCard(){
		return bonuses;
	}
	
/**
 * use bonus according bonus type
 * @param player player who use the bonus
 * @param deck politicDeck of the gameboard
 */
	public void useBonus(Player player, Model model){
		for(Bonus bonus : bonuses){
			bonus.useBonus(player, model);
		}
	}

//	public List<Bonus> getBonus() {
//		return bonus;
	}

	

