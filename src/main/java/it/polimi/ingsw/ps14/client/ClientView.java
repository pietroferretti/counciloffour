package it.polimi.ingsw.ps14.client;

import it.polimi.ingsw.ps14.message.Message;

public abstract class ClientView implements Runnable {

	
	
	
	public abstract void readMessage(Message message);
	
	@Override
	public abstract void run();
}
