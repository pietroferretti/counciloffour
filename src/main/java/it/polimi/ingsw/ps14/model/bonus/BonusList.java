package it.polimi.ingsw.ps14.model.bonus;

import java.util.List;

import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.PoliticDeck;

public class BonusList {

	private final List<Bonus> bonus;
	
	public BonusList(List<Bonus> bonus){
		this.bonus=bonus;
	}
	
	public List<Bonus> getBonusCard(){
		return bonus;
	}
	
/**
 * use bonus according bonus type
 * @param player player who use the bonus
 * @param deck politicDeck of the player (to add politic cards if they are in the bonus)
 */
	public void useBonus(Player player,PoliticDeck deck){
		for(Bonus bon : bonus){
			if(bon instanceof BonusAssistant) 
				((BonusAssistant)bon).useBonus(player);
			else if(bon instanceof BonusCoin) 
				((BonusCoin)bon).useBonus(player);
			else if(bon instanceof BonusMainAction) 
				((BonusMainAction)bon).useBonus(player);
			else if(bon instanceof BonusNobility) 
				((BonusNobility)bon).useBonus(player);
			else if(bon instanceof BonusPoliticCard) 
				((BonusPoliticCard)bon).useBonus(player,deck);
			else if(bon instanceof BonusVictoryPoint) 
				((BonusVictoryPoint)bon).useBonus(player);
		}
	}

//	public List<Bonus> getBonus() {
//		return bonus;
	}

	

