package it.polimi.ingsw.ps14.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.ps14.controller.Controller;
import it.polimi.ingsw.ps14.model.Game;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.view.CLIView;
import it.polimi.ingsw.ps14.view.View;

/*
 * La classe Server resta in ascolto su una specifica porta e gestisce la ripartizione delle connessioni in ingresso,
 */
public class Server {
	private static final int PORT = 12345;
	private static final int COUNTDOWN = 20;
	private static int IDPlayer;
	private ServerSocket serverSocket;

	private ExecutorService executor;

	private List<ConnectionSocket> connections = new ArrayList<ConnectionSocket>();

	private Map<Integer, ConnectionSocket> waitingConnection = new HashMap<>();

	static Timer timer;
	TimerTask timerTask;

	/*
	 * Registro una connessione attiva
	 */
	private synchronized void registerConnection(ConnectionSocket c) {
		connections.add(c);
	}

	/*
	 * Deregistro una connessione
	 */
	public synchronized void deregisterConnection(ConnectionSocket c) {
		connections.remove(c);
		Iterator<Integer> iterator = waitingConnection.keySet().iterator();
		while (iterator.hasNext()) {
			if (waitingConnection.get(iterator.next()) == c) {
				iterator.remove();
			}
		}

	}

	/*
	 * Mi metto in attesa di un altro giocatore
	 */
	public synchronized void meeting(ConnectionSocket c, int IDPlayer) {
		waitingConnection.put(IDPlayer, c);
		if (waitingConnection.size() == 2) {
			timer.cancel();

			timer = new Timer();

			timer.schedule(timerTask, (long) COUNTDOWN * 1000);

			timerTask = new TimerTask() {
				@Override
				public void run() {
					try {

						List<Integer> keys = new ArrayList<>(waitingConnection.keySet());
						Game model = new Game();// Q:game contiene anche list di
												// player...come glielo passo?
												// A:li setto dopo
						Controller controller = new Controller(model);
						for (int key : keys) {
							if (waitingConnection.get(key) instanceof ConnectionSocket) {
								ConnectionSocket conn = waitingConnection.get(key);
								// cli view vuole modelview, model view vuola
								// lista
								// giocatori...come faccio ad averla prima di
								// mostrare la cli ai connessi?
								View playerView = new CLIView(conn.getSocketIn(), conn.getSocketOut(), new Player(key));
								// Cliview
								// scambia
								// oggetti
								// col
								// server,
								// non
								// stringhe!
								model.addObserver(playerView);
								playerView.addObserver(controller);// sistema il
																	// costruttore
																	// di
																	// cliview
																	// e questo
																	// va a
																	// posto
								model.addPlayer(new Player(key));// aggiungere
																	// un
																	// costruttore
																	// che
																	// costruisce
																	// un
																	// player
																	// partendo
																	// dall'id
							}
						
						//if(waitingConnection.get(key) instanceof ConnectionRMI){
							//??????????????????????????????????
						}
					

						waitingConnection.clear();
					} catch (Exception e) {
						// LOG.log(Level.SEVERE, "Unexpected exception while
						// creating a " +
						// "new game.", e);
					}
				}
			};
		}
	}

	public Server() throws IOException {
		timer = new Timer();
		this.serverSocket = new ServerSocket(PORT);
	}

	public void startSocket() {
		executor = Executors.newFixedThreadPool(128);
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("SERVER SOCKET READY ON PORT" + PORT);
		} catch (IOException e1) {
			System.out.println("SERVER SOCKET ERROR");
			e1.printStackTrace();
			return;
		}
		while (true) {
			try {
				Socket newSocket = serverSocket.accept();
				ConnectionSocket connection = new ConnectionSocket(newSocket, this, IDPlayer);
				IDPlayer++;
				registerConnection(connection);
				executor.submit(connection);// like Thread.start();
			} catch (IOException e) {
				System.out.println("Errore di connessione!");
			}
		}
	}

	public void startRMI() {
		// idplayer ++ quando attivo una nuova connessione
	}

	public static void main(String[] args) {
		Server server;
		IDPlayer = 0;
		try {
			server = new Server();
			System.out.println("START RMI");
			server.startRMI();
			System.out.println("START SOCKET");
			server.startSocket();
		} catch (IOException e) {
			System.err.println("Impossibile inizializzare il server: " + e.getMessage() + "!");
		}
	}

}