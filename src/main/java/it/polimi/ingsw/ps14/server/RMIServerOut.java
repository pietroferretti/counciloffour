package it.polimi.ingsw.ps14.server;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.rmi.ClientViewRemote;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableCouncillorsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.ChatMsg;
import it.polimi.ingsw.ps14.message.fromserver.CitiesColorBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameEndedMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameStartedMsg;
import it.polimi.ingsw.ps14.message.fromserver.InfoPrivateMsg;
import it.polimi.ingsw.ps14.message.fromserver.InfoPublicMsg;
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
import it.polimi.ingsw.ps14.model.ColorCity;

public class RMIServerOut {

	private static final Logger LOGGER = Logger.getLogger(RMIServerOut.class
			.getName());

	ClientViewRemote clientView;
	private boolean active;

	public RMIServerOut(ClientViewRemote clientView) {
		this.clientView = clientView;
		active = true;
	}

	public void castMessage(Message arg) {
		try {
			if (active) {

				if (arg instanceof AvailableAssistantsUpdatedMsg) {
					clientView
							.availableAssistantUpdate(((AvailableAssistantsUpdatedMsg) arg)
									.getUpdatedAvailableAssistants());
				}

				if (arg instanceof AvailableCouncillorsUpdatedMsg) {
					clientView
							.availableCouncillorsUpdate(((AvailableCouncillorsUpdatedMsg) arg)
									.getUpdatedAvailableCouncillors());
				}

				if (arg instanceof CitiesColorBonusesUpdatedMsg) {
					CitiesColorBonusesUpdatedMsg msg = (CitiesColorBonusesUpdatedMsg) arg;
					Map<ColorCity, Integer> colorBonuses = msg
							.getUpdatedColorBonuses();
					clientView.citiesColorBonusesUpdate(
							colorBonuses.get(ColorCity.GOLD),
							colorBonuses.get(ColorCity.SILVER),
							colorBonuses.get(ColorCity.BRONZE),
							colorBonuses.get(ColorCity.BLUE));
				}

				if (arg instanceof GameStartedMsg) {
					clientView.setGameStart(((GameStartedMsg) arg).getState());
				}

				if (arg instanceof GameEndedMsg) {
					clientView.gameEnded(((GameEndedMsg) arg).getEndResults());
				}
				
				if (arg instanceof InfoPrivateMsg) {
					clientView.info(((InfoPrivateMsg) arg).getInfo());
				}
				
				if (arg instanceof InfoPublicMsg) {
					clientView.info(((InfoPublicMsg) arg).getInfo());
				}

				if (arg instanceof KingBonusesUpdatedMsg) {
					clientView.kingBonusUpdate(((KingBonusesUpdatedMsg) arg)
							.getUpdatedShowableKingBonus());
				}

				if (arg instanceof KingUpdatedMsg) {
					clientView.kingUpdate(((KingUpdatedMsg) arg)
							.getUpdatedKing());
				}

				if (arg instanceof MarketUpdatedMsg) {
					clientView.marketUpdate(((MarketUpdatedMsg) arg)
							.getUpdatedMarket());
				}

				if (arg instanceof NobilityTrackUpdatedMsg) {
					clientView
							.nobilityTrackUpdate(((NobilityTrackUpdatedMsg) arg)
									.getUpdatedNobilityTrack());
				}

				if (arg instanceof OtherPlayerUpdateMsg) {
					OtherPlayerUpdateMsg msg = (OtherPlayerUpdateMsg) arg;
					clientView.otherPlayerUpdate(msg.getId(), msg.getName(),
							msg.getColor(), msg.getCoins(),
							msg.getAssistants(), msg.getLevel(),
							msg.getPoints(), msg.getNumEmporiums());
				}

				if (arg instanceof PersonalUpdateMsg) {
					clientView.personalUpdate(((PersonalUpdateMsg) arg)
							.getPlayer());
				}

				if (arg instanceof PlayerChangedPrivateMsg) {
					clientView.playerChangePrivate(
							((PlayerChangedPrivateMsg) arg).getPlayerID(),
							((PlayerChangedPrivateMsg) arg).toString());
				}

				if (arg instanceof PlayerChangedPublicMsg) {
					clientView.playerChangePublic(
							((PlayerChangedPublicMsg) arg).getPlayerID(),
							((PlayerChangedPublicMsg) arg).toString());
				}

				if (arg instanceof PlayerIDMsg) {
					clientView.setPlayerID(((PlayerIDMsg) arg).getPlayerID());
				}

				if (arg instanceof RegionUpdatedMsg) {
					clientView.regionUpdate(((RegionUpdatedMsg) arg)
							.getUpdatedRegion());
				}

				if (arg instanceof SoldItemMsg) {
					clientView.itemSold(((SoldItemMsg) arg).getItemSold());
				}

				if (arg instanceof StateUpdatedMsg) {
					clientView.stateUpdate(((StateUpdatedMsg) arg)
							.getUpdatedState());
				}
				
				if(arg instanceof ChatMsg){
					String author=((ChatMsg)arg).getAuthor();
					String text=((ChatMsg)arg).getText();
					clientView.showChatMsg(author, text);
				}
			}

		} catch (RemoteException e) {
			LOGGER.log(Level.SEVERE, "Error on the RMI outbound server");
			active=false;
		}
	}

}
