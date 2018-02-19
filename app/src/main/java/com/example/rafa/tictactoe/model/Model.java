package com.example.rafa.tictactoe.model;

import android.util.Log;

/**
 * Created by Rafa on 2/16/2018.
 */

public class Model {

    private Logic logic;
    private final EGameMode gameMode;
    private EGameDifficult difficultSinglePlayer;

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
        difficultSinglePlayer = null;
    }

    public Model(EGameMode gameMode, EPlayerType playerx, EGameDifficult gameDifficult) {
        this(gameMode,playerx);
        this.difficultSinglePlayer = gameDifficult;
    }

    private void startSinglePlayerGame(EPlayerType symbol){
        logic = new Logic(symbol);
    }

    private void startMultiplayerGame(EPlayerType symbol){
        logic = new Logic(symbol);
    }

    // Interation Methods

    public void playTile(int row, int col){
        if(!logic.playTile(row,col))
            return;

        //Artificial Intelligence
        if(gameMode.equals(EGameMode.SINGLE_PLAYER)){
            playTileAI();
        }
    }

    public void startNextGame(){
        logic.startNextGame();

        //Artificial Intelligence, first play in Single Player Game
        EPlayerType initialPlayer = logic.getInitialPlayer();
        EPlayerType currentPlayer = logic.getCurrentPlayer();
        if(!initialPlayer.equals(currentPlayer) &&
            gameMode.equals(EGameMode.SINGLE_PLAYER)){
            int randomRow = (int) (Math.random() * Constants.ROWS);
            int randomCol = (int) (Math.random() * Constants.COLUMNS);
            logic.playTile(randomRow,randomCol);
        }
    }

    private void playTileAI(){
        EPlayerType opponent = logic.getInitialPlayer().equals(EPlayerType.PLAYERX) ?
                EPlayerType.PLAYERO : EPlayerType.PLAYERX;
        Log.i("TicTacToe","Opponent: " + opponent);
        AlphaBetaPruningAI.run(opponent,logic,getMaxDepthByDifficult(difficultSinglePlayer));
    }

    private int getMaxDepthByDifficult(EGameDifficult gameDifficult){
        switch (gameDifficult){
            case EASY:
                return 1;
            case MEDIUM:
                return 2;
            case HARD:
                return 4;
            default:
                throw new RuntimeException("Difficult is not set.");
        }
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

    public EGameMode getGameMode() {
        return gameMode;
    }
}
