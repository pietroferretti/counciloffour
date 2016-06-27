package it.polimi.ingsw.ps14.client.rmi;

public class Life implements Runnable {

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
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
