package it.polimi.ingsw.ps14.client.RMI;

import it.polimi.ingsw.ps14.model.ColorCouncillor;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Observable;

/**
 * this class define method callable on the client
 * 
 *
 */

public class ClientRMIView extends UnicastRemoteObject implements ClientViewRemote, Serializable{
	
	public ClientRMIView() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6111979881550001331L;

	public void updateClient(Observable o,
	          Object arg) throws RemoteException {
		System.out.println(arg.toString());
	}

	@Override
	public void availableAssistantUpdate(int assistantUpadated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void availableCouncillorsUpdate(
			Map<ColorCouncillor, Integer> updatedAvailableCouncillors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void citiesColorBonusesUpdate(int updatedBonusGold,
			int updatedBonusSilver, int updatedBonusBronze, int updatedBonusBlue) {
		// TODO Auto-generated method stub
		
	}



}
