package it.polimi.ingsw.ps14.client.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.client.view.gui.GUI;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.WaitingFor;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class GUIView extends ClientView implements Runnable {

    private GUI mainWindow;
    private Communication communication;
	private List<List<String>> endResults;

    public GUIView(String name) {
        super.setPlayerName(name);
    }

    @Override
    public void readMessage(Message message) {
        // TODO Auto-generated method stub
    	mainWindow.getChatArea().append(String.format("%n %s", message.toString()));
    }

    @Override
    public void run() {
        Integer id = playerID;
        SwingUtilities.invokeLater(new Runnable() {
        	@Override
            public void run() {
                mainWindow = new GUI(id, communication);
                mainWindow.setPlayerName(name);
                mainWindow.setVisible(true);
            }
        });

//        GUI.start();
    }

    @Override
    public void setCommunication(Communication communication) {
        this.communication = communication;
    }
    
	@Override
	public void setGameStarted(boolean gameStarted) {
		super.setGameStarted(gameStarted);

		if (gameStarted) {
			communication.setPlayerName(super.playerID, super.name);
		}

	}

    @Override
    public void showAvailableAssistant(int update) {
		mainWindow.getChatArea().append("\n"+"Assistant available now: " + update);

    }

    @Override
    public void showAvailableCommands() {
        
		
		if (gameState == null) {

			mainWindow.getChatArea().append("\n"+"The game hasn't started yet. You can see the available commands with 'instructions'");

		} else if (gameState.getGamePhase() == GamePhase.TURNS) {

			showCommandsTurns();

		} else if (gameState.getGamePhase() == GamePhase.FINALTURNS) {

			mainWindow.getChatArea().append("\n"+"Final turns!");
			showCommandsTurns();

		} else if (gameState.getGamePhase() == GamePhase.MARKET) {

			showCommandsMarket();

		} else if (gameState.getGamePhase() == GamePhase.END) {

			mainWindow.getChatArea().append("\n"+"The game has ended. Enter 'results' to see the rankings.");

		}

    }

    @Override
    public void showAvailableCouncillor(Map<ColorCouncillor, Integer> updatedAvailableCouncillors) {
ColorCouncillor[] map = ColorCouncillor.values();
		for (ColorCouncillor m : map)
			mainWindow.getChatArea().append("\n"+m.toString() + " -> " + updatedAvailableCouncillors.get(m).toString() + "\n");
    }

    @Override
    public void showChatMsg(String author, String text) {
mainWindow.getChatArea().append("\n"+author+": "+text);

    }

    @Override
    public void showCitiesColorBonuses(int updatedBonusGold, int updatedBonusSilver, int updatedBonusBronze,
            int updatedBonusBlue) {
mainWindow.getChatArea().append("\n"+"CitiesColorBonuses now: BonusGold=" + updatedBonusGold + ", BonusSilver="
				+ updatedBonusSilver + ", BonusBronze=" + updatedBonusBronze + ", BonusBlue=" + updatedBonusBlue);
	
    }

    private void showCommandsMarket() {

		mainWindow.getChatArea().append("\n"+"* Market Phase *");

		if (gameState.getCurrentMarketState() == MarketState.SELLING) {

			mainWindow.getChatArea().append("\n"+"Currently selling.");

			if (gameState.getCurrentPlayer().getId() != playerID) {

				mainWindow.getChatArea().append("\n"+String.format("It's %s's turn to sell.", gameState.getCurrentPlayer().getName()));

			} else {

				mainWindow.getChatArea().append("\n"+"It's your turn to sell.");

			}

		} else if (gameState.getCurrentMarketState() == MarketState.BUYING) {

			mainWindow.getChatArea().append("\n"+"Currently buying.");

			if (gameState.getCurrentPlayer().getId() != playerID) {

				mainWindow.getChatArea().append("\n"+String.format("It's %s's turn to buy.", gameState.getCurrentPlayer().getName()));

			} else {

				mainWindow.getChatArea().append("\n"+"It's your turn to buy. You can buy something or pass the turn.");

			}

		} else if (gameState.getCurrentMarketState() == MarketState.END) {

			mainWindow.getChatArea().append("\n"+"The market has ended, you shouldn't be here.");

		}

	}

    private void showCommandsTurns() {

		mainWindow.getChatArea().append("\n"+"* Turns Phase *");

		if (gameState.getCurrentPlayer().getId() != playerID) {

			mainWindow.getChatArea().append("\n"+String.format("It's %s's turn.", gameState.getCurrentPlayer().getName()));

		} else {

			mainWindow.getChatArea().append("\n"+"It's your turn. Enter a command:");

			if (gameState.getWaitingFor() == WaitingFor.NOTHING) {

				showCommandsTurnStates();

			} else if (gameState.getWaitingFor() == WaitingFor.TAKEPERMIT) {

				mainWindow.getChatArea().append("\n"+"You got a bonus by moving forward in the nobility track!");
				mainWindow.getChatArea().append("\n"+String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

				for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
					mainWindow.getChatArea().append("\n"+String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
				}

				mainWindow.getChatArea().append("\n"+"Choose with 'choose id1 [id2 ...]'");

			} else if (gameState.getWaitingFor() == WaitingFor.FROMPERMITS) {

				mainWindow.getChatArea().append("\n"+"You got a bonus by moving forward in the nobility track!");
				mainWindow.getChatArea().append("\n"+"You can get the benefits of one of the business permits you own for the second time.");
				mainWindow.getChatArea().append("\n"+String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

				for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
					mainWindow.getChatArea().append("\n"+String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
				}

				mainWindow.getChatArea().append("\n"+"Choose with 'choose id1 [id2 ...]'");

			} else if (gameState.getWaitingFor() == WaitingFor.FROMTOKENS) {

				mainWindow.getChatArea().append("\n"+"You got a bonus by moving forward in the nobility track!");
				mainWindow.getChatArea().append("\n"+"You can get a bonus from one of the cities where you built an emporium");
				mainWindow.getChatArea().append("\n"+String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

				for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
					mainWindow.getChatArea().append("\n"+String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
				}

				mainWindow.getChatArea().append("\n"+"Choose with 'choose id1 [id2 ...]'");

			}

		}

	}

    private void showCommandsTurnStates() {

		TurnState currTurnState = gameState.getCurrentTurnState();
		if (currTurnState instanceof InitialTurnState) {

			mainWindow.getChatArea().append("\n"+"Draw a card. Enter 'draw':");

		} else if ((currTurnState instanceof CardDrawnState)
				|| (currTurnState instanceof MainActionDoneTurnState && gameState.getAdditionalMainsToDo() > 0)) {

			mainWindow.getChatArea().append("\n"+"You can do a main or a quick action. Enter a command:");

		} else if ((currTurnState instanceof QuickActionDoneTurnState)
				|| (currTurnState instanceof MainAndQuickActionDoneTurnState
						&& gameState.getAdditionalMainsToDo() > 0)) {

			mainWindow.getChatArea().append("\n"+"You can do a main action. Enter a command:");

		} else if (currTurnState instanceof MainActionDoneTurnState && gameState.getAdditionalMainsToDo() == 0) {

			mainWindow.getChatArea().append("\n"+"You can do a quick action or pass the turn. Enter a command:");

		} else if (currTurnState instanceof MainAndQuickActionDoneTurnState
				&& gameState.getAdditionalMainsToDo() == 0) {

			mainWindow.getChatArea().append("\n"+"You have already done your main and quick action. You have to pass the turn. Enter 'pass':");

		} else if (currTurnState instanceof EndTurnState) {

			mainWindow.getChatArea().append("\n"+"End of your turn, you shouldn't be here.");

		}

	}

    @Override
    public void showEndGame(List<List<String>> endResults) {
		this.endResults = endResults;
		if (endResults == null) {
			mainWindow.getChatArea().append("\n"+"The game hasn't ended yet.");
			return;
		}
		
		// 0 - id
		// 1 - name
		// 2 - points
		// 3 - assistants
		// 4 - cards
		// 5 - emporiums
		// 6 - nobility
		// 7 - permits
		// 8 - coins

		
		mainWindow.getChatArea().append("\n"+"*** THE GAME HAS ENDED ***");
		mainWindow.getChatArea().append("\n"+String.format("The winner is %s, with %s points!", endResults.get(0).get(1), endResults.get(0).get(2)));

		if (this.getPlayerID().equals(Integer.valueOf(endResults.get(0).get(0)))) {
			mainWindow.getChatArea().append("\n"+"Congratulations! You won!");
		}

		mainWindow.getChatArea().append("\n"+"Complete rankings:");

		for (int i = 0; i < endResults.size(); i++) {
			List<String> plrRes = endResults.get(i);

			mainWindow.getChatArea().append("\n"+String.format("%d) %s with %s points, %s assistants and %s cards.", i, plrRes.get(1),
					plrRes.get(2), plrRes.get(3), plrRes.get(4)));
			mainWindow.getChatArea().append("\n"+String.format("  %s emporiums,  %s nobility,  %s permits,  %s coins", plrRes.get(5),
					plrRes.get(6), plrRes.get(7), plrRes.get(8)));
		}

    }

    @Override
    public void showGameStart() {
        // TODO Auto-generated method stub
        GameStartedDialog d;
        d = new GameStartedDialog(mainWindow, true);
        d.setVisible(true);

    }

    @Override
    public void showInfo(String text) {
        // TODO Auto-generated method stub
    	mainWindow.getInfoArea().append("\n"+text);
    }

    @Override
    public void showItemSold(ItemForSale item) {
		mainWindow.getChatArea().append("\n"+item.toString());

    }

    @Override
    public void showKingBonus(int updatedShowableKingBonus) {
        		mainWindow.getCLIarea().append("\n"+"KingBonusesUpdatedMsg [updatedShowableKingBonus=" + updatedShowableKingBonus + "]");

    }

    @Override
    public void showKingUpdate(King updatedKing) {
		mainWindow.getCLIarea().append("\n"+"KingUpdatedMsg [updatedKing=" + updatedKing + "]");

    }

    @Override
    public void showMarket(Market updatedMarket) {
		mainWindow.getCLIarea().append("\n"+updatedMarket.toString());

    }

    @Override
    public void showNobilityTrack(NobilityTrack updatedNobilityTrack) {
mainWindow.getCLIarea().append("\n"+updatedNobilityTrack.toString());
    }

    @Override
    public void showOtherPlayer(int id, String name, Color color, int coins, int assistants, int level, int points,
            int numEmporiums) {
	mainWindow.getChatArea().append("\n"+"\nName: " + name + "\nColor: " + color.toString() + "\nCoins: " + Integer.toString(coins)
				+ "\nAssistants: " + Integer.toString(assistants) + "\nNobility level: " + Integer.toString(level)
				+ "\nVictory Points: " + Integer.toString(points));
    }

    @Override
    public void showPersonalDetails(Player p) {
mainWindow.getCLIarea().append("\n"+p.toString());
    }

    @Override
    public void showPlayerChangesPrivate(String message) {
        // TODO Auto-generated method stub
        System.out.println("it.polimi.ingsw.ps14.client.view.GUIView.showPlayerChangesPrivate()");
        System.out.println(message);
        mainWindow.getCLIarea().append(message);

    }

    @Override
    public void showPlayerChangesPublic(String notice) {
		mainWindow.getCLIarea().append("\n"+notice);

    }

    @Override
    public void showPrivateMsg(String text) {
        // TODO Auto-generated method stub
        mainWindow.getCLIarea().append(text);

    }

    @Override
    public void showRegion(Region updatedRegion) {
		mainWindow.getChatArea().append("\n"+updatedRegion.toString());

    }

}
