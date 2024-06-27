package view;

import model.*;

import java.util.ArrayList;

public class GameEnvironment {
    private Faction faction ;
    private CommandersOfNorthenRealms commander;
    private Row closeRangeRow;
    private Row middleRangeRow;
    private Row farRangeRow;
    private ArrayList<Card> inHandCards;
    private ArrayList<Card> discardPile;
    private ArrayList<Card> remainingCards;
    private Card voteCard;
    private int totalScore;
    private int numberOfDiamonds;
    private ArrayList<Spell> activeAllSpells;


    public ArrayList<Card> getInHandCards(){
        return inHandCards;
    }
    public ArrayList<Card> getDiscardPileCards(){
        return discardPile;
    }
    public ArrayList<Card> getRemainingCards(){
        return remainingCards;
    }
    public Card getVoteCard(){
        return voteCard;
    }
    public void setVoteCard(Card voteCard){
        this.voteCard = voteCard;
    }

    public Faction getFaction() {
        return faction;
    }

    public CommandersOfNorthenRealms getCommander() {
        return commander;
    }
//    public gameEnvironment(){}

    public int getTotalScore(){
        return totalScore;
    }
    public int getNumberOfDiamonds(){
        return numberOfDiamonds;
    }
    public void setTotalScore(int totalScore){
        this.totalScore = totalScore;
    }


    public Row getCloseRangeRow() {
        return closeRangeRow;
    }

    public Row getFarRangeRow() {
        return farRangeRow;
    }

    public Row getMiddleRangeRow() {
        return middleRangeRow;
    }

    public void setNumberOfDiamonds(int numberOfDiamonds) {
        this.numberOfDiamonds = numberOfDiamonds;
    }
}
