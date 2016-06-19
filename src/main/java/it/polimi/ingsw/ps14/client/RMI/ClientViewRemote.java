package it.polimi.ingsw.ps14.client.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observable;

public interface ClientViewRemote
extends Remote{

	public void setID(Integer id);

	public Integer getID();
	
	//metodi invocabili dal server sul client
	public void updateClient(Observable o,
	          Object arg) 
			throws RemoteException;
}
