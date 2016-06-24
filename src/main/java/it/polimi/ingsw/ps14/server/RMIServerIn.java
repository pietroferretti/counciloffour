package it.polimi.ingsw.ps14.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import it.polimi.ingsw.ps14.client.rmi.ClientViewRemote;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromclient.DoneBuyingMsg;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellNoneMsg;
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

/**
 * 
 * RMIServer implements all methods callable from the client, it create message
 * to send to the controller
 *
 */

public class RMIServerIn extends Observable implements RMIViewRemote {

	private Server server;

	private List<RMIServerView> serverViews;

	public RMIServerIn(Server server) {
		this.server = server;
		serverViews = new ArrayList<>();
	}

	public void registerClient(ClientViewRemote clientStub)
			throws RemoteException {
		System.out.println("CLIENT REGISTRATO");
		server.registerWaitingConnectionRMI(clientStub, this);
		server.meeting();
	}

	public void addServerView(RMIServerView serverView) {
		serverViews.add(serverView);
	}

	public void sendToServerView(Integer playerID, Message msg) {

		for (RMIServerView serverView : serverViews) {
			if (serverView.getPlayerID() == playerID) {
				serverView.forwardMessage(msg);
			}
		}

	}

	@Override
	public void setPlayerName(Integer playerID, String name) {

		PlayerNameMsg message = new PlayerNameMsg(name);
		sendToServerView(playerID, message);
		
	}

	@Override
	public void drawCard(Integer playerID) {
		System.out.println("i received a draw message");

		TurnActionMsg action = new TurnActionMsg(new DrawCardAction(playerID));
		sendToServerView(playerID, action);
		System.out.println("i notified the observers");
	}

	@Override
	public void electCouncillor(Integer playerID, ColorCouncillor cc,
			String regionORking) {
		TurnActionMsg action = new TurnActionMsg(new ElectCouncillorAction(
				playerID, cc, regionORking));
		sendToServerView(playerID, action);
	}

	@Override
	public void acquireBusinessPermitTile(Integer playerID, RegionType rt,
			Integer permID, List<PoliticCard> politics) {
		TurnActionMsg action = new TurnActionMsg(
				new AcquireBusinessPermiteTileAction(playerID, rt, permID,
						new ArrayList<PoliticCard>(politics)));
		sendToServerView(playerID, action);
	}

	@Override
	public void buildWithKing(Integer playerID, String city,
			List<PoliticCard> politics) {
		TurnActionMsg action = new TurnActionMsg(
				new BuildEmporiumWithHelpOfKingAction(playerID, city, politics));
		sendToServerView(playerID, action);
	}

	@Override
	public void buildWithPermit(Integer playerID, Integer permitID,
			String cityname) {
		TurnActionMsg action = new TurnActionMsg(
				new BuildEmporiumUsingPermitTileAction(playerID, permitID,
						cityname));
		sendToServerView(playerID, action);
	}

	@Override
	public void engage(Integer playerID) {
		TurnActionMsg action = new TurnActionMsg(new EngageAssistantAction(
				playerID));
		sendToServerView(playerID, action);
	}

	@Override
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt) {
		TurnActionMsg action = new TurnActionMsg(
				new ChangeBusinessPermitTilesAction(playerID, rt));
		sendToServerView(playerID, action);
	}

	@Override
	public void performAdditionalMainAction(Integer playerID) {
		TurnActionMsg action = new TurnActionMsg(
				new PerformAdditionalMainActionAction(playerID));
		sendToServerView(playerID, action);
	}

	@Override
	public void electWithAssistant(Integer playerID, RegionType rt,
			ColorCouncillor cc) {
		TurnActionMsg action = new TurnActionMsg(
				new SendAssistantToElectCouncillorAction(playerID, rt, cc));
		sendToServerView(playerID, action);
	}

	@Override
	public void passTurn(Integer playerID) {
		TurnActionMsg action = new TurnActionMsg(new EndTurnAction(playerID));
		sendToServerView(playerID, action);
	}

	@Override
	public void answerNobilityRequest(Integer playerID, List<String> chosenIDs) {
		// TODO;
	}

	@Override
	public void showMyDetails(Integer playerID) {
		Message action = new UpdateThisPlayerMsg(playerID);
		sendToServerView(playerID, action);
	}

	@Override
	public void showDetails(Integer playerID) {
		Message action = new UpdateOtherPlayersMsg(playerID);
		sendToServerView(playerID, action);
	}

	@Override
	public void showGamebord(Integer playerID) {
		Message action = new UpdateGameBoardMsg();
		sendToServerView(playerID, action);
	}

	@Override
	public void sell(Integer playerID, List<ItemForSale> items) {
		SellMsg action = new SellMsg(new SellAction(items));
		sendToServerView(playerID, action);
	}

	@Override
	public void buy(Integer playerID, Integer objID, Integer quantity) {
		Message action = new BuyMsg(new BuyAction(playerID, objID, quantity));
		sendToServerView(playerID, action);
	}

	@Override
	public void sellNone(Integer playerID) throws RemoteException {
		Message action = new SellNoneMsg();
		sendToServerView(playerID, action);

	}

	@Override
	public void doneBuying(Integer playerID) throws RemoteException {
		Message action = new DoneBuyingMsg();
		sendToServerView(playerID, action);

	}

}
