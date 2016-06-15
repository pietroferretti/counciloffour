package it.polimi.ingsw.ps14.client.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.view.View;

public class ClientOutHandler implements Runnable {

	private View clientView;
	private ObjectOutputStream socketOut;


	public ClientOutHandler(ObjectOutputStream socketOut, View view) {
		this.socketOut = socketOut;
		this.clientView = view;
	}

	@Override
	public void run() {

		System.out.println("RUNNING");
		Scanner stdIn = new Scanner(System.in);

		while (true) {

			String inputLine = stdIn.nextLine();
			Message inputMsg = clientView.writeMsg(inputLine);

			if (inputMsg != null) {
				try {
					socketOut.writeObject(inputMsg);
					socketOut.flush();
					System.out.println("sending " + inputLine);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}
}
