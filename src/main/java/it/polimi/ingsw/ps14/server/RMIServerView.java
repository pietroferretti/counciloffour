package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.client.RMI.ClientViewRemote;

import java.util.Observable;

/**
 * 
 * there is a server view for each client (rmi and socket),  for rmi this contains a clientStub and playerID
 *
 */
public class RMIServerView extends ServerView{
	
	private ClientViewRemote client;

	public RMIServerView(int id,ClientViewRemote client) {
		super(id);
		this.client=client;
	}
	
	

	public ClientViewRemote getClient() {
		return client;
	}



	public void setClient(ClientViewRemote client) {
		this.client = client;
	}



	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
