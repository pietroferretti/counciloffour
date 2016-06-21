package it.polimi.ingsw.ps14.client.RMI;

import it.polimi.ingsw.ps14.model.ColorCouncillor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
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
	
	public void availableAssistantUpdate(int assistantUpadated);
	public void availableCouncillorsUpdate(Map<ColorCouncillor, Integer> updatedAvailableCouncillors);
	public void citiesColorBonusesUpdate(int updatedBonusGold,int updatedBonusSilver, int updatedBonusBronze, int updatedBonusBlue);
	
}
