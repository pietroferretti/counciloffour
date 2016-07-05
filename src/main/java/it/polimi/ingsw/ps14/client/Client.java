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

	private static final int SOCKET_PORT = 19999;
	private static final int RMI_PORT = 52365;
	private static final String NAME = "council4";

	private static final Logger LOGGER = Logger.getLogger(Client.class
			.getName());
                              

	private Client() {
	};

	public static void main(String[] args) throws IOException,
			NotBoundException, AlreadyBoundException, InterruptedException {

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
			
		} else {
			scanner.close();
			throw new IllegalArgumentException(
					"Something went wrong while parsing the interface type");
		}

		String host;
		do {
			System.out.println("What's the IP address of the server? (Leave empty if local)");
			host = scanner.nextLine();
		} while (!(host.isEmpty() || isValidIP(host)));
		
		if (host.isEmpty()) {
			host = "127.0.0.1";
		}

		String connType;
		do {
			System.out.println("Socket or RMI?");
			connType = scanner.nextLine();
		} while (!connType.toUpperCase().matches("^(SOCKET|RMI)$"));

		if (connType.toUpperCase().matches("^(SOCKET)$")) {
			Socket socket;
			try {

				try {
					socket = new Socket(host, SOCKET_PORT);
					System.out.println("Connection created.");

				} catch (IOException e) {

					LOGGER.warning(String.format(
							"Couldn't create socket at IP '%s' on port '%d'",
							host, SOCKET_PORT));
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
				
				waitForIdThenRun(clientView);

			} catch (IOException e) {

				LOGGER.log(Level.WARNING,
						"Couldn't create connection, closing.", e);

			}

		} else if (connType.toUpperCase().matches("^(RMI)$")) {

			Registry registry = LocateRegistry.getRegistry(host, RMI_PORT);
			ServerViewRemote serverStub = (ServerViewRemote) registry.lookup(NAME);

			Life life=new Life();
			ClientViewRemoteImpl rmiView = new ClientViewRemoteImpl(clientView,life);

			RMICommunication communication = new RMICommunication(serverStub);
			
			life.setCommmunication(communication);
			clientView.setCommunication(communication);
			
			serverStub.registerClient(rmiView);
			
			System.out.println("Connection created.");

			waitForIdThenRun(clientView);
			
		} else {
			scanner.close();
			throw new IllegalArgumentException(
					"Something went wrong while parsing the connection type");
		}

	}
	
	private static void waitForIdThenRun(ClientView clientView) throws InterruptedException {
		if (clientView.getPlayerID() != null) {
			
			Thread clientViewThread = new Thread(clientView);
			clientViewThread.start();
		
		} else {
		
			System.out.println("Waiting for an ID from the server...");
			int checks = 0;
			while (clientView.getPlayerID() == null && checks<30) {
				Thread.sleep(100);
				checks++;
			}
			
			if (clientView.getPlayerID() != null) {
				Thread clientViewThread = new Thread(clientView);
				clientViewThread.start();
			} else {
				System.out.println("No ID received from server, closing...");
			}
		}		
	}
	
	private static boolean isValidIP(String ip) {
	    try {
	        if (ip == null || ip.isEmpty()) {
	            return false;
	        }

	        String[] parts = ip.split("\\.");
	        if (parts.length != 4) {
	            return false;
	        }

	        for (String s : parts) {
	            int i = Integer.parseInt(s);
	            if ((i < 0) || (i > 255)) {
	                return false;
	            }
	        }
	        
	        if (ip.endsWith(".")) {
	            return false;
	        }
	        
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

}
