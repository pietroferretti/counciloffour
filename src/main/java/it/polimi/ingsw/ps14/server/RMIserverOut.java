package it.polimi.ingsw.ps14.server;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.RMI.ClientViewRemote;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableCouncillorsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.CitiesColorBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.ErrorMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameStartedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.MarketUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.NobilityTrackUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.OtherPlayerUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PersonalUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerChangedPrivateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerChangedPublicMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerIDMsg;
import it.polimi.ingsw.ps14.message.fromserver.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.SoldItemMsg;
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;

public class RMIserverOut {
	
	private static final Logger LOGGER = Logger.getLogger(RMIserverOut.class.getName());
	
	ClientViewRemote clientView;
	
	public RMIserverOut(ClientViewRemote clientView)  {
		this.clientView= clientView; 
		//	update(null,new PlayerIDMsg(id));
		// TODO cosa mi significa questo update?
	}

	public void castMessage(Message arg) {
		try {
		if(arg instanceof AvailableAssistantsUpdatedMsg){
			
				clientView.availableAssistantUpdate(((AvailableAssistantsUpdatedMsg)arg).getUpdatedAvailableAssistants());			
		}
		if(arg instanceof AvailableCouncillorsUpdatedMsg){
			clientView.availableCouncillorsUpdate(((AvailableCouncillorsUpdatedMsg)arg).getUpdatedAvailableCouncillors());			
	}
		if(arg instanceof CitiesColorBonusesUpdatedMsg){
			CitiesColorBonusesUpdatedMsg msg=(CitiesColorBonusesUpdatedMsg) arg;
			clientView.citiesColorBonusesUpdate(msg.getUpdatedBonusGold(), msg.getUpdatedBonusSilver(), msg.getUpdatedBonusBronze(), msg.getUpdatedBonusBlue());	
	}
		if(arg instanceof ErrorMsg){
			clientView.error(((ErrorMsg)arg).getPlayerID(),((ErrorMsg)arg).getInfo());			
	}
		if(arg instanceof GameStartedMsg){
			clientView.setGameStart(((GameStartedMsg)arg).getState());			
	}
		if(arg instanceof KingBonusesUpdatedMsg){
			clientView.kingBonusUpdate(((KingBonusesUpdatedMsg)arg).getUpdatedShowableKingBonus());			
	}
		if(arg instanceof KingUpdatedMsg){
			clientView.kingUpdate(((KingUpdatedMsg)arg).getUpdatedKing());			
	}
		if(arg instanceof MarketUpdatedMsg){
			clientView.marketUpdate(((MarketUpdatedMsg)arg).getUpdatedMarket());			
	}
		if(arg instanceof NobilityTrackUpdatedMsg){
			clientView.nobilityTrackUpdate(((NobilityTrackUpdatedMsg)arg).getUpdatedNobilityTrack());			
	}
		if(arg instanceof OtherPlayerUpdateMsg){
			OtherPlayerUpdateMsg msg=(OtherPlayerUpdateMsg)arg;
			clientView.otherPlayerUpdate(msg.getId(), msg.getName(), msg.getColor(), msg.getCoins(), msg.getAssistants(), msg.getLevel(), msg.getPoints(), msg.getNumEmporiums()); 	
	}
		if(arg instanceof PersonalUpdateMsg){
			clientView.personalUpdate(((PersonalUpdateMsg)arg).getPlayer());			
	}
		if(arg instanceof PlayerChangedPrivateMsg){
			clientView.playerChangePrivate(((PlayerChangedPrivateMsg)arg).getPlayerID(), ((PlayerChangedPrivateMsg)arg).toString());			
	}
		if(arg instanceof PlayerChangedPublicMsg){
			clientView.playerChangePublic(((PlayerChangedPublicMsg)arg).getPlayerID(), ((PlayerChangedPublicMsg)arg).toString());			
	}
		if(arg instanceof PlayerIDMsg){
			clientView.setPlayerID(((PlayerIDMsg)arg).getPlayerID());			
	}
		if(arg instanceof RegionUpdatedMsg){
			clientView.regionUpdate(((RegionUpdatedMsg)arg).getUpdatedRegion());			
	}
		if(arg instanceof SoldItemMsg){
			clientView.itemSold(((SoldItemMsg)arg).getItemSold());			
	}
		if(arg instanceof StateUpdatedMsg){
			clientView.stateUpdate(((StateUpdatedMsg)arg).getUpdatedState());			
	}
		
		
		} catch (RemoteException e) {
			LOGGER.log(Level.SEVERE, "Error on the RMI outbound server", e);
		}
	}
	
}
