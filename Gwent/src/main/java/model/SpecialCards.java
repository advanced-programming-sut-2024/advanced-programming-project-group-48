package model;

import controller.menu.controller.GameEnvironmentController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpecialCards extends Card implements CardAction{
    public final String name;
    public final String type;
    public final int maxNumber;
    public final CardAction action;
    public final String ability;
    public static final ArrayList<SpecialCards> allSpecialCards = new ArrayList<>();
    public static final Map<String , SpecialCards> getCardName = new HashMap<>();



    public SpecialCards(String name , String type , CardAction cardAction , String ability){
        super(name , "All" , 0 , 3 , type , false , cardAction , ability );
        this.name = name;
        this.type = type;
        this.maxNumber = 3;
        this.action = cardAction;
        this.ability = ability;
        allSpecialCards.add(this);
        getCardName.put(name , this);
    }
    public static Card getSpecialCardByName(String name){
        for(SpecialCards i: allSpecialCards){
            if(i.name.equals(name)){
                return i;
            }
        }
        return null;
    }

    static CardAction BitingFrost = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            for (Card i : gameEnvironment.closedRow) {
                i.power = 1;
            }
            for (Card i : gameEnvironment.enemyClosedRow) {
                i.power = 1;
            }
        }

        @Override
        public void execute() {
        }
    };
    static CardAction Mardoeme = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            if (gameEnvironment.closeHorn.equals(Mardoeme.toString()) || Arrays.stream(gameEnvironment.closedRow).toList().contains(Card.getCardByName("Berserker"))) {
                for (Card i : gameEnvironment.closedRow) {
                    if (i.name.equals("Berserker")) {
                        i = Card.getCardByName("Bear");
                    }
                }
            } else if (gameEnvironment.enemyCloseHorn.equals(Mardoeme.toString()) || Arrays.stream(gameEnvironment.enemyClosedRow).toList().contains(Card.getCardByName("Berserker"))) {
                for (Card i : gameEnvironment.enemyClosedRow) {
                    if (i.name.equals("Berserker")) {
                        i = Card.getCardByName("Bear");
                    }
                }
            } else if (gameEnvironment.siegeHorn.equals(Mardoeme.toString()) || Arrays.stream(gameEnvironment.siegeRow).toList().contains("Berserker")) {
                for (Card i : gameEnvironment.enemySiegeRow) {
                    if (i.name.equals("Berserker")) {
                        i = Card.getCardByName("Bear");
                    }
                }
            } else if (gameEnvironment.enemySiegeHorn.equals(Mardoeme.toString()) || Arrays.stream(gameEnvironment.enemySiegeRow).toList().contains("Berserker")) {
                for (Card i : gameEnvironment.enemySiegeRow) {
                    if (i.name.equals("Berserker")) {
                        i = Card.getCardByName("Bear");
                    }
                }
            } else if (gameEnvironment.rangedHorn.equals(Mardoeme.toString()) || Arrays.stream(gameEnvironment.rangedRow).toList().contains("Berserker")) {
                for (Card i : gameEnvironment.rangedRow) {
                    if (i.name.equals("Berserker")) {
                        i = Card.getCardByName("Bear");
                    }
                }
            } else if (gameEnvironment.enemyRangedHorn.equals(Mardoeme.toString()) || Arrays.stream(gameEnvironment.enemyRangedRow).toList().contains("Berserker")) {
                for (Card i : gameEnvironment.enemyRangedRow) {
                    if (i.name.equals("Berserker")) {
                        i = Card.getCardByName("Bear");
                    }
                }
            }
        }

        @Override
        public void execute() {

        }
    };


    static CardAction Scorch = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            if (Arrays.stream(gameEnvironment.closedRow).toList().contains(Scorch)) {
                for (Card i : gameEnvironment.closedRow) {
                    if (i.power == i.currenuPower && i.isHero == false) {
                        i = null;
                    }
                }
            }
            if (Arrays.stream(gameEnvironment.enemyClosedRow).toList().contains(Scorch)) {
                for (Card i : gameEnvironment.closedRow) {
                    if (i.power == i.currenuPower && i.isHero == false) {
                        i = null;
                    }
                }
            }
            if (Arrays.stream(gameEnvironment.rangedRow).toList().contains(Scorch)) {
                for (Card i : gameEnvironment.rangedRow) {
                    if (i.power == i.currenuPower && i.isHero == false) {
                        i = null;
                    }
                }
            }
            if (Arrays.stream(gameEnvironment.enemyRangedRow).toList().contains(Scorch)) {
                for (Card i : gameEnvironment.enemyRangedRow) {
                    if (i.power == i.currenuPower && i.isHero == false) {
                        i = null;
                    }
                }
            }
            if (Arrays.stream(gameEnvironment.siegeRow).toList().contains(Scorch)) {
                for (Card i : gameEnvironment.siegeRow) {
                    if (i.power == i.currenuPower && i.isHero == false) {
                        i = null;
                    }
                }
            }
            if (Arrays.stream(gameEnvironment.enemySiegeRow).toList().contains(Scorch)) {
                for (Card i : gameEnvironment.enemySiegeRow) {
                    if (i.power == i.currenuPower && i.isHero == false) {
                        i = null;
                    }
                }
            }
        }

        @Override
        public void execute() {

        }
    };

    static CardAction CommandersHorn = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            if (gameEnvironment.closeHorn.equals(CommandersHorn.toString()) || Arrays.stream(gameEnvironment.closedRow).toList().contains(Card.getCardByName("CommandersHorn"))) {
                for(Card i: gameEnvironment.closedRow){
                    i.power*=2;
                }
            }
            if (gameEnvironment.enemyCloseHorn.equals(CommandersHorn.toString()) || Arrays.stream(gameEnvironment.enemyClosedRow).toList().contains(Card.getCardByName("CommandersHorn"))) {
                for(Card i: gameEnvironment.enemyClosedRow){
                    i.power*=2;
                }
            }
            if (gameEnvironment.siegeHorn.equals(CommandersHorn.toString()) || Arrays.stream(gameEnvironment.siegeRow).toList().contains(Card.getCardByName("CommandersHorn"))) {
                for(Card i: gameEnvironment.siegeRow){
                    i.power*=2;
                }
            }
            if (gameEnvironment.enemySiegeHorn.equals(CommandersHorn.toString()) || Arrays.stream(gameEnvironment.enemySiegeRow).toList().contains(Card.getCardByName("CommandersHorn"))) {
                for(Card i: gameEnvironment.enemySiegeRow){
                    i.power*=2;
                }
            }
            if (gameEnvironment.rangedHorn.equals(CommandersHorn.toString()) || Arrays.stream(gameEnvironment.rangedRow).toList().contains(Card.getCardByName("CommandersHorn"))) {
                for(Card i: gameEnvironment.rangedRow){
                    i.power*=2;
                }
            }
            if (gameEnvironment.enemyRangedHorn.equals(CommandersHorn.toString()) || Arrays.stream(gameEnvironment.enemyRangedRow).toList().contains(Card.getCardByName("CommandersHorn"))) {
                for(Card i: gameEnvironment.enemyRangedRow){
                    i.power*=2;
                }
            }

        }

        @Override
        public void execute() {

        }
    };




    static CardAction ImpenetrableFog = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            for(Card i: gameEnvironment.rangedRow){
                i.power = 1;
            }
            for(Card i: gameEnvironment.enemyRangedRow){
                i.power = 1;
            }
        }

        @Override
        public void execute() {

        }
    };

    static CardAction TorrentialRain = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            for(Card i: gameEnvironment.siegeRow){
                i.power = 1;
            }
            for(Card i: gameEnvironment.enemySiegeRow){
                i.power = 1;
            }
        }

        @Override
        public void execute() {

        }
    };

    static CardAction SkelligeStorm = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            for(Card i: gameEnvironment.rangedRow){
                i.power = 1;
            }
            for(Card i: gameEnvironment.enemyRangedRow){
                i.power = 1;
            }
            for(Card i: gameEnvironment.siegeRow){
                i.power = 1;
            }
            for(Card i: gameEnvironment.enemySiegeRow){
                i.power = 1;
            }
        }

        @Override
        public void execute() {

        }
    };

    static CardAction ClearWeather = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            for(Card i: gameEnvironment.deckUser.getSpecialCards()){
                if(i.type.equals("Weather")){
                    i = null;
                }
            }
            for(Card i: gameEnvironment.deckEnemy.getSpecialCards()){
                if(i.type.equals("Weather")){
                    i = null;
                }
            }
        }

        @Override
        public void execute() {

        }
    };



    static {
        new SpecialCards("Mardoeme" , "Spell" , Mardoeme , "Mardoeme");
        new SpecialCards("Scorch" , "Spell" , Scorch , "Scorch");
        new SpecialCards("Commander’s horn" , "Spell" , Card.CommandersHorn , "CommandersHorn");
        new SpecialCards("Decoy" , "Spell" , Decoy , "Decoy");
        new SpecialCards("Biting Frost" , "Weather", BitingFrost , "BitingFrost");
        new SpecialCards("Impenetrable fog" , "Weather" , ImpenetrableFog , "ImpenetrableFog");
        new SpecialCards("Torrential Rain" , "Weather" , TorrentialRain , "TorrentialRain");
        new SpecialCards("Skellige Storm" , "Weather" , SkelligeStorm , "SkelligeStorm");
        new SpecialCards("Clear Weather" , "Weather" , ClearWeather , "ClearWeather");

    }


    @Override
    public void execute(GameEnvironment gameEnvironment) {

    }

    @Override
    public void execute() {

    }
}

