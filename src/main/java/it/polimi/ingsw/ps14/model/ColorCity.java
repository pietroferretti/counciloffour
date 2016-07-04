package it.polimi.ingsw.ps14.model;

/**
 * The colors a city can be. There can only be one purple city, and that city is
 * the one where the king is located at the beginning of a game. The purple city
 * doesn't have a token.
 */
public enum ColorCity {
    GOLD("#FFCC00"), SILVER("#CCCCCC"), BRONZE("#E9C6A2"), BLUE("#9999FF"), PURPLE("#F0FFF0");

    ColorCity(String rgb) {
        this.rgb = rgb;
    }
    final String rgb;
    
    public static String getColorCity(ColorCity cc){
        return cc.rgb;
    }
}
