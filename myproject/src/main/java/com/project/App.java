package com.project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    Task task = new Task();
    Notification notification = new Notification(task);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("แอปตารางเวลา");

        Label titleLabel = new Label("ตารางเวลาของฉัน");

        TextField taskInput = new TextField();
        taskInput.setPromptText("ป้อนกิจกรรม...");
        DatePicker datePicker = new DatePicker();
        TextField timeInput = new TextField();
        timeInput.setPromptText("ป้อนเวลา (HH:mm)");

        Button addButton = new Button("เพิ่มกิจกรรม");
        HBox topRightBox = new HBox(addButton);
        topRightBox.setAlignment(Pos.TOP_RIGHT);


        Button editButton = new Button("แก้ไขกิจกรรม");
        Button deleteButton = new Button("ลบกิจกรรม");
        HBox bottomButtonBox = new HBox(10, editButton, deleteButton);
        bottomButtonBox.setAlignment(Pos.CENTER);
        bottomButtonBox.setPadding(new Insets(10));

        addButton.setOnAction(e -> task.addTask(taskInput, datePicker, timeInput));
        editButton.setOnAction(e -> task.editTask());
        deleteButton.setOnAction(e -> task.deleteTask());

        VBox layout = new VBox(10, titleLabel, topRightBox, taskInput, datePicker, timeInput, task.getScheduleList(),
                bottomButtonBox);
        layout.setPadding(new Insets(20));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> notification.checkNotifications()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
