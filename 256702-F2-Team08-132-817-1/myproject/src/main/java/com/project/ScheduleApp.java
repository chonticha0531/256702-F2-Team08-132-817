package com.project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.application.Platform;

public class ScheduleApp extends Application {
    private ListView<String> scheduleList;
    private Map<String, LocalDateTime> taskTimes = new HashMap<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

        scheduleList = new ListView<>();

        Button editButton = new Button("แก้ไขกิจกรรม");
        Button deleteButton = new Button("ลบกิจกรรม");
        HBox bottomButtonBox = new HBox(10, editButton, deleteButton);
        bottomButtonBox.setAlignment(Pos.CENTER);
        bottomButtonBox.setPadding(new Insets(10));

        addButton.setOnAction(e -> addTask(taskInput, datePicker, timeInput));
        editButton.setOnAction(e -> editTask());
        deleteButton.setOnAction(e -> deleteTask());

        VBox layout = new VBox(10, titleLabel, topRightBox, taskInput, datePicker, timeInput, scheduleList, bottomButtonBox);
        layout.setPadding(new Insets(20));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> checkNotifications()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addTask(TextField taskInput, DatePicker datePicker, TextField timeInput) {
        String task = taskInput.getText();
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
        String time = timeInput.getText();

        if (!task.isEmpty() && !date.isEmpty() && time.matches("\\d{2}:\\d{2}")) {
            String taskDetails = task + " - " + date + " " + time;
            scheduleList.getItems().add(taskDetails);
            taskTimes.put(taskDetails, LocalDateTime.parse(date + " " + time, formatter));

            taskInput.clear();
            timeInput.clear();
        }
    }

    private void editTask() {
        String selectedTask = scheduleList.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            TextInputDialog dialog = new TextInputDialog(selectedTask.split(" - ")[0]);
            dialog.setTitle("แก้ไขกิจกรรม");
            dialog.setHeaderText("แก้ไขชื่อกิจกรรม");
            dialog.setContentText("ชื่อกิจกรรมใหม่:");

            dialog.showAndWait().ifPresent(newTask -> {
                if (!newTask.isEmpty()) {
                    String oldTask = selectedTask;
                    String newTaskDetails = newTask + " - " + selectedTask.split(" - ")[1];

                    scheduleList.getItems().set(scheduleList.getSelectionModel().getSelectedIndex(), newTaskDetails);
                    LocalDateTime oldTime = taskTimes.remove(oldTask);
                    taskTimes.put(newTaskDetails, oldTime);
                }
            });
        }
    }

    private void deleteTask() {
        String selectedTask = scheduleList.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            scheduleList.getItems().remove(selectedTask);
            taskTimes.remove(selectedTask);
        }
    }

    private void checkNotifications() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("🔍 ตรวจสอบกิจกรรม เวลา: " + now); // เช็คว่าฟังก์ชันทำงาน
    
        Iterator<Map.Entry<String, LocalDateTime>> iterator = taskTimes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LocalDateTime> entry = iterator.next();
            System.out.println("⏰ เช็คกิจกรรม: " + entry.getKey() + " -> " + entry.getValue()); // Debug ข้อมูลกิจกรรม
    
            if (entry.getValue().isBefore(now) || entry.getValue().isEqual(now)) {
                System.out.println("🚨 แจ้งเตือน: " + entry.getKey()); // เช็คว่าถึงเวลาจริง
                showNotification(entry.getKey()); // เรียกแจ้งเตือน
                iterator.remove();
                scheduleList.getItems().remove(entry.getKey());
                break;
            }
        }
    }
    
private void showNotification(String task) {
    Platform.runLater(() -> {
        System.out.println("📢 แสดงป๊อปอัพ: " + task); // Debug

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("แจ้งเตือนกิจกรรม");
        alert.setHeaderText("เตือนความจำ");
        alert.setContentText("ถึงเวลา: " + task);
        alert.showAndWait();
    });
}
    

    public static void main(String[] args) {
        launch(args);
    }
}
