package controller.menu.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Card;
import model.User;
import model.Faction;
import view.menus.PreGameMenu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ArrangeDeckController {
@FXML
private ChoiceBox<String> choice_faction = new ChoiceBox<>();

@FXML
public ChoiceBox<String> chooseLeader = new ChoiceBox<>();
@FXML
public ChoiceBox<String> operation = new ChoiceBox<>();
@FXML
public ChoiceBox<String> nameOfCard = new ChoiceBox<>();
@FXML
public ChoiceBox<Integer> numberOfCard = new ChoiceBox<>();
    public void initialize() {
        if (choice_faction.getItems().isEmpty()) {
            choice_faction.getItems().addAll(new ArrayList<>(Faction.getFaction().keySet()));
            choice_faction.setValue("Monsters");
        }

        choice_faction.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue)-> {
            User.loggedInUser.getDeck().setFaction(newValue);
            if("Northen Realms".equals(newValue)){
                chooseLeader.getItems().setAll(Faction.getCommandersOfNorthenRealms());
            }
            else if("Monsters".equals(newValue)){
                chooseLeader.getItems().setAll(Faction.getCommandersOfMonsters());
            }
            else if("Nilfgaardian Empire".equals(newValue)){
                chooseLeader.getItems().setAll(Faction.getCommandersOfNilfgaardianEmpire());
            }
            else if("Scoia’taell".equals(newValue)){
                chooseLeader.getItems().setAll(Faction.getCommandersOfScoiaTaell());
            }
            else if("Skellige".equals(newValue)){
                chooseLeader.getItems().setAll(Faction.getCommandersOfSkellige());
            }
        });

        chooseLeader.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue)-> {
            User.loggedInUser.getDeck().setCommander(newValue);
        });

        if (operation.getItems().isEmpty()) {
            operation.getItems().addAll("add", "remove");
        }
        choice_faction.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue)-> {
            User.loggedInUser.getDeck().setFaction(newValue);
            ArrayList<String> elemans = new ArrayList<>();
            for(Card i: Card.allCards){
                if(i.faction.equals(newValue)){
                    elemans.add(i.name);
                }
            }
            if(elemans.isEmpty()){
                if(newValue .equals("Scoia’taell")){
                    for(Card i: Card.allCards){
                        if(i.faction.equals("Scoia'tael")){
                            elemans.add(i.name);
                        }
                    }
                }
            }
            if(elemans.isEmpty()){
                if(newValue .equals("Northen Realms")){
                    for(Card i: Card.allCards){
                        if(i.faction.equals("Northern Realms")){
                            elemans.add(i.name);
                        }
                    }
                }
            }
            nameOfCard.getItems().setAll(elemans);
        });


        if (numberOfCard.getItems().isEmpty()) {
            numberOfCard.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
        }
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

    public void chooseFaction() {
        String selectedOption = choice_faction.getValue();
        if(User.loggedInUser.getDeck() != null){
            User.loggedInUser.getDeck().setFaction(selectedOption);
        }
        chooseLeader.getItems().addAll(Faction.getFaction().get(User.loggedInUser.getDeck().getFaction()));
    }
//    public void addOrRemoveCard(){
//        String selectedOperation = operation.getValue();
//        int selectedNumber = numberOfCard.getValue();
//        String selectedCard = nameOfCard.getValue();
//        if(selectedOperation.equals("add")){
//            if(selectedNumber > Card.getCardByName(selectedCard).maxNumber-User.loggedInUser.getDeck().getNumberOfCardsInDeckp().get(selectedCard)){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("error!");
//                alert.setContentText("invalid number");
//                alert.showAndWait();
//            }
//            //todo ---> if special cards > 10  ---> error
//            else {
//                Card newCard = Card.getCardByName(selectedCard).clone();
//                User.loggedInUser.getDeck().getAllCards().add(newCard);
//                User.loggedInUser.getDeck().getNumberOfCardsInDeckp().put(newCard.name , User.loggedInUser.getDeck().getNumberOfCardsInDeckp().get(selectedCard)+1);
//            }
//        }
//        else if(selectedOperation.equals("remove")){
//            if(!User.loggedInUser.getDeck().getAllCards().contains(Card.getCardByName(selectedCard))){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("remove from deck");
//                alert.setContentText("you have not this card in your deck!!!🙅‍♀️🙅‍♀️");
//                alert.showAndWait();
//            }
//            else if(selectedNumber > Card.getCardByName(selectedCard).maxNumber - User.loggedInUser.getDeck().getNumberOfCardsInDeckp().get(selectedCard)){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("remove from deck");
//                alert.setContentText("you do not have "+ selectedNumber + "🔢 of this type of card.");
//                alert.showAndWait();
//            }
//            else{
//                User.loggedInUser.getDeck().getAllCards().remove(Card.getCardByName(selectedCard));
//                User.loggedInUser.getDeck().getNumberOfCardsInDeckp().put(selectedCard , User.loggedInUser.getDeck().getNumberOfCardsInDeckp().get(selectedCard)-1);
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("remove card");
//                alert.setContentText("card🎴 removed of your deck successfully");
//                alert.showAndWait();
//            }
//        }
//
//    }
    public Optional<ButtonType> showAlert(Alert.AlertType type , String title , String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);

        // Create a TextArea for the content
        TextArea area = new TextArea(content);
        area.setWrapText(true);
        area.setEditable(false);

        // Set the TextArea as the content of the DialogPane
        alert.getDialogPane().setContent(area);
        alert.setResizable(true);

        return alert.showAndWait();
    }
    public void showCards() {
        showAlert(Alert.AlertType.INFORMATION , "cards info" , cardsOfThisFactionInfo());
    }
    public String cardsOfThisFactionInfo() {
        choice_faction.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue)-> {
            User.loggedInUser.getDeck().setFaction(newValue);
            });
        StringBuilder cardsInfo = new StringBuilder();
        for(Card i: Card.allCards){
            if(Card.getFaction(i).equals(User.loggedInUser.getDeck().getFaction())){
                cardsInfo.append("name: ").append(i.name).append("\t").append("is hero: ")
                        .append(i.isHero).append("\t").append("type: ").append(i.type).append("\t")
                        .append("ability: ").append(i.ability).append("\t").append("number of card: ")
                        .append(i.maxNumber).append("\t").append("number of this card in deck: ")
                        .append(0).append("\t").append("power: ").append(i.power).append("\n");
            }

        }
        return cardsInfo.toString();
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
            cardsInDeck.append(User.loggedInUser.getDeck().getAllCards().get(i).name).append("\n");
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
            userData.append("name of card: ").append(User.loggedInUser.getDeck().getAllCards().get(i).name).append("\t")
                    .append("power of card: ").append(User.loggedInUser.getDeck().getAllCards().get(i).power).append("\n");
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

    public String ListOfLeadersString() {
        choice_faction.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue) -> {
            User.loggedInUser.getDeck().setFaction(newValue);
        });
        StringBuilder leaders = new StringBuilder();
            for(String i: Faction.getFaction().get(User.loggedInUser.getDeck().getFaction())){
                leaders.append(i).append("\n");
        }
        return leaders.toString();
    }

    public void chooseLeader(){
        String selectedOption = chooseLeader.getValue();
        if(User.loggedInUser.getDeck() != null){
            User.loggedInUser.getDeck().setCommander(selectedOption);
        }
    }

    public void changeTurn(MouseEvent mouseEvent) {
        //todo  ---->  controll this part from game menu
    }

    public void StartGame(MouseEvent mouseEvent) {
        //todo   ----->  check the special and ... cards
    }

    public void submitOperation(MouseEvent mouseEvent) {
        AtomicReference<String> chOperation = new AtomicReference<>();
        AtomicInteger number = new AtomicInteger();
        AtomicReference<String> cardName = new AtomicReference<>();
        String selectedCard = nameOfCard.getValue();
        String selectedOperation = operation.getValue();
        int selectedNumber = numberOfCard.getValue();

        if (selectedOperation!= null && selectedOperation.equals("add")) {
            if (User.loggedInUser.getDeck().getAllCards().contains(Card.getCardByName(selectedCard))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("Card already exists in your deck.");
                alert.showAndWait();
            }
            else if(selectedNumber > Card.getCardByName(selectedCard).maxNumber ||
                    Card.getCardByName(selectedCard).maxNumber - User.loggedInUser.getDeck().getNumberOfCardsInDeckp().get(selectedCard) < selectedNumber){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("invalid number!");
                alert.showAndWait();
            }
            else {
                for(int i = 0; i< selectedNumber; i++){
                    Card newCard = Card.getCardByName(selectedCard).clone();
                    User.loggedInUser.getDeck().getAllCards().add(newCard);
                    User.loggedInUser.getDeck().getNumberOfCardsInDeckp().put(newCard.name, User.loggedInUser.getDeck().getNumberOfCardsInDeckp().get(selectedCard) + 1);
                }

            }
        } else if (selectedOperation.equals("remove")) {
            boolean isThereCard = false;
            for(Card i: User.loggedInUser.getDeck().getAllCards()){
                if(i.name .equals(selectedCard)){
                    isThereCard = true;
                }
            }
            if (!isThereCard) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Remove from deck");
                alert.setContentText("You don't have this card in your deck.");
                alert.showAndWait();
            } else if (selectedNumber > User.loggedInUser.getDeck().getNumberOfCardsInDeckp().get(selectedCard)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Remove from deck");
                alert.setContentText("You don't have " + selectedNumber + " of this type of card.");
                alert.showAndWait();
            } else {
                for(Card i: User.loggedInUser.getDeck().getAllCards()){
                    if(i.name.equals(selectedCard)){
                        User.loggedInUser.getDeck().getAllCards().remove(i);
                        break;
                    }
                }
//                User.loggedInUser.getDeck().getAllCards().remove(Card.getCardByName(selectedCard));
                User.loggedInUser.getDeck().getNumberOfCardsInDeckp().put(selectedCard, User.loggedInUser.getDeck().getNumberOfCardsInDeckp().get(selectedCard) - 1);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove card");
                alert.setContentText("Card removed from your deck successfully.");
                alert.showAndWait();
            }
        }
    }

    public void goToPregameMenu(MouseEvent mouseEvent) throws Exception {
        view.menus.ArrangeDeck.appStage.close();
        new PreGameMenu().start(new Stage());
    }
}


