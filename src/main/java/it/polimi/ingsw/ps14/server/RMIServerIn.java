package it.polimi.ingsw.ps14.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.ingsw.ps14.client.rmi.ClientViewRemote;
import it.polimi.ingsw.ps14.message.DisconnectionMsg;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromclient.DoneBuyingMsg;
import it.polimi.ingsw.ps14.message.fromclient.NobilityRequestAnswerMsg;
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
import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermitTileAction;
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

	private Timer timer;
	private TimerTask task;

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
		TurnActionMsg message = new TurnActionMsg(new DrawCardAction(playerID));
		sendToServerView(playerID, message);
	}

	@Override
	public void electCouncillor(Integer playerID, ColorCouncillor cc,
			String regionORking) {
		TurnActionMsg message = new TurnActionMsg(new ElectCouncillorAction(
				playerID, cc, regionORking));
		sendToServerView(playerID, message);
	}

	@Override
	public void acquireBusinessPermitTile(Integer playerID, RegionType rt,
			Integer permID, List<PoliticCard> politics) {
		TurnActionMsg message = new TurnActionMsg(
				new AcquireBusinessPermitTileAction(playerID, rt, permID,
						new ArrayList<PoliticCard>(politics)));
		sendToServerView(playerID, message);
	}

	@Override
	public void buildWithKing(Integer playerID, String city,
			List<PoliticCard> politics) {
		TurnActionMsg message = new TurnActionMsg(
				new BuildEmporiumWithHelpOfKingAction(playerID, city, politics));
		sendToServerView(playerID, message);
	}

	@Override
	public void buildWithPermit(Integer playerID, Integer permitID,
			String cityname) {
		TurnActionMsg message = new TurnActionMsg(
				new BuildEmporiumUsingPermitTileAction(playerID, permitID,
						cityname));
		sendToServerView(playerID, message);
	}

	@Override
	public void engage(Integer playerID) {
		TurnActionMsg message = new TurnActionMsg(new EngageAssistantAction(
				playerID));
		sendToServerView(playerID, message);
	}

	@Override
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt) {
		TurnActionMsg message = new TurnActionMsg(
				new ChangeBusinessPermitTilesAction(playerID, rt));
		sendToServerView(playerID, message);
	}

	@Override
	public void performAdditionalMainAction(Integer playerID) {
		TurnActionMsg message = new TurnActionMsg(
				new PerformAdditionalMainActionAction(playerID));
		sendToServerView(playerID, message);
	}

	@Override
	public void electWithAssistant(Integer playerID, RegionType rt,
			ColorCouncillor cc) {
		TurnActionMsg message = new TurnActionMsg(
				new SendAssistantToElectCouncillorAction(playerID, rt, cc));
		sendToServerView(playerID, message);
	}

	@Override
	public void passTurn(Integer playerID) {
		TurnActionMsg message = new TurnActionMsg(new EndTurnAction(playerID));
		sendToServerView(playerID, message);
	}

	@Override
	public void answerNobilityRequest(Integer playerID, List<String> chosenIDs) {
		Message message = new NobilityRequestAnswerMsg(chosenIDs);
		sendToServerView(playerID, message);
	}

	@Override
	public void showMyDetails(Integer playerID) {
		Message message = new UpdateThisPlayerMsg(playerID);
		sendToServerView(playerID, message);
	}

	@Override
	public void showDetails(Integer playerID) {
		Message message = new UpdateOtherPlayersMsg(playerID);
		sendToServerView(playerID, message);
	}

	@Override
	public void showGameboard(Integer playerID) {
		Message message = new UpdateGameBoardMsg();
		sendToServerView(playerID, message);
	}

	@Override
	public void sell(Integer playerID, List<ItemForSale> items) {
		SellMsg message = new SellMsg(new SellAction(items));
		sendToServerView(playerID, message);
	}

	@Override
	public void buy(Integer playerID, Integer objID, Integer quantity) {
		Message message = new BuyMsg(new BuyAction(playerID, objID, quantity));
		sendToServerView(playerID, message);
	}

	@Override
	public void sellNone(Integer playerID) throws RemoteException {
		Message message = new SellNoneMsg();
		sendToServerView(playerID, message);

	}

	@Override
	public void doneBuying(Integer playerID) throws RemoteException {
		Message message = new DoneBuyingMsg();
		sendToServerView(playerID, message);

	}

	@Override
	public void clientAlive(Integer playerID) throws RemoteException {
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				Message message=new DisconnectionMsg(playerID);
				sendToServerView(playerID,message);
			}
		};
	}

}
