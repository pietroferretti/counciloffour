package it.polimi.ingsw.ps14.client.socket;

import it.polimi.ingsw.ps14.client.ClientView;
import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromclient.ChooseUsedPermitMsg;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellMsg;
import it.polimi.ingsw.ps14.message.fromclient.TurnActionMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateOtherPlayersMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermiteTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumWithHelpOfKingAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.market.BuyAction;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.ChangeBusinessPermitTilesAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.EngageAssistantAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.PerformAdditionalMainActionAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.SendAssistantToElectCouncillorAction;

import java.util.ArrayList;
import java.util.List;

public class SocketCommunication implements Communication {

	private SocketMessageHandlerOut msgHandlerOut;
	private ClientView clientView;
	
	public SocketCommunication(SocketMessageHandlerOut msgHandlerOut,ClientView clientView) {
		this.msgHandlerOut = msgHandlerOut;
		this.clientView=clientView;
	}
	
	public void receiveMessage(Message msg){
		//TODO
		
		//clientView.stampamessaggio(); MANDI AL CLIENT VIEW QUELLO DA STAMPARE
		
	}

public void sendPlayerName(String name){//TODO
	msgHandlerOut.sendMessage(new PlayerNameMsg(name));
}
	
	@Override
	public void drawCard(Integer playerID) {
		msgHandlerOut.sendMessage(new TurnActionMsg(new DrawCardAction(
				playerID)));
	}

	@Override
	public void electCouncillor(Integer playerID, ColorCouncillor cc,
			String regionORking) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new ElectCouncillorAction(playerID, cc, regionORking)));
	}

	@Override
	public void acquireBusinessPermitTile(Integer playerID, RegionType rt,
			Integer permID, List<PoliticCard> politics) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new AcquireBusinessPermiteTileAction(permID, rt, permID,
						new ArrayList<PoliticCard>(politics))));
	}

	@Override
	public void buildWithKing(Integer playerID, String city,
			List<PoliticCard> politics) {
		// TODO Auto-generated method stub
		msgHandlerOut
				.sendMessage(new TurnActionMsg(
						new BuildEmporiumWithHelpOfKingAction(playerID, city,
								politics)));

	}

	@Override
	public void buildWithPermit(Integer playerID, Integer permitID,
			String cityname) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new BuildEmporiumUsingPermitTileAction(playerID, permitID,
						cityname)));

	}

	@Override
	public void engage(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new EngageAssistantAction(playerID)));

	}

	@Override
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new ChangeBusinessPermitTilesAction(playerID, rt)));

	}

	@Override
	public void performAdditionalMainAction(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new PerformAdditionalMainActionAction(playerID)));

	}

	@Override
	public void electWithAssistant(Integer playerID, RegionType rt,
			ColorCouncillor cc) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new SendAssistantToElectCouncillorAction(playerID, rt, cc)));

	}

	@Override
	public void usedCard(Integer permID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new ChooseUsedPermitMsg(permID));

	}

	@Override
	public void passTurn(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(new EndTurnAction(
				playerID)));

	}

	@Override
	public void showMyDetails(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new UpdateThisPlayerMsg(playerID));

	}

	@Override
	public void showDetails(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new UpdateOtherPlayersMsg(playerID));

	}

	@Override
	public void showGamebord(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new UpdateGameBoardMsg());

	}

	@Override
	public void sell(List<ItemForSale> items) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new SellMsg(new SellAction(items)));

	}

	@Override
	public void buy(Integer permID, Integer playerID, Integer quantity) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new BuyMsg(new BuyAction(permID, playerID,
				quantity)));

	}

}