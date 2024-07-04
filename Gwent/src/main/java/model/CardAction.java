package model;

import controller.menu.controller.GameEnvironmentController;

// Define the interface for the method
public interface CardAction {
    void execute(GameEnvironmentController.GameEnvironment gameEnvironment);

    void execute();
}
