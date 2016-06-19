package it.polimi.ingsw.ps14.client.RMI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;

public class ClientRMIView extends UnicastRemoteObject implements ClientViewRemote, Serializable,ClientFolda{
	
	private Integer id;

	protected ClientRMIView() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setID(Integer id){
		this.id=id;
	}

	public Integer getID(){
		return id;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6111979881550001331L;

	public void updateClient(Observable o,
	          Object arg) throws RemoteException {
		System.out.println(arg.toString());
	}
}
