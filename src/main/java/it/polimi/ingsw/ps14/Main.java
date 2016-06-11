package it.polimi.ingsw.ps14;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.view.View;

public class Main {
	private static final Logger LOGGER= Logger.getLogger(Main.class.getName());
	
	private Main() {};
	
	public static void main(String[] args) throws IOException, CloneNotSupportedException{
		
		// Start server
		LOGGER.info("Server started.");
		
		LOGGER.warning("Not implemented :(");
		
		boolean exit = false;
		while(!exit) {
			// Aspettare le connessioni, qui o in altre classi
			
			// ottieni una view per ogni giocatore
			List<View> views = null;
			// crea un nuovo "gioco", passa tutte le view dei giocatori
			Game game = new Game(views);
					// all'interno del gioco, crea un nuovo model
													// -> a sua volta crea una gameboard
						// crea giocatori e passali al model
					// crea un oggetto controller, inizializza i turni
					// il controller osserva tutte le view
					// un modelview osserva il model
					// le view osservano il modelview
					// quando una view riceve un comando, fa una notifyObservers() che chiama l'update del controller
					// il controller esegue l'azione o fa qualcosa
					// alla fine di ogni azione il controller cambia lo stato del gioco
					//  -> turni, fasi del gioco
					// se alla fine dell'azione ci sono 10 empori -> stato fine gioco
					//												(turni finali)
					//											  -> stato fine fine -> calcola punteggi -> mostra vincitore
			
			// Aspetta nuove connessioni sul thread principale
			// il gioco già in corso continua e viene giocato sui threads delle view
		
			// riparti col ciclo in teoria
			exit = true;
		}
		
		LOGGER.info("Server stopped.");
	}

}
