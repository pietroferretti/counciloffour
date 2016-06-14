package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.ps14.view.CLIView;
import it.polimi.ingsw.ps14.view.View;

public class ClientSocket {

	private final static int PORT = 19999;
	private final static String IP = "192.168.43.212";

	View view = new CLIView(System.out);

	public void startClient() throws UnknownHostException, IOException {

		Socket socket = new Socket(IP, PORT);

		System.out.println("Connection created");

		ExecutorService executor = Executors.newFixedThreadPool(2);

		executor.submit(new ClientOutHandler(new ObjectOutputStream(socket.getOutputStream()), view));

		executor.submit(new ClientInHandler(new ObjectInputStream(socket.getInputStream()), view));
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		ClientSocket client = new ClientSocket();
		client.startClient();

	}
}
