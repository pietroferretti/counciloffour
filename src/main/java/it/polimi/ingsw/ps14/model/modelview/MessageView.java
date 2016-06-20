package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.ErrorMsg;
import it.polimi.ingsw.ps14.model.MessageObservable;
import it.polimi.ingsw.ps14.model.Model;

public class MessageView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4832193626262776215L;

	private Message messageCopy;

	public MessageView(MessageObservable messageObs) {
		this.messageCopy = messageObs.getMessage();
	}

	public Message getMessageCopy() {
		return messageCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof MessageObservable))
			throw new IllegalArgumentException();
		else {
			
			Message msg = ((MessageObservable) o).getMessage();
	
			if (msg != null) {
				
				if (msg instanceof ErrorMsg) {
					messageCopy = new ErrorMsg(((ErrorMsg) msg).getPlayerID(), ((ErrorMsg) msg).getInfo());
				} else {
					// TODO
					messageCopy = msg;
				}
				((MessageObservable) o).clearMessage();
				
				setChanged();
				notifyObservers();
				
			}
		}

	}

}
