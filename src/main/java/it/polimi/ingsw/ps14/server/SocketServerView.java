package it.polimi.ingsw.ps14.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateOtherPlayerMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateRequestMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.GamePhaseUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.NobilityTrackUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.OtherPlayerUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PersonalUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerIDMsg;
import it.polimi.ingsw.ps14.message.fromserver.PrivateMessage;
import it.polimi.ingsw.ps14.message.fromserver.RegionBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.model.modelview.PlayerView;
import it.polimi.ingsw.ps14.model.modelview.RegionView;

public class SocketServerView extends ServerView implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(SocketServerView.class.getName());

	private Socket socket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private Server server;
	private ModelView modelCopy;

	private boolean active = true;

	public SocketServerView(Socket socket, Server server, int idPlayer) throws IOException {
		super(idPlayer);
		this.socket = socket;
		this.server = server;
		this.socketIn = new ObjectInputStream(socket.getInputStream());
		this.socketOut = new ObjectOutputStream(socket.getOutputStream());
		socketOut.writeObject(new PlayerIDMsg(idPlayer));
		LOGGER.info(String.format("Sent id to player %d", super.getPlayerID()));
	}

	public void setModelCopy(ModelView modelCopy) {
		this.modelCopy = modelCopy;
	}

	private synchronized boolean isActive() {
		return active;
	}

	private void close() {
		try {
			socket.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, String.format("Error when closing the socket with id '%d'", super.getPlayerID()),
					e);
		}
		active = false;

		LOGGER.info(String.format("Deregistering socket with id '%d'", super.getPlayerID()));
		server.deregisterConnection(this);
	}

	@Override
	public void run() {

		// continua ad aspettare messaggi dal socket
		// se ricevi messaggi -> notifyObservers

		try {
			while (this.isActive()) {

				Object objectReceived = socketIn.readObject();

				LOGGER.info(String.format("Received object %s", objectReceived));

				if (!(objectReceived instanceof UpdateRequestMsg && objectReceived instanceof Message)) {
					setChanged();
					notifyObservers(objectReceived);
				}
				else if (objectReceived instanceof UpdateRequestMsg)
					sendUpdates((UpdateRequestMsg) objectReceived);

				else {
					LOGGER.warning(String.format("The socket with id '%d' received an object that is not a message. %n"
							+ "Object received: %s", super.getPlayerID(), objectReceived.toString()));
				}
			}
		} catch (IOException | NoSuchElementException | ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, String.format("Error in socket view with id '%d'", super.getPlayerID()), e);
		} finally {
			close();
		}

	}

	private void sendOthersUpdate() {
		for (PlayerView pv : modelCopy.getPlayersView()) {
			if (pv.getPlayerCopy().getId() != super.getPlayerID())
				sendMessage(new OtherPlayerUpdateMsg(pv.getPlayerCopy()));
		}
	}

	private void sendPersonalUpdate() {
		sendMessage(new PersonalUpdateMsg(modelCopy.getPlayerByID(super.getPlayerID())));
	}

	/**
	 * It sends the requested updates to the client; it build messages with the
	 * {@link ModelView} objects.
	 * 
	 * @param objectReceived
	 *            - Request for updates.
	 */
	private void sendUpdates(UpdateRequestMsg requestReceived) {
		if (requestReceived instanceof UpdateGameBoardMsg) {
			// TODO inutile se solo chi ha il turno può fare richieste
			// sendMessage(new
			// CurrentPlayerUpdatedMsg(modelCopy.getCurrentPlayerView().getCurrentPlayerNameCopy(),
			// modelCopy.getCurrentPlayerView().getCurrentPlayerIDCopy()));
			sendMessage(new GamePhaseUpdatedMsg(modelCopy.getGamePhaseView().getGamePhaseCopy()));
			sendMessage(new AvailableAssistantsUpdatedMsg(
					modelCopy.getAvailableAssistantsView().getAvailableAssistantsCopy()));
			sendMessage(new KingBonusesUpdatedMsg(modelCopy.getKingBonusesView().getShowableKingBonus()));
			sendMessage(new NobilityTrackUpdatedMsg(modelCopy.getNobilityTrackView().getNobilityTrackCopy()));
			sendMessage(new RegionBonusesUpdatedMsg(modelCopy.getRegionBonusesView().getBonusGoldCopy(),
					modelCopy.getRegionBonusesView().getBonusSilverCopy(),
					modelCopy.getRegionBonusesView().getBonusBronzeCopy(),
					modelCopy.getRegionBonusesView().getBonusBlueCopy()));
			for (RegionView rv : modelCopy.getRegionsView()) {
				sendMessage(new RegionUpdatedMsg(rv.getRegionCopy()));
			}
			sendPersonalUpdate();
			sendOthersUpdate();

		} else if (requestReceived instanceof UpdateThisPlayerMsg) {
			sendPersonalUpdate();
		} else if (requestReceived instanceof UpdateOtherPlayerMsg) {
			sendOthersUpdate();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof PrivateMessage) {
			if (((PrivateMessage) arg).getPlayerID() == super.getPlayerID()) {
				sendMessage((Message) arg);
			}
		} else if (arg instanceof Message) {
			sendMessage((Message) arg);
		} else {
			LOGGER.warning(String.format(
					"The server view with id '%d' received an object that is not a message. %n" + "Object received: %s",
					super.getPlayerID(), arg.toString()));
		}
	}

	public void sendMessage(Message msg) {
		try {
			socketOut.writeObject(msg);
			socketOut.flush();
			LOGGER.info(String.format("Writing message %s on socket %d", msg, super.getPlayerID()));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, String.format("Error while writing on socket with id '%d'", super.getPlayerID()),
					e);
		}
	}

}
