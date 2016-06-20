package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.MessageHandlerOut;
import it.polimi.ingsw.ps14.message.Message;

public class SocketMessageHandlerOut implements MessageHandlerOut {
	private static final Logger LOGGER = Logger.getLogger(SocketMessageHandlerOut.class.getName());

	private ObjectOutputStream socketOut;
	
	public SocketMessageHandlerOut(ObjectOutputStream socketOut) {
		this.socketOut = socketOut;
	}

	@Override
	public void handleMessage(Message message) {

		try {
			
			System.out.println(String.format("Sending message %s on socket", message.toString()));	// per debug, da togliere?
			socketOut.writeObject(message);
			socketOut.flush();

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error writing on socket", e);
		}
	}

}
