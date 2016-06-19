package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.client.RMI.ClientViewRemote;
import it.polimi.ingsw.ps14.model.actions.Action;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIViewRemote extends Remote {

	public void registerClient(
			ClientViewRemote clientStub) 
			throws RemoteException;
	
	public void eseguiAzione(Action action) 
			throws RemoteException;
}
