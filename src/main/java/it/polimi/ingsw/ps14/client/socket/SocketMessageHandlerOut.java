package it.polimi.ingsw.ps14.client.socket;

import it.polimi.ingsw.ps14.message.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMessageHandlerOut implements Runnable{
	private static final Logger LOGGER = Logger.getLogger(SocketMessageHandlerOut.class.getName());

	private ObjectOutputStream socketOut;
	
	public SocketMessageHandlerOut(ObjectOutputStream socketOut) {
		this.socketOut = socketOut;
	}

	public void sendMessage(Message message) {

		try {
			
			System.out.println(String.format("Sending message %s on socket", message.toString()));	// per debug, da togliere?
			socketOut.writeObject(message);
			socketOut.flush();

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error writing on socket", e);
		}
	}

	@Override
	public void run() {
	}

}
