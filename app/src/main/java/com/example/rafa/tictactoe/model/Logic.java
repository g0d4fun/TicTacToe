package com.example.rafa.tictactoe.model;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by Rafa on 2/16/2018.
 */

public class Logic {

    public EPlayerType board[][];
    public final EPlayerType initialPlayer;
    public EPlayerType currentPlayer;
    public int playerXWins;
    public int playerOWins;
    public int gameDraws;

    public Logic(EPlayerType playerType) {
        board = new EPlayerType[3][3];
        cleanBoard();
        currentPlayer = playerType;
        initialPlayer = playerType;
        this.playerXWins = 0;
        this.playerOWins = 0;
        this.gameDraws = 0;
    }

    private EPlayerType getRandomPlayer() {
        if ((int) (Math.random() * 2 + 1) == 1) {
            return EPlayerType.PLAYERO;
        }
        return EPlayerType.PLAYERX;
    }

    private void cleanBoard() {
        for (EPlayerType[] boardRow : board) {
            Arrays.fill(boardRow, EPlayerType.EMPTY);
        }
    }

    private EPlayerType changeCurrentPlayer() {
        switch (currentPlayer) {
            case PLAYERO:
                currentPlayer = EPlayerType.PLAYERX;
                break;
            case PLAYERX:
                currentPlayer = EPlayerType.PLAYERO;
                break;
        }
        return currentPlayer;
    }

    private WinnerInfo buildWinnerInfo(int row1,int col1, int row2,
                                       int col2, int row3, int col3,boolean isDraw){
        if(isDraw)
            return new WinnerInfo(EPlayerType.EMPTY,EGameResult.DRAW,null);

        int winnerTiles[][] = {{row1,col1}, {row2,col2}, {row3,col3}};
        EPlayerType playerType = board[row1][col1];
        EGameResult gameResult = convertToGameResult(playerType);

        return new WinnerInfo(playerType,gameResult,winnerTiles);
    }

    public WinnerInfo checkWinnerResult() {
        // Check For Winner in Rows
        for (int i = 0; i < Constants.ROWS; i++) {
            if (checkTilesThreeInRow(board[i][0], board[i][1], board[i][2])) {
                Log.i("TicTacToe","Winner Rows");
                return buildWinnerInfo(i,0,i,1,i,2,false);
            }
        }

        //Check for Winner in Columns
        for (int i = 0; i < Constants.COLUMNS; i++) {
            if (checkTilesThreeInRow(board[0][i], board[1][i], board[2][i])) {
                Log.i("TicTacToe","Winner Columns");
                return buildWinnerInfo(0,i,1,i,2,i,false);
            }
        }


        //Check for Winner in Diagonals
        if (checkTilesThreeInRow(board[0][0], board[1][1], board[2][2])) {
            Log.i("TicTacToe","Winner Diagonals");
            return buildWinnerInfo(0,0,1,1,2,2,false);
        } else if (checkTilesThreeInRow(board[0][2], board[1][1], board[2][0])) {
            Log.i("TicTacToe","Winner Diagonals");
            return buildWinnerInfo(0,2,1,1,2,0,false);
        }

        //Check For Game Draw
        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLUMNS; j++) {
                if (board[i][j].equals(EPlayerType.EMPTY))
                    return null; // There is an empty tile
            }
        }
        // Game Result is Draw
        return buildWinnerInfo(-1,-1,-1,-1,-1,-1,true);
    }

    private EGameResult convertToGameResult(EPlayerType playerType) {
        if (playerType.equals(EPlayerType.PLAYERO))
            return EGameResult.PLAYERO_WON;
        else if (playerType.equals(EPlayerType.PLAYERX))
            return EGameResult.PLAYERX_WON;
        return null;
    }

    private boolean checkTilesThreeInRow(EPlayerType tile1, EPlayerType tile2, EPlayerType tile3) {
        if (tile1.equals(tile2) && tile2.equals(tile3)
                && !tile1.equals(EPlayerType.EMPTY)) {
            return true;
        }
        return false;
    }

    public boolean playTile(int row, int col) {
        if (row < 0 || col < 0 || row > 3 || col > 3)
            return false;

        else if (!board[row][col].equals(EPlayerType.EMPTY))
            return false;

        else if (checkWinnerResult() != null)
            return false;

        board[row][col] = currentPlayer;
        changeCurrentPlayer();
        //Log.i("TicTacToe","Current Player: " + currentPlayer.name());
        return true;
    }

    public boolean startNextGame() {
        switch (checkWinnerResult().getGameResult()) {
            case PLAYERO_WON:
                playerOWins++;
                currentPlayer = EPlayerType.PLAYERO;
                break;
            case PLAYERX_WON:
                playerXWins++;
                currentPlayer = EPlayerType.PLAYERX;
                break;
            case DRAW:
                gameDraws++;
                currentPlayer = getRandomPlayer();
                break;
            default:
                return false;
        }
        cleanBoard();
        return true;
    }

    public static EPlayerType[][] deepCopyIntMatrix(EPlayerType[][] input) {
        if (input == null)
            return null;
        EPlayerType[][] result = new EPlayerType[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }

    public EPlayerType getCurrentPlayer() {
        return currentPlayer;
    }

    public EPlayerType[][] getBoard() {
        return board;
    }

    public int getPlayerXWins() {
        return playerXWins;
    }

    public int getPlayerOWins() {
        return playerOWins;
    }

    public int getGameDraws() {
        return gameDraws;
    }

    public Logic getClone(){
        Logic clone = new Logic(initialPlayer);
        clone.board = deepCopyIntMatrix(board);
        clone.currentPlayer = currentPlayer;
        clone.playerOWins = playerOWins;
        clone.playerXWins = playerXWins;
        clone.gameDraws = gameDraws;

        return clone;
    }

    public EPlayerType getInitialPlayer() {
        return initialPlayer;
    }
}
