package com.project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class Task {
    private ListView<String> scheduleList;
    private Map<String, LocalDateTime> taskTimes;
    private DateTimeFormatter formatter;

    public Task() {
        scheduleList = new ListView<>();
        taskTimes = new HashMap<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public void addTask(TextField taskInput, DatePicker datePicker, TextField timeInput) {
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

    public void editTask() {
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

    public void deleteTask() {
        String selectedTask = scheduleList.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            scheduleList.getItems().remove(selectedTask);
            taskTimes.remove(selectedTask);
        }
    }

    public ListView<String> getScheduleList() {
        return scheduleList;
    }

    public Map<String, LocalDateTime> getTaskTimes() {
        return taskTimes;
    }
}
