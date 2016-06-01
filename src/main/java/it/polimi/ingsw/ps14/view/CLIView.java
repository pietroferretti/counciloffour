package it.polimi.ingsw.ps14.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.model.ModelView;

/*
 * --------------------------Command Line Interface-----------------------
 * It acquires infos from player and show the game state each time something
 * has changed
 * 
 */

public class CLIView extends Observable implements View {

	private Scanner input;
	private PrintStream output;
	private Printer printer;

	protected PrintStream getOutput() {
		return output;
	}

	public CLIView(InputStream inputStream, OutputStream outputStream) {
		input = new Scanner(inputStream);
		output = new PrintStream(outputStream);
		printer = new Printer(output);
	}

	// It prints infos about regions, king, nobility track and victory points
	public void showGameBoard(ModelView model) {
		printer.printRegions(getGameboard(model).getRegions());
		printer.printKing(getGameboard(model).getKing());
		printer.printNobilityTrack(getGameboard(model).getNobilityTrack(), getPlayers(model));
		printer.printVictoryPoints(getPlayers(model));
	}
	
	//It prints infos about the specific player
	//TODO implementare controllo tale che ogni giocatore veda solo i propri attributi
	
	public void showPlayerDetails(Player player){
		/*
		 *carte politica
		 *permessi disponibili
		 *permessi utilizzati?
		 *numero assistenti
		 *nobiltà, punti??
		 * 
		 */
	}

	private List<Player> getPlayers(ModelView model) {
		return model.getModelCopy().getPlayers();
	}

	private GameBoard getGameboard(ModelView model) {
		return model.getModelCopy().getGameBoard();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
