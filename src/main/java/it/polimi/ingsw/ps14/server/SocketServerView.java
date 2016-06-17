package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromServer.PlayerIDMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServerView extends ServerView implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(SocketServerView.class.getName());

	private Socket socket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private Server server;

	private boolean active = true;

	public SocketServerView(Socket socket, Server server, int idPlayer)
			throws IOException {
		super(idPlayer);
		this.socket = socket;
		this.server = server;
		this.socketIn = new ObjectInputStream(socket.getInputStream());
		this.socketOut = new ObjectOutputStream(socket.getOutputStream());
		socketOut.writeObject(new PlayerIDMsg(idPlayer));
	}

	private synchronized boolean isActive() {
		return active;
	}
	
	private void close() {
		try {
			socket.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, String.format("Error when closing the socket with id '%d'", super.getPlayerID()), e);
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
				
				if (objectReceived instanceof Message) {
					notifyObservers(objectReceived);
				} else {
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

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Message) {
			try {
				socketOut.writeObject(arg);
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, String.format("Error while writing on socket with id '%d'", super.getPlayerID()), e);
			}
		} else {
			LOGGER.warning(String.format("The server view with id '%d' received an object that is not a message. %n"
											+ "Object received: %s", super.getPlayerID(), arg.toString()));
		}
	}

}
