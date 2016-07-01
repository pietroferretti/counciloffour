package it.polimi.ingsw.ps14.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.rmi.ClientViewRemoteImpl;
import it.polimi.ingsw.ps14.client.rmi.Life;
import it.polimi.ingsw.ps14.client.rmi.RMICommunication;
import it.polimi.ingsw.ps14.client.socket.SocketCommunication;
import it.polimi.ingsw.ps14.client.socket.SocketMessageHandlerIn;
import it.polimi.ingsw.ps14.client.socket.SocketMessageHandlerOut;
import it.polimi.ingsw.ps14.client.view.CLIView;
import it.polimi.ingsw.ps14.client.view.ClientView;
import it.polimi.ingsw.ps14.client.view.GUIView;
import it.polimi.ingsw.ps14.server.ServerViewRemote;

public class Client {

	private static final int RMI_PORT = 52365;
	private static final String NAME = "council4";
	private static final String HOST = "127.0.0.1";// 192.168.43.212
	private static final int PORT = 19999;


	private static final Logger LOGGER = Logger.getLogger(Client.class
			.getName());
                              

	private Client() {
	};

	public static void main(String[] args) throws IOException,
			NotBoundException, AlreadyBoundException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Council of Four!");

		System.out.println("Choose a name:");
		String name = scanner.nextLine();
		
		String view;
		ClientView clientView;
		do {
			System.out.println("CLI or GUI?");
			view = scanner.nextLine();
		} while (!view.toUpperCase().matches("^(CLI|GUI)$"));
		
		if (view.toUpperCase().matches("^(CLI)$")) {
			
			clientView = new CLIView(scanner, name);
		
		} else if (view.toUpperCase().matches("^(GUI)$")) {
			
			clientView = new GUIView(name);
			System.out.println("GUI not yet implemented");
			
		} else {
			scanner.close();
			throw new IllegalArgumentException(
					"Something went wrong while parsing the interface type");
		}


		String input;
		do {
			System.out.println("Socket or RMI?");
			input = scanner.nextLine();
		} while (!input.toUpperCase().matches("^(SOCKET|RMI)$"));

		if (input.toUpperCase().matches("^(SOCKET)$")) {
			Socket socket;
			try {

				try {
					socket = new Socket(HOST, PORT);
					System.out.println("Connection created.");

				} catch (IOException e) {

					LOGGER.warning(String.format(
							"Couldn't create socket at IP '%s' on port '%d'",
							HOST, PORT));
					throw e;

				}

				
				SocketMessageHandlerOut msgHandlerOut = new SocketMessageHandlerOut(
						new ObjectOutputStream(socket.getOutputStream()));

				SocketCommunication socketCommunication = new SocketCommunication(msgHandlerOut,clientView);
				clientView.setCommunication(socketCommunication);
				
				SocketMessageHandlerIn msgHandlerIn = new SocketMessageHandlerIn(socketCommunication, new ObjectInputStream(
								socket.getInputStream()));
				
				Thread inThread = new Thread(msgHandlerIn);
				inThread.start();
				
				// prima di fare partire la guiview bisogna aspettare di avere l'id (?)
//				waitForIdThenRun(clientView);
				clientView.run();

			} catch (IOException e) {

				LOGGER.log(Level.WARNING,
						"Couldn't create connection, closing.", e);

			}

		} else if (input.toUpperCase().matches("^(RMI)$")) {

			Registry registry = LocateRegistry.getRegistry(HOST, RMI_PORT);
			ServerViewRemote serverStub = (ServerViewRemote) registry.lookup(NAME);

			Life life=new Life();
			ClientViewRemoteImpl rmiView = new ClientViewRemoteImpl(clientView,life);

			RMICommunication communication = new RMICommunication(serverStub);
			
			life.setCommmunication(communication);
			clientView.setCommunication(communication);
			
			serverStub.registerClient(rmiView);
			
			System.out.println("Connection created.");

//			waitForIdThenRun(clientView);
			clientView.run();
			
			
			
		} else {
			scanner.close();
			throw new IllegalArgumentException(
					"Something went wrong while parsing the connection type");
		}

	}
	
	private static void waitForIdThenRun(ClientView clientView){
		if (clientView.getPlayerID() != null) {
			
			clientView.run(); 
		
		} else {
		
			System.out.println("Waiting for an ID from the server...");
			int checks = 0;
			while (clientView.getPlayerID() != null && checks<30) {
				try {
					Thread.sleep(100);
					checks++;
				} catch (InterruptedException e) {
					LOGGER.log(Level.SEVERE, "Error while waiting for an ID.", e);
				}
			}
			
			if (clientView.getPlayerID() == null) {
				System.out.println("No ID received from server, closing...");
			} else {
				clientView.run();
			}
		}		
	}

}
