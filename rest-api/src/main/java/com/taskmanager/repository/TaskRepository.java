package com.taskmanager.repository;

import com.taskmanager.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static final String FILE_PATH = "tasks.dat";

    public void saveTasks(List<Task> tasks) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(tasks);
        }
    }

    public List<Task> loadTasks() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Task>) ois.readObject();
        }
    }

    public void saveTask(Task task) throws IOException, ClassNotFoundException {
        List<Task> tasks = loadTasks();
        tasks.add(task);
        saveTasks(tasks);
    }

    public Task findTaskById(String id) throws IOException, ClassNotFoundException {
        List<Task> tasks = loadTasks();
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public void deleteTask(String id) throws IOException, ClassNotFoundException {
        List<Task> tasks = loadTasks();
        tasks.removeIf(task -> task.getId().equals(id));
        saveTasks(tasks);
    }
}
