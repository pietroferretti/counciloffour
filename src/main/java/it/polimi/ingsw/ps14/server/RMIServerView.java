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
	private Server server;
	private RMIServerOut serverRMIout;
	protected boolean active;

	private Timer timer;
	private TimerTask task;

	public RMIServerView(int id, ClientViewRemote client, Server server) {
		super(id);
		this.server = server;
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

		} else {

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
			LOGGER.warning(String.format("Object observed not of type ModelView, but %s", o.getClass()));
	}

	@Override
	public void sendMessage(Message msg) {

		serverRMIout.castMessage(msg);

	}

	public void timerPlayer() {
		int playerID = super.getPlayerID();
		RMIServerView rmiServerView = this;
		
		if (timer != null)
			timer.cancel();
		
		timer = new Timer();
		task = new TimerTask() {

			private int id = playerID;
			private RMIServerView serverView = rmiServerView;
			
			@Override
			public void run() {
				try {
					System.out.println(String.format("RMI client %d disconnected!", id));
					LOGGER.info(String.format("Deregistering RMI client with id '%d'",
							serverView.getPlayerID()));
					server.deregisterConnection(serverView);
					Message message = new DisconnectionMsg(id);
					forwardMessage(message);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}

		};
		timer.schedule(task, TIMEOUT);
	}


}
