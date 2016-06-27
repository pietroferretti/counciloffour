package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.client.rmi.ClientViewRemote;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 
 * interface that specifies methods callable from the client
 *
 */
public interface RMIViewRemote extends Remote{

	public void registerClient(ClientViewRemote clientStub)
			throws RemoteException;

	public void setPlayerName(Integer playerID, String name) throws RemoteException;

	public void drawCard(Integer playerID) throws RemoteException;

	public void electCouncillor(Integer playerID, ColorCouncillor cc,
			String regionORking) throws RemoteException;

	public void acquireBusinessPermitTile(Integer playerID, RegionType rt,
			Integer permID, List<PoliticCard> politics) throws RemoteException;

	public void buildWithKing(Integer playerID, String city,
			List<PoliticCard> politics)throws RemoteException;

	public void buildWithPermit(Integer playerID, Integer permitID,
			String cityname)throws RemoteException;

	public void engage(Integer playerID)throws RemoteException;

	public void changeBusinessPermitTiles(Integer playerID, RegionType rt)throws RemoteException;

	public void performAdditionalMainAction(Integer playerID)throws RemoteException;

	public void electWithAssistant(Integer playerID, RegionType rt,
			ColorCouncillor cc)throws RemoteException;

	public void passTurn(Integer playerID)throws RemoteException;

	public void showMyDetails(Integer playerID)throws RemoteException;

	public void showDetails(Integer playerID)throws RemoteException;

	public void showGameboard(Integer playerID)throws RemoteException;

	public void sell(Integer playerID, List<ItemForSale> items)throws RemoteException;

	public void buy(Integer playerID, Integer objID, Integer quantity)throws RemoteException;
	
	public void answerNobilityRequest(Integer playerID, List<String> objectIDs) throws RemoteException;
	
	public void sellNone(Integer playerID) throws RemoteException;
	
	public void doneBuying(Integer playerID) throws RemoteException;
	
	public void clientAlive(Integer playerID) throws RemoteException;
}
