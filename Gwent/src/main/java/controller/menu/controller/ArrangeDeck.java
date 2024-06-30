package controller.menu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import model.Card;
import model.User;
import model.card.Faction;

import java.util.ArrayList;

public class ArrangeDeck {
@FXML
private ChoiceBox<String> choice_faction = new ChoiceBox<>();

@FXML
private ChoiceBox<String> chooseLeader = new ChoiceBox<>();
    public void initialize() {
        ArrayList<String> a = new ArrayList<>(Faction.getFaction().keySet());
            choice_faction.getItems().addAll(a);
            chooseLeader.getItems().addAll(Faction.getFaction().get(User.loggedInUser.getDeck().getFaction()));
    }

    public void showFactions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Factions");
        StringBuilder factions = new StringBuilder();
        if(Faction.getFaction() != null) {
            for(String i: Faction.getFaction().keySet()){
                factions.append(i).append("\n");
            }
        }

        alert.setContentText(factions.toString());
        alert.showAndWait();
    }

    public void choiseBox() {
        String selectedOption = choice_faction.getValue();
        if(User.loggedInUser.getDeck() != null){
            User.loggedInUser.getDeck().setFaction(selectedOption);
        }
        chooseLeader.getItems().addAll(Faction.getFaction().get(User.loggedInUser.getDeck().getFaction()));
    }

    public void showCards() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("cards info:");
        alert.setContentText(cardsOfThisFactionInfo());
        alert.showAndWait();
    }
    private static String cardsOfThisFactionInfo() {
        StringBuilder cardsInfo = new StringBuilder();
        for(Card i: Card.allCards){
            if(i.faction.equals(User.loggedInUser.getDeck().getFaction())){
                cardsInfo.append("name: ").append(i.name).append("\t").append("is hero: ")
                        .append(i.isHero).append("\t").append("type: ").append(i.type).append("\t")
                        .append("ability: ").append(i.ability).append("\t").append("number of card: ")
                        .append(i.maxNumber).append("\t").append("number of this card in deck: ")
                        .append(0).append("\t").append("power: ").append(i.power).append("\n");
            }
            return cardsInfo.toString();
        }
        return null;
    }

    public void showDeck() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("cards in deck");
        alert.setContentText(cardsInDeckList());
        alert.showAndWait();
    }

    private static String cardsInDeckList() {
        StringBuilder cardsInDeck = new StringBuilder();
        for(int i = 0; i < User.loggedInUser.getDeck().getAllCards().size(); i++){
            cardsInDeck.append(User.loggedInUser.getDeck().getAllCards().get(i).getName()).append("\n");
        }
        return cardsInDeck.toString();
    }

    public void showUserData() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("user infoℹ️ℹ️: ");
        alert.setContentText(UserInfo());
        alert.showAndWait();
    }

    private static String UserInfo() {
        StringBuilder userData = new StringBuilder();
        userData.append("name: ").append(User.loggedInUser.getUsername()).append("\t")
                .append("faction: ").append(User.loggedInUser.getDeck().getFaction()).append("\t")
                .append("num of cards: ").append(User.loggedInUser.getDeck().getAllCards().size()).append("\t").append("\n");
        for(int i = 0; i < User.loggedInUser.getDeck().getAllCards().size(); i++){
            userData.append("name of card: ").append(User.loggedInUser.getDeck().getAllCards().get(i).getName()).append("\t")
                    .append("power of card: ").append(User.loggedInUser.getDeck().getAllCards().get(i).getPower()).append("\n");
        }
        //todo ---> how to count number of special cards , heros and ... in deck???
        return userData.toString();
    }

    public void showLeaders() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("show leaders");
        alert.setContentText(ListOfLeadersString());
        alert.showAndWait();
    }

    private static String ListOfLeadersString() {
        StringBuilder leaders = new StringBuilder();
        if(User.loggedInUser.getDeck().getFaction() != null){
            for(String i: Faction.getFaction().get(User.loggedInUser.getDeck().getFaction())){
                leaders.append(i).append("\n");
            }
        }
        return leaders.toString();
    }

    public void chooseLeader(){
        String selectedOption = chooseLeader.getValue();
        if(User.loggedInUser.getDeck() != null){
            User.loggedInUser.getDeck().setCommander(selectedOption);
        }
    }
}


