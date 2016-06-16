package it.polimi.ingsw.ps14.view;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Scanner;

import it.polimi.ingsw.ps14.client.ClientView;
import it.polimi.ingsw.ps14.client.MessageHandlerOut;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.UpdateOtherPlayerMsg;
import it.polimi.ingsw.ps14.message.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.modelview.ModelView;

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

	private Printer printer;
	private boolean gameStarted;
	private boolean myTurn;
	private Interpreter interpreter;
	private final Integer playerID;
	private MessageHandlerOut msgHandlerOut;

	public CLIView(){
		printer = new Printer(new PrintStream(System.out));
		gameStarted = false;
		myTurn = false;
		this.playerID = 0;
		interpreter = new Interpreter();
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
		interpreter.parseMsg(message);
	}

	@Override
	public void run() {
		Scanner in = new Scanner(System.in);

		while (true) {
			print("Enter command:");
			String input = in.nextLine();
			
			Message msg;
			print("inserito " + input);
			if (!gameStarted) {

				print("c");
				print("The game hasn't started yet!!!!");
			} else if (!myTurn) {

				print("c");
				print("Wait for your turn!");
			} else {

				print("c");
				msg = interpreter.parseString(input, playerID);
				if (msg == null)
					print("Input error! Retry:");
				else
					handleMessageOut(msg);
				// else TODO invia msg

				print("c");
			}
		}
	}

}
