package controller.menu.controller;

import client.Client;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.menus.ForgetPasswordMenu;
import view.menus.RegisterMenu;


public class ForgetPasswordController {
    public TextField AnswerOfTheQuestion;
    public TextField NumberOfQuestion;
    public TextField NewPassword;
    public Label ErrorText;
    public TextField Username;

    public void generateRandomPassword() {
        String password=PasswordGenerator.generateRandomPassword(12);
        NewPassword.setText(password);
    }

    public void changePassword() throws Exception {
        if (Username.getText().isEmpty()||AnswerOfTheQuestion.getText().isEmpty() || NumberOfQuestion.getText().isEmpty() || NewPassword.getText().isEmpty()) {
            ErrorText.setText("Please fill all the fields");
            return;
        }
        if(!NumberOfQuestion.getText().matches("[123]")){
            ErrorText.setText("Please enter a valid number for question number");
            return;
        }
        int numberOfQuestion = Integer.parseInt(NumberOfQuestion.getText());
        sendChangePasswordReq(Username.getText(), AnswerOfTheQuestion.getText(), numberOfQuestion, NewPassword.getText());
        String response = Client.currentClient.receiveResponse();
        if(response.equals("Password changed successfully")) {
            new RegisterMenu().start(ForgetPasswordMenu.appStage);
        } else {
            ErrorText.setText(response);
        }
    }

    private void sendChangePasswordReq(String username, String answerOfTheQuestion, int numberOfQuestion, String newPassword) {
        Client.currentClient.sendMessage("changePassword:" + username + ":" + answerOfTheQuestion + ":" + numberOfQuestion + ":" + newPassword);
    }
}
