package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.server.SocketServerView;

/**
 * It write objects in dedicated server IN socket ({@link SocketServerView}).
 *
 */
public class SocketMessageHandlerOut {
	private static final Logger LOGGER = Logger.getLogger(SocketMessageHandlerOut.class.getName());

	private ObjectOutputStream socketOut;

	public SocketMessageHandlerOut(ObjectOutputStream socketOut) {
		this.socketOut = socketOut;
	}

	/**
	 * Forward a {@link Message} through socket.
	 * 
	 * @param message
	 */
	public synchronized void sendMessage(Message message) {

		try {

			socketOut.writeObject(message);
			socketOut.flush();

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error writing on socket", e);
		}
	}

}
