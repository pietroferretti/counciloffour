package it.polimi.ingsw.ps14.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.Game;
import it.polimi.ingsw.ps14.client.rmi.ClientViewRemote;

/*
 * La classe Server resta in ascolto su una specifica porta e gestisce la ripartizione delle connessioni in ingresso,
 */
public class Server {

	private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

	private static final int PLAYERS_NUMBER = 4;

	private static final int PORT = 19999;
	
	private static final int COUNTDOWN = 1;	// TODO ricordare di settare a 20 per la consegna

	private static final int RMI_PORT = 52365;
	private static final String NAME = "council4";

	private Integer idCounter;
	private ServerSocket serverSocket;

	private ExecutorService executor;

	private List<ServerView> connections = new ArrayList<>();
	private List<ServerView> waitingConnections = new ArrayList<>();

	private static Timer timer;
	private TimerTask timerTask;

	private static List<Game> activeGames = new ArrayList<>();

	public Server() {
		timer = new Timer();
		idCounter = 0;
	}

	/*
	 * Registro una connessione attiva in socket
	 */
	public synchronized void registerConnection(ServerView c) {
		connections.add(c);
	}

	/*
	 * Registro una connessione attiva in rmi
	 */
	public synchronized void registerWaitingConnectionRMI(
			ClientViewRemote clientStub, ServerViewRemoteImpl rmiServerIn) {
		RMIServerView connection = new RMIServerView(idCounter, clientStub);
		idCounter++;
		
		rmiServerIn.addServerView(connection);

		waitingConnections.add(connection);

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
	public synchronized void meeting() {

		LOGGER.info("Connection added.");
		if (waitingConnections.size() == PLAYERS_NUMBER) {
			timer.cancel();
			List<ServerView> viewsReady = new ArrayList<>(waitingConnections);
			waitingConnections.clear();
			createGame(viewsReady);
		}
		if (waitingConnections.size() == 2) {

			LOGGER.info("Starting timer.");

			timer = new Timer();
			timerTask = new TimerTask() {

				@Override
				public void run() {
					if (waitingConnections.size() >= 2 && waitingConnections.size() < PLAYERS_NUMBER) {
						List<ServerView> viewsReady = new ArrayList<>(waitingConnections);
						waitingConnections.clear();
						createGame(viewsReady);
					}
				}
			};
			timer.schedule(timerTask, (long) COUNTDOWN * 1000);
		}

	}

	public void startRMI() throws RemoteException, AlreadyBoundException {

		Registry registry;

		registry = LocateRegistry.createRegistry(RMI_PORT);
		System.out.println("Constructing the RMI registry");
		ServerViewRemoteImpl rmiView = new ServerViewRemoteImpl(this);
		registry.bind(NAME, rmiView);

		UnicastRemoteObject.exportObject(rmiView, 0);

		System.out.println("Binding the server implementation to the registry");

	}

	public void startSocket() {
		executor = Executors.newFixedThreadPool(128);

		try (ServerSocket serverSocketResource = new ServerSocket(PORT)) {
			serverSocket = serverSocketResource;
			
			LOGGER.info("SERVER SOCKET READY ON PORT " + PORT);
		
			while (true) {
			
				Socket newSocket = serverSocket.accept();
				SocketServerView connection = new SocketServerView(newSocket, this, idCounter);
				idCounter++;
				registerConnection(connection);
				waitingConnections.add(connection);
				meeting();

				executor.submit(connection);
			}

		} catch (IOException e) {
			
			LOGGER.log(Level.SEVERE, "Socket connection error!", e);
		
		}
	}

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		Server server = new Server();

		LOGGER.info("STARTING RMI SERVER");
		server.startRMI();

		LOGGER.info("STARTING SOCKET SERVER");
		server.startSocket();
	}

	private void createGame(List<ServerView> viewsReady) {
		try {
			Thread.sleep(500);			
			LOGGER.info(String.format("Creating game with %d player.", viewsReady.size()));

			activeGames.add(new Game(viewsReady));

		} catch (IOException | InterruptedException e) {
			LOGGER.log(Level.SEVERE, "Unexpected exception while creating a new game.", e);
		}
	}
}
