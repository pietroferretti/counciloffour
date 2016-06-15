package it.polimi.ingsw.ps14.view;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.UpdateOtherPlayerMsg;
import it.polimi.ingsw.ps14.message.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.modelview.ModelView;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Scanner;

/*
 * --------------------------Command Line Interface-----------------------
 * It acquires infos from player and show the game state each time something
 * has changed
 * 
 */
//TODO faccio stampare solo i dettagli del giocatori che ha finito il turno
//TODO metodo per tutti i miei dettagli?
//TODO implementare richiesta per stampa di tutto
public class CLIView extends View implements Runnable {

	private Printer printer;
	private boolean gameStarted;
	private boolean myTurn;
	private Interpreter interpreter;
	Scanner scan = new Scanner(System.in);
	private final Integer playerID;

	public CLIView(OutputStream outputStream, Integer playerID) {

		printer = new Printer(new PrintStream(outputStream));
		gameStarted = false;
		myTurn = false;
		this.playerID = playerID;
		interpreter = new Interpreter(playerID);
	}

	/**
	 * It prints infos about regions, king, nobility track and victory points.
	 */

	@Override
	public void showGameboard(GameBoard gameBoard) {
		// FIXME
		// print("-----REGIONS LIST-----");
		// print("");
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
	@Override
	public void showThisPlayerDetails(Player player) {
		printer.printPlayerPublicDetails(player);
		printer.printPlayerPrivateDetails(player);
	}

	public void print(String string) {
		printer.print(string);
	}

	@Override
	public void showMainActions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showQuickActions() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Message writeMsg(String input) {
		if (!gameStarted) {
			print("The game hasn't started yet!!!!");
			return null;
		}
		if (!myTurn) {
			print("Wait for your turn!");
			return null;
		} else
			return interpreter.parseString(input);
	}

	@Override
	public void readMsg(Message arg) {

		if (arg instanceof UpdateThisPlayerMsg) {
			showThisPlayerDetails(((UpdateThisPlayerMsg) arg).getPlayer());

		} else if (arg instanceof UpdateOtherPlayerMsg) {
			showOtherPlayerDetails(((UpdateOtherPlayerMsg) arg).getPlayer());

		} else if (arg instanceof UpdateGameBoardMsg) {
			showGameboard(((UpdateGameBoardMsg) arg).getGameBoard());
		}

	}

	private void showOtherPlayerDetails(Player player) {
		printer.printPlayerPublicDetails(player);
	}

	@Override
	public void setModelView(ModelView modelView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showOtherPlayersDetails() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPlayerID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run() {
		String input = scan.nextLine();
		interpreter.sent(input);
		if (!gameStarted) {
			print("The game hasn't started yet!!!!");
		} else if (!myTurn) {
			print("Wait for your turn!");
		} else
			interpreter.parseString(input);
		

	}
}
