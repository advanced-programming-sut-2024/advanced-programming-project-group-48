package view;

import model.Card;
import model.Faction;

import java.util.ArrayList;

public class GameEnvironment {
    private Faction faction;
    private String commander;
    private ArrayList<model.Card> closeRangeRow;
    private ArrayList<model.Card> middleRangeRow;
    private ArrayList<model.Card> farRangeRow;
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


}
