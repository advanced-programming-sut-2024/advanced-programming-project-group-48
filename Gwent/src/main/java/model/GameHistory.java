package model;

import java.util.Date;

public class GameHistory {

    String opponentUserName;
    Date dateOfGame;
    int opponentScoreRound1;
    int opponentScoreRound2;
    int opponentScoreRound3;
    int userScoreRound1;
    int userScoreRound2;
    int userScoreRound3;
    int sumOfScoreOfUser;
    int sumOfScoreOfOpponent;
    Deck deck;


    public String getOpponentUserName(){
        return opponentUserName;
    }
    public Date getDateOfGame(){
        return dateOfGame;
    }
    public int getOpponentScoreRound1(){
        return opponentScoreRound1;
    }
    public int getOpponentScoreRound2(){
        return opponentScoreRound2;
    }
    public int getOpponentScoreRound3(){
        return opponentScoreRound3;
    }
    public boolean winnerOfThisGame(){
        return true;
    }
    public int getUserScoreRound1(){
        return userScoreRound1;
    }
    public int getUserScoreRound2(){
        return userScoreRound2;
    }
    public int getUserScoreRound3(){
        return userScoreRound3;
    }
    public int getSumOfScoreOfUser(){
        return sumOfScoreOfUser;
    }
    public int getSumOfScoreOfOpponent(){
        return sumOfScoreOfOpponent;
    }

}
