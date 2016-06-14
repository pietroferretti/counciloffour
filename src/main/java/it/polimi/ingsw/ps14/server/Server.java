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
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.Game;
import it.polimi.ingsw.ps14.controller.Controller;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.view.CLIView;
import it.polimi.ingsw.ps14.view.View;

/*
 * La classe Server resta in ascolto su una specifica porta e gestisce la ripartizione delle connessioni in ingresso,
 */
public class Server {
	private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
	private static final int PORT = 19999;
	private static final int COUNTDOWN = 20;
	private static int IDPlayer;
	private ServerSocket serverSocket;

	private ExecutorService executor;

	private List<ConnectionSocket> connections = new ArrayList<ConnectionSocket>();

	private Map<Integer, ConnectionSocket> waitingConnection = new HashMap<>();

	private static Timer timer;
	private TimerTask timerTask;
	
	private static List<Game> activeGames = new ArrayList<>();
	
	public Server() throws IOException {
		timer = new Timer();
	}
	
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
	public synchronized void meeting(ConnectionSocket c, int idPlayer) {
		waitingConnection.put(idPlayer, c);
		if (waitingConnection.size() == 2) {

			timer = new Timer();

			timerTask = new TimerTask() {
				@Override
				public void run() {
					try {

						List<Integer> keys = new ArrayList<>(waitingConnection.keySet());

						System.err.println("creo partita con " + keys.size() + " connessioni!");

						List<View> views = new ArrayList<>();
						for (int key : keys) {
							if (waitingConnection.get(key) instanceof ConnectionSocket) {
								ConnectionSocket conn = waitingConnection.get(key);

								views.add(new CLIView(conn.getSocketOut()));
								System.out.println("CREATO NUOVA VIEW");

							}

							// if(waitingConnection.get(key) instanceof
							// ConnectionRMI){
							// ??????????????????????????????????
						}
						
						activeGames.add(new Game(views));
						
						waitingConnection.clear();
					} catch (Exception e) {
						LOGGER.log(Level.SEVERE, "Unexpected exception while creating a " + "new game.", e);
					}
				}
			};
			timer.schedule(timerTask, (long) COUNTDOWN * 1000);

		}
	}

	public void startSocket() {
		executor = Executors.newFixedThreadPool(128);
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("SERVER SOCKET READY ON PORT " + PORT);
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
				meeting(connection, IDPlayer);
				System.out.println("appena uscito da meeting");
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