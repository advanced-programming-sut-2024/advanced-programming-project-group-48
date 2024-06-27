module Gwent {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports view;
    exports controller to javafx.fxml;
    opens controller to javafx.fxml;
    opens view;
    exports model;
    opens model;
    exports view.menus;
    opens view.menus;
    exports controller.menu.controller to javafx.fxml;
    opens controller.menu.controller to javafx.fxml;
    exports model.card;
    opens model.card;

}