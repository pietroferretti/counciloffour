package it.polimi.ingsw.ps14.client.rmi;

import java.awt.Color;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.ingsw.ps14.client.view.ClientView;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.State;

/**
 * This class implements the methods invokable ON the client with RMI
 * 
 */

public class ClientViewRemoteImpl extends UnicastRemoteObject implements ClientViewRemote {

	private static final long serialVersionUID = 6111979881550001331L;

	private transient Timer timer;
	private transient TimerTask timerTask;
	private boolean alreadyCalled = false;

	private Life life;

	private ClientView cv;

	public ClientViewRemoteImpl(ClientView cv, Life life) throws RemoteException {
		super();
		this.cv = cv;
		this.life = life;
	}

	@Override
	public void availableAssistantUpdate(int assistantUpdated) {
		cv.showAvailableAssistant(assistantUpdated);

	}

	@Override
	public void availableCouncillorsUpdate(Map<ColorCouncillor, Integer> updatedAvailableCouncillors) {
		cv.showAvailableCouncillor(updatedAvailableCouncillors);
	}

	@Override
	public void citiesColorBonusesUpdate(int updatedBonusGold, int updatedBonusSilver, int updatedBonusBronze,
			int updatedBonusBlue) {
		cv.showCitiesColorBonuses(updatedBonusGold, updatedBonusSilver, updatedBonusBronze, updatedBonusBlue);
	}

	@Override
	public void info(String text) {
		cv.showInfo(text);
	}

	@Override
	public void setGameStart(State initialGameState, String mapName) throws IOException {
		cv.setGameStarted(true);
		cv.setGameState(initialGameState);
		cv.loadMap(mapName);
		cv.showGameStart();
	}

	@Override
	public void kingBonusUpdate(int updatedShowableKingBonus) {
		cv.showKingBonus(updatedShowableKingBonus);
	}

	@Override
	public void kingUpdate(King updatedKing) {
		cv.showKingUpdate(updatedKing);
	}

	@Override
	public void marketUpdate(Market updatedMarket) {
		cv.showMarket(updatedMarket);
	}

	@Override
	public void nobilityTrackUpdate(NobilityTrack updatedNobilityTrack) {
		cv.showNobilityTrack(updatedNobilityTrack);
	}

	@Override
	public void personalUpdate(Player p) {
		cv.showPersonalDetails(p);
	}

	@Override
	public void playerChangePrivate(int playerID, Player player, String message) {
		cv.showPlayerChangesPrivate(player, message);
	}

	@Override
	public void playerChangePublic(int playerID, String notice) {
		cv.showPlayerChangesPublic(notice);
	}

	@Override
	public void setPlayerID(int playerID) {
		cv.setPlayerID(playerID);
		life.setPlayerID(playerID);
		Thread thread = new Thread(life);
		thread.start();
	}

	@Override
	public void regionUpdate(Region updatedRegion) {
		cv.showRegion(updatedRegion);
	}

	@Override
	public void itemSold(ItemForSale item) {
		cv.showItemSold(item);
	}

	@Override
	public void stateUpdate(State updatedState) {
		cv.setGameState(updatedState);
		if (!alreadyCalled) {
			timer = new Timer();
			alreadyCalled = true;
			task();
		}
	}

	@Override
	public void otherPlayerUpdate(int id, String name, Color color, int coins, int assistants, int level, int points,
			int numEmporiums) {
		cv.showOtherPlayer(id, name, color, coins, assistants, level, points, numEmporiums);
	}

	@Override
	public void gameEnded(List<List<String>> endResults) {
		cv.showEndGame(endResults);
	}

	/**
	 * It suggest the user what to do next.
	 */
	private void task() {
		timerTask = new TimerTask() {

			@Override
			public void run() {
				cv.showAvailableCommands();
				alreadyCalled = false;
			}
		};
		timer.schedule(timerTask, 200);

	}

	@Override
	public void showChatMsg(String author, String text) throws RemoteException {
		cv.showChatMsg(author, text);
	}
}
