package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientInHandler implements Runnable {

	private ObjectInputStream socketIn;

	public ClientInHandler(ObjectInputStream socketIn) {
		this.socketIn = socketIn;
	}

	@Override
	public void run() {

		while (true) {

			try {
				Object object = socketIn.readObject();

				if (object instanceof String)
					System.out.println((String) object);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Errore in connessione col SERVER");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Errore in connessione col SERVER");
				return;
			}
		}
	}
}
