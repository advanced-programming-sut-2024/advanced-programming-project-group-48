module Gwent {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports view;
    opens view;
    exports model;
    opens model;
    exports view.menus;
    opens view.menus;
    exports model.card;
    opens model.card;
    exports controller.menu.controller;
    opens controller.menu.controller;

}