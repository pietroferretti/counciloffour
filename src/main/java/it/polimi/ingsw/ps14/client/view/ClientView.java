package it.polimi.ingsw.ps14.client.view;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps14.client.Client;
import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.controller.Controller;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.State;

/**
 * It's the view on the {@link Client} side; it receives {@link Model}'s updates
 * and send infos to the {@link Controller} using a communication type.
 * 
 *
 */
public abstract class ClientView implements Runnable {

	protected Integer playerID;
	protected String name;
	protected boolean gameStarted;
	protected State gameState;

	public void setPlayerID(Integer playerID) {
		this.playerID = playerID;
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

	public abstract void loadMap(String mapName) throws IOException;

	public abstract void readMessage(Message message);

	public abstract void setCommunication(Communication communication);

	public abstract void showAvailableAssistant(int update);

	public abstract void showAvailableCommands();

	public abstract void showAvailableCouncillor(Map<ColorCouncillor, Integer> updatedAvailableCouncillors);

	public abstract void showChatMsg(String author, String text);

	public abstract void showCitiesColorBonuses(int updatedBonusGold, int updatedBonusSilver, int updatedBonusBronze,
			int updatedBonusBlue);

	public abstract void showEndGame(List<List<String>> endResults);

	public abstract void showGameStart();

	public abstract void showInfo(String text);

	public abstract void showItemSold(ItemForSale item);

	public abstract void showKingBonus(int updatedShowableKingBonus);

	public abstract void showKingUpdate(King updatedKing);

	public abstract void showMarket(Market updatedMarket);

	public abstract void showNobilityTrack(NobilityTrack updatedNobilityTrack);

	// doesn't take Player as a parameter to keep other players' info safe
	public abstract void showOtherPlayer(int id, String name, Color color, int coins, int assistants, int level,
			int points, int numEmporiums);

	public abstract void showPersonalDetails(Player p);

	public abstract void showPlayerChangesPrivate(Player player, String message);

	public abstract void showPlayerChangesPublic(String notice);

	public abstract void showPrivateMsg(String text);

	public abstract void showRegion(Region updatedRegion);

}
