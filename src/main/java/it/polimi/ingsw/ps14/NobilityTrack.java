package it.polimi.ingsw.ps14;

import java.util.Observable;

import it.polimi.ingsw.ps14.model.bonus.BonusAssistant;
import it.polimi.ingsw.ps14.model.bonus.BonusCoin;
import it.polimi.ingsw.ps14.model.bonus.BonusList;
import it.polimi.ingsw.ps14.model.bonus.BonusMainAction;
import it.polimi.ingsw.ps14.model.bonus.BonusPoliticCard;
import it.polimi.ingsw.ps14.model.bonus.BonusVictoryPoint;

/*
 * This class contains all the bonuses a player can get 
 * when leveling up on the nobility track.
 * 
 * TODO Se abbiamo sbatti possiamo fare delle classi per i bonus speciali, poi caricarli in NobilityTrack
 * 		all'inizializzazione del gioco + aggiungere la personalizzazione della nobility track dai settings
 */
public class NobilityTrack extends Observable implements Cloneable{
	
	private final GameBoard gameboard;
	
	public NobilityTrack(GameBoard gameboard) {
		this.gameboard = gameboard;
	}
	
	/**
	 * Given a Player, this method finds the bonus corresponding to
	 * their nobility level and applies it to the Player.
	 * @param player 		The player that needs to get the bonus
	 */
	public void awardNobilityBonus(Player player) {
		switch(player.getLevel()) {
		case 2:			
			// 2 coins, 2 victory points
			BonusList bonusListLv2 = new BonusList(new BonusCoin(2), new BonusVictoryPoint(2));
			bonusListLv2.useBonus(player, gameboard.getPoliticDeck());
			break;
		case 4:
			// bonus da token città
			// TODO ho bisogno di un modo per chiedere al giocatore!!!
			break;
		case 6:
			// 1 Main action
			BonusMainAction bonusLv6 = new BonusMainAction(1);
			bonusLv6.useBonus(player);
			break;
		case 8:
			// 3 victory points, 1 politic card
			BonusList bonusLv8 = new BonusList(new BonusVictoryPoint(3), new BonusPoliticCard(1));
			bonusLv8.useBonus(player, gameboard.getPoliticDeck());
			break;
		case 10:
			// guadagna 1 business permit
			break;
		case 12:
			// 5 victory points, 1 assistant
			BonusList bonusLv12 = new BonusList(new BonusVictoryPoint(5), new BonusAssistant(1));
			bonusLv12.useBonus(player, gameboard.getPoliticDeck());
			break;
		case 14:
			// bonus da business permit
			break;
		case 16:
			// 2 bonus diversi da token città
			break;
		case 18:
			// 8 victory points
			BonusVictoryPoint bonusLv18 = new BonusVictoryPoint(8);
			bonusLv18.useBonus(player);
			break;
		case 19:
			// 2 victory points
			BonusVictoryPoint bonusLv19 = new BonusVictoryPoint(2);
			bonusLv19.useBonus(player);
			break;
		case 20:
			// 3 victory points
			BonusVictoryPoint bonusLv20 = new BonusVictoryPoint(3);
			bonusLv20.useBonus(player);
			break;
		default:
			break;
		}
	}
	
	// TODO fare in modo che non sia completamente scollegato dall'altro metodo
	public boolean bonusExistsAtLevel(int level) {
		int[] levelsWithBonus = {2, 4, 6, 8, 10, 12, 14, 16, 18, 19, 20};
	    for (int i : levelsWithBonus) {
	        if (i == level) {
	            return true;
	        }
	    }
	    return false;
	}
	
	@Override
	public String toString() {
		//TODO
		throw new UnsupportedOperationException();
	}
}