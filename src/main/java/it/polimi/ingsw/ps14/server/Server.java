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

import it.polimi.ingsw.ps14.client.rmi.ClientViewRemote;

/*
 * La classe Server resta in ascolto su una specifica porta e gestisce la ripartizione delle connessioni in ingresso,
 */
public class Server {

	private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

	private static final int SOCKET_PORT = 19999;
	private static final int RMI_PORT = 52365;
	
	private static final String NAME = "council4";
	
	private static final int COUNTDOWN = 20;	// TODO ricordare di settare a 20 per la consegna
	private static final int DEFAULT_PLAYERS_NUMBER = 4;

	private int maxPlayers;
	
	private Integer idCounter;
	private ServerSocket serverSocket;

	private ExecutorService executor;

	private List<ServerView> connections = new ArrayList<>();
	private List<ServerView> waitingConnections = new ArrayList<>();

	private static Timer timer;
	private TimerTask timerTask;

	private static List<Game> activeGames = new ArrayList<>();

	public Server(int maxPlayers) {
		this.maxPlayers = maxPlayers;
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
		RMIServerView connection = new RMIServerView(idCounter, clientStub, this);
		idCounter++;
		
		rmiServerIn.addServerView(connection);

		waitingConnections.add(connection);

	}

	/*
	 * Deregistro una connessione
	 */
	public synchronized void deregisterConnection(ServerView c) {
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
		if (waitingConnections.size() == maxPlayers) {
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
					if (waitingConnections.size() >= 2 && waitingConnections.size() < maxPlayers) {
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

		LOGGER.info("Constructing the RMI registry");
		registry = LocateRegistry.createRegistry(RMI_PORT);

		LOGGER.info("Binding the server implementation to the registry");
		ServerViewRemoteImpl rmiView = new ServerViewRemoteImpl(this);
		registry.bind(NAME, rmiView);

		UnicastRemoteObject.exportObject(rmiView, 0);

		LOGGER.info("SERVER RMI READY ON PORT " + RMI_PORT);

	}

	public void startSocket() {
		executor = Executors.newFixedThreadPool(128);

		try (ServerSocket serverSocketResource = new ServerSocket(SOCKET_PORT)) {
			serverSocket = serverSocketResource;
			
			LOGGER.info("SERVER SOCKET READY ON PORT " + SOCKET_PORT);
		
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

	/**
	 * Takes the maximum number of players for a game as argument.
	 * @param args
	 * 			the maximum number of players for a game
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		int maxPlayers = DEFAULT_PLAYERS_NUMBER;
		
		if (args.length > 0) {
		    try {
		        maxPlayers = Integer.parseInt(args[0]);
				System.out.println(String.format("Max players: %d", maxPlayers));
		    } catch (NumberFormatException e) {
		        LOGGER.warning("Argument" + args[0] + " must be an integer.");
		        System.exit(1);
		    }
		} else {
			System.out.println(String.format("Max players: %d (default value)", maxPlayers));
		}
		
		Server server = new Server(maxPlayers);

		LOGGER.info("STARTING RMI SERVER");
		server.startRMI();

		LOGGER.info("STARTING SOCKET SERVER");
		server.startSocket();
	}

	private void createGame(List<ServerView> viewsReady) {
		try {
			LOGGER.info(String.format("Creating game with %d player.", viewsReady.size()));

			activeGames.add(new Game(viewsReady));

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unexpected exception while creating a new game.", e);
		
		}
	}
}
