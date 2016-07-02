package it.polimi.ingsw.ps14.model;

/**
 * Colors that a politic card can be. A "jolly" is a multi-colored card. Used in
 * {@link PoliticCard}. Value is the hsb angle used for the GUI
 */
public enum ColorPolitic {
	PURPLE (280), PINK(320), WHITE(0), ORANGE(30), BLACK(0), BLUE(240), JOLLY(0);

        private int hsbAngle;
    ColorPolitic(int numVal) {
        this.hsbAngle = numVal;
    }

    public int getNumVal() {
        return hsbAngle;
    }
}
