package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.client.view.ClientView;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromclient.DoneBuyingMsg;
import it.polimi.ingsw.ps14.message.fromclient.MyChatMsg;
import it.polimi.ingsw.ps14.message.fromclient.NobilityRequestAnswerMsg;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellNoneMsg;
import it.polimi.ingsw.ps14.message.fromclient.TurnActionMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateOtherPlayersMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateThisPlayerMsg;
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
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumWithHelpOfKingAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.market.BuyAction;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.ChangeBusinessPermitTilesAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.EngageAssistantAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.PerformAdditionalMainActionAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.SendAssistantToElectCouncillorAction;

/**
 * It handles methods that the client can invoke on the server and methods that
 * server can invoke on the client.
 *
 */
public class SocketCommunication implements Communication {

	private static final Logger LOGGER = Logger.getLogger(SocketCommunication.class.getName());

	private Timer timer;
	private TimerTask timerTask;
	private boolean alreadyCalled = false;

	private SocketMessageHandlerOut msgHandlerOut;
	private ClientView clientView;

	public SocketCommunication(SocketMessageHandlerOut msgHandlerOut, ClientView clientView) {
		this.msgHandlerOut = msgHandlerOut;
		this.clientView = clientView;
	}

	// ------------------------from server --------------------------

	/**
	 * It recognised the specific type of a {@link Message} and invokes the
	 * proper method on the {@link ClientView}.
	 * 
	 * @param message
	 *            - {@link Message}
	 * @throws IOException
	 */
	public void receiveMessage(Message message) throws IOException {
		if (message != null) {
			if ((message instanceof PlayerIDMsg) && clientView.getPlayerID() == null) {
				clientView.setPlayerID(((PlayerIDMsg) message).getPlayerID());
				LOGGER.info(String.format("Player id set as %d", clientView.getPlayerID()));

			} else if (message instanceof GameStartedMsg) {
				clientView.setGameStarted(true);
				clientView.setGameState(((GameStartedMsg) message).getState());
				clientView.loadMap(((GameStartedMsg) message).getMapName());
				clientView.showGameStart();

			} else if (message instanceof StateUpdatedMsg) {

				clientView.setGameState(((StateUpdatedMsg) message).getUpdatedState());

				if (!alreadyCalled) {
					timer = new Timer();
					alreadyCalled = true;
					task();
				}

			} else if (message instanceof GameEndedMsg) {

				clientView.showEndGame(((GameEndedMsg) message).getEndResults());

			} else if (message instanceof AvailableAssistantsUpdatedMsg) {
				clientView.showAvailableAssistant(
						((AvailableAssistantsUpdatedMsg) message).getUpdatedAvailableAssistants());

			} else if (message instanceof AvailableCouncillorsUpdatedMsg) {
				clientView.showAvailableCouncillor(
						((AvailableCouncillorsUpdatedMsg) message).getUpdatedAvailableCouncillors());

			} else if (message instanceof CitiesColorBonusesUpdatedMsg) {
				clientView.showCitiesColorBonuses(((CitiesColorBonusesUpdatedMsg) message).getBonusGold(),
						((CitiesColorBonusesUpdatedMsg) message).getBonusSilver(),
						((CitiesColorBonusesUpdatedMsg) message).getBonusBronze(),
						((CitiesColorBonusesUpdatedMsg) message).getBonusBlue());

			} else if (message instanceof InfoPrivateMsg || message instanceof InfoPublicMsg) {
				clientView.showInfo(message.toString());
			} else if (message instanceof KingBonusesUpdatedMsg) {
				clientView.showKingBonus(((KingBonusesUpdatedMsg) message).getUpdatedShowableKingBonus());
			} else if (message instanceof KingUpdatedMsg) {
				clientView.showKingUpdate(((KingUpdatedMsg) message).getUpdatedKing());
			} else if (message instanceof MarketUpdatedMsg) {
				clientView.showMarket(((MarketUpdatedMsg) message).getUpdatedMarket());
			} else if (message instanceof NobilityTrackUpdatedMsg) {
				clientView.showNobilityTrack(((NobilityTrackUpdatedMsg) message).getUpdatedNobilityTrack());
			} else if (message instanceof OtherPlayerUpdateMsg) {
				clientView.showOtherPlayer(((OtherPlayerUpdateMsg) message).getId(),
						((OtherPlayerUpdateMsg) message).getName(), ((OtherPlayerUpdateMsg) message).getColor(),
						((OtherPlayerUpdateMsg) message).getCoins(), ((OtherPlayerUpdateMsg) message).getAssistants(),
						((OtherPlayerUpdateMsg) message).getLevel(), ((OtherPlayerUpdateMsg) message).getPoints(),
						((OtherPlayerUpdateMsg) message).getNumEmporiums());
			} else if (message instanceof PersonalUpdateMsg) {
				clientView.showPersonalDetails(((PersonalUpdateMsg) message).getPlayer());
			} else if (message instanceof PlayerChangedPrivateMsg) {
				clientView.showPlayerChangesPrivate(((PlayerChangedPrivateMsg) message).getPlayer(),
						message.toString());
			} else if (message instanceof PlayerChangedPublicMsg) {
				clientView.showPlayerChangesPublic(((PlayerChangedPublicMsg) message).getNotice());
			} else if (message instanceof RegionUpdatedMsg) {
				clientView.showRegion(((RegionUpdatedMsg) message).getUpdatedRegion());
			} else if (message instanceof ChatMsg) {
				clientView.showChatMsg(((ChatMsg) message).getAuthor(), ((ChatMsg) message).getText());

			} else {
				clientView.readMessage(message);
			}
		} else
			LOGGER.info("Couldn't interpret message.");

	}

	// ------------------------------from client------------------------------

	@Override
	public void setPlayerName(Integer playerID, String name) {
		msgHandlerOut.sendMessage(new PlayerNameMsg(name));

	}

	@Override
	public void drawCard(Integer playerID) {
		msgHandlerOut.sendMessage(new TurnActionMsg(new DrawCardAction(playerID)));

	}

	@Override
	public void electCouncillor(Integer playerID, ColorCouncillor cc, String regionORking) {
		msgHandlerOut.sendMessage(new TurnActionMsg(new ElectCouncillorAction(playerID, cc, regionORking)));

	}

	@Override
	public void acquireBusinessPermitTile(Integer playerID, RegionType rt, Integer permID, List<PoliticCard> politics) {
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new AcquireBusinessPermitTileAction(playerID, rt, permID, new ArrayList<PoliticCard>(politics))));

	}

	@Override
	public void buildWithKing(Integer playerID, String city, List<PoliticCard> politics) {
		msgHandlerOut.sendMessage(new TurnActionMsg(new BuildEmporiumWithHelpOfKingAction(playerID, city, politics)));

	}

	@Override
	public void buildWithPermit(Integer playerID, Integer permitID, String cityname) {
		msgHandlerOut
				.sendMessage(new TurnActionMsg(new BuildEmporiumUsingPermitTileAction(playerID, permitID, cityname)));

	}

	@Override
	public void engage(Integer playerID) {
		msgHandlerOut.sendMessage(new TurnActionMsg(new EngageAssistantAction(playerID)));

	}

	@Override
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt) {
		msgHandlerOut.sendMessage(new TurnActionMsg(new ChangeBusinessPermitTilesAction(playerID, rt)));

	}

	@Override
	public void performAdditionalMainAction(Integer playerID) {
		msgHandlerOut.sendMessage(new TurnActionMsg(new PerformAdditionalMainActionAction(playerID)));

	}

	@Override
	public void electWithAssistant(Integer playerID, RegionType rt, ColorCouncillor cc) {
		msgHandlerOut.sendMessage(new TurnActionMsg(new SendAssistantToElectCouncillorAction(playerID, rt, cc)));

	}

	@Override
	public void passTurn(Integer playerID) {
		msgHandlerOut.sendMessage(new TurnActionMsg(new EndTurnAction(playerID)));

	}

	@Override
	public void showMyDetails(Integer playerID) {
		msgHandlerOut.sendMessage(new UpdateThisPlayerMsg(playerID));

	}

	@Override
	public void showDetails(Integer playerID) {
		msgHandlerOut.sendMessage(new UpdateOtherPlayersMsg(playerID));

	}

	@Override
	public void showGameboard(Integer playerID) {
		msgHandlerOut.sendMessage(new UpdateGameBoardMsg());

	}

	@Override
	public void sell(Integer playerID, List<ItemForSale> items) {
		msgHandlerOut.sendMessage(new SellMsg(new SellAction(items)));

	}

	@Override
	public void buy(Integer playerID, Integer objID, Integer quantity) {
		msgHandlerOut.sendMessage(new BuyMsg(new BuyAction(playerID, objID, quantity)));

	}

	@Override
	public void answerNobilityRequest(Integer playerID, List<String> objectIDs) {
		msgHandlerOut.sendMessage(new NobilityRequestAnswerMsg(objectIDs));
	}

	@Override
	public void sellNone(Integer playerID) {
		msgHandlerOut.sendMessage(new SellNoneMsg());
	}

	@Override
	public void doneFinishBuying(Integer playerID) {
		msgHandlerOut.sendMessage(new DoneBuyingMsg());

	}

	private void task() {
		timerTask = new TimerTask() {

			@Override
			public void run() {
				clientView.showAvailableCommands();
				alreadyCalled = false;
			}
		};
		timer.schedule(timerTask, 200);

	}

	@Override
	public void chat(Integer playerID, String chat) {
		msgHandlerOut.sendMessage(new MyChatMsg(chat, playerID));
	}

}
