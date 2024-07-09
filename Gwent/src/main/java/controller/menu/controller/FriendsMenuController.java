package controller.menu.controller;

import client.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import view.menus.FriendsMenu;
import view.menus.MainMenu;

public class FriendsMenuController {
    public TextField Username;
    public TextField AcceptUsername;

    public void showSearchResult(MouseEvent mouseEvent) {
        String username = Username.getText();
        if(username.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("invalid input!");
            alert.setContentText("Please enter a username");
            alert.showAndWait();
            return;
        }
        sendAddFriendSearchReq(username);
        String response = Client.currentClient.receiveResponse();
        if (response.equals("User with this username was not found")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("invalid input!");
            alert.setContentText("User with this username was not found");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("User found!");
        alert.setHeaderText("User Information : 👤");
        alert.setContentText(response);
        ButtonType addFriendButton = new ButtonType("Add Friend");
        alert.getButtonTypes().setAll(ButtonType.CANCEL, addFriendButton);
        java.util.Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == addFriendButton) {
            sendAddFriendRequestReq(username);
            String response2 = Client.currentClient.receiveResponse();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Friend Request");
            alert2.setContentText(response2);
            alert2.showAndWait();
        }
    }

    private void sendAddFriendRequestReq(String username) {
        Client.currentClient.sendMessage("addFriend:" + username);
    }

    private void sendAddFriendSearchReq(String username) {
        Client.currentClient.sendMessage("addFriendSearch:" + username);
    }

    public void showAllFriends(MouseEvent mouseEvent) {
        sendShowAllFriendsReq();
        String response = Client.currentClient.receiveResponse();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Friends List");
        alert.setHeaderText("Your Friends List : 👥");
        alert.setContentText(response);
        alert.showAndWait();
    }

    private void sendShowAllFriendsReq() {
        Client.currentClient.sendMessage("showAllFriends");
    }

    public void showAllFriendRequests(MouseEvent mouseEvent) {
        sendShowAllFriendRequestsReq();
        String response = Client.currentClient.receiveResponse();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Friend Requests");
        alert.setHeaderText("Your Friend Requests : 📩");
        alert.setContentText(response);
        alert.showAndWait();
    }

    private void sendShowAllFriendRequestsReq() {
        Client.currentClient.sendMessage("showAllFriendRequests");
    }

    public void acceptRequest(MouseEvent mouseEvent) {
        String username = AcceptUsername.getText();
        if(username.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("invalid input!");
            alert.setContentText("Please enter a username");
            alert.showAndWait();
            return;
        }
        sendAcceptRequestReq(username);
        String response = Client.currentClient.receiveResponse();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Friend Request");
        alert.setContentText(response);
        alert.showAndWait();
    }

    private void sendAcceptRequestReq(String username) {
        Client.currentClient.sendMessage("acceptFriendRequest:" + username);
    }

    public void BackToMainMenu(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(FriendsMenu.appStage);
        FriendsMenu.appStage=null;
    }

    public void rejectRequest(MouseEvent mouseEvent) {
        String username = AcceptUsername.getText();
        if(username.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("invalid input!");
            alert.setContentText("Please enter a username");
            alert.showAndWait();
            return;
        }
        sendRejectRequestReq(username);
        String response = Client.currentClient.receiveResponse();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Friend Request");
        alert.setContentText(response);
        alert.showAndWait();
    }

    private void sendRejectRequestReq(String username) {
        Client.currentClient.sendMessage("rejectFriendRequest:" + username);
    }
}
