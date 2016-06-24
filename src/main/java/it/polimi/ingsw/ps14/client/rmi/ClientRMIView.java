package it.polimi.ingsw.ps14.client.rmi;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.State;
import it.polimi.ingsw.ps14.view.ClientView;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * This class implements the methods callable on the client with RMI
 * 
 */

public class ClientRMIView extends UnicastRemoteObject implements
		ClientViewRemote, Serializable {

	private ClientView cv;

	public ClientRMIView(ClientView cv) throws RemoteException {
		super();
		this.cv = cv;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6111979881550001331L;

	public void updateClient(Observable o, Object arg) throws RemoteException {
		System.out.println(arg.toString());
	}

	@Override
	public void availableAssistantUpdate(int assistantUpdated) {
		cv.showAvailableAssistant(assistantUpdated);

	}

	@Override
	public void availableCouncillorsUpdate(
			Map<ColorCouncillor, Integer> updatedAvailableCouncillors) {
		cv.showAvailableCouncillor(updatedAvailableCouncillors);
	}

	@Override
	public void citiesColorBonusesUpdate(int updatedBonusGold,
			int updatedBonusSilver, int updatedBonusBronze, int updatedBonusBlue) {
		cv.showCitiesColorBonuses(updatedBonusGold, updatedBonusSilver,
				updatedBonusBronze, updatedBonusBlue);
	}

	@Override
	public void error(Integer playerID, String text) {
		cv.showError(text);
	}

	@Override
	public void setGameStart(State initialGameState) {
		cv.setGameStarted(true);
		cv.setGameState(initialGameState); 
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
	public void playerChangePrivate(int playerID, String message) {
		cv.showPlayerChangesPrivate(message);
	}

	@Override
	public void playerChangePublic(int playerID, String notice) {
		cv.showPlayerChangesPublic(notice);
	}

	@Override
	public void setPlayerID(int playerID) {
		cv.setPlayerID(playerID);
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
	}

	@Override
	public void otherPlayerUpdate(int id, String name, Color color, int coins,
			int assistants, int level, int points, int numEmporiums) {
		cv.showOtherPlayer(id, name, color, coins, assistants, level, points,
				numEmporiums);
	}

	@Override
	public void gameEnded(List<List<String>> endResults) {
		cv.showEndGame(endResults);
		
	}

}
