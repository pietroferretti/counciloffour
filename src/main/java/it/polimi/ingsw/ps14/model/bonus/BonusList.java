package it.polimi.ingsw.ps14.model.bonus;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

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
	public void useBonus(Player player, GameBoard gameboard){
		for(Bonus bonus : bonuses){
			bonus.useBonus(player, gameboard);
		}
	}

//	public List<Bonus> getBonus() {
//		return bonus;
	}

	

