package it.polimi.ingsw.ps14.client.socket;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.client.view.CLIView;
import it.polimi.ingsw.ps14.client.view.ClientView;
import it.polimi.ingsw.ps14.message.JumpTurnMsg;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromclient.DoneBuyingMsg;
import it.polimi.ingsw.ps14.message.fromclient.NobilityRequestAnswerMsg;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellNoneMsg;
import it.polimi.ingsw.ps14.message.fromclient.TurnActionMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateOtherPlayersMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameEndedMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameStartedMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerIDMsg;
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.TimeOutMsg;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class SocketCommunication implements Communication {

	private static final Logger LOGGER = Logger.getLogger(CLIView.class
			.getName());

	private Timer timer;
	private TimerTask timerTask;
	private boolean alreadyCalled = false;

	private boolean imAlive=true;
	private boolean timerStarted=false;
	private TimerTask timerTurnTask;
	private Timer timerTurn;
	private int timeOut;

	private SocketMessageHandlerOut msgHandlerOut;
	private ClientView clientView;

	public SocketCommunication(SocketMessageHandlerOut msgHandlerOut,
			ClientView clientView) {
		this.msgHandlerOut = msgHandlerOut;
		this.clientView = clientView;
	}

	// ------------------------from server --------------------------
	public void receiveMessage(Message message) {
		if (message != null) {
			if ((message instanceof PlayerIDMsg)
					&& clientView.getPlayerID() == null) {
				clientView.setPlayerID(((PlayerIDMsg) message).getPlayerID());
				LOGGER.info(String.format("Player id set as %d",
						clientView.getPlayerID()));

			} else if (message instanceof TimeOutMsg) {
				this.timeOut = (((TimeOutMsg) message).getTimeOut());

			} else if (message instanceof GameStartedMsg) {
				clientView.showGameStart();
				clientView.setGameStarted(true);
				clientView.setGameState(((GameStartedMsg) message).getState());
				clientView.showAvailableCommands();
			} else if (message instanceof StateUpdatedMsg) {

				clientView.setGameState(((StateUpdatedMsg) message)
						.getUpdatedState());
				LOGGER.info(String.format("Game state updated."
						+ clientView.getGameState().getGamePhase().toString())); // dettagli è meglio
				
				if (clientView.getGameState().getCurrentPlayer().getId() == clientView
						.getPlayerID()) {
					clientView.setMyTurn(true);
					imAlive = false;
//					if(!timerStarted)
//						startTimer();
				} else {
					clientView.setMyTurn(false);
				}
				if (!alreadyCalled) {
					timer = new Timer();
					alreadyCalled = true;
					task();
				}

			} else if (message instanceof GameEndedMsg) {

				clientView
						.showEndGame(((GameEndedMsg) message).getEndResults());

			} else {
				clientView.readMessage(message);

			}
		} else
			LOGGER.info(String.format("Couldn't interpret message."));

	}

	// ------------------------------ from client
	// ---------------------------------

	@Override
	public void setPlayerName(Integer playerID, String name) {
		msgHandlerOut.sendMessage(new PlayerNameMsg(name));
		imAlive = true;
		timerStarted=false;
	}

	@Override
	public void drawCard(Integer playerID) {
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new DrawCardAction(playerID)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void electCouncillor(Integer playerID, ColorCouncillor cc,
			String regionORking) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(new ElectCouncillorAction(
				playerID, cc, regionORking)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void acquireBusinessPermitTile(Integer playerID, RegionType rt,
			Integer permID, List<PoliticCard> politics) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new AcquireBusinessPermitTileAction(playerID, rt, permID,
						new ArrayList<PoliticCard>(politics))));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void buildWithKing(Integer playerID, String city,
			List<PoliticCard> politics) {
		// TODO Auto-generated method stub
		msgHandlerOut
				.sendMessage(new TurnActionMsg(
						new BuildEmporiumWithHelpOfKingAction(playerID, city,
								politics)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void buildWithPermit(Integer playerID, Integer permitID,
			String cityname) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new BuildEmporiumUsingPermitTileAction(playerID, permitID,
						cityname)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void engage(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(new EngageAssistantAction(
				playerID)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new ChangeBusinessPermitTilesAction(playerID, rt)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void performAdditionalMainAction(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new PerformAdditionalMainActionAction(playerID)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void electWithAssistant(Integer playerID, RegionType rt,
			ColorCouncillor cc) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new TurnActionMsg(
				new SendAssistantToElectCouncillorAction(playerID, rt, cc)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void passTurn(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut
				.sendMessage(new TurnActionMsg(new EndTurnAction(playerID)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void showMyDetails(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new UpdateThisPlayerMsg(playerID));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void showDetails(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new UpdateOtherPlayersMsg(playerID));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void showGameboard(Integer playerID) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new UpdateGameBoardMsg());
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void sell(Integer playerID, List<ItemForSale> items) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new SellMsg(new SellAction(items)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void buy(Integer playerID, Integer objID, Integer quantity) {
		// TODO Auto-generated method stub
		msgHandlerOut.sendMessage(new BuyMsg(new BuyAction(playerID, objID,
				quantity)));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void answerNobilityRequest(Integer playerID, List<String> objectIDs) {
		msgHandlerOut.sendMessage(new NobilityRequestAnswerMsg(objectIDs));
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void sellNone(Integer playerID) {
		msgHandlerOut.sendMessage(new SellNoneMsg());
		imAlive = true;
		timerStarted=false;


	}

	@Override
	public void doneFinishBuying(Integer playerID) {
		msgHandlerOut.sendMessage(new DoneBuyingMsg());
		imAlive = true;
		timerStarted=false;


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

//
//	private void startTimer() {
//		timerTurn=new Timer();
//		timerStarted=true;
//		timerTurnTask = new TimerTask() {
//
//			@Override
//			public void run() {
//				if (clientView.isMyTurn() && !imAlive)
//					msgHandlerOut.sendMessage(new JumpTurnMsg());
//				timerStarted=false;
//			}
//		};
//		timerTurn.schedule(timerTurnTask, timeOut);
//
//	}
}
