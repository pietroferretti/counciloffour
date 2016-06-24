package it.polimi.ingsw.ps14.server;

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
import it.polimi.ingsw.ps14.view.View;

public abstract class ServerView extends View {

	private ModelView modelView;

	public ServerView(Integer id) {
		super(id);
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
			sendMessage(new CitiesColorBonusesUpdatedMsg(modelView.getCitiesColorBonusesView().getBonusGoldCopy(),
					modelView.getCitiesColorBonusesView().getBonusSilverCopy(),
					modelView.getCitiesColorBonusesView().getBonusBronzeCopy(),
					modelView.getCitiesColorBonusesView().getBonusBlueCopy()));
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
		sendMessage(new PersonalUpdateMsg(modelView.getPlayerByID(super.getPlayerID())));
	}

	protected void sendOthersUpdate() {
		for (PlayerView pv : modelView.getPlayersView()) {
			if (pv.getPlayerCopy().getId() != super.getPlayerID())
				sendMessage(new OtherPlayerUpdateMsg(pv.getPlayerCopy()));
		}
	}

	protected void sendMessage(Message msg) {
	}
}