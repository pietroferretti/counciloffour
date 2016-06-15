package it.polimi.ingsw.ps14.client;

import it.polimi.ingsw.ps14.message.Message;

public abstract class MessageHandlerIn implements Runnable {

	private final ClientView client;
	
	public MessageHandlerIn(ClientView client) {
		this.client = client;
	}
	
	public void handleMessage(Message message) {
		client.handleMessage(message);
	};
	
	@Override
	public abstract void run();
}
