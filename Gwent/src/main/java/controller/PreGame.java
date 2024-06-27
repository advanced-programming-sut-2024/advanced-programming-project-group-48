package controller;
import model.Card;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

public class PreGame {
    private void createGame(Matcher matcher){
        //todo
    }
    private String showFaction(){
        return User.loggedInUser.getDeck().getFaction();
    }
    private String selectFaction(Matcher matcher){
        String factionName = matcher.group("factionName");
        User.loggedInUser.getDeck().setFaction(factionName);
        String commanderName = matcher.group("commanderName");
        User.loggedInUser.getDeck().setCommander(commanderName);
        return null;
    }

    private String showCards(){
        //todo
        return null;
    }
    private ArrayList<Card> showDeck(){
        return User.loggedInUser.getDeck().getAllCards();
    }
    public static String showInformationCurrentUser(){
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("username: ").append(User.loggedInUser.getUsername()).append("\n");
        userInfo.append("nickname: ").append(User.loggedInUser.getNickname()).append("\n");
        userInfo.append("email: ").append(User.loggedInUser.getEmail()).append("\n");
        return userInfo.toString();
    }
    private String showLeader(){
        return User.loggedInUser.getDeck().getCommander();
    }
    private void saveDeck(){
        try(FileWriter writer = new FileWriter("Deck.txt")) {
            for(Card card: User.loggedInUser.getDeck().getAllCards()){
                writer.write(card.getName() + System.lineSeparator());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadDeck(){
        try(BufferedReader br = new BufferedReader(new FileReader("output.txt"))){
            String line;
            while((line = br.readLine())!=null) {
                //todo
                //User.loggedInUser.getDeckCards().setCards(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void addToDeck(Matcher matcher){
        String cardName = matcher.group("cardName");
        //todo
    }
    private void deleteFromDeck(Matcher matcher){
        String cardName = matcher.group("cardName");
        for(Card i: User.loggedInUser.getDeck().getAllCards()){
            if(i.getName().equals(cardName)){
                User.loggedInUser.getDeck().getAllCards().remove(i);
                break;
            }
        }
    }

}
