package it.polimi.ingsw.ps14.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.model.modelview.PlayerView;

/*
 * --------------------------Command Line Interface-----------------------
 * It acquires infos from player and show the game state each time something
 * has changed
 * 
 */

public class CLIView extends Observable implements View, Runnable {

	private Scanner input;
	private PrintStream output;
	private Printer printer;

	private ModelView mv;
	//private Message answer;

	public CLIView(InputStream inputStream, OutputStream outputStream) {
		input = new Scanner(inputStream);
		output = new PrintStream(outputStream);
		printer = new Printer(output);
	}

	protected PrintStream getOutput() {
		return output;
	}

	// It prints infos about regions, king, nobility track and victory points
	/*public void showGameBoard(ModelView model) {
		printer.printRegions(getGameboard(model).getRegions());
		printer.printKing(getGameboard(model).getKing());
		printer.printNobilityTrack(getGameboard(model).getNobilityTrack(), getPlayers(model));
		printer.printVictoryPoints(getPlayers(model));
	}*/

	// TODO implementare controllo tale che ogni giocatore veda solo i propri
	// attributi

	public void showPlayersDetails(List<PlayerView> players) {
		for (PlayerView player : players) {
			printer.printPlayerPublicDetails(player.getPlayerCopy());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (((String) arg).equals("PLAYERVIEW")) {
			showPlayersDetails(mv.getPlayersView());
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		//output.println("Insert your name:");
		//String name = input.nextLine();

		// answer = new ModifyPlayerNameMessage(input.nextLine());
		// answer.setMessage(input.nextLine());

	}

}
