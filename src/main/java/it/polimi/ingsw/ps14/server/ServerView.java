package it.polimi.ingsw.ps14.server;

import it.polimi.ingsw.ps14.model.modelview.ModelView;
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

}