package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocket{
	private static final Logger LOGGER = Logger.getLogger(ClientSocket.class.getName());

	private static final int PORT = 19999;
	private static final String IP = "127.0.0.1";

	private Socket socket;
	
	public ClientSocket() throws IOException {
		
		try {
		
			socket = new Socket(IP, PORT);
			System.out.println("Connection created");
		
		} catch (IOException e) {
			
			LOGGER.warning(String.format("Couldn't create socket at IP '%s' on port '%d'", IP, PORT));
			throw e;
			
		}
	}

	public InputStream getInputStream() {
		try { 
			return socket.getInputStream();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Couldn't retrieve InputStream.", e);
			return null;
		}
	}
	
	public OutputStream getOutputStream() {
		try { 
			return socket.getOutputStream();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Couldn't retrieve OutputStream.", e);
			return null;
		}
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error when closing the socket.", e);
		}
	}

}
