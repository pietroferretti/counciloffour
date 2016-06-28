package it.polimi.ingsw.ps14.client.rmi;

import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.client.view.ClientView;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.server.ServerViewRemote;

public class RMICommunication implements Communication {

	private ServerViewRemote serverStub;
	private ClientView clientView;

	public RMICommunication(ServerViewRemote serverStub, ClientView clientView) throws RemoteException {
		this.serverStub = serverStub;
		this.clientView = clientView;
	}

	
	
	@Override
	public void setPlayerName(Integer playerID, String name) {
		
			try {
				serverStub.setPlayerName(playerID, name);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void drawCard(Integer playerID) {
		
			try {
				serverStub.drawCard(playerID);
				System.out.println("calling drawcard method");

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void electCouncillor(Integer playerID, ColorCouncillor cc,
			String regionORking) {
		
			try {
				serverStub.electCouncillor(playerID, cc, regionORking);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void acquireBusinessPermitTile(Integer playerID, RegionType rt,
			Integer permID, List<PoliticCard> politics)  {
			try {
				serverStub
						.acquireBusinessPermitTile(playerID, rt, permID, politics);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void buildWithKing(Integer playerID, String city,
			List<PoliticCard> politics)  {
			try {
				serverStub.buildWithKing(playerID, city, politics);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void buildWithPermit(Integer playerID, Integer permitID,
			String cityname)  {
			try {
				serverStub.buildWithPermit(playerID, permitID, cityname);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void engage(Integer playerID)  {
			try {
				serverStub.engage(playerID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt) {
			try {
				serverStub.changeBusinessPermitTiles(playerID, rt);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void performAdditionalMainAction(Integer playerID) {
			try {
				serverStub.performAdditionalMainAction(playerID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void electWithAssistant(Integer playerID, RegionType rt,
			ColorCouncillor cc)  {
			try {
				serverStub.electWithAssistant(playerID, rt, cc);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void passTurn(Integer playerID) {
			try {
				serverStub.passTurn(playerID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void showMyDetails(Integer playerID) {
			try {
				serverStub.showMyDetails(playerID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void showDetails(Integer playerID) {
			try {
				serverStub.showDetails(playerID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void showGameboard(Integer playerID) {
			try {
				serverStub.showGameboard(playerID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void sell(Integer playerID, List<ItemForSale> items){
			try {
				serverStub.sell(playerID, items);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void buy(Integer playerID, Integer objID, Integer quantity) {
			try {
				serverStub.buy(playerID, objID, quantity);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Override
	public void answerNobilityRequest(Integer playerID, List<String> objectIDs) {
		try {
			serverStub.answerNobilityRequest(playerID, objectIDs);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}



	@Override
	public void sellNone(Integer playerID) {
		try {
			serverStub.sellNone(playerID);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}		
	}



	@Override
	public void doneFinishBuying(Integer playerID) {
		try {
			serverStub.doneBuying(playerID);
		} catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}		
	}
	
	public void clientAlive(Integer playerID){
		try{
			serverStub.clientAlive(playerID);
		}catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
	}



	@Override
	public void chat(Integer playerID, String chat) {
		try{
			serverStub.chat(playerID,chat);
		}catch (RemoteException e) {
			System.err.println("Errore nell'invocazione del metodo");

		}
		
	}

}
