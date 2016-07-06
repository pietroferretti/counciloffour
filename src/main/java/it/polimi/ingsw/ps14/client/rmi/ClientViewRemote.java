package it.polimi.ingsw.ps14.client.rmi;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import it.polimi.ingsw.ps14.message.fromserver.GameStartedMsg;
import it.polimi.ingsw.ps14.model.ColorCity;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.State;

/**
 * Interface of the client stub; it contains all the methods that the Server can
 * invoke on the Client.
 * 
 */
public interface ClientViewRemote extends Remote {
	/**
	 * It updates the number of available assistants.
	 * 
	 * @param assistantUpdated
	 *            - int
	 * @throws RemoteException
	 */
	public void availableAssistantUpdate(int assistantUpdated) throws RemoteException;

	/**
	 * It updates the number of available Councillor.
	 * 
	 * @param updatedAvailableCouncillors
	 * @throws RemoteException
	 */
	public void availableCouncillorsUpdate(Map<ColorCouncillor, Integer> updatedAvailableCouncillors)
			throws RemoteException;

	/**
	 * It updates the bonus for building an empotium in all the cities with the
	 * same {@link ColorCity}.
	 * 
	 * @param updatedBonusGold
	 *            - int
	 * @param updatedBonusSilver
	 *            - int
	 * @param updatedBonusBronze
	 *            - int
	 * @param updatedBonusBlue
	 *            - int
	 * @throws RemoteException
	 */
	public void citiesColorBonusesUpdate(int updatedBonusGold, int updatedBonusSilver, int updatedBonusBronze,
			int updatedBonusBlue) throws RemoteException;

	/**
	 * It show a {@link String} text.
	 * 
	 * @param text
	 *            - {@link String}
	 * @throws RemoteException
	 */
	public void info(String text) throws RemoteException;

	/**
	 * It set the GameStarted attribute and set the proper region maps.
	 * 
	 * @param initialGameState
	 * @param mapName
	 * @throws IOException
	 */
	public void setGameStart(State initialGameState, String mapName) throws IOException;

	/**
	 * It updates the usable king bonus.
	 * 
	 * @param updatedShowableKingBonus
	 *            - int
	 * @throws RemoteException
	 */
	public void kingBonusUpdate(int updatedShowableKingBonus) throws RemoteException;

	/**
	 * It updates the {@link King} infos.
	 * 
	 * @param updatedKing
	 *            - {@link King}
	 * @throws RemoteException
	 */
	public void kingUpdate(King updatedKing) throws RemoteException;

	/**
	 * It updates the {@link Market}.
	 * 
	 * @param updatedMarket
	 *            - {@link Market}
	 * @throws RemoteException
	 */
	public void marketUpdate(Market updatedMarket) throws RemoteException;

	/**
	 * It updates the {@link NobilityTrack}
	 * 
	 * @param updatedNobilityTrack
	 *            - {@link NobilityTrack}.
	 * @throws RemoteException
	 */
	public void nobilityTrackUpdate(NobilityTrack updatedNobilityTrack) throws RemoteException;

	/**
	 * It updates a {@link Player} different from the user.
	 * 
	 * @param id
	 *            - int, other {@link Player} id
	 * @param name-
	 *            {@link String}, other {@link Player}
	 * @param color-
	 *            {@link Color} , other {@link Player} color
	 * @param coins-
	 *            int, other {@link Player} id
	 * @param assistants-
	 *            int, other {@link Player} coins
	 * @param level-
	 *            int, other {@link Player} level
	 * @param points-
	 *            int, other {@link Player} points
	 * @param numEmporiums-
	 *            int, other {@link Player} numEmporiums
	 * @throws RemoteException
	 */
	public void otherPlayerUpdate(int id, String name, Color color, int coins, int assistants, int level, int points,
			int numEmporiums) throws RemoteException;

	/**
	 * It updates the user details.
	 * 
	 * @param p-{@link
	 * 			Player}
	 * @throws RemoteException
	 */
	public void personalUpdate(Player p) throws RemoteException;

	/**
	 * It communicates a change in the user's details.
	 * 
	 * @param playerID
	 *            - int, user ID
	 * @param player
	 *            - user, {@link Player}
	 * @param message
	 *            - {@link String}
	 * @throws RemoteException
	 */
	public void playerChangePrivate(int playerID, Player player, String message) throws RemoteException;

	/**
	 * Tt communicates a change in a specific {@link Player} to all
	 * {@link Player}s.
	 * 
	 * @param playerID
	 *            - int
	 * @param notice
	 *            - {@link String}
	 * @throws RemoteException
	 */
	public void playerChangePublic(int playerID, String notice) throws RemoteException;

	public void setPlayerID(int playerID) throws RemoteException;

	/**
	 * It updates a specific {@link Region}.
	 * 
	 * @param updatedRegion-{@link
	 * 			Region}
	 * @throws RemoteException
	 */
	public void regionUpdate(Region updatedRegion) throws RemoteException;

	/**
	 * It communicates to all {@link Player}s that an {@link ItemForSale} has
	 * been sold.
	 * 
	 * @param item-{@link
	 * 			ItemForSale}
	 * @throws RemoteException
	 */
	public void itemSold(ItemForSale item) throws RemoteException;

	/**
	 * It updates the {@link State} of the game.
	 * 
	 * @param updatedState
	 *            - {@link State}
	 * @throws RemoteException
	 */
	public void stateUpdate(State updatedState) throws RemoteException;

	/**
	 * It shows a chat message to all {@link Player}s.
	 * 
	 * @param author
	 *            - {@link String}
	 * @param text
	 *            - {@link ItemForSale}
	 * @throws RemoteException
	 */
	public void showChatMsg(String author, String text) throws RemoteException;

	/**
	 * It show the final results to all {@link Player}s.
	 * 
	 * @param endResults
	 * @throws RemoteException
	 */
	public void gameEnded(List<List<String>> endResults) throws RemoteException;

}
