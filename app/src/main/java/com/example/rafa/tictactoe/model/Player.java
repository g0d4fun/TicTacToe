package com.example.rafa.tictactoe.model;

/**
 * Created by Rafa on 2/16/2018.
 */

public class Player {

    private final EPlayerType playerType;
    private int gamesWon;
    private int gamesDraw;

    public Player(EPlayerType playerType) {
        this.playerType = playerType;
        this.gamesDraw = 0;
        this.gamesWon = 0;
    }

    public void wonGame(){
        gamesWon++;
    }

    public void drawGame(){
        gamesDraw++;
    }

    public EPlayerType getPlayerType() {
        return playerType;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesDraw() {
        return gamesDraw;
    }
}
