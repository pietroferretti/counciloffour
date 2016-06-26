package it.polimi.ingsw.ps14.server;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromclient.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateOtherPlayersMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateRequestMsg;
import it.polimi.ingsw.ps14.message.fromclient.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.CitiesColorBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.NobilityTrackUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.OtherPlayerUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PersonalUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;
import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.model.modelview.PlayerView;
import it.polimi.ingsw.ps14.model.modelview.RegionView;

public abstract class ServerView extends Observable implements Observer {

	private Integer id;
	private String name;
	private ModelView modelView;
	private final int timeOut;

	public ServerView(Integer id, int timeOut) {
		this.id = id;
		this.timeOut=timeOut;
	}

	public void setPlayerID(Integer id){
		this.id=id;
	}
	
	public int getPlayerID() {
		return id;
	}
	
	public String getPlayerName() {
		return name;
	}
	
	public void setPlayerName(String name) {
		this.name = name;
	}
	public ModelView getModelView() {
		return modelView;
	}

	public void setModelView(ModelView modelView) {
		this.modelView = modelView;
	}
	
	/**
	 * It sends the requested updates to the client; it build messages with the
	 * {@link ModelView} objects.
	 * 
	 * @param objectReceived
	 *            - Request for updates.
	 */
	protected void sendUpdates(UpdateRequestMsg requestReceived) {

		if (requestReceived instanceof UpdateGameBoardMsg) {
			sendMessage(new StateUpdatedMsg(modelView.getStateView().getStateCopy()));
			sendMessage(new AvailableAssistantsUpdatedMsg(
					modelView.getAvailableAssistantsView().getAvailableAssistantsCopy()));
			sendMessage(new KingBonusesUpdatedMsg(modelView.getKingBonusesView().getShowableKingBonus()));
			sendMessage(new NobilityTrackUpdatedMsg(modelView.getNobilityTrackView().getNobilityTrackCopy()));
			sendMessage(new KingUpdatedMsg(modelView.getKingView().getKingCopy()));
			sendMessage(new CitiesColorBonusesUpdatedMsg(modelView.getCitiesColorBonusesView().getColorBonusesCopy()));
			sendMessage(new RegionUpdatedMsg(modelView.getRegionsView().get(0).getRegionCopy()));
			for (RegionView rv : modelView.getRegionsView()) {
				sendMessage(new RegionUpdatedMsg(rv.getRegionCopy()));
			}

		} else if (requestReceived instanceof UpdateThisPlayerMsg) {
			sendPersonalUpdate();
		} else if (requestReceived instanceof UpdateOtherPlayersMsg) {
			sendOthersUpdate();
		}
	}

	protected void sendPersonalUpdate() {
		sendMessage(new PersonalUpdateMsg(modelView.getPlayerByID(this.id)));
	}

	protected void sendOthersUpdate() {
		for (PlayerView pv : modelView.getPlayersView()) {
			if (pv.getPlayerCopy().getId() != this.id)
				sendMessage(new OtherPlayerUpdateMsg(pv.getPlayerCopy()));
		}
	}

	protected void sendMessage(Message msg) {
	}
}