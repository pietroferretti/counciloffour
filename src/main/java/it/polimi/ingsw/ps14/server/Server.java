package it.polimi.ingsw.ps14.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.Game;
import it.polimi.ingsw.ps14.view.View;

/*
 * La classe Server resta in ascolto su una specifica porta e gestisce la ripartizione delle connessioni in ingresso,
 */
public class Server {
	private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

	private static final int PORT = 19999;
	private static final int COUNTDOWN = 20;

	private int idCounter;
	private ServerSocket serverSocket;

	private ExecutorService executor;

	private List<SocketServerView> connections = new ArrayList<>();
	private List<SocketServerView> waitingConnections = new ArrayList<>();

	private static Timer timer;
	private TimerTask timerTask;

	private static List<Game> activeGames = new ArrayList<>();

	public Server() {
		timer = new Timer();
		idCounter = 0;
	}

	/*
	 * Registro una connessione attiva
	 */
	private synchronized void registerConnection(SocketServerView c) {
		connections.add(c);
	}

	/*
	 * Deregistro una connessione
	 */
	public synchronized void deregisterConnection(SocketServerView c) {
		connections.remove(c);
		if (waitingConnections.contains(c)) {
			waitingConnections.remove(c);
		}
	}

	/*
	 * Mi metto in attesa di un altro giocatore
	 */
	public synchronized void meeting(SocketServerView c) {
		waitingConnections.add(c);
		LOGGER.info("Connection added.");

		if (waitingConnections.size() == 2) {
			
			LOGGER.info("Starting timer.");
			
			timer = new Timer();
			timerTask = new TimerTask() {

				@Override
				public void run() {
					if(waitingConnections.size()>=2){
					try {
						LOGGER.info(String.format("Creating game with %d player.", waitingConnections.size()));

						List<View> views = new ArrayList<>(waitingConnections);

						activeGames.add(new Game(views));

						waitingConnections.clear();

					} catch (Exception e) {
						LOGGER.log(Level.SEVERE, "Unexpected exception while creating a new game.", e);
					}
					}
				}
			};
			timer.schedule(timerTask, (long) COUNTDOWN * 1000);
		}

	}

	public void startRMI() {
		// idplayer ++ quando attivo una nuova connessione
		LOGGER.warning("RMI not yet implemented");
	}

	public void startSocket() {
		executor = Executors.newFixedThreadPool(128);

		try {
			serverSocket = new ServerSocket(PORT);
			LOGGER.info("SERVER SOCKET READY ON PORT " + PORT);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "SERVER SOCKET ERROR", e);
			return;
		}

		boolean exit = false;
		while (!exit) {
			try {
				Socket newSocket = serverSocket.accept();
				SocketServerView connection = new SocketServerView(newSocket, this, idCounter);
				idCounter++;
				registerConnection(connection);
				meeting(connection);

				executor.submit(connection);

			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Socket connection error!", e);
				exit = true;
			}
		}

		if (serverSocket != null && !serverSocket.isClosed()) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Error when closing server socket.", e);
			}
		}
	}

	public static void main(String[] args) {
		Server server = new Server();

		LOGGER.info("STARTING RMI SERVER");
		server.startRMI();

		LOGGER.info("STARTING SOCKET SERVER");
		server.startSocket();
	}

}