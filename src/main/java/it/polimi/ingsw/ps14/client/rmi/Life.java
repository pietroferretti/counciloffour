package it.polimi.ingsw.ps14.client.rmi;


public class Life implements Runnable {

	private RMICommunication comm;
	private Integer playerID=null;

	public Life(RMICommunication comm) {
		this.comm = comm;
	}
	
	public void setPlayerID(int id){
		playerID=id;
	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(1000 * 30);
				comm.clientAlive(playerID);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
