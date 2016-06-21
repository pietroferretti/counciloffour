package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.view.View;

public abstract class ServerView extends View {

	private ModelView modelView;

	public ServerView(Integer id) {
		super(id);
	}
	
	public void setPlayerID(Integer id){
		super.setPlayerID(id);
	}

	public ModelView getModelView() {
		return modelView;
	}

	public void setModelView(ModelView modelView) {
		this.modelView = modelView;
	}

	// private Message message;
	//
	//
	//
	// private void setMessage(Message message) {
	// this.message = message;
	// setChanged();
	// notifyObservers(message);
	// }
	//
	// @Override
	// public int getPlayerID() {
	// return playerID;
	// }
	//
	// @Override
	// public void update(Observable o, Object arg) {
	//
	// if (!(o instanceof ModelView)) {
	//
	// throw new IllegalArgumentException();
	//
	// } else if (arg instanceof PlayerChangedPublicMsg)
	// send(arg);
	// else if (arg instanceof PlayerChangedPrivateMsg) {
	// if (((PlayerChangedPrivateMsg) arg).getPlayerID() == playerID) {
	// send(arg);
	// } else {
	// // TODO avvisare altri
	// }
	//
	// } else if ("REGIONVIEW".equals((String) arg)) {
	// showOtherPlayersDetails();
	// }
	//
	// }
}