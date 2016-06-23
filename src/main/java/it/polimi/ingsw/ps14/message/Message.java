package it.polimi.ingsw.ps14.message;

import java.io.Serializable;

/**
 * An interface for the messages that are sent by the ModelView to the ServerView,
 * and from the ServerView to the Controller.
 * 
 * When using sockets, these messages are also sent to the client
 * to be interpreted by the SocketCommunication class.
 * 
 * @see it.polimi.ingsw.ps14.model.modelview.ModelView ModelView
 * @see it.polimi.ingsw.ps14.server.ServerView ServerView
 * @see it.polimi.ingsw.ps14.controller.Controller Controller
 * @see it.polimi.ingsw.ps14.client.socket.SocketCommunication SocketCommunication
 */
public interface Message extends Serializable {
	
}
