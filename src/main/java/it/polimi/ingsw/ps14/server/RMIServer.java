package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.client.RMI.ClientViewRemote;
import it.polimi.ingsw.ps14.model.actions.Action;

import java.rmi.RemoteException;
import java.util.Observable;

public class RMIServer extends Observable implements RMIViewRemote {

	private Server server;

	public RMIServer(Server server) {
		this.server=server;
	}

	public void registerClient(ClientViewRemote clientStub) throws RemoteException {
		System.out.println("CLIENT REGISTRATO");
		server.registerWaitingConnectionRMI(clientStub);
		server.meeting();
	}


	public void eseguiAzione(Action action) throws RemoteException {
		System.out.println("I am the RMI view I am notifying my observers");
		setChanged();
		this.notifyObservers(action);
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
