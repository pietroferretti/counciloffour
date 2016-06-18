package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.ClientView;
import it.polimi.ingsw.ps14.client.MessageHandlerIn;
import it.polimi.ingsw.ps14.message.Message;

public class SocketMessageHandlerIn extends MessageHandlerIn {
	private static final Logger LOGGER = Logger.getLogger(SocketMessageHandlerIn.class.getName());
	
	private ObjectInputStream socketIn;
	
	public SocketMessageHandlerIn(ClientView client, ObjectInputStream socketIn) {
		super(client);
		this.socketIn = socketIn;
	}
	
	@Override
	public void run() {

		while (true) {

			try {
				Object object = socketIn.readObject();
				
				LOGGER.info(String.format("Received object %s", object));
				
				if (object instanceof Message) {
					handleMessage((Message) object);
				} else {
					LOGGER.warning(String.format("The socket received an object that is not a message. %n"
							+ "Object received: %s", object.toString()));
				}
			} catch (ClassNotFoundException | IOException e ) {
				LOGGER.log(Level.SEVERE, "Error reading from socket.", e);
				return;
			}
		}
	}

}
