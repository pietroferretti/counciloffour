package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.ErrorMsg;
import it.polimi.ingsw.ps14.model.Model;

public class MessageView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4832193626262776215L;

	private Message messageCopy;

	public MessageView(Message messageCopy) {
		this.messageCopy = messageCopy;
	}

	public Message getMessageCopy() {
		return messageCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Model))
			throw new IllegalArgumentException();
		else {
			Message msg = ((Model) o).getMessage();
			if (msg instanceof ErrorMsg) {
				messageCopy = new ErrorMsg(((ErrorMsg) msg).getPlayerID(), ((ErrorMsg) msg).getInfo());
			} else {
				// TODO

			}
			setChanged();
			notifyObservers();
		}

	}

}