package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class ElectCouncillorAction extends MainAction {

	private final ColorCouncillor councillor;
	private final RegionType regionType;

	// chi chiama questo metodo deve ricavare il balcone dalla region o dal king
	public ElectCouncillorAction(Integer playerID, ColorCouncillor councillor,String regionTypeOrKing) {
		super(playerID);
		this.councillor = councillor;
		if(regionTypeOrKing.compareTo("KING")!=0)
			this.regionType = RegionType.valueOf(regionTypeOrKing);
		else 
			regionType=null;
	}

	@Override
	public boolean isValid(Model model) {
		return model.getGameBoard().councillorIsAvailable(councillor);
	}

	@Override
	public TurnState execute(TurnState previousState,Model model) {
		Player player = id2player(super.getPlayer(), model);
		Balcony balcony ;
		if(regionType!=null)
			balcony=model.getGameBoard().getRegion(regionType).getBalcony();
		else
			balcony=model.getGameBoard().getKing().getBalcony();
		
		int electCouncillorPrize = 4;
		// electCouncillor return discarded councillor, discarded councillor is
		// added to availableCouncillors in gameboard
		model.getGameBoard().addDiscardedCouncillor(
				balcony.electCouncillor(councillor));

		// electCouncillor give 4 coins to player
		player.addCoins(electCouncillorPrize);

		return nextState(previousState,player);
	}

}
