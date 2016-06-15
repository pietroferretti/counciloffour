package it.polimi.ingsw.ps14.view;

import it.polimi.ingsw.ps14.message.DrawCardMsg;
import it.polimi.ingsw.ps14.message.Message;

public class Interpreter {
	

	public static Message parseString(String input) {

		if (input.matches("^draw$")) {
			return new DrawCardMsg();
		}

		if (input.matches("^(main action | main)$")) {
			// TODO STAMPA COMANDI
			//return new MainActionRequestMsg();
		}

		if (input.matches("^build with king(\\s*\\d*)+$")) {
			// MA LA CITY CHE PRENDE NON è QUELLA DEL RE??

			//return new MainActionRBuildEmporiumWithHelpOfKingtMsg();
		}
		return null;

	}

}
