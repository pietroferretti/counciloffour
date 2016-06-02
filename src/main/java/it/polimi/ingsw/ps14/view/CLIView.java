package it.polimi.ingsw.ps14.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Scanner;

import it.polimi.ingsw.ps14.model.ModelView;

/*
 * --------------------------Command Line Interface-----------------------
 * It acquires infos from player and show the game state each time something
 * has changed
 * 
 */

public class CLIView extends Observable implements View {

	private Scanner input;
	private PrintStream output;

	public CLIView(InputStream inputStream, OutputStream outputStream) {
		input = new Scanner(inputStream);
		output = new PrintStream(outputStream);
	}

	public void showModel(ModelView model) {
		// TODO print everything
	}

//	private void print(String string){
//		output.println(string);
//	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
