package it.polimi.ingsw.ps14.model.bonus;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.PoliticDeck;

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
	public void useBonus(Player player, PoliticDeck deck){
		for(Bonus bon : bonuses){
			if(bon instanceof BonusAssistant) 
				((BonusAssistant)bon).useBonus(player);
			else if(bon instanceof BonusCoin) 
				((BonusCoin)bon).useBonus(player);
			else if(bon instanceof BonusMainAction) 
				((BonusMainAction)bon).useBonus(player);
			else if(bon instanceof BonusNobility) 
				((BonusNobility)bon).useBonus(player);
			else if(bon instanceof BonusPoliticCard) 
				((BonusPoliticCard)bon).useBonus(player, deck);
			else if(bon instanceof BonusVictoryPoint) 
				((BonusVictoryPoint)bon).useBonus(player);
		}
	}

//	public List<Bonus> getBonus() {
//		return bonus;
	}

	

