package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.message.DisconnectionMsg;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.MyChatMsg;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateRequestMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerIDMsg;
import it.polimi.ingsw.ps14.message.fromserver.PrivateMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServerView extends ServerView implements Runnable {
	private static final Logger LOGGER = Logger
			.getLogger(SocketServerView.class.getName());

	private Socket socket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private Server server;

	// private int timeOut;
	// private TimerTask timerTask;
	// private Timer timer;

	private boolean active = true;

	public SocketServerView(Socket socket, Server server, int idPlayer)
			throws IOException {
		super(idPlayer);
		this.socket = socket;
		this.server = server;
		this.socketIn = new ObjectInputStream(socket.getInputStream());
		this.socketOut = new ObjectOutputStream(socket.getOutputStream());
		socketOut.writeObject(new PlayerIDMsg(idPlayer));
		LOGGER.info(String.format("Sent id to player %d", super.getPlayerID()));
		// this.timeOut=timeOut;
	}

	private boolean isActive() {
		return active;
	}

	private void close() {
		try {
			socket.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, String.format(
					"Error when closing the socket with id '%d'",
					super.getPlayerID()), e);
		}
		active = false;

		LOGGER.info(String.format("Deregistering socket with id '%d'",
				super.getPlayerID()));
		server.deregisterConnection(this);

	}

	@Override
	public void run() {

		// continua ad aspettare messaggi dal socket
		// se ricevi messaggi -> notifyObservers

		try {
			while (this.isActive()) {

				Object objectReceived = socketIn.readObject();

				LOGGER.info(String.format("Received object %s",
						objectReceived.getClass()));

				if (objectReceived instanceof PlayerNameMsg) {

					super.setPlayerName(((PlayerNameMsg) objectReceived)
							.getPlayerName());
					LOGGER.info(String.format(
							"Set player name as '%s' for socketview %d",
							super.getPlayerName(), super.getPlayerID()));
					setChanged();
					notifyObservers(objectReceived);

				} else if (objectReceived instanceof UpdateRequestMsg) {

					sendUpdates((UpdateRequestMsg) objectReceived);

				} else if (objectReceived instanceof MyChatMsg) {
					
					super.chat.sendChat(((MyChatMsg)objectReceived).getText(),super.getPlayerName());
					
					

				} else if (objectReceived instanceof Message) {

					setChanged();
					notifyObservers(objectReceived);

				} else {
					LOGGER.warning(String.format(
							"The socket with id '%d' received an object that is not a message. %n"
									+ "Object received: %s",
							super.getPlayerID(), objectReceived.toString()));
				}
			}
		} catch (IOException | NoSuchElementException | ClassNotFoundException e) {
			LOGGER.log(
					Level.SEVERE,
					String.format("CLIENT DISCONNESSO id '%d'",
							super.getPlayerID()));
			Message disconnect = new DisconnectionMsg(getPlayerID());
			setChanged();
			notifyObservers(disconnect);
			this.active = false;
		} finally {
			close();
		}

	}

	// private void sendOthersUpdate() {
	// for (PlayerView pv : super.getModelView().getPlayersView()) {
	// if (pv.getPlayerCopy().getId() != super.getPlayerID())
	// sendMessage(new OtherPlayerUpdateMsg(pv.getPlayerCopy()));
	// }
	// }

	// private void sendPersonalUpdate() {
	// sendMessage(new
	// PersonalUpdateMsg(super.getModelView().getPlayerByID(super.getPlayerID())));
	// }

	// /**
	// * It sends the requested updates to the client; it build messages with
	// the
	// * {@link ModelView} objects.
	// *
	// * @param objectReceived
	// * - Request for updates.
	// */
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
	// sendMessage(new
	// KingUpdatedMsg(super.getModelView().getKingView().getKingCopy()));
	// sendMessage(new CitiesColorBonusesUpdatedMsg(
	// super.getModelView().getCitiesColorBonusesView().getBonusGoldCopy(),
	// super.getModelView().getCitiesColorBonusesView().getBonusSilverCopy(),
	// super.getModelView().getCitiesColorBonusesView().getBonusBronzeCopy(),
	// super.getModelView().getCitiesColorBonusesView().getBonusBlueCopy()));
	// sendMessage(new
	// RegionUpdatedMsg(super.getModelView().getRegionsView().get(0).getRegionCopy()));
	// for (RegionView rv : super.getModelView().getRegionsView()) {
	// sendMessage(new RegionUpdatedMsg(rv.getRegionCopy()));
	// }
	//
	// } else if (requestReceived instanceof UpdateThisPlayerMsg) {
	// sendPersonalUpdate();
	// } else if (requestReceived instanceof UpdateOtherPlayersMsg) {
	// sendOthersUpdate();
	// }
	// }

	@Override
	public void update(Observable o, Object arg) {
		if (this.isActive()) {
			if (arg instanceof PrivateMessage) {
				if (((PrivateMessage) arg).getPlayerID() == super.getPlayerID()) {
					sendMessage((Message) arg);
				}
			} else if (arg instanceof Message) {
				sendMessage((Message) arg);
			} else if (arg != null) {
				LOGGER.warning(String.format(
						"The server view with id '%d' received an object that is not a message. %n"
								+ "Object received: %s", super.getPlayerID(),
						arg.toString()));
			}
		}
	}

	@Override
	protected void sendMessage(Message msg) {
		try {
			if (isActive()) {
				socketOut.writeObject(msg);
				socketOut.flush();
				LOGGER.info(String.format("Writing message %s on socket %d",
						msg.getClass(), super.getPlayerID()));
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, String.format(
					"Error while writing on socket with id '%d'",
					super.getPlayerID()), e);
		}
	}


}
