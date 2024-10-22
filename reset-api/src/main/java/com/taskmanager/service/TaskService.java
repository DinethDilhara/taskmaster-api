package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private TaskRepository taskRepository = new TaskRepository();

    public List<Task> getAllTasks() throws IOException, ClassNotFoundException {
        return taskRepository.loadTasks();
    }

    public Task getTaskById(String id) throws IOException, ClassNotFoundException {
        return taskRepository.findTaskById(id);
    }

    public void createTask(Task task) throws IOException, ClassNotFoundException {
        taskRepository.saveTask(task);
    }

    public void deleteTask(String id) throws IOException, ClassNotFoundException {
        taskRepository.deleteTask(id);
    }

    private static final String FILE_NAME = "tasks.dat";

    public void saveTasks(List<Task> tasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                tasks = (List<Task>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    public void addTask(Task task) {
        List<Task> tasks = loadTasks();
        tasks.add(task);
        saveTasks(tasks);
    }

    public boolean deleteTaskById(String id) {
        List<Task> tasks = loadTasks();
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));
        if (removed) {
            saveTasks(tasks);
        }
        return removed;
    }

    public boolean updateTask(String id, Task updatedTask) {
        List<Task> tasks = loadTasks();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getId().equals(id)) {
                task.setTitle(updatedTask.getTitle());
                task.setDescription(updatedTask.getDescription());
                task.setPriority(updatedTask.getPriority());
                task.setStatus(updatedTask.getStatus());
                task.setDueDate(updatedTask.getDueDate());
                saveTasks(tasks);
                return true;
            }
        }
        return false;
    }


}
