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
        userScorePerRound[0]=Integer.max(userScoreRound1,0);
        userScorePerRound[1]=Integer.max(userScoreRound2,0);
        userScorePerRound[2]=Integer.max(userScoreRound3,0);
        opponentScoresPerRound[0]=Integer.max(opponentScoreRound1,0);
        opponentScoresPerRound[1]=Integer.max(opponentScoreRound2,0);
        opponentScoresPerRound[2]=Integer.max(opponentScoreRound3,0);
        this.dateOfGame=dateOfGame;
        this.opponentUsername=opponentUsername;
    }
    public int getTotalScoreOfUser(){
        return userScorePerRound[0]+userScorePerRound[1]+userScorePerRound[2];
    }
    public int getTotalScoreOfOpponent(){
        return opponentScoresPerRound[0]+opponentScoresPerRound[1]+opponentScoresPerRound[2];
    }
    public String getUsernameOfWinner(){
        if(getTotalScoreOfOpponent() > getTotalScoreOfUser()){
            return opponentUsername;
        }
        return User.loggedInUser.getUsername();
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
