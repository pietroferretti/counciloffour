package it.polimi.ingsw.ps14.view;

import it.polimi.ingsw.ps14.client.Client;
import it.polimi.ingsw.ps14.client.ClientView;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.TurnFinishedMsg;
import it.polimi.ingsw.ps14.message.fromClient.NewCurrentPlayerMsg;
import it.polimi.ingsw.ps14.message.fromserver.CurrentPlayerUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameStartedMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerIDMsg;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.modelview.ModelView;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Logger;

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
	private static final Logger LOGGER = Logger.getLogger(CLIView.class
			.getName());

	private Printer printer;
	private boolean gameStarted;
	private boolean myTurn;
	private Interpreter interpreter;
	private Integer playerID; // settare playerID
	private Scanner in;

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

	public void setModelView(ModelView modelView) {
		// TODO Auto-generated method stub

	}

	public void showOtherPlayersDetails() {
		// TODO Auto-generated method stub

	}

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public int getPlayerID() {
		// TODO Auto-generated method stub
		return 0;
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
				myTurn = false;
			} else if (message instanceof CurrentPlayerUpdatedMsg) {
				// TODO nome?
				print("Player " + ((CurrentPlayerUpdatedMsg) message).getUpdateCurrentPlayerIDCopy() + "'s turn");
				if (((CurrentPlayerUpdatedMsg) message).getUpdateCurrentPlayerIDCopy() == playerID) {
					myTurn = true;
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
			print("Enter command:");
			input = in.nextLine();

			Message msg;
			print("inserito " + input);
			if (input.toUpperCase().matches("INSTRUCTION"))
				showIntruction();
			else if (!gameStarted) {

				print("The game hasn't started yet!!!!");
			} else if (!myTurn) {

				print("Wait for your turn!");
			} else {
				print("ciao");
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

	private void showIntruction() {
		print("INSTRUCTIONS:");
		print("DRAW - draw a politic card");
		print("ELECT COLOR REGIONTYPE_KING - elect COLOR councillor\n\t in balcony of REGION TYPE or KING");
		print("ACQUIRE REGIONTYPE PERMIT_ID COLOR_POLITIC - acquire\n\t permit whit id PERMIT-ID of region REGIONTYPE using COLOR1 COLOR2 ...");
		print("BUILD-WITH-KING CITYname CARDS - build emporium in city\n\t CITYname with help of the king using COLOR1 COLOR2 ...");
		print("BUILD-WITH-PERMIT PERMITid CITYname - build emporium \n\tusing PERMIT-ID in CITYname");
		print("ENGAGE - engage assistant");
		print("CHANGE REGIONTYPE - change business permit of REGIONTYPE");
		print("MAIN - perform an other main action");
		print("ELECT-WITH-ASSISTANT REGIONTYPE COLORCOUNCILLOR - elect\n\t COLOR councillor in balcony REGIONTYPE with help of assistant ");
		print("USED-CARD PERMITid - choose used card to recycle");
		print("FINISH - finish action");
		print("SHOW MYDETAILS/DETAILS/GAMEBOARD - show whatever you want");
		print("SELL BUSINESS ID1-PRICE,ID2-PRICE,ID3-PRICE... \n\tASSISTANTS NUM-PRICE POLITIC COLOR1-PRICE,COLOR2-PRICE... - sell!sell!sell!");
		print("BUY ITEM_ID QUANTITY(optional) - buy! insert quantity\n\t only if buy some assistant of the item and not the whole item");

	}

}
