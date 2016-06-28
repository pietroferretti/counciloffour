package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.client.rmi.ClientViewRemote;
import it.polimi.ingsw.ps14.message.DisconnectionMsg;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.MyChatMsg;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateRequestMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerIDMsg;
import it.polimi.ingsw.ps14.message.fromserver.PrivateMessage;
import it.polimi.ingsw.ps14.model.modelview.ModelView;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * 
 * there is a server view for each client (rmi and socket), for rmi this
 * contains a clientStub, playerID, serverRMIout
 *
 */
public class RMIServerView extends ServerView {
	private static final Logger LOGGER = Logger.getLogger(RMIServerView.class
			.getName());

	private static final int TIMEOUT = 3000;
	private RMIServerOut serverRMIout;
	protected boolean active;

	private Timer timer;
	private TimerTask task;

	public RMIServerView(int id, ClientViewRemote client) {
		super(id);
		serverRMIout = new RMIServerOut(client);
		serverRMIout.castMessage(new PlayerIDMsg(id));
		LOGGER.info(String.format("Sent id to player %d", super.getPlayerID()));
	}

	public void forwardMessage(Message msg) {
		if (msg instanceof PlayerNameMsg) {

			super.setPlayerName(((PlayerNameMsg) msg).getPlayerName());
			LOGGER.info(String.format("Set player name as '%s' for rmiView %d",
					super.getPlayerName(), super.getPlayerID()));
			setChanged();
			notifyObservers(msg);
			
		}else if(msg instanceof MyChatMsg){
			
			super.chat.sendChat(((MyChatMsg)msg).getText(),super.getPlayerName());

		} else if (msg instanceof UpdateRequestMsg) {

			sendUpdates((UpdateRequestMsg) msg);

		} else if (msg instanceof Message) {

			setChanged();
			notifyObservers(msg); 	// forward message to controller
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
			} else if (arg != null) {
				LOGGER.warning(String.format(
						"The server view with id '%d' received an object that is not a message. %n"
								+ "Object received: %s", super.getPlayerID(),
						arg));
			}

		} else
			System.err.println("osservatore sbagliato" + o);
	}

	// private void sendUpdates(UpdateRequestMsg requestReceived) {
	//
	// if (requestReceived instanceof UpdateGameBoardMsg) {
	// sendMessage(new
	// StateUpdatedMsg(super.getModelView().getStateView().getStateCopy()));
	// sendMessage(new AvailableAssistantsUpdatedMsg(
	// super.getModelView().getAvailableAssistantsView().getAvailableAssistantsCopy()));
	// sendMessage(new
	// KingBonusesUpdatedMsg(super.getModelView().getKingBonusesView().getShowableKingBonus()));
	// sendMessage(
	// new
	// NobilityTrackUpdatedMsg(super.getModelView().getNobilityTrackView().getNobilityTrackCopy()));
	// sendMessage(new CitiesColorBonusesUpdatedMsg(
	// super.getModelView().getCitiesColorBonusesView().getBonusGoldCopy(),
	// super.getModelView().getCitiesColorBonusesView().getBonusSilverCopy(),
	// super.getModelView().getCitiesColorBonusesView().getBonusBronzeCopy(),
	// super.getModelView().getCitiesColorBonusesView().getBonusBlueCopy()));
	// sendMessage(new
	// KingUpdatedMsg(super.getModelView().getKingView().getKingCopy()));
	// for (RegionView rv : super.getModelView().getRegionsView()) {
	// sendMessage(new RegionUpdatedMsg(rv.getRegionCopy()));
	// }
	// } else if (requestReceived instanceof UpdateThisPlayerMsg) {
	// sendPersonalUpdate();
	// } else if (requestReceived instanceof UpdateOtherPlayersMsg) {
	// sendOthersUpdate();
	// }
	// }

	// private void sendOthersUpdate() {
	// for (PlayerView pv : super.getModelView().getPlayersView()) {
	// if (pv.getPlayerCopy().getId() != super.getPlayerID())
	// sendMessage(new OtherPlayerUpdateMsg(pv.getPlayerCopy()));
	// }
	// }
	//
	// private void sendPersonalUpdate() {
	// sendMessage(new
	// PersonalUpdateMsg(super.getModelView().getPlayerByID(super.getPlayerID())));
	// }

	@Override
	public void sendMessage(Message msg) {

		serverRMIout.castMessage(msg);
		LOGGER.info(String.format("callind method %s on rmi %d", msg,
				super.getPlayerID()));

	}

	public void timerPlayer() {
		int id = super.getPlayerID();
		
		if (timer != null)
			timer.cancel();
		
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				Message message = new DisconnectionMsg(id);
				forwardMessage(message);
				System.out.println("CLIENT DISCONNESSO");
			}

		};
		timer.schedule(task, TIMEOUT);
	}


}
