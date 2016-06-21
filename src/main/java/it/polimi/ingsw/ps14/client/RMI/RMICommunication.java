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
			System.err.println("Errore nell'invocazione del metodo");
		}
	}

	@Override
	public void drawCard(Integer playerID) {
		try {
			serverStub.drawCard(playerID);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void electCouncillor(Integer playerID, ColorCouncillor cc,
			String regionORking) {
		try {
			serverStub.electCouncillor(playerID, cc, regionORking);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void acquireBusinessPermitTile(Integer playerID, RegionType rt,
			Integer permID, List<PoliticCard> politics) {
		try {
			serverStub
					.acquireBusinessPermitTile(playerID, rt, permID, politics);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void buildWithKing(Integer playerID, String city,
			List<PoliticCard> politics) {
		try {
			serverStub.buildWithKing(playerID, city, politics);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void buildWithPermit(Integer playerID, Integer permitID,
			String cityname) {
		try {
			serverStub.buildWithPermit(playerID, permitID, cityname);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void engage(Integer playerID) {
		try {
			serverStub.engage(playerID);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt) {
		try {
			serverStub.changeBusinessPermitTiles(playerID, rt);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void performAdditionalMainAction(Integer playerID) {
		try {
			serverStub.performAdditionalMainAction(playerID);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void electWithAssistant(Integer playerID, RegionType rt,
			ColorCouncillor cc) {
		try {
			serverStub.electWithAssistant(playerID, rt, cc);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void passTurn(Integer playerID) {
		try {
			serverStub.passTurn(playerID);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void showMyDetails(Integer playerID) {
		try {
			serverStub.showMyDetails(playerID);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void showDetails(Integer playerID) {
		try {
			serverStub.showDetails(playerID);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void showGamebord(Integer playerID) {
		try {
			serverStub.showGamebord(playerID);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void sell(List<ItemForSale> items) {
		try {
			serverStub.sell(items);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

	@Override
	public void buy(Integer permID, Integer playerID, Integer quantity) {
		try {
			serverStub.buy(permID, playerID, quantity);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}
	
	@Override
	public void answerNobilityRequest(List<String> objectIDs) {
		try {
			serverStub.answerNobilityRequest(objectIDs);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}

}
