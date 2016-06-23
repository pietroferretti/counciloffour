package it.polimi.ingsw.ps14.server;

import java.util.Observable;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.rmi.ClientViewRemote;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateOtherPlayersMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateRequestMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.CitiesColorBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.NobilityTrackUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.OtherPlayerUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PersonalUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerIDMsg;
import it.polimi.ingsw.ps14.message.fromserver.PrivateMessage;
import it.polimi.ingsw.ps14.message.fromserver.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;
import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.model.modelview.PlayerView;
import it.polimi.ingsw.ps14.model.modelview.RegionView;

/**
 * 
 * there is a server view for each client (rmi and socket), for rmi this
 * contains a clientStub, playerID, serverRMIout
 *
 */
public class RMIServerView extends ServerView {
	private static final Logger LOGGER = Logger
			.getLogger(RMIServerView.class.getName());

	private RMIserverOut serverRMIout;

	public RMIServerView(int id, ClientViewRemote client) {
		super(id);
		serverRMIout = new RMIserverOut(client);
		serverRMIout.castMessage(new PlayerIDMsg(id));
		LOGGER.info(String.format("Sent id to player %d", super.getPlayerID()));
	}

	public void forwardMessage(Message msg) {
		if (msg instanceof PlayerNameMsg) {

			super.setPlayerName(((PlayerNameMsg) msg).getPlayerName());
			LOGGER.info(String.format(
					"Set player name as '%s' for rmiView %d",
					super.getPlayerName(), super.getPlayerID()));

		} else if (msg instanceof UpdateRequestMsg) {

			sendUpdates((UpdateRequestMsg) msg);

		} else if (msg instanceof Message) {

			setChanged();
			notifyObservers(msg); // inoltro al controller
		} 
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (o instanceof ModelView) {
		
			if (arg instanceof PrivateMessage) {
				if (((PrivateMessage) arg).getPlayerID() == super.getPlayerID()) {
					serverRMIout.castMessage((Message) arg);
				}
			} else if (arg instanceof Message) {
				serverRMIout.castMessage((Message) arg);
			} else {
				LOGGER.warning(String.format(
						"The server view with id '%d' received an object that is not a message. %n" + "Object received: %s",
						super.getPlayerID(), arg.toString()));
			}		
			
		}  else
			System.err.println("osservatore sbagliato" + o);
	}

	private void sendUpdates(UpdateRequestMsg requestReceived) {

		if (requestReceived instanceof UpdateGameBoardMsg) {
			// TODO inutile se solo chi ha il turno può fare richieste
			// sendMessage(new
			// CurrentPlayerUpdatedMsg(super.getModelView().getCurrentPlayerView().getCurrentPlayerNameCopy(),
			// super.getModelView().getCurrentPlayerView().getCurrentPlayerIDCopy()));
			sendMessage(new StateUpdatedMsg(super.getModelView().getStateView().getStateCopy()));
			sendMessage(new AvailableAssistantsUpdatedMsg(
					super.getModelView().getAvailableAssistantsView().getAvailableAssistantsCopy()));
			sendMessage(new KingBonusesUpdatedMsg(super.getModelView().getKingBonusesView().getShowableKingBonus()));
			sendMessage(
					new NobilityTrackUpdatedMsg(super.getModelView().getNobilityTrackView().getNobilityTrackCopy()));
			
//			sendMessage(new RegionUpdatedMsg(super.getModelView().getRegionsView().get(0).getRegionCopy()));
			
			sendMessage(new CitiesColorBonusesUpdatedMsg(
					super.getModelView().getCitiesColorBonusesView().getBonusGoldCopy(),
					super.getModelView().getCitiesColorBonusesView().getBonusSilverCopy(),
					super.getModelView().getCitiesColorBonusesView().getBonusBronzeCopy(),
					super.getModelView().getCitiesColorBonusesView().getBonusBlueCopy()));
			sendMessage(new RegionUpdatedMsg(super.getModelView().getRegionsView().get(0).getRegionCopy()));
			for (RegionView rv : super.getModelView().getRegionsView()) {
				sendMessage(new RegionUpdatedMsg(rv.getRegionCopy()));
			 }
			sendPersonalUpdate();
			sendOthersUpdate();

		} else if (requestReceived instanceof UpdateThisPlayerMsg) {
			sendPersonalUpdate();
		} else if (requestReceived instanceof UpdateOtherPlayersMsg) {
			sendOthersUpdate();
		}
	}
	
	private void sendOthersUpdate() {
		for (PlayerView pv : super.getModelView().getPlayersView()) {
			if (pv.getPlayerCopy().getId() != super.getPlayerID())
				sendMessage(new OtherPlayerUpdateMsg(pv.getPlayerCopy()));
		}
	}

	private void sendPersonalUpdate() {
		sendMessage(new PersonalUpdateMsg(super.getModelView().getPlayerByID(super.getPlayerID())));
	}


	public void sendMessage(Message msg) {

			serverRMIout.castMessage(msg);
			LOGGER.info(String.format("callind method %s on rmi %d", msg,
					super.getPlayerID()));
		
	}

}
