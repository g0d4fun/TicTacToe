package com.example.rafa.tictactoe.model;

import android.util.Log;

import java.util.EventListener;

/**
 * Created by Rafa on 2/18/2018.
 */

public class AlphaBetaPruningAI {

    public static int MAX_DEPTH;

    public static void run (EPlayerType playerTypeAI,Logic logic,int maxDepth) {
        if (maxDepth < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }
        MAX_DEPTH = maxDepth;
        alphaBetaPruning(logic,0,playerTypeAI,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }

    private static int alphaBetaPruning (Logic logic,int curDepth,
                                         EPlayerType playerTypeAI, int alpha, int beta) {
        if (curDepth++ == MAX_DEPTH || logic.checkWinnerResult() != null) {
            return score(playerTypeAI, logic, curDepth);
        }
        if (logic.getCurrentPlayer() == playerTypeAI) {
            return getMax(logic,curDepth,playerTypeAI, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            return getMin(logic,curDepth,playerTypeAI, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }

    /**
     *
     * @param logic The Tic Tac Toe Game
     * @param curDepth The Current Depth
     * @param playerTypeAI AI player Identification
     * @param alpha alpha value
     * @param beta beta value
     * @return board score
     */
    private static int getMax(Logic logic, int curDepth,EPlayerType playerTypeAI, int alpha, int beta){
        int rowBestMoveIndex = -1;
        int colBestMoveIndex = -1;

        EPlayerType board[][] = logic.getBoard();

        breakloops:
        for(int i = 0; i < Constants.ROWS; i++){
            for(int j = 0; j < Constants.COLUMNS; j++){
                if(board[i][j].equals(EPlayerType.EMPTY)){
                    Logic clone = logic.getClone();
                    clone.playTile(i,j);
                    int score = alphaBetaPruning(clone,curDepth,playerTypeAI,alpha,beta);

                    if(score > alpha){
                        alpha = score;
                        rowBestMoveIndex = i;
                        colBestMoveIndex = j;
                    }

                    //Pruning
                    if(alpha >= beta){
                        break breakloops;
                    }
                }
            }
        }
        if(rowBestMoveIndex != -1 && colBestMoveIndex != -1){
            logic.playTile(rowBestMoveIndex,colBestMoveIndex);
        }
        return alpha;
    }

    private static int getMin(Logic logic, int curDepth,EPlayerType playerTypeAI, int alpha, int beta){
        int rowBestMoveIndex = -1;
        int colBestMoveIndex = -1;

        EPlayerType board[][] = logic.getBoard();

        breakloops:
        for(int i = 0; i < Constants.ROWS; i++){
            for(int j = 0; j < Constants.COLUMNS; j++){
                if(board[i][j].equals(EPlayerType.EMPTY)){
                    Logic clone = logic.getClone();
                    clone.playTile(i,j);
                    int score = alphaBetaPruning(clone,curDepth,playerTypeAI,alpha,beta);

                    if(score < beta){
                        beta = score;
                        rowBestMoveIndex = i;
                        colBestMoveIndex = j;
                    }

                    //Pruning
                    if(alpha >= beta){
                        break breakloops;
                    }
                }
            }
        }

        if(rowBestMoveIndex != -1 && colBestMoveIndex != -1){
            logic.playTile(rowBestMoveIndex,colBestMoveIndex);
        }
        return beta;
    }

    private static int score (EPlayerType player, Logic logic, int curDepth) {
        if (player == null || player.equals(EPlayerType.EMPTY)) {
            throw new IllegalArgumentException("Player Type Should be X ou O.");
        }
        EPlayerType opponent = (player.equals(EPlayerType.PLAYERX)) ?
                EPlayerType.PLAYERO : EPlayerType.PLAYERX;

        WinnerInfo winner = logic.checkWinnerResult();
        if (winner != null && winner.getWinner() == player) {
            return 10 - curDepth;
        } else if (winner != null && winner.getWinner() == opponent) {
            return -10 + curDepth;
        } else {
            return 0;
        }
    }
}
