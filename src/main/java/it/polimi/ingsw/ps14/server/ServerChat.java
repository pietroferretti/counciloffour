package it.polimi.ingsw.ps14.server;

import java.util.List;

public class ServerChat {
	private List<ServerView> serverView;
	
	public ServerChat(List<ServerView> views){
		this.serverView=views;
		}
	
	public void sendChat(String text,String author){
		for(ServerView sv : serverView){
			sv.forwardChat(author,text);
		}
	}
	
}
