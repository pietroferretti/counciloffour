package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.InfoPrivateMsg;
import it.polimi.ingsw.ps14.message.fromserver.InfoPublicMsg;
import it.polimi.ingsw.ps14.model.MessageObservable;
import it.polimi.ingsw.ps14.model.Model;

/**
 * 
 * It contains an updated copy of the {@link MessageObservable} enclosed in the
 * {@link Model}. It gets an update from the {@link MessageObservable}, updates itself and
 * notifies the {@link ModelView}.
 *
 */
public class MessageView extends Observable implements Observer, Serializable {

	private static final Logger LOGGER = Logger.getLogger(MessageView.class
			.getName());
	
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

				if (msg instanceof InfoPrivateMsg) {
					
					messageCopy = new InfoPrivateMsg(((InfoPrivateMsg) msg).getPlayerID(), ((InfoPrivateMsg) msg).getInfo());
				
				} else if (msg instanceof InfoPublicMsg) {
					
					messageCopy = new InfoPublicMsg(((InfoPublicMsg) msg).getInfo());
				
				} else {
					LOGGER.warning(String.format("Couldn't recognize message %s in model", msg.getClass()));
				}
				
				((MessageObservable) o).clearMessage();

				setChanged();
				notifyObservers();

			}
		}

	}

}
