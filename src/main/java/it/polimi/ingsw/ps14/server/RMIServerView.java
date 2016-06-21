package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.client.RMI.ClientViewRemote;
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
import it.polimi.ingsw.ps14.message.fromserver.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;
import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.model.modelview.PlayerView;
import it.polimi.ingsw.ps14.model.modelview.RegionView;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * 
 * there is a server view for each client (rmi and socket), for rmi this
 * contains a clientStub, playerID, serverRMIout
 *
 */
public class RMIServerView extends ServerView implements Observer {
	private static final Logger LOGGER = Logger
			.getLogger(SocketServerView.class.getName());

	private RMIserverOut serverRMIout;
	private RMIServerIn rmiServer;

	public RMIServerView(int id, ClientViewRemote client, RMIServerIn rmiServer) {
		super(id);
		serverRMIout = new RMIserverOut(id, client);
		this.rmiServer = rmiServer;
		rmiServer.setPlayerID(id);
		rmiServer.addObserver(this);

	}

	public RMIServerIn getRmiServerIn() {
		return rmiServer;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ModelView) {
			serverRMIout.update((ModelView) o, arg); // chiamo i metodi del
														// client
		} else if (o instanceof RMIServerIn) {
			if (arg instanceof PlayerNameMsg) {

				super.setPlayerName(((PlayerNameMsg) arg).getPlayerName());
				LOGGER.info(String.format(
						"Set player name as '%s' for rmiView %d",
						super.getPlayerName(), super.getPlayerID()));

			} else if (arg instanceof UpdateRequestMsg) {

				sendUpdates((UpdateRequestMsg) arg);

			} else if (arg instanceof Message) {

				setChanged();
				notifyObservers(arg); // inoltro al controller
			} else {
				LOGGER.warning(String.format(
						"The socket with id '%d' received an object that is not a message. %n"
								+ "Object received: %s", super.getPlayerID(),
						arg.toString()));
			}

		} else
			System.err.println("osservatore sbagliato" + o);
	}

	private void sendUpdates(UpdateRequestMsg requestReceived) {

		if (requestReceived instanceof UpdateGameBoardMsg) {
			// TODO inutile se solo chi ha il turno può fare richieste
			// sendMessage(new
			// CurrentPlayerUpdatedMsg(super.getModelView().getCurrentPlayerView().getCurrentPlayerNameCopy(),
			// super.getModelView().getCurrentPlayerView().getCurrentPlayerIDCopy()));
			sendMessage(new StateUpdatedMsg(super.getModelView().getStateView()
					.getStateCopy()));
			sendMessage(new AvailableAssistantsUpdatedMsg(super.getModelView()
					.getAvailableAssistantsView().getAvailableAssistantsCopy()));
			sendMessage(new KingBonusesUpdatedMsg(super.getModelView()
					.getKingBonusesView().getShowableKingBonus()));
			sendMessage(new NobilityTrackUpdatedMsg(super.getModelView()
					.getNobilityTrackView().getNobilityTrackCopy()));
			sendMessage(new CitiesColorBonusesUpdatedMsg(super.getModelView()
					.getCitiesColorBonusesView().getBonusGoldCopy(), super
					.getModelView().getCitiesColorBonusesView()
					.getBonusSilverCopy(), super.getModelView()
					.getCitiesColorBonusesView().getBonusBronzeCopy(), super
					.getModelView().getCitiesColorBonusesView()
					.getBonusBlueCopy()));
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
