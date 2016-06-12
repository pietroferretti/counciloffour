package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientInHandler implements Runnable {

	private ObjectInputStream socketIn;
	
	public ClientInHandler(ObjectInputStream socketIn) {
		this.socketIn=socketIn;
	}
	
	@Override
	public void run() {

		while(true){
			
			try {
				Object object=socketIn.readObject();
				
				
				System.out.println(object);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
