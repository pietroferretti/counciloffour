package it.polimi.ingsw.ps14.client.RMI;

import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.server.RMIViewRemote;

public class RMICommunication implements Communication {

	RMIViewRemote serverStub;

	public RMICommunication(RMIViewRemote serverStub) {
		this.serverStub = serverStub;
	}

	@Override
	public void setPlayerName(String name) {
		try {
			serverStub.setPlayerName(name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void drawCard(Integer playerID) {
		try {
			serverStub.drawCard(playerID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void electCouncillor(Integer playerID, ColorCouncillor cc,
			String regionORking) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acquireBusinessPermitTile(Integer playerID, RegionType rt,
			Integer permID, List<PoliticCard> politics) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildWithKing(Integer playerID, String city,
			List<PoliticCard> politics) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildWithPermit(Integer playerID, Integer permitID,
			String cityname) {
		// TODO Auto-generated method stub

	}

	@Override
	public void engage(Integer playerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performAdditionalMainAction(Integer playerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void electWithAssistant(Integer playerID, RegionType rt,
			ColorCouncillor cc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void usedCard(Integer permID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void passTurn(Integer playerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMyDetails(Integer playerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDetails(Integer playerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showGamebord(Integer playerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sell(List<ItemForSale> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buy(Integer permID, Integer playerID, Integer quantity) {
		// TODO Auto-generated method stub

	}

}
