module Gwent {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.slf4j;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    exports model;
    opens model;
    exports view.menus;
    opens view.menus;
    exports controller.menu.controller;
    opens controller.menu.controller;

    // Export the client package to javafx.graphics
    exports client to javafx.graphics;

}