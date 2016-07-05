package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.message.Message;
/**
 * write object in dedicated server IN socket
 * @author federico
 *
 */
public class SocketMessageHandlerOut {
	private static final Logger LOGGER = Logger.getLogger(SocketMessageHandlerOut.class.getName());

	private ObjectOutputStream socketOut;

	
	public SocketMessageHandlerOut(ObjectOutputStream socketOut) {
		this.socketOut = socketOut;                   
	}

	public synchronized void sendMessage(Message message) {

		try {
			
			LOGGER.info(String.format("Sending message %s on socket", message.getClass()));	
			socketOut.writeObject(message);
			socketOut.flush();

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error writing on socket", e);
		}
	}

}
