package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.client.RMI.ClientViewRemote;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.view.View;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class RMIServer implements Remote {

	private Set<ClientViewRemote> clients;
	private Server server;

	public RMIServer(Server server) {
		this.clients = new HashSet<>();
		this.server=server;
	}

	public void registerClient(ClientViewRemote clientStub) throws RemoteException {
		System.out.println("CLIENT REGISTRATO");
		server.registerWaitingConnectionRMI(clientStub);
		server.meeting();
		this.clients.add(clientStub);
	}


	public void eseguiAzione(Action action) throws RemoteException {
		System.out.println("I am the RMI view I am notifying my observers");
		this.notifyObservers(action);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public Set<ClientViewRemote> getRMIclients(){
		return clients;
	}

}
