package it.polimi.ingsw.ps14.client.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.State;

/**
 * this public abstract class is unique for cli and gui, the back end call these
 * methods and this class use method of cliView or GuiView according to type
 * chosen.
 * 
 * @author federico
 *
 */
public abstract class ClientView implements Runnable {

	protected Integer playerID;
	protected String name;
	protected boolean gameStarted;
	protected State gameState;
//	protected boolean myTurn;

	public void setPlayerID(Integer playerID) {
		this.playerID = playerID;
//		gameStarted = false;	TODO tenere?
	}

	public Integer getPlayerID() {
		return playerID;
	}

	public void setPlayerName(String name) {
		this.name = name;
	}

	public String getPlayerName() {
		return name;
	}

//	public boolean isMyTurn() {
//		return myTurn;
//	}
//
//	public void setMyTurn(boolean myTurn) {
//		this.myTurn = myTurn;
//	}

	public State getGameState() {
		return gameState;
	}

	public void setGameState(State gameState) {
		this.gameState = gameState;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}


	public abstract void readMessage(Message message);

	public abstract void setCommunication(Communication communication);

	public abstract void showAvailableAssistant(int update);

	public abstract void showAvailableCommands();

	public abstract void showAvailableCouncillor(
			Map<ColorCouncillor, Integer> updatedAvailableCouncillors);

	public abstract void showChatMsg(String author, String text);

	public abstract void showCitiesColorBonuses(int updatedBonusGold,
			int updatedBonusSilver, int updatedBonusBronze, int updatedBonusBlue);

	public abstract void showEndGame(List<List<String>> endResults);

	public abstract void showGameStart();

	public abstract void showInfo(String text);

	public abstract void showItemSold(ItemForSale item);

	public abstract void showKingBonus(int updatedShowableKingBonus);

	public abstract void showKingUpdate(King updatedKing);

	public abstract void showMarket(Market updatedMarket);

	public abstract void showNobilityTrack(NobilityTrack updatedNobilityTrack);

	// doesn't take Player as a parameter to keep other players' info safe
	// TODO solo le carte sono segrete in teoria
	public abstract void showOtherPlayer(int id, String name, Color color,
			int coins, int assistants, int level, int points, int numEmporiums);

	public abstract void showPersonalDetails(Player p);

	public abstract void showPlayerChangesPrivate(String message);

	public abstract void showPlayerChangesPublic(String notice);

	public abstract void showPrivateMsg(String text);

	public abstract void showRegion(Region updatedRegion);

}
