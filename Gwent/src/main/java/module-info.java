module Gwent {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    exports model;
    opens model;
    exports view.menus;
    opens view.menus;
    exports controller.menu.controller;
    opens controller.menu.controller;

    // Export the client package to javafx.graphics
    exports client to javafx.graphics;

}