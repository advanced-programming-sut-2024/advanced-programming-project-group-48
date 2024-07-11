package controller.menu.controller;

import org.junit.jupiter.api.Test;

public class ForgetPasswordControllerTest {
    @Test
    public void generateRandomPassword(){
        ForgetPasswordController forgetPasswordController = new ForgetPasswordController();
        forgetPasswordController.generateRandomPassword();

    }
}
