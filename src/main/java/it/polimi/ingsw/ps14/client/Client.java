package it.polimi.ingsw.ps14.client;

import it.polimi.ingsw.ps14.client.RMI.ClientRMI;
import it.polimi.ingsw.ps14.client.socket.ClientSocket;
import it.polimi.ingsw.ps14.client.socket.SocketMessageHandlerIn;
import it.polimi.ingsw.ps14.client.socket.SocketMessageHandlerOut;
import it.polimi.ingsw.ps14.view.CLIView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
	private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

	private Client() {
	};

	public static void main(String[] args) throws IOException, NotBoundException, AlreadyBoundException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Council of Four!");

		// Un giorno, "CLI o GUI?"
		ClientView clientView = new CLIView(scanner);

		String input;
		do {
			System.out.println("Socket or RMI?");
			input = scanner.nextLine().toUpperCase();
		} while (!input.matches("^(SOCKET|RMI)$"));

		
		if (input.matches("^(SOCKET)$")) {

			// crea socket
			try {
			
				ClientSocket socket = new ClientSocket();
			
				// messagehandlerout = new socketmessagehandlerout(socket.out)
				SocketMessageHandlerOut msgHandlerOut = new SocketMessageHandlerOut(new ObjectOutputStream(socket.getOutputStream()));
				// 
				clientView.setHandlerOut(msgHandlerOut);
				
				// messagehandlerin = new socketmessagehandlerin(clientview)
				MessageHandlerIn msgHandlerIn = new SocketMessageHandlerIn(clientView, new ObjectInputStream(socket.getInputStream()));
				
				// all'interno di CLIView aggiungiamo un ciclo while true get input
				// from scanner
	
				ExecutorService executor = Executors.newFixedThreadPool(2);
	
				executor.submit(clientView);
				executor.submit(msgHandlerIn);
			
			} catch (IOException e) {
				
				LOGGER.log(Level.WARNING, "Couldn't create connection, closing.", e);
				
			}
			
		} else if (input.matches("^(RMI)$")) {
			LOGGER.warning("RMI not yet implemented");
			
			ClientRMI rmi= new ClientRMI(scanner);
			
			
			
		} else {
			scanner.close();
			throw new IllegalArgumentException("Something went wrong while parsing the connection type");
		}

		// ?
	}

}
