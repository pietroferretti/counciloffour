package it.polimi.ingsw.ps14.client.RMI;

import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.server.RMIViewRemote;
import it.polimi.ingsw.ps14.view.CLIView;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import javax.management.Query;

public class ClientRMI {
	private final static int RMI_PORT = 52365;
	private static final String NAME = "council4";

	private final static String HOST = "127.0.0.1";

	private final static int PORT = 52365;

	public ClientRMI(Scanner scanner) throws RemoteException,
			NotBoundException, AlreadyBoundException {

		Registry registry = LocateRegistry.getRegistry(HOST, PORT);
		RMIViewRemote serverStub = (RMIViewRemote) registry.lookup(NAME);

		ClientRMIView rmiView = new ClientRMIView();

		serverStub.registerClient(rmiView);

		CLIView gui = new CLIView(scanner, rmiView);// gli passo rmi view remote
													// che contiene i metodi
													// invocabili sul server (
													// mi servirà
													// nell'interpreterServer)

		// while (true) {
		//
		// String inputLine = stdIn.nextLine();
		// System.out.println("SENDING"+inputLine);
		// Action action;
		// Query query;
		// try {
		//
		// switch (inputLine) {
		// case "ON":
		// action = new TurnOn();
		// serverStub.eseguiAzione(action);
		// break;
		// case "OFF":
		// action = new TurnOff();
		// serverStub.eseguiAzione(action);
		// break;
		// case "PRINT":
		// action = new PrintModel();
		// serverStub.eseguiAzione(action);
		// break;
		// case "SCOMMETTI":
		// action = new Scommetti();
		// serverStub.eseguiAzione(action);
		// break;
		//
		// default:
		// break;
		// }
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		// }
	}

}
