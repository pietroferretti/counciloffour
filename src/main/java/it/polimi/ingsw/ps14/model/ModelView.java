package it.polimi.ingsw.ps14.model;

import java.util.Observable;
import java.util.Observer;

/*
 * Questa classe viene introdotta per disaccoppiare il model dalla view
 * e quindi impedire eventuali modifiche non permesse al model.
 */

public class ModelView extends Observable implements Observer {

	private Game modelCopy;

	@Override
	public void update(java.util.Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (!(o instanceof Game)) {
			throw new IllegalArgumentException();
		}
	//TODO	modelCopy = (Game) arg.clone();
		setChanged();
		notifyObservers();
	}

	public Game getModelCopy() {
		return modelCopy;
	}

}
