package it.polimi.ingsw.ps14.view;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.ClientView;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.TurnFinishedMsg;
import it.polimi.ingsw.ps14.message.fromserver.CurrentPlayerUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameStartedMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerIDMsg;
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.State;

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

	private Printer printer;
	private boolean gameStarted;
	private boolean myTurn;
	private State gameState;
	private Interpreter interpreter;
	private Integer playerID;
	private Scanner in;
	
	private Player winner;	// da settare quando arriva qualcosa come GameEndedMsg
	// TODO magari un map per fare una classifica con i punti

	public CLIView(Scanner scanner) {
		printer = new Printer(new PrintStream(System.out));
		gameStarted = false;
		myTurn = false;
		interpreter = new Interpreter();
		in = scanner;
		playerID = null;
	}

	/**
	 * It prints infos about regions, king, nobility track and victory points.
	 */

	public void showGameboard(GameBoard gameBoard) {
		print("-----REGIONS LIST-----");
		print("");
		// for (RegionView regionView : mv.getRegionsView()) {
		// printer.printRegions(regionView.getRegionCopy());
		// }
		// printer.printKing(mv.getKingView().getKingCopy());
		// printer.printNobilityTrack(mv.getNobilityTrackView().getNobilityTrackCopy(),
		// getPlayers());
		// printer.printVictoryPoints(getPlayers());
	}

	/**
	 * It prints this player private details
	 */
	// TODO change this in all player details

	public void showThisPlayerDetails(Player player) {
		printer.printPlayerPublicDetails(player);
		printer.printPlayerPrivateDetails(player);
	}

	public void showMainActions() {
		// TODO Auto-generated method stub

	}

	public void showQuickActions() {
		// TODO Auto-generated method stub

	}

	public String getPlayerName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void print(String string) {
		System.out.println(string);
	}

	private void showOtherPlayerDetails(Player player) {
		printer.printPlayerPublicDetails(player);
	}

	public void showOtherPlayersDetails() {
		// TODO Auto-generated method stub

	}

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public Integer getPlayerID() {
		return playerID;
	}

	@Override
	public void handleMessage(Message message) {
		String str;
		if (message != null) {
			if ((message instanceof PlayerIDMsg) && playerID == null) {

				playerID = ((PlayerIDMsg) message).getPlayerID();
				LOGGER.info(String.format("Player id set as %d", playerID));

			} else if (message instanceof GameStartedMsg) {
				gameStarted = true;
			} else if (message instanceof TurnFinishedMsg) {
				// turnfinishedmsg cosa fa?
				
			} else if (message instanceof StateUpdatedMsg) {

				gameState = ((StateUpdatedMsg) message).getUpdatedState();
				print("Game state updated.");	// dettagli è meglio
				
				if (gameState.getCurrentPlayer().getId() == playerID) {
					myTurn = true;
				} else {
					myTurn = false;
				}

			} else {
				str = interpreter.parseMsg(message);
				if (str != null) {
					print(str);
				} else {
					print("Couldn't interpret message.");
				}
			}
		}
	}

	@Override
	public void run() {

		print("Enter your name: da implementare ancora");
		String input = in.nextLine();
		
		// per debug
		// gameStarted=true;
		// myTurn=true;

		while (true) {
			
			showAvailableCommands();
			
			print("Enter command:");
			input = in.nextLine();

			Message msg;
			print("inserito " + input);
			if (input.toUpperCase().matches("INSTRUCTIONS") || input.toUpperCase().matches("HELP")) { 

				showInstructions();

			} else if (!gameStarted) {

				print("The game hasn't started yet!!!!");

			} else if (!myTurn) {

				print("Wait for your turn!");

			} else {

				msg = interpreter.parseString(input.toUpperCase(), playerID);

				if (msg == null)
					print("Input error! Retry:");
				else {
					print("messaggio:" + msg.toString());

					handleMessageOut(msg);
				}

				// invia msg

			}
		}
	}

	private void showInstructions() {
		print("*** Commands:");
		print("DRAW - draw a politic card");
		print("ELECT color coast|hills|mountains|king - elect a councillor in the chosen balcony");
		print("ACQUIRE coast|hills|mountains permit_id card_color [card_color ...] - acquire the permit with id 'permit_id' from the chosen region using the cards specified");
		print("BUILD-WITH-KING city_name card_color [card_color ...] - build an emporium in the city 'city_name' with the help of the king using the cards specified");
		print("BUILD-WITH-PERMIT city_name permit_id - build an emporium in the city 'city_name' using the permit specified");
		print("ENGAGE - engage an assistant");
		print("CHANGE coast|hills|mountains - change faceup business permits in the region specified");
		print("MAIN - perform another main action");
		print("ELECT-WITH-ASSISTANT coast|hills|mountains|king color - elect a councillor in the chosen balcony with help of an assistant");
		print("USED-CARD permit_id - choose used card to recycle");	// TODO in che senso riciclare un business permit usato?
		print("FINISH - pass the turn");
		print("SHOW MYDETAILS/DETAILS/GAMEBOARD - show whatever you want");
		print("SELL BUSINESS ID1-PRICE,ID2-PRICE,ID3-PRICE... \n\tASSISTANTS NUM-PRICE POLITIC COLOR1-PRICE,COLOR2-PRICE... - sell!sell!sell!");
		print("BUY ITEM_ID QUANTITY(optional) - buy! insert quantity\n\t only if buy some assistant of the item and not the whole item");
	}

	private void showAvailableCommands() {
		// TODO raffinare
		
		if (gameState.getGamePhase() == GamePhase.TURNS) {
			
			showCommandsTurns();
			
		} else if (gameState.getGamePhase() == GamePhase.FINALTURNS) {
			
			print("It's your final turn!");	// solo se è il suo turno?
			showCommandsTurns();
		
		} else if (gameState.getGamePhase() == GamePhase.MARKET) {
			
			showCommandsMarket();
			
		} else if (gameState.getGamePhase() == GamePhase.END) {
			
			print(String.format("The game has ended, %s has won!", winner.getName()));
			
		}
		
	}
	
	private void showCommandsTurns() {
		
	}
	
	private void showCommandsMarket() {
		
	}
	
}
