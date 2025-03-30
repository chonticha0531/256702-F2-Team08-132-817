module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.project to javafx.fxml;
    exports com.project;
}