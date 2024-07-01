package view;

import javafx.scene.image.ImageView;
import model.card.*;

import java.util.ArrayList;

public class GameEnvironment {
    private Faction faction ;
    private CommandersOfNorthenRealms commander;
    private Row closeRangeRow;
    private Row middleRangeRow;
    private Row farRangeRow;
    private Card voteCard;
    private int numberOfDiamonds;


    //GameEnvironment fields
    private ArrayList<Card> siegeRow;
    private ArrayList<Card> closedRow;
    private ArrayList<Card> rangedRow;
    private ArrayList<Card> enemySiegeRow;
    private ArrayList<Card> enemyClosedRow;
    private ArrayList<Card> enemyRangedRow;
    private ArrayList<Card> inHandCard;
    private int enemyNumberRemainingCards1;
    private int numberRemainingCards;
    private int enemyCrystalsNumber;
    private int crystalsNumber;
    private int totalScore;
    private int enemyTotalScore;
    public String commanderCard111;
    public String commanderCard11;
    public String commanderCard1;
    public String enemyCommanderCard;
    public String commanderCard;
    public ArrayList<Card> enemyRemainingCard;
    public ArrayList<Card> remainingCard;
    public ArrayList<Card> enemyDiscardPile;
    public ArrayList<Card> discardPile;




    public ArrayList<Card> getInHandCards(){
        return inHandCard;
    }
    public ArrayList<Card> getDiscardPileCards(){
        return discardPile;
    }
    public ArrayList<Card> getRemainingCards() { return remainingCard; }
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
