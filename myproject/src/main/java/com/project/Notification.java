package com.project;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Notification {
    private Task task;

    public Notification(Task task) {
        this.task = task;
    }

    public void checkNotifications() {
        LocalDateTime now = LocalDateTime.now();

        Iterator<Map.Entry<String, LocalDateTime>> iterator = task.getTaskTimes().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LocalDateTime> entry = iterator.next();

            if (entry.getValue().isBefore(now) || entry.getValue().isEqual(now)) {

                showNotification(entry.getKey()); 
                iterator.remove();
                task.getScheduleList().getItems().remove(entry.getKey());
                break;
            }
        }
    }

    public void showNotification(String task) {
        Platform.runLater(() -> {


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("แจ้งเตือนกิจกรรม");
            alert.setHeaderText("เตือนความจำ");
            alert.setContentText("ถึงเวลา: " + task);
            alert.showAndWait();
        });
    }
}
