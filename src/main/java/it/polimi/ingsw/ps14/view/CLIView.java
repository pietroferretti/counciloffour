package it.polimi.ingsw.ps14.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.Message;
import it.polimi.ingsw.ps14.controller.NewPlayerMsg;
import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.model.modelview.PlayerChangedPrivateMsg;
import it.polimi.ingsw.ps14.model.modelview.PlayerChangedPublicMsg;
import it.polimi.ingsw.ps14.model.modelview.PlayerView;
import it.polimi.ingsw.ps14.model.modelview.RegionView;

/*
 * --------------------------Command Line Interface-----------------------
 * It acquires infos from player and show the game state each time something
 * has changed
 * 
 */
//TODO faccio stampare solo i dettagli del giocatori che ha finito il turno
//TODO metodo per tutti i miei dettagli?
//TODO implementare richiesta per stampa di tutto
public class CLIView extends Observable implements View, Runnable {

	private Scanner input;
	// private PrintStream output;
	private Printer printer;

	// each player owns a specific view CLI or GUI identified by his ID.
	private int playerID;

	private ModelView mv;
	private Message message;

	public CLIView(InputStream inputStream, OutputStream outputStream, ModelView modelView, int playerID) {
		input = new Scanner(inputStream);
		// output = new PrintStream(outputStream);
		printer = new Printer(new PrintStream(outputStream));
		mv = modelView;
		this.playerID = playerID;
	}

	public int getPlayerID() {
		return playerID;
	}

	/**
	 * It prints infos about regions, king, nobility track and victory points.
	 */
	@Override
	public void showGameboard() {
		show("-----REGIONS LIST-----");
		show("");
		for (RegionView regionView : mv.getRegionsView()) {
			printer.printRegions(regionView.getRegionCopy());
		}
		printer.printKing(mv.getKingView().getKingCopy());
		printer.printNobilityTrack(mv.getNobilityTrackView().getNobilityTrackCopy(), getPlayers());
		printer.printVictoryPoints(getPlayers());
	}

	private void setMessage(Message message) {
		this.message = message;
		setChanged();
		notifyObservers(message);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		show("Insert your name:");
		String name = input.nextLine();
		setMessage(new NewPlayerMsg(name));

		// TODO CONTROLLARE!!!
		// while (true) {
		// input.nextLine();
		// }

	}

	@Override
	public void update(Observable o, Object arg) {

		if (!(o instanceof ModelView)) {

			throw new IllegalArgumentException();

		} else if (arg instanceof PlayerChangedPublicMsg) {
			// avvisare altri
			if (((PlayerChangedPublicMsg) arg).getPlayerID() == playerID)
				showThisPlayerDetails();
			else
				showOtherPlayersDetails();

		} else if (arg instanceof PlayerChangedPrivateMsg) {

			if (((PlayerChangedPublicMsg) arg).getPlayerID() == playerID)
				showThisPlayerDetails();
			// avvisare altri

		} else if ("REGIONVIEW".equals((String) arg)) {
			showOtherPlayersDetails();
		}

	}

	/**
	 * It prints each player public details
	 * 
	 */
	// TODO It prints other players public details
	@Override
	public void showOtherPlayersDetails() {
		for (Player player : getPlayers()) {
			if (!(player.getId() == playerID))
				printer.printPlayerPublicDetails(player);
		}
	}

	/**
	 * It prints this player private details
	 */
	// TODO change this in all player details
	@Override
	public void showThisPlayerDetails() {
		for (Player player : getPlayers()) {
			if (player.getId() == playerID) {
				printer.printPlayerPublicDetails(player);
				printer.printPlayerPrivateDetails(player);
				break;
			}

		}
	}

	private List<Player> getPlayers() {
		List<Player> players = new ArrayList<>();
		for (PlayerView playerView : mv.getPlayersView()) {
			players.add(playerView.getPlayerCopy());
		}
		return players;
	}

	@Override
	public void show(String string) {
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

}
