package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.view.View;

public class ClientInHandler implements Runnable {

	private ObjectInputStream socketIn;
	private View clientView;

	public ClientInHandler(ObjectInputStream socketIn, View clientView) {
		this.socketIn = socketIn;
		this.clientView = clientView;
	}

	@Override
	public void run() {

		while (true) {

			try {
				Object object = socketIn.readObject();
				clientView.readMsg((Message) object);
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
