package it.polimi.ingsw.ps14.client.rmi;

import it.polimi.ingsw.ps14.client.Client;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that run in a separated thread to send "ping" to server each TIMEPING ms.
 * In that way server can know when client is disconnected.
 *
 */
public class Life implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(Life.class
			.getName());
	
	private volatile RMICommunication comm = null;
	private volatile Integer playerID = null;
	private final int TIMEPING = 2000;

	public void setCommmunication(RMICommunication comm) {
		this.comm = comm;
	}

	public void setPlayerID(int id) {
		playerID = id;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (comm != null && playerID != null) {
					Thread.sleep(TIMEPING);
					comm.clientAlive(playerID);
				}
				else Thread.sleep(200);
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, "Ping thread interrupted.", e);
				Thread.currentThread().interrupt();
			}
			
		}

	}

}
