package it.polimi.ingsw.ps14.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Observable;

import it.polimi.ingsw.ps14.model.actions.Action;

public class ConnectionSocket extends Observable implements Runnable {

	private Socket socket;
	private final int IDPlayer;

	//
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	//

	private Server server;

	private boolean active = true;

	public ConnectionSocket(Socket socket, Server server, int IDPlayer) throws IOException {
		this.socket = socket;
		this.server = server;
		this.IDPlayer = IDPlayer;
		//
		this.socketIn = new ObjectInputStream(socket.getInputStream());
		this.socketOut = new ObjectOutputStream(socket.getOutputStream());
		//
	}

	
	public ObjectInputStream getSocketIn() {
		return socketIn;
	}


	public ObjectOutputStream getSocketOut() {
		return socketOut;
	}

	private synchronized boolean isActive() {
		return active;
	}

	@Override
	public void run() {

		server.meeting(this, IDPlayer);

		try {
			while (isActive()) {
				
				//ESEMPIO DI COME SI PUò USARE
				
				Object object = socketIn.readObject();
				if (object instanceof Action) {
					Action action = (Action) object;
					System.out.println("VIEW: received the action " + action);

					this.notifyObservers(action);

				}
				if (object instanceof String) {
					// non è che tutti gli object possono essere visti come
					// stringhe e viene sempre eseguito questo if?
					String data = (String) object;
					System.out.println("VIEW: received the string " + data);
					// this.socketOut.writeObject(data);
					this.socketOut.flush();
				}

			}

		} catch (IOException | NoSuchElementException | ClassNotFoundException e) {
			System.err.println("Errore!");
		} finally {
			close();
		}
	}

	public synchronized void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
		}
		active = false;
	}

	private void close() {
		closeConnection();
		System.out.println("Deregistro il client!");
		server.deregisterConnection(this);
	}

}
