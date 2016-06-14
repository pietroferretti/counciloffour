package it.polimi.ingsw.ps14.model;

/**
 * The phases of a running game. Used in {@link Model}.
 * 
 * @see it.polimi.ingsw.ps14.controller.Controller Controller
 */
public enum GamePhase {

	/**
	 * Set as this during the regular turns phase, when the players can perform
	 * main and quick actions.
	 */
	TURNS,

	/**
	 * Set as this during the market phase, when players sell and buy objects.
	 */
	MARKET,

	/**
	 * Set as this after one player has built 10 emporiums. Each player plays
	 * one regular turn, then the game ends.
	 */
	FINALTURNS,

	/**
	 * Set as this when the game has ended.
	 */
	END
}