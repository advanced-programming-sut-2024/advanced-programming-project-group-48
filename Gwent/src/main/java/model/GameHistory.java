package model;

import java.util.Date;

public class GameHistory {

    private final String opponentUsername;
    private final Date dateOfGame;
    private final int[] opponentScoresPerRound = new int[3];
    private final int[] userScorePerRound = new int[3];

    public GameHistory(int userScoreRound1,int userScoreRound2,int userScoreRound3,
                       int opponentScoreRound1,int opponentScoreRound2,int opponentScoreRound3,
                       Date dateOfGame,String opponentUsername){
        userScorePerRound[0]=userScoreRound1;
        userScorePerRound[1]=userScoreRound2;
        userScorePerRound[2]=userScoreRound3;
        opponentScoresPerRound[0]=opponentScoreRound1;
        opponentScoresPerRound[1]=opponentScoreRound2;
        opponentScoresPerRound[2]=opponentScoreRound3;
        this.dateOfGame=dateOfGame;
        this.opponentUsername=opponentUsername;
    }
    private int getTotalScoreOfUser(){
        return userScorePerRound[0]+userScorePerRound[1]+userScorePerRound[2];
    }
    private int getTotalScoreOfOpponent(){
        return opponentScoresPerRound[0]+opponentScoresPerRound[1]+opponentScoresPerRound[2];
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public Date getDateOfGame() {
        return dateOfGame;
    }

    public int[] getOpponentScoresPerRound() {
        return opponentScoresPerRound;
    }

    public int[] getUserScorePerRound() {
        return userScorePerRound;
    }
}
