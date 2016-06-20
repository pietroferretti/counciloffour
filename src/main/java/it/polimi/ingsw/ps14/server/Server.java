package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.Game;
import it.polimi.ingsw.ps14.client.RMI.ClientFolda;
import it.polimi.ingsw.ps14.client.RMI.ClientViewRemote;
import it.polimi.ingsw.ps14.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * La classe Server resta in ascolto su una specifica porta e gestisce la ripartizione delle connessioni in ingresso,
 */
public class Server {
	private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

	private static final int PORT = 19999;
	private static final int COUNTDOWN = 20;
	
	private final static int RMI_PORT = 52365;
	private final String NAME = "council4";


	private Integer idCounter;
	private ServerSocket serverSocket;

	private ExecutorService executor;

	private List<ServerView> connections = new ArrayList<>();
	private List<ServerView> waitingConnectionsSocket = new ArrayList<>();
	
	private List<ClientViewRemote> waitingConnectionsRMI = new ArrayList<>();

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
	public synchronized void registerWaitingConnectionRMI(View c) {
		View connection=new ClientFolda(idCounter);
		idCounter++;
		waitingConnections.add(c);

	}

	/*
	 * Deregistro una connessione
	 */
	public synchronized void deregisterConnection(SocketServerView c) {
		connections.remove(c);
		if (waitingConnectionsSocket.contains(c)) {
			waitingConnectionsSocket.remove(c);
		}
	}

	/*
	 * Mi metto in attesa di un altro giocatore
	 */
	public synchronized void meeting() {
		
		LOGGER.info("Connection added.");

		if ((waitingConnectionsSocket.size() + waitingConnectionsRMI.size()) == 2) {
			
			LOGGER.info("Starting timer.");
			
			timer = new Timer();
			timerTask = new TimerTask() {

				@Override
				public void run() {
					if((waitingConnectionsSocket.size() + waitingConnectionsRMI.size())>=2){
					try {
						LOGGER.info(String.format("Creating game with %d player.", (waitingConnectionsSocket.size() + waitingConnectionsRMI.size())));

						List<View> viewsSocket = new ArrayList<>(waitingConnectionsSocket);
						List<View> viewsRMI = new ArrayList<>(waitingConnectionsRMI);

						activeGames.add(new Game(viewsSocket,viewsRMI));
						

						waitingConnectionsSocket.clear();
						waitingConnectionsRMI.clear();

					} catch (Exception e) {
						LOGGER.log(Level.SEVERE, "Unexpected exception while creating a new game.", e);
					}
					}
				}
			};
			timer.schedule(timerTask, (long) COUNTDOWN * 1000);
		}

	}

	public void startRMI() throws RemoteException, AlreadyBoundException {
		// idplayer ++ quando attivo una nuova connessione
		LOGGER.warning("RMI not yet implemented");
		
		Registry registry;
		
			registry = LocateRegistry.createRegistry(RMI_PORT);
			System.out.println("Constructing the RMI registry");
			RMIServerView rmiView=new RMIServerView(this);
			registry.bind(NAME, rmiView);
		
		
//		rmiView.registerObserver(this.controller);
//		this.gioco.registerObserver(rmiView);
//		
//		RMIViewRemote viewRemote=(RMIViewRemote) UnicastRemoteObject.
//				exportObject(rmiView, 0);
//		
//		System.out.println("Binding the server implementation to the registry");
		
		
		
		
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
				waitingConnectionsSocket.add(connection);
				meeting();

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

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		Server server = new Server();

		LOGGER.info("STARTING RMI SERVER");
		server.startRMI();

		LOGGER.info("STARTING SOCKET SERVER");
		server.startSocket();
	}

}