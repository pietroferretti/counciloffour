package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.message.Message;

/**
 * It listen to the input socket, when a message is received the proper
 * communication method is invoked.
 * 
 *
 */
public class SocketMessageHandlerIn implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(SocketMessageHandlerIn.class.getName());

	private ObjectInputStream socketIn;
	private final SocketCommunication comm;

	public SocketMessageHandlerIn(SocketCommunication socketCommunication, ObjectInputStream socketIn) {
		comm = socketCommunication;
		this.socketIn = socketIn;
	}

	public void receiveMessage(Message message) throws IOException {
		comm.receiveMessage(message);
	};

	@Override
	public void run() {

		while (true) {

			try {
				Object object = socketIn.readObject();

				if (object instanceof Message) {
					receiveMessage((Message) object);
				} else {
					LOGGER.warning(String.format(
							"The socket received an object that is not a message. %n" + "Object received: %s",
							object.toString()));
				}
			} catch (ClassNotFoundException | IOException e) {
				LOGGER.log(Level.SEVERE, "Error reading from socket.", e);
				return;
			}
		}
	}

}
