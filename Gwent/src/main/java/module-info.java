module Gwent {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports view;
    exports controller to javafx.fxml;
    opens controller to javafx.fxml;
    opens view;

}