package controller.menu.controller;

import client.Client;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Faction;
import view.menus.ArrangeDeck;
import view.menus.MainMenu;

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
        sendAddCardReq(cardName, cardCount);
        String response = Client.currentClient.receiveResponse();
        ErrorText.setText(response);
    }

    private void sendAddCardReq(String cardName, Integer cardCount) {
        Client.currentClient.sendMessage("addCardToDeck:" + cardName + ":" + cardCount);
    }

    private void removeCardFromDeck(String cardName, Integer cardCount) {
        // Implementation for removing a card from the deck
        // This should include validation for the card count and any specific rules related to removing cards
    }

    public void showDeck(MouseEvent mouseEvent) {
        sendShowDeckReq();
        String response = Client.currentClient.receiveResponse();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deck");
        alert.setHeaderText("Your Deck");
        alert.setContentText(response);
        alert.showAndWait();
    }

    private void sendShowDeckReq() {
        Client.currentClient.sendMessage("showDeck");
    }

    public void changeToMainMenu(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(ArrangeDeck.appStage);
        ArrangeDeck.appStage = null;
    }

    public void saveLeaderFaction(MouseEvent mouseEvent) {
        String faction = choiceFaction.getValue();
        String leader = chooseLeader.getValue();
        if(faction==null||leader==null||faction.isEmpty() || leader.isEmpty()){
            ErrorText.setText("Please select a faction and a leader");
            return;
        }
        sendSaveLeaderFactionReq(faction, leader);
        String response = Client.currentClient.receiveResponse();
        ErrorText.setText(response);
    }

    private void sendSaveLeaderFactionReq(String faction, String leader) {
        Client.currentClient.sendMessage("saveLeaderFaction:" + faction + ":" + leader);
    }

}