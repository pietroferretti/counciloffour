package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClientOutHandler implements Runnable {

	private ObjectOutputStream socketOut;

	public ClientOutHandler(ObjectOutputStream socketOut) {
		this.socketOut = socketOut;
	}

	@Override
	public void run() {

		System.out.println("RUNNING");
		Scanner stdIn = new Scanner(System.in);

		while (true) {

			String inputLine = stdIn.nextLine();
			try {
				socketOut.writeObject(inputLine);
				socketOut.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("sending "+ inputLine);
				
			

		}
	}
}
