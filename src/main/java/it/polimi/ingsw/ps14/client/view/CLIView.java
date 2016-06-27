package it.polimi.ingsw.ps14.client.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.Communication;
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

/*
 * --------------------------Command Line Interface-----------------------
 * It acquires infos from player and show the game state each time something
 * has changed
 * 
 */
//TODO faccio stampare solo i dettagli del giocatori che ha finito il turno
//TODO metodo per tutti i miei dettagli?
//TODO implementare richiesta per stampa di tutto
public class CLIView extends ClientView implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(CLIView.class.getName());

	private Interpreter interpreter;
	private Scanner in;

	public CLIView(Scanner scanner, String name) {
		super.setPlayerName(name);
		myTurn = false;
		interpreter = new Interpreter();
		in = scanner;
		playerID = null;
	}

	public void setCommunication(Communication communication) {
		interpreter.setCommunication(communication);
	}

	public void showAvailableAssistant(int update) {
		System.out.println("Assistant available now: " + update);
	}

	public void showAvailableCouncillor(Map<ColorCouncillor, Integer> updatedAvailableCouncillors) {
		ColorCouncillor[] map = ColorCouncillor.values();
		for (ColorCouncillor m : map)
			System.out.println(m.toString() + " -> " + updatedAvailableCouncillors.get(m).toString() + "\n");
	}

	public void showCitiesColorBonuses(int updatedBonusGold, int updatedBonusSilver, int updatedBonusBronze,
			int updatedBonusBlue) {
		System.out.println("CitiesColorBonuses now: BonusGold=" + updatedBonusGold + ", BonusSilver="
				+ updatedBonusSilver + ", BonusBronze=" + updatedBonusBronze + ", BonusBlue=" + updatedBonusBlue);
	}

	public void showError(String text) {
		print(text);
	}

	public void showGameStart() {
		print("Game Started! Good Luck :)");
	}

	public void showKingBonus(int updatedShowableKingBonus) {
		System.out.println("KingBonusesUpdatedMsg [updatedShowableKingBonus=" + updatedShowableKingBonus + "]");
	}

	public void showKingUpdate(King updatedKing) {
		System.out.println("KingUpdatedMsg [updatedKing=" + updatedKing + "]");
	}

	public void showMarket(Market updatedMarket) {
		System.out.println(updatedMarket.toString());
	}

	public void showNobilityTrack(NobilityTrack updatedNobilityTrack) {
		System.out.println(updatedNobilityTrack.toString());
	}

	public void showPersonalDetails(Player p) {
		System.out.println(p.toString());
	}

	public void showPlayerChangesPrivate(String message) {
		print(message);
	}

	public void showPlayerChangesPublic(String notice) {
		print(notice);
	}

	public void showPrivateMsg(String text) {
		print(text);
	}

	public void showRegion(Region updatedRegion) {
		print(updatedRegion.toString());
	}

	public void showItemSold(ItemForSale item) {
		print(item.toString());
	}

	public void showOtherPlayer(int id, String name, Color color, int coins, int assistants, int level, int points,
			int numEmporiums) {
		System.out.println("\nName: " + name + "\nColor: " + color.toString() + "\nCoins: " + Integer.toString(coins)
				+ "\nAssistants: " + Integer.toString(assistants) + "\nNobility level: " + Integer.toString(level)
				+ "\nVictory Points: " + Integer.toString(points));
	}

	/**
	 * It prints infos about regions, king, nobility track and victory points.
	 */

	// public void showGameboard(GameBoard gameBoard) {
	// print("-----REGIONS LIST-----");
	// print("");
	// // for (RegionView regionView : mv.getRegionsView()) {
	// // printer.printRegions(regionView.getRegionCopy());
	// // }
	// // printer.printKing(mv.getKingView().getKingCopy());
	// //
	// printer.printNobilityTrack(mv.getNobilityTrackView().getNobilityTrackCopy(),
	// // getPlayers());
	// // printer.printVictoryPoints(getPlayers());
	// }

	/**
	 * It prints this player private details
	 */
	// TODO change this in all player details

	// public void showThisPlayerDetails(Player player) {
	// printer.printPlayerPublicDetails(player);
	// printer.printPlayerPrivateDetails(player);
	// }
	//
	// public void showMainActions() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// public void showQuickActions() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// public String getPlayerName() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	public void print(String string) {
		System.out.println(string);
	}

	@Override
	public void run() {

		while (true) {

//			print("");
//			showAvailableCommands();

		//	print("Enter command:");
			String input = in.nextLine();

			if (input.toUpperCase().matches("INSTRUCTIONS") || input.toUpperCase().matches("HELP")) {
				showInstructions();

			} else if (!isGameStarted()) {
				print("The game hasn't started yet!!!!");
				// TODO il giocatore può fare qualcosa quando il gioco non è
				// ancora iniziato?

				// } else if (!myTurn) {
				//
				// print("Wait for your turn!");

				// TODO lasciare al giocatore la possibilità di fare 'show' e
				// 'help'

			} else {

				if (!interpreter.parseString(input.toUpperCase(), getPlayerID())) {
					print("Command not recognized! Retry:");
				}
			}
		}
	}

	private void showInstructions() {
		System.out.println("Player ID: " + playerID);
		print("*** Commands:");
		print("DRAW - draw a politic card");
		print("main action:");
		print("ELECT color coast|hills|mountains|king - elect a councillor in the chosen balcony");
		print("ACQUIRE coast|hills|mountains permit_id card_color [card_color ...] - acquire the permit with id 'permit_id' from the chosen region using the cards specified");
		print("BUILD-WITH-KING city_name card_color [card_color ...] - build an emporium in the city 'city_name' with the help of the king using the cards specified");
		print("BUILD-WITH-PERMIT city_name permit_id - build an emporium in the city 'city_name' using the permit specified");
		print("quick action:");
		print("ENGAGE - engage an assistant");
		print("CHANGE coast|hills|mountains - change faceup business permits in the region specified");
		print("MAIN - perform another main action");
		print("ELECT-WITH-ASSISTANT coast|hills|mountains|king color - elect a councillor in the chosen balcony with help of an assistant");
		print("USED-CARD permit_id - choose used card to recycle"); // TODO in
																	// che senso
																	// riciclare
																	// un
																	// business
																	// permit
																	// usato?
		print("FINISH - pass the turn");
		print("SHOW MYDETAILS/DETAILS/GAMEBOARD - show whatever you want");
		print("SELL BUSINESS ID1-PRICE,ID2-PRICE,ID3-PRICE... \n\tASSISTANTS NUM-PRICE POLITIC COLOR1-PRICE,COLOR2-PRICE... - sell!sell!sell!");
		print("SELL NONE - don't sell anything :(");
		print("BUY ITEM_ID QUANTITY(optional) - buy! insert quantity\n\t only if buy some assistant of the item and not the whole item");
		print("BUY FINISH - terminate your buying phase");
	}

	public void showAvailableCommands() {

		if (gameState == null) {

			print("The game hasn't started yet. You can do 'instructions' request");

		} else if (gameState.getGamePhase() == GamePhase.TURNS) {

			showCommandsTurns();

		} else if (gameState.getGamePhase() == GamePhase.FINALTURNS) {

			print("Final turns!");
			showCommandsTurns();

		} else if (gameState.getGamePhase() == GamePhase.MARKET) {

			showCommandsMarket();

		} else if (gameState.getGamePhase() == GamePhase.END) {

			// TODO insieme a gameendedmsg

		}

	}

	private void showCommandsTurns() {

		print("* Turns Phase *");

		if (gameState.getCurrentPlayer().getId() != playerID) {

			print(String.format("It's player %d's turn.", gameState.getCurrentPlayer().getId()));

		} else {

			print("It's your turn. Enter a command:");

			if (gameState.getWaitingFor() == WaitingFor.NOTHING) {

				showCommandsTurnStates();

			} else if (gameState.getWaitingFor() == WaitingFor.TAKEPERMIT) {

				print("You got a bonus by moving forward in the nobility track!");
				print(String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

				for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
					print(String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
				}

				print("Choose with 'choose id1 [id2 ...]'");

			} else if (gameState.getWaitingFor() == WaitingFor.FROMPERMITS) {

				print("You got a bonus by moving forward in the nobility track!");
				print("You can get the benefits of one of the business permits you own for the second time.");
				print(String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

				for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
					print(String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
				}

				print("Choose with 'choose id1 [id2 ...]'");

			} else if (gameState.getWaitingFor() == WaitingFor.FROMTOKENS) {

				print("You got a bonus by moving forward in the nobility track!");
				print("You can get a bonus from one of the cities where you built an emporium");
				print(String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

				for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
					print(String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
				}

				print("Choose with 'choose id1 [id2 ...]'");

			}

		}

	}

	private void showCommandsTurnStates() {

		TurnState currTurnState = gameState.getCurrentTurnState();
		if (currTurnState instanceof InitialTurnState) {

			print("Draw a card. Enter 'draw':");

		} else if ((currTurnState instanceof CardDrawnState)
				|| (currTurnState instanceof MainActionDoneTurnState && gameState.getAdditionalMainsToDo() > 0)) {

			print("You can do a main or a quick action. Enter a command:");

		} else if ((currTurnState instanceof QuickActionDoneTurnState)
				|| (currTurnState instanceof MainAndQuickActionDoneTurnState
						&& gameState.getAdditionalMainsToDo() > 0)) {

			print("You can do a main action. Enter a command:");

		} else if (currTurnState instanceof MainActionDoneTurnState && gameState.getAdditionalMainsToDo() == 0) {

			print("You can do a quick action or pass the turn. Enter a command:");

		} else if (currTurnState instanceof MainAndQuickActionDoneTurnState
				&& gameState.getAdditionalMainsToDo() == 0) {

			print("You have already done your main and quick action. You have to pass the turn. Enter 'pass':");

		} else if (currTurnState instanceof EndTurnState) {

			print("End of your turn, you shouldn't be here.");
			LOGGER.warning("Something went wrong, it's still this player's turn even after it ended!!");

		}

	}

	private void showCommandsMarket() {

		print("* Market Phase *");

		if (gameState.getCurrentMarketState() == MarketState.SELLING) {

			print("Currently selling.");

			if (gameState.getCurrentPlayer().getId() != playerID) {

				print(String.format("It's player %d's turn to sell.", gameState.getCurrentPlayer().getId()));

			} else {

				print("It's your turn to sell.");

			}

		} else if (gameState.getCurrentMarketState() == MarketState.BUYING) {

			print("Currently buying.");

			if (gameState.getCurrentPlayer().getId() != playerID) {

				print(String.format("It's player %d's turn to buy.", gameState.getCurrentPlayer().getId()));

			} else {

				print("It's your turn to buy. You can buy something or pass the turn.");

			}

		} else if (gameState.getCurrentMarketState() == MarketState.END) {

			print("The market has ended, you shouldn't be here.");
			LOGGER.warning("Something went wrong, the game is still in the market phase even after it ended!!");

		}

	}

	@Override
	public void setGameStarted(boolean gameStarted) {
		super.setGameStarted(gameStarted);

		if (gameStarted) {
			interpreter.getCommunication().setPlayerName(super.playerID, super.name);
		}

	}

	@Override
	public void readMessage(Message message) {
		print(message.toString());
	}

	@Override
	public void showEndGame(List<List<String>> endResults) {
		// 0 - id
		// 1 - name
		// 2 - points
		// 3 - assistants
		// 4 - cards
		// 5 - emporiums
		// 6 - nobility
		// 7 - permits
		// 8 - coins

		// TODO null checks?

		print("*** THE GAME HAS ENDED ***");
		print(String.format("The winner is %s, with %s points!", endResults.get(0).get(1), endResults.get(0).get(2)));

		if (this.getPlayerID().equals(Integer.valueOf(endResults.get(0).get(0)))) {
			print("Congratulations! You won!");
		}

		print("");
		print("Complete rankings:");

		for (int i = 0; i < endResults.size(); i++) {
			List<String> plrRes = endResults.get(i);

			print(String.format("%s) %s with %s points, %s assistants and %s cards.", plrRes.get(0), plrRes.get(1),
					plrRes.get(2), plrRes.get(3), plrRes.get(4)));
			print(String.format("  emporiums -> %s,  nobility -> %s,  permits -> %s,  coins -> %s", plrRes.get(5),
					plrRes.get(6), plrRes.get(7), plrRes.get(8)));
		}

	}


}
