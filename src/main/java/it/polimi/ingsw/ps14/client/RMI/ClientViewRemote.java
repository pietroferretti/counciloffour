package it.polimi.ingsw.ps14.client.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observable;
/**
 * interface of client stub that server will have
 * 
 *
 */
public interface ClientViewRemote extends Remote{
	
	
	//metodi invocabili dal server sul client
	public void updateClient(Observable o,
	          Object arg) 
			throws RemoteException;
}
