package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.client.RMI.ClientViewRemote;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromclient.ChooseUsedPermitMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellMsg;
import it.polimi.ingsw.ps14.message.fromclient.TurnActionMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateOtherPlayersMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermiteTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumWithHelpOfKingAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.market.BuyAction;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.ChangeBusinessPermitTilesAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.EngageAssistantAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.PerformAdditionalMainActionAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.SendAssistantToElectCouncillorAction;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 
 * RMIServer implements all methods callable from the client, it create message
 * to send to the controller
 *
 */
public class RMIServer extends Observable implements RMIViewRemote {

	private Server server;

	public RMIServer(Server server) {
		this.server = server;
	}

	public void registerClient(ClientViewRemote clientStub)
			throws RemoteException {
		System.out.println("CLIENT REGISTRATO");
		server.registerWaitingConnectionRMI(clientStub);
		server.meeting();
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerName(String name) {
		System.out.println("da implementare");
//		super.setPlayerName(((PlayerNameMsg) objectReceived).getPlayerName());
//		LOGGER.info(String.format("Set player name as '%s' for rmi view %d",
//				super.getPlayerName(), super.getPlayerID()));//FIXME
	}

	@Override
	public void drawCard(Integer playerID) {
		TurnActionMsg action = new TurnActionMsg(new DrawCardAction(playerID));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void electCouncillor(Integer playerID, ColorCouncillor cc,
			String regionORking) {
		TurnActionMsg action = new TurnActionMsg(new ElectCouncillorAction(
				playerID, cc, regionORking));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void acquireBusinessPermitTile(Integer playerID, RegionType rt,
			Integer permID, List<PoliticCard> politics) {
		TurnActionMsg action = new TurnActionMsg(
				new AcquireBusinessPermiteTileAction(permID, rt, permID,
						new ArrayList<PoliticCard>(politics)));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void buildWithKing(Integer playerID, String city,
			List<PoliticCard> politics) {
		TurnActionMsg action = new TurnActionMsg(
				new BuildEmporiumWithHelpOfKingAction(playerID, city, politics));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void buildWithPermit(Integer playerID, Integer permitID,
			String cityname) {
		TurnActionMsg action = new TurnActionMsg(
				new BuildEmporiumUsingPermitTileAction(playerID, permitID,
						cityname));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void engage(Integer playerID) {
		TurnActionMsg action = new TurnActionMsg(new EngageAssistantAction(
				playerID));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt) {
		TurnActionMsg action = new TurnActionMsg(
				new ChangeBusinessPermitTilesAction(playerID, rt));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void performAdditionalMainAction(Integer playerID) {
		TurnActionMsg action = new TurnActionMsg(
				new PerformAdditionalMainActionAction(playerID));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void electWithAssistant(Integer playerID, RegionType rt,
			ColorCouncillor cc) {
		TurnActionMsg action = new TurnActionMsg(
				new SendAssistantToElectCouncillorAction(playerID, rt, cc));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void usedCard(Integer permID) {
		ChooseUsedPermitMsg choice = new ChooseUsedPermitMsg(permID);
		setChanged();
		notifyObservers(choice);
	}

	@Override
	public void passTurn(Integer playerID) {
		TurnActionMsg action = new TurnActionMsg(new EndTurnAction(playerID));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void showMyDetails(Integer playerID) {
		Message action = new UpdateThisPlayerMsg(playerID);
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void showDetails(Integer playerID) {
		Message action = new UpdateOtherPlayersMsg(playerID);
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void showGamebord(Integer playerID) {
		Message action = new UpdateGameBoardMsg();
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void sell(List<ItemForSale> items) {
		SellMsg action = new SellMsg(new SellAction(items));
		setChanged();
		notifyObservers(action);
	}

	@Override
	public void buy(Integer permID, Integer playerID, Integer quantity) {
		Message action = new BuyMsg(new BuyAction(permID, playerID, quantity));
		setChanged();
		notifyObservers(action);
	}

}
