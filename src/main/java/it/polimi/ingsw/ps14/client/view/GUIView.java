package it.polimi.ingsw.ps14.client.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;

public class GUIView extends ClientView implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCommunication(Communication communication) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showError(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showGameStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showKingBonus(int updatedShowableKingBonus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showKingUpdate(King updatedKing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMarket(Market updatedMarket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showNobilityTrack(NobilityTrack updatedNobilityTrack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPersonalDetails(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPlayerChangesPrivate(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPlayerChangesPublic(String notice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPrivateMsg(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showRegion(Region updatedRegion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showItemSold(ItemForSale item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showOtherPlayer(int id, String name, Color color, int coins,
			int assistants, int level, int points, int numEmporiums) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAvailableAssistant(int update) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAvailableCouncillor(
			Map<ColorCouncillor, Integer> updatedAvailableCouncillors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showCitiesColorBonuses(int updatedBonusGold,
			int updatedBonusSilver, int updatedBonusBronze, int updatedBonusBlue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showEndGame(List<List<String>> endResults) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAvailableCommands() {
		// TODO Auto-generated method stub
		
	}



}