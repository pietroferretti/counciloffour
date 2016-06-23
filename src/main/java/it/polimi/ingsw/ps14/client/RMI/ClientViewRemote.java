package it.polimi.ingsw.ps14.client.RMI;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.State;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Observable;

/**
 * interface of client stub that server will have
 * 
 *
 */
public interface ClientViewRemote extends Remote {

	// metodi invocabili dal server sul client
	
	public void updateClient(Observable o, Object arg) throws RemoteException;

	public void availableAssistantUpdate(int assistantUpdated) throws RemoteException;

	public void availableCouncillorsUpdate(
			Map<ColorCouncillor, Integer> updatedAvailableCouncillors) throws RemoteException;

	public void citiesColorBonusesUpdate(int updatedBonusGold,
			int updatedBonusSilver, int updatedBonusBronze, int updatedBonusBlue) throws RemoteException;

	public void error(Integer playerID, String text) throws RemoteException;

	public void setGameStart(State initialGameState) throws RemoteException;

	public void kingBonusUpdate(int updatedShowableKingBonus) throws RemoteException;

	public void kingUpdate(King updatedKing) throws RemoteException;

	public void marketUpdate(Market updatedMarket) throws RemoteException;

	public void nobilityTrackUpdate(NobilityTrack updatedNobilityTrack) throws RemoteException;

	public void otherPlayerUpdate(int id,String name,Color color,int coins, int assistants, int level,int points,int numEmporiums) throws RemoteException;

	public void personalUpdate(Player p) throws RemoteException;

	public void playerChangePrivate(int playerID, String message) throws RemoteException;

	public void playerChangePublic(int playerID, String notice) throws RemoteException;

	public void setPlayerID(int playerID) throws RemoteException;

	public void regionUpdate(Region updatedRegion) throws RemoteException;

	public void itemSold(ItemForSale item) throws RemoteException;

	public void stateUpdate(State updatedState) throws RemoteException;

}
