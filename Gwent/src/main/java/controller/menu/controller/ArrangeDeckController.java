package controller.menu.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Faction;
import model.User;
import view.menus.ArrangeDeck;
import view.menus.PreGameMenu;

public class ArrangeDeckController {
    public Label ErrorText;
    @FXML
    private ChoiceBox<String> choiceFaction;
    @FXML
    private ChoiceBox<String> chooseLeader;
    @FXML
    private ChoiceBox<String> operation;
    @FXML
    private ChoiceBox<String> nameOfCard;
    @FXML
    private ChoiceBox<Integer> numberOfCard;

    public void initialize() {
        initializeFactionChoiceBox();
        initializeOperationChoiceBox();
        initializeNumberOfCardChoiceBox();
    }

    private void initializeFactionChoiceBox() {
        choiceFaction.setItems(FXCollections.observableArrayList(Faction.getFactionNames()));
        choiceFaction.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateLeaderAndCardChoices(newValue));
    }

    private void updateLeaderAndCardChoices(String faction) {
        chooseLeader.setItems(FXCollections.observableArrayList(Faction.getLeadersByFaction(faction)));
        nameOfCard.setItems(FXCollections.observableArrayList(Faction.getCardsByFaction(faction)));
    }

    private void initializeOperationChoiceBox() {
        operation.setItems(FXCollections.observableArrayList("Add", "Remove"));
    }

    private void initializeNumberOfCardChoiceBox() {
        numberOfCard.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
    }

    public void submitOperation() {
        String selectedOperation = operation.getValue();
        String cardName = nameOfCard.getValue();
        Integer cardCount = numberOfCard.getValue();

        if ("Add".equals(selectedOperation)) {
            addCardToDeck(cardName, cardCount);
        } else if ("Remove".equals(selectedOperation)) {
            removeCardFromDeck(cardName, cardCount);
        }
    }

    private void addCardToDeck(String cardName, Integer cardCount) {
        if (User.loggedInUser == null) {
            System.err.println("You are not logged in");
            return;
        }
        if (User.loggedInUser.getDeck().addCard(cardName, cardCount))
            ErrorText.setText("Card added to deck successfully");

        else
            ErrorText.setText("Card could not be added to deck");

    }

    private void removeCardFromDeck(String cardName, Integer cardCount) {
        if (User.loggedInUser == null) {
            System.err.println("You are not logged in");
            return;
        }
        if (User.loggedInUser.getDeck().removeCard(cardName, cardCount))
            ErrorText.setText("Card removed from deck successfully");

        else
            ErrorText.setText("Card could not be removed from deck");

    }


    public void showDeck(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deck");
        alert.setHeaderText("Your Deck");
        alert.setContentText(User.loggedInUser.getDeck().showDeck());
        alert.showAndWait();
    }


    public void changeToPreGameMenu(MouseEvent mouseEvent) throws Exception {
        new PreGameMenu().start(ArrangeDeck.appStage);
        ArrangeDeck.appStage = null;
    }

    public void saveLeaderFaction(MouseEvent mouseEvent) {
        String faction = choiceFaction.getValue();
        String leader = chooseLeader.getValue();
        if(faction==null||leader==null||faction.isEmpty() || leader.isEmpty()){
            ErrorText.setText("Please select a faction and a leader");
            return;
        }
        if (User.loggedInUser == null) {
            System.err.println("You are not logged in");
            return;
        }
        User.loggedInUser.getDeck().setFaction(faction);
        User.loggedInUser.getDeck().setCommander(leader);

        ErrorText.setText("Leader and faction saved successfully");
    }

}


