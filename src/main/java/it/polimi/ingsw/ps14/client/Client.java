package it.polimi.ingsw.ps14.client;

import it.polimi.ingsw.ps14.client.rmi.ClientRMIView;
import it.polimi.ingsw.ps14.client.rmi.RMICommunication;
import it.polimi.ingsw.ps14.client.socket.SocketCommunication;
import it.polimi.ingsw.ps14.client.socket.SocketMessageHandlerIn;
import it.polimi.ingsw.ps14.client.socket.SocketMessageHandlerOut;
import it.polimi.ingsw.ps14.server.RMIViewRemote;
import it.polimi.ingsw.ps14.view.CLIView;
import it.polimi.ingsw.ps14.view.ClientView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

	private static final int RMI_PORT = 52365;
	private static final String NAME = "council4";
	private static final String HOST = "192.168.43.212";// 127.0.0.1
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

		// Un giorno, "CLI o GUI?"
		ClientView clientView = new CLIView(scanner);

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
					System.out.println("Connection created");

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

				// FIXME send the player name to the server
//				socketCommunication.setPlayerName(name);
				
				ExecutorService executor = Executors.newFixedThreadPool(3);
				executor.submit(clientView);
				executor.submit(msgHandlerIn);

			} catch (IOException e) {

				LOGGER.log(Level.WARNING,
						"Couldn't create connection, closing.", e);

			}

		} else if (input.toUpperCase().matches("^(RMI)$")) {

			Registry registry = LocateRegistry.getRegistry(HOST, RMI_PORT);
			RMIViewRemote serverStub = (RMIViewRemote) registry.lookup(NAME);

			ClientRMIView rmiView = new ClientRMIView(clientView);

			serverStub.registerClient(rmiView);
			RMICommunication communication=new RMICommunication(serverStub, clientView);
			clientView.setCommunication(communication);
			clientView.run();

		} else {
			scanner.close();
			throw new IllegalArgumentException(
					"Something went wrong while parsing the connection type");
		}

		// ?
	}

}
