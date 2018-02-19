package com.example.rafa.tictactoe.model;

/**
 * Created by Rafa on 2/18/2018.
 */

public class WinnerInfo {
    private final EPlayerType winner;
    private final EGameResult gameResult;
    private final int tilesCoordsWinner[][];

    public WinnerInfo(EPlayerType winner, EGameResult gameResult, int[][] tilesCoordsWinner) {
        this.winner = winner;
        this.gameResult = gameResult;
        this.tilesCoordsWinner = tilesCoordsWinner;
    }

    public EPlayerType getWinner() {
        return winner;
    }

    public EGameResult getGameResult() {
        return gameResult;
    }

    public int[][] getTilesCoordsWinner() {
        return tilesCoordsWinner;
    }
}
