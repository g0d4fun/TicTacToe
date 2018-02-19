package com.example.rafa.tictactoe.model;

import android.util.Log;

/**
 * Created by Rafa on 2/16/2018.
 */

public class Model {

    public Logic logic;
    public EGameMode gameMode;

    public Model(EGameMode gameMode,EPlayerType symbol) {
        this.gameMode = gameMode;

        switch (gameMode){
            case SINGLE_PLAYER:
                startSinglePlayerGame(symbol);
                break;
            case MULTIPLAYER:
                startMultiplayerGame(symbol);
                break;
            case ONLINE:
                break;
            default:
                throw new RuntimeException("It should not Happen");
        }
    }

    private void startSinglePlayerGame(EPlayerType symbol){
        logic = new Logic(symbol);
    }

    private void startMultiplayerGame(EPlayerType symbol){
        logic = new Logic(symbol);
    }

    // Interation Methods

    public void playTile(int row, int col){
        logic.playTile(row,col);

        //Artificial Intelligence
        if(gameMode.equals(EGameMode.SINGLE_PLAYER)){
            EPlayerType opponent = logic.getInitialPlayer().equals(EPlayerType.PLAYERX) ?
                    EPlayerType.PLAYERO : EPlayerType.PLAYERX;
            Log.i("TicTacToe","Opponent: " + opponent);
            AlphaBetaPruningAI.run(opponent,logic,3);
        }
    }

    public void startNextGame(){
        logic.startNextGame();
    }

    // Getters Methods
    public WinnerInfo getWinnerResult(){
        return logic.checkWinnerResult();
    }

    public EPlayerType getCurrentPlayer(){
        return logic.getCurrentPlayer();
    }

    public EPlayerType[][] getBoard() {
        return logic.getBoard();
    }

    public int getPlayerXWins() {
        return logic.getPlayerXWins();
    }

    public int getPlayerOWins() {
        return logic.getPlayerOWins();
    }

    public int getGameDraws() {
        return logic.getGameDraws();
    }
}
