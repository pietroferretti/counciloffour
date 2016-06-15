package it.polimi.ingsw.ps14.view;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Scanner;

import it.polimi.ingsw.ps14.client.ClientView;
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
	Scanner scan = new Scanner(System.in);
	private final Integer playerID;

	public CLIView(OutputStream outputStream) {
		
		printer = new Printer(new PrintStream(outputStream));
		gameStarted = false;
		myTurn = false;
		this.playerID = 0;
		interpreter = new Interpreter(playerID);
	}

	/**
	 * It prints infos about regions, king, nobility track and victory points.
	 */

	
	public void showGameboard(GameBoard gameBoard) {
		 print("-----REGIONS LIST-----");
		 print("");
//		 for (RegionView regionView : mv.getRegionsView()) {
//		 printer.printRegions(regionView.getRegionCopy());
//		 }
//		 printer.printKing(mv.getKingView().getKingCopy());
//		 printer.printNobilityTrack(mv.getNobilityTrackView().getNobilityTrackCopy(),
//		 getPlayers());
//		 printer.printVictoryPoints(getPlayers());
	}

	/**
	 * It prints this player private details
	 */
	// TODO change this in all player details
	
	public void showThisPlayerDetails(Player player) {
		printer.printPlayerPublicDetails(player);
		printer.printPlayerPrivateDetails(player);
	}

	public void print(String string) {
		printer.print(string);
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
		//TODO come l'update
	}
	
	@Override
	public void run() {
		String input = scan.nextLine();
		interpreter.sent(input);
		Message msg;
		if (!gameStarted) {
			print("The game hasn't started yet!!!!");
		} else if (!myTurn) {
			print("Wait for your turn!");
		} else{
			msg=interpreter.parseString(input);
			if(msg==null)
				print("Input error! Retry:");
			//else TODO invia msg
		}
	}
	
}
