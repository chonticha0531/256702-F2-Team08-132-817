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
        // System.out.println("🔍 ตรวจสอบกิจกรรม เวลา: " + now); // เช็คว่าฟังก์ชันทำงาน

        Iterator<Map.Entry<String, LocalDateTime>> iterator = task.getTaskTimes().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LocalDateTime> entry = iterator.next();
            // System.out.println("⏰ เช็คกิจกรรม: " + entry.getKey() + " -> " +
            // entry.getValue()); // Debug ข้อมูลกิจกรรม

            if (entry.getValue().isBefore(now) || entry.getValue().isEqual(now)) {
                // System.out.println("🚨 แจ้งเตือน: " + e ntry.getKey()); // เช็คว่าถึงเวลาจริง
                showNotification(entry.getKey()); // เรียกแจ้งเตือน
                iterator.remove();
                task.getScheduleList().getItems().remove(entry.getKey());
                break;
            }
        }
    }

    public void showNotification(String task) {
        Platform.runLater(() -> {
            // System.out.println("📢 แสดงป๊อปอัพ: " + task); // Debug

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("แจ้งเตือนกิจกรรม");
            alert.setHeaderText("เตือนความจำ");
            alert.setContentText("ถึงเวลา: " + task);
            alert.showAndWait();
        });
    }
}
