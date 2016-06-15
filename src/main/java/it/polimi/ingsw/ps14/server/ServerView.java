package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.view.View;

public abstract class ServerView extends View {

	public ServerView(int id) {
		super(id);
	}
	
//	private ModelView mv;
//	private Message message;
//	private int playerID;
//	private ConnectionSocket connection;
//
//	public ServerView(ModelView mv, Message message, ConnectionSocket connection) {
//		this.mv = mv;
//		this.message = message;
//		this.connection = connection;
//	}
//
//	@Override
//	public void setModelView(ModelView modelView) {
//		mv = modelView;
//	}
//
//	private void setMessage(Message message) {
//		this.message = message;
//		setChanged();
//		notifyObservers(message);
//	}
//	
//	@Override
//	public int getPlayerID() {
//		return playerID;
//	}
//
//	@Override
//	public void update(Observable o, Object arg) {
//
//		if (!(o instanceof ModelView)) {
//
//			throw new IllegalArgumentException();
//
//		} else if (arg instanceof PlayerChangedPublicMsg)
//			send(arg);
//		else if (arg instanceof PlayerChangedPrivateMsg) {
//			if (((PlayerChangedPrivateMsg) arg).getPlayerID() == playerID) {
//				send(arg);
//			} else {
//				// TODO avvisare altri
//			}
//
//		} else if ("REGIONVIEW".equals((String) arg)) {
//			showOtherPlayersDetails();
//		}
//
//	}
//
//	private void send(Object arg) {
//
//		try {
//			connection.getSocketOut().writeObject(arg);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			connection.getSocketOut().flush();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void showOtherPlayersDetails() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void showGameboard(GameBoard gameBoard) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void showMainActions() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void showQuickActions() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public String getPlayerName() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}